package nl.capaxit.jaxrsamf.person;

import nl.capaxit.jaxrsamf.generic.ETagMemoryCache;
import nl.capaxit.jaxrsamf.jaxrs.MediaTypes;
import nl.capaxit.jaxrsamf.jaxrs.response.GenericResponse;
import nl.capaxit.jaxrsamf.person.domain.Person;
import nl.capaxit.jaxrsamf.person.mapper.InMemoryPersonMapper;
import nl.capaxit.jaxrsamf.person.mapper.PersonMapper;
import nl.capaxit.jaxrsamf.person.validation.PersonValidator;
import nl.capaxit.jaxrsamf.validation.ValidationResult;
import nl.capaxit.jaxrsamf.validation.WebApplicationExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Locale;

/**
 * Person controller. Content negotiaton is done using the Accept (what the client wants as response) and
 * the Content-Type (what the server can expect) headers.
 *
 * Methods always returns a Response object which gives you more options in determining what to return to the client in
 * terms of headers for example or response codes.
 *
 * Versioning using the URL:
 *
 * <ul>
 *     <li>Version mapping can be done usign jax-rs,</li>
 *     <li>It is clear which version of a resource is requested</li>
 *     <li>For clients it is easier to use URL versioning scheme and they can make no mistakes by omitting for example the version.</li>
 *     <li>Felixibility in versioning individual resources.</li>
 * </ul>
 *
 * Why no version in context-root
 * <ul>
 *     <li>Less flesibility to version individual resources. With every new version all resources have the new version in the context root.</li>
 *     <li>Multiple versions require mutiple context-roots ie. multiple servlets (jax-rs) applications or multiple wars.</li>
 * </ul>
 *
 * Accept-header
 * <ul>
 *     <li>PRO: URL remains the same whatever the version is.</li>
 *     <li>CON: More code to differentiate versions since jax-rs cannot use Accept header version for mapping resources.</li>
 *     <li>PRO: Flexibility for individual resources.</li>
 * </ul>
 *
 * But the policy is: no multiple versions unless...
 *
 * @author Jamie Craane
 */
@Component
@Consumes({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_X_AMF})
@Produces({"application/json;version=1", MediaTypes.APPLICATION_X_AMF})
@Path("/v1/persons")
public class PersonResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    @Qualifier("inMemoryPersonMapper")
    private static PersonMapper personMapper = new InMemoryPersonMapper();

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response retrieve(@Context final HttpServletRequest httpServletRequest, @Context final Request request) {
        LOGGER.debug("retrieve(){}");
        final List<Person> persons = personMapper.retrievePersons();

        final StringBuilder sb = new StringBuilder();
        for (final Person person : persons) {
            person.setDetails(Link.fromUri(UriBuilder.fromResource(PersonResource.class).path(Long.toString(person.getId())).build()).build());
            sb.append(person);
        }

        final String ETag = DigestUtils.md5DigestAsHex(sb.toString().getBytes());
        final EntityTag entityTag = new EntityTag(ETag);
        final Response.ResponseBuilder builder = request.evaluatePreconditions(entityTag);

        if (builder == null) {
            final URI self = UriBuilder.fromResource(PersonResource.class).build();
            final Link link = Link.fromUri(self).build();
            return Response.ok().tag(ETag).entity(new GenericResponse<>(link, persons)).build();
        }

        ETagMemoryCache.getInstance().setPersonsETag(ETag);
        return builder.build();
    }

    /**
     * Retrieves a single person. Throws a WebApplicationException of the person does not exist which translates
     * to a 404 on the client side.
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Response retrievePerson(@PathParam("id") Long id) {
        LOGGER.debug("retrieve(id)({})", id);

        Person person = personMapper.retrieve(id);

        if (person == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        return Response.ok().entity(person).build();
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
    public Response update(final Person person, @PathParam("id") Long id) {
        LOGGER.debug("update(person, id)({},{})", person, id);

        validatePerson(person);

        Person updated = personMapper.update(person, id);
        if (updated == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        return Response.noContent().build();
    }

    /**
     * Deletes the person under the give id.
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final Long id) {
        LOGGER.debug("delete(id)({})", id);

        if (personMapper.retrieve(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        personMapper.delete(id);
        return Response.noContent().build();
    }
}
