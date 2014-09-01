package nl.capaxit.jaxrsamf.status.domain;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamiecraane on 01/09/14.
 */
public class Status implements Serializable {
    private static final long serialVersionUID = 7847992967542748950L;

    private final Map<String, String> ETags;

    private Status(final Map<String, String> ETags) {
        this.ETags = ETags;
    }

    public Map<String, String> getETags() {
        return ETags;
    }

    public static class StatusBuilder {
        private final Map<String, String> ETags = new HashMap<>(15);

        public StatusBuilder eTag(final String name, final String ETag) {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "name is null or empty.");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(ETag), "ETag is null or empty.");

            ETags.put(name, ETag);
            return this;
        }

        public Status build() {
            return new Status(ETags);
        }
    }
}
