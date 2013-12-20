package nl.capaxit.jaxrsamf.endpoint;

import nl.capaxit.jaxrsamf.domain.Person;
import nl.capaxit.jaxrsamf.domain.mapper.PersonMapper;
import nl.capaxit.jaxrsamf.validation.ValidationResult;
import nl.capaxit.jaxrsamf.validation.WebApplicationExceptionFactory;
import nl.capaxit.jaxrsamf.validation.person.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Locale;

/**
 * Person controller. Content negotiaton is done using the Accept (what the client wants as response) and
 * the Content-Type (what the server can expect) headers.
 *
 * TODO: Integrate bean validation framework instead of custom validator.
 * TODO: Add error translation for XML.
 *
 * @author Jamie Craane
 */
@Component
@Produces({MediaTypes.APPLICATION_X_AMF, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaTypes.APPLICATION_X_AMF, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("/persons")
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    @Qualifier("inMemoryPersonMapper")
    private PersonMapper personMapper;

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @GET
    public List<Person> retrieve() {
        LOGGER.debug("retrieve()");

        return personMapper.retrievePersons();
    }

    // TODO: Re-add this.
    /*@Produces("application/xml")
    @GET
    public PersonResponse retrieveXml() {
        final List<Person> persons = personMapper.retrievePersons();
        final PersonResponse responseWrapper = new PersonResponse();
        responseWrapper.setPersons(persons);
        return responseWrapper;
    }*/

    /**
     * Retrieves a single person. Throws a WebApplicationException of the person does not exist which translates
     * to a 404 on the client side.
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Person retrieve(@PathParam("id") Long id) {
        LOGGER.debug("retrieve(id)({})", id);

        Person person = personMapper.retrieve(id);

        if (person == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        return person;
    }

    /**
     * We use POST here since we do not know the id of the person in advance (because the id is server-generated).
     * We effectively create a new resource below /persons. This is not idempotent which matches with POST
     * since POST is not idempotent.
     *
     * We return a 201 if the resource is created containing the entity (the person which is created)
     * and set the Location header to point to the newly created person.
     *
     * @param person
     * @return
     */
    @POST
    public Response create(final Person person, @Context final HttpServletResponse response) {
        LOGGER.debug("create(person, response)({})", person);

        validatePerson(person);

        personMapper.create(person);
        UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
        uriBuilder.path(String.valueOf(person.getId()));
        return Response.created(uriBuilder.build()).
                entity(person).
                build();
    }

    private void validatePerson(final Person person) {
        final PersonValidator validator = new PersonValidator();
        final ValidationResult errors = validator.validate(person);
        if (errors.hasErrors()) {
            final WebApplicationException ex = WebApplicationExceptionFactory.forValidationResult(messageSource, new Locale("NL"), errors);
            throw ex;
        }
    }

    /**
     * Updates the person under the given id with the given contents. Throws a 404 if no person
     * exists with the given id.
     * @param person
     */
    @PUT
    @Path("/{id}")
    public void update(final Person person, @PathParam("id") Long id) {
        LOGGER.debug("update(person, id)({},{})", person, id);

        validatePerson(person);

        Person updated = personMapper.update(person, id);
        if (updated == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
    }

    /**
     * Deletes the person under the give id.
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") final Long id) {
        LOGGER.debug("delete(id)({})", id);

        if (personMapper.retrieve(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        personMapper.delete(id);
    }
}
