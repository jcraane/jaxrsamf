package nl.capaxit.jaxrsamf.jaxrs.response;

import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamiecraane on 17/08/14.
 */
public class GenericResponse<T> implements Serializable {
    private Link link;
    private T payload;
    private Map<String, Serializable> meta = new HashMap<>(5);

    public GenericResponse(final Link link, final T payload) {
        this.link = link;
        this.payload = payload;
    }

    public GenericResponse(final T payload) {
        this.payload = payload;
    }

    public Map<String, Serializable> getMeta() {
        return meta;
    }

    public void addMeta(final String key, final Serializable value) {
        meta.put(key, value);
    }
}
