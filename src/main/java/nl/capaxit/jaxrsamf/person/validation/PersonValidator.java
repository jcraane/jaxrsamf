package nl.capaxit.jaxrsamf.person.validation;

import com.google.common.base.Strings;
import nl.capaxit.jaxrsamf.person.domain.Person;
import nl.capaxit.jaxrsamf.validation.ValidationError;
import nl.capaxit.jaxrsamf.validation.ValidationResult;
import nl.capaxit.jaxrsamf.validation.Validator;

/**
 * Validator for the person object.
 *
 * @author jcraane
 */
public class PersonValidator implements Validator<Person> {
    @Override
    public ValidationResult validate(final Person person) {
        final ValidationResult validationErrors = new ValidationResult();

        if (person == null) {
            validationErrors.addError(ValidationError.forMessageKey("empty.request"));
            return validationErrors;
        }

        if (Strings.isNullOrEmpty(person.getLastName())) {
            validationErrors.addError(ValidationError.forMessageKey("field.error.required", "lastName"));
        }

        if (Strings.isNullOrEmpty(person.getFirstName())) {
            validationErrors.addError(ValidationError.forMessageKey("field.error.required", "firstName"));
        }

        return validationErrors;
    }
}
