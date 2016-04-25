package hu.unideb.inf.Unizon.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator
public class AlphabeticInputValidator implements Validator {

	// Pattern starting with alphabetic character, continued with alphabetic,
	// space and '-' characters. Used for validating the name of the country and
	// the city.
	private static Pattern PATTERN = Pattern.compile("[\\p{Alpha}][\\p{Alpha}\\p{Space}\\-]*");

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value == null || ((String) value).isEmpty()) {
			return;
		}

		String comp = component.getAttributes().get("componentname").toString().toLowerCase();
		comp = addNameToComponent(comp);
		String summary = "Not appropriate " + comp + "!";
		comp = comp.substring(0, 1).toUpperCase() + comp.substring(1);
		String detail = comp + " ";

		if (((String) value).length() > 100) {
			detail += "cannot be longer than 100 characters.";
		} else if (!PATTERN.matcher((String) value).matches()) {
			detail += "consists of alphabetic, space and other characters (like '-').";
		} else {
			return;
		}

		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
	}

	/**
	 * Adds " name" to static XHTML arguments (user.xhtml).
	 */
	private static String addNameToComponent(String component) {
		switch (component) {
		case "country":
		case "city":
			return component + " name";
		default:
			return component;
		}
	}
}
