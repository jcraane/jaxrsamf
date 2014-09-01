package nl.capaxit.jaxrsamf.jaxrs.filter;

import nl.capaxit.jaxrsamf.jaxrs.FilterPriorities;
import nl.capaxit.jaxrsamf.jaxrs.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Filter which logs the X-Request-ID header.
 *
 * Created by jamiecraane on 01/09/14.
 */
@Provider
@Priority(FilterPriorities.REQUEST_LOGGING)
public class RequestIdResponseFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdResponseFilter.class);

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        final String requestId = requestContext.getHeaderString(Headers.REQUEST_ID);
        LOGGER.debug("request-id = [{}]", requestId);
    }
}
