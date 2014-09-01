package nl.capaxit.jaxrsamf.jaxrs;

import javax.ws.rs.Priorities;

/**
 * Created by jamiecraane on 01/09/14.
 */
public class FilterPriorities {
    public static final int REQUEST_LOGGING = Priorities.AUTHENTICATION + 100;
}
