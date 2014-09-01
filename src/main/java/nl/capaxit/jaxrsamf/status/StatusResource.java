package nl.capaxit.jaxrsamf.status;

import com.google.common.base.Strings;
import nl.capaxit.jaxrsamf.generic.ETagMemoryCache;
import nl.capaxit.jaxrsamf.jaxrs.MediaTypes;
import nl.capaxit.jaxrsamf.jaxrs.response.GenericResponse;
import nl.capaxit.jaxrsamf.status.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jamiecraane on 01/09/14.
 */
@Component
@Consumes({MediaType.APPLICATION_JSON, MediaTypes.APPLICATION_X_AMF})
@Produces({"application/json;version=1", MediaTypes.APPLICATION_X_AMF})
@Path("/v1/status")
public class StatusResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusResource.class);

    @GET
    public Response status() {
        LOGGER.debug("status");

        final Status status = new Status();
        String personsETag = ETagMemoryCache.getInstance().getPersonsETag();
        if (Strings.isNullOrEmpty(personsETag)) {
            personsETag = "NotComputed";
        }

        status.addEtag("persons", personsETag);
        return Response.ok().entity(new GenericResponse(status)).build();
    }
}
