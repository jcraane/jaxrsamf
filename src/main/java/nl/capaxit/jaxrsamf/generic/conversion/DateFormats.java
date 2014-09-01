package nl.capaxit.jaxrsamf.generic.conversion;

/**
 * Holds the various date formats.
 *
 * @author jcraane
 */
public enum DateFormats {
    YEAR_MONTH_DAY("YYYY-MM-dd");

    private final String format;

    DateFormats(final String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
