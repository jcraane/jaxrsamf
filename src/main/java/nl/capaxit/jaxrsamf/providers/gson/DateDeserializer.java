package nl.capaxit.jaxrsamf.providers.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import nl.capaxit.jaxrsamf.domain.conversion.DateFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Deserializer for date object.
 *
 * @author jcraane
 */
public class DateDeserializer implements JsonDeserializer<Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateDeserializer.class);

    @Override
    public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormats.YEAR_MONTH_DAY.getFormat());
            try {
                return simpleDateFormat.parse(json.getAsString());
            } catch (final ParseException e) {
                LOGGER.info("Invalid date [{}].", json.getAsString());
                return null;
            }
        }

        return null;
    }
}
