package nl.capaxit.jaxrsamf.providers.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import nl.capaxit.jaxrsamf.domain.conversion.DateFormats;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Serializer for a date object.
 *
 * @author jcraane
 */
public class DateSerializer implements JsonSerializer<Date> {
    @Override
    public JsonElement serialize(final Date src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormats.YEAR_MONTH_DAY.getFormat());
            return new JsonPrimitive(simpleDateFormat.format(src));
        }

        return new JsonPrimitive("");
    }
}
