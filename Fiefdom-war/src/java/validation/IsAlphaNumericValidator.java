package validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsAlphaNumericValidator implements ConstraintValidator<IsAlphaNumeric, String> {

	@Override
	public void initialize(IsAlphaNumeric constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Pattern.matches("[a-zA-Z0-9_]+",value);
	}
}
