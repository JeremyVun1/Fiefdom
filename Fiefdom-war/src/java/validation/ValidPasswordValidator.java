package validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();

		//check list of different constraints and change constraint message based on which constraint is not met
		return (
			containsUppercase(password, context)
			&& containsLowercase(password, context)
			&& containsNumber(password, context)
		);
	}

	private boolean containsUppercase(String password, ConstraintValidatorContext context) {
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		context.buildConstraintViolationWithTemplate(
				"Password must contain atleast 1 Uppercase letter").addConstraintViolation();
		return false;
	}

	private boolean containsLowercase(String password, ConstraintValidatorContext context) {
		for (char c : password.toCharArray()) {
			if (Character.isLowerCase(c)) {
				return true;
			}
		}
		context.buildConstraintViolationWithTemplate(
				"Password must contain atleast 1 Lowercase letter").addConstraintViolation();
		return false;
	}

	private boolean containsNumber(String password, ConstraintValidatorContext context) {
		for (char c : password.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		context.buildConstraintViolationWithTemplate(
				"Password must contain atleast one number").addConstraintViolation();
		return false;
	}
}
