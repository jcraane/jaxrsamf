package nl.capaxit.jaxrsamf.resources.status;

import nl.capaxit.jaxrsamf.jaxrs.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by jamiecraane on 01/09/14.
 */
@Component
@Consumes({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_X_AMF})
@Produces({"application/json;version=1", MediaTypes.APPLICATION_X_AMF})
@Path("/v1/status")
public class StatusResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);


}
