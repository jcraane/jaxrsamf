package nl.capaxit.jaxrsamf.endpoint;

import nl.capaxit.jaxrsamf.domain.Person;
import nl.capaxit.jaxrsamf.domain.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Jamie Craane
 */
@Component
@Path("/persons")
public class PersonController {
    @Autowired
    private PersonMapper personMapper;

    // Somehow, the application/x-amf should be declared first.
    @GET
    @Produces({Constants.APPLICATION_X_AMF, MediaType.APPLICATION_JSON})
    @Consumes({Constants.APPLICATION_X_AMF, MediaType.APPLICATION_JSON})
    public List<Person> getPersons() {
        return personMapper.retrievePersons();
    }
}
