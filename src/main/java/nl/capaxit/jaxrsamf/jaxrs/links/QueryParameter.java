package nl.capaxit.jaxrsamf.jaxrs.links;

/**
 * Created by jamiecraane on 18/08/14.
 */
public class QueryParameter {
    private final String name;
    private final String value;

    public QueryParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
