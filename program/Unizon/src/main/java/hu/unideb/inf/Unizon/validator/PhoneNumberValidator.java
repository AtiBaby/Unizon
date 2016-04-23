package hu.unideb.inf.Unizon.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator()
public class PhoneNumberValidator implements Validator {

	private static Pattern PATTERN = Pattern.compile("[+]??\\d{11}");

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || ((String) value).isEmpty()) {
			return;
		}

		if (((String) value).length() > 12 || !PATTERN.matcher((String) value).matches()) {

			String summary = "Not appropriate phone number format!";
			String detail = "Phone number consists of an optional '+' character and 11 digits.";
			// Possible further fine tuning: get the content of the input field
			// and analyze it (how many extra characters there are, detected
			// alphabetic characters and so on). Build the detailed part of the
			// message based on these information.

			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
		}
	}
}
