package nl.capaxit.jaxrsamf.generic;

import com.google.common.base.Strings;

/**
 * Created by jamiecraane on 01/09/14.
 */
public class ETagMemoryCache {
    private static final ETagMemoryCache SINGLETON = new ETagMemoryCache();

    private String personsETag;

    public static ETagMemoryCache getInstance() {
        return SINGLETON;
    }

    public String getPersonsETag() {
        return Strings.isNullOrEmpty(personsETag) ? "NotComputed" : personsETag;
    }

    public void setPersonsETag(final String personsETag) {
        this.personsETag = personsETag;
    }
}
