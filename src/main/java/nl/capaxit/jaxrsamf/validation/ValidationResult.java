package nl.capaxit.jaxrsamf.validation;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds the ValidationErrors.
 *
 * Created by jcraane on 03/11/13.
 */
public class ValidationResult implements Iterable<ValidationError> {
    public static final int CAPACITY = 10;
    private final List<ValidationError> validationErrors = new ArrayList<ValidationError>(CAPACITY);

    public void addError(final ValidationError validationError) {
        Preconditions.checkNotNull(validationError);
        validationErrors.add(validationError);
    }

    /**
     * Add all errors from give validationResult to this ValidationResult.
     * @param validationResult The errors to add.
     */
    public void addAll(final ValidationResult validationResult) {
        Preconditions.checkNotNull(validationResult, "validationResult is null.");
        validationErrors.addAll(validationResult.validationErrors);
    }

    @Override
    public Iterator<ValidationError> iterator() {
        return validationErrors.iterator();
    }

    /**
     * Returns the first validation error if present, else Optional.absent().
     * @return The first validation error if present, else Optional.absent().
     */
    public Optional<ValidationError> getFirst() {
        if (validationErrors.isEmpty()) {
            return Optional.absent();
        }

        return Optional.of(validationErrors.get(0));
    }

    public int size() {
        return validationErrors.size();
    }

    public boolean hasErrors() {
        return validationErrors.size() > 0;
    }

    public List<ValidationError> getValidationErrors() {
        return Lists.newArrayList(validationErrors);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationResult{");
        sb.append("validationErrors=").append(validationErrors);
        sb.append('}');
        return sb.toString();
    }
}
