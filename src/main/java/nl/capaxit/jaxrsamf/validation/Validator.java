package nl.capaxit.jaxrsamf.validation;

/**
 * Validation interface to validate a type T.
 *
 * Created by jcraane on 01/11/13.
 */
public interface Validator<T> {
    /**
     * Validates t.
     * @param t The object to validate.
     * @return ValidationResult holding all validation errors if present.
     */
    ValidationResult validate(T t);
}
