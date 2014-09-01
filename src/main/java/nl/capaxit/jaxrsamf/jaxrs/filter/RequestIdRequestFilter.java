package nl.capaxit.jaxrsamf.jaxrs.filter;

import com.google.common.base.Strings;
import nl.capaxit.jaxrsamf.jaxrs.FilterPriorities;
import nl.capaxit.jaxrsamf.jaxrs.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.UUID;

/**
 * Filter which Logs the X-Request-ID header if present, or inserts a UUID id in the X-Request-ID if no value present.
 *
 * Created by jamiecraane on 01/09/14.
 */
@Provider
@Priority(FilterPriorities.REQUEST_LOGGING)
public class RequestIdRequestFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdRequestFilter.class);

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String requestId = requestContext.getHeaderString(Headers.REQUEST_ID);
        if (Strings.isNullOrEmpty(requestId)) {
            requestId = UUID.randomUUID().toString().toLowerCase();
            requestContext.getHeaders().add(Headers.REQUEST_ID, requestId);
        }

        LOGGER.debug("request-id = [{}]", requestId);
    }
}
