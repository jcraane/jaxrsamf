package nl.capaxit.jaxrsamf.validation;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.context.MessageSource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for constructing web application exceptions.
 *
 * @author jcraane
 */
public final class WebApplicationExceptionFactory {
    private WebApplicationExceptionFactory() {
    }

    public static WebApplicationException forValidationResult(final MessageSource messageSource, final Locale locale, final ValidationResult errors) {
        final List<String> resolvedErrorMessages = Lists.transform(errors.getValidationErrors(), new Function<ValidationError, String>() {
            @Override
            public String apply(final ValidationError validationError) {
                return messageSource.getMessage(validationError.getMessageKey(), validationError.getArguments(), locale);
            }
        });

        return new WebApplicationException(Response.status(Response.Status.CONFLICT).entity(resolvedErrorMessages).
            type(MediaType.APPLICATION_JSON_TYPE).build());
    }
}
