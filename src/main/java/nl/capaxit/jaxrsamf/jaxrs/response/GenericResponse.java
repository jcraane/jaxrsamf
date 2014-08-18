package nl.capaxit.jaxrsamf.jaxrs.response;

import javax.ws.rs.core.Link;
import java.io.Serializable;

/**
 * Created by jamiecraane on 17/08/14.
 */
public class GenericResponse<T> implements Serializable {
    private Link link;
    private T payload;

    public GenericResponse(final Link link, final T payload) {
        this.link = link;
        this.payload = payload;
    }

    public GenericResponse(final T payload) {
        this.payload = payload;
    }
}
