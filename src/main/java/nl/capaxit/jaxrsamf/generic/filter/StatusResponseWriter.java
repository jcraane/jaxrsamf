package nl.capaxit.jaxrsamf.generic.filter;

import nl.capaxit.jaxrsamf.generic.ETagMemoryCache;
import nl.capaxit.jaxrsamf.jaxrs.response.GenericResponse;
import nl.capaxit.jaxrsamf.status.domain.Status;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

/**
 * Created by jamiecraane on 01/09/14.
 */
@Provider
public class StatusResponseWriter implements WriterInterceptor {
    @Override
    public void aroundWriteTo(final WriterInterceptorContext context) throws IOException, WebApplicationException {
        final GenericResponse response = (GenericResponse) context.getEntity();
        response.addMeta("resourceStatus", (java.io.Serializable) new Status.StatusBuilder().eTag("persons", ETagMemoryCache.getInstance().getPersonsETag()).build().getEtags());
        context.proceed();
    }
}
