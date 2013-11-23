package nl.capaxit.jaxrsamf.endpoint;

import nl.capaxit.jaxrsamf.domain.Person;
import nl.capaxit.jaxrsamf.domain.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

/**
 * Person controller. Content negotiaton is done using the Accept (what the client wants as response) and
 * the Content-Type (what the server can expect) headers.
 *
 * @author Jamie Craane
 */
@Component
// Somehow, the application/x-amf should be declared first.
@Produces({MediaTypes.APPLICATION_X_AMF, MediaType.APPLICATION_JSON})
@Consumes({MediaTypes.APPLICATION_X_AMF, MediaType.APPLICATION_JSON})
@Path("/persons")
public class PersonController {
    @Autowired
    @Qualifier("inMemoryPersonMapper")
    private PersonMapper personMapper;

    @Context
    private UriInfo uriInfo;

    @GET
    public List<Person> retrieve() {
        return personMapper.retrievePersons();
    }

    /**
     * Retrieves a single person. Throws a WebApplicationException of the person does not exist which translates
     * to a 404 on the client side.
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    public Person retrieve(@PathParam("id") Long id) {
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
        personMapper.create(person);
        UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
        uriBuilder.path(String.valueOf(person.getId()));
        return Response.created(uriBuilder.build()).
                entity(person).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    /**
     * Updates the person under the given id with the given contents. Throws a 404 if no person
     * exists with the given id.
     * @param person
     */
    @PUT
    @Path("/{id}")
    public void update(final Person person, @PathParam("id") Long id) {
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
        if (personMapper.retrieve(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }

        personMapper.delete(id);
    }
}
