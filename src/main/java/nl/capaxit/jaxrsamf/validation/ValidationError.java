package nl.capaxit.jaxrsamf.validation;

/**
 * Holds a single validation error.
 *
 * Created by jcraane on 01/11/13.
 */
public final class ValidationError {
    private ValidationError(final String messageKey) {
        this.messageKey = messageKey;
    }

    private final String messageKey;

    /**
     * Creates a validation error with the resource id of the message to show.
     * @param resourceId The resource id with the message association with this ValidationError.
     * @return ValidationError.
     */
    public static ValidationError forMessageKey(final String resourceId) {
        return new ValidationError(resourceId);
    }

    /**
     * @return The resource id of the message associated with this ValidationError.
     */
    public String getMessageKey() {
        return messageKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationError{");
        sb.append("messageKey=").append(messageKey);
        sb.append('}');
        return sb.toString();
    }
}
