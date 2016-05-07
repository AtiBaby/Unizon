package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.email.EmailSessionBean;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.util.Password;

@ManagedBean
@ViewScoped
public class ForgotPasswordController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private UserFacade userFacade;

	@EJB
	EmailSessionBean emailSessionBean;

	private String username;
	private String emailAddress;
	private String from;
	private String to;
	private String subject;
	private String text;
	private String name;
	private User user;

	private static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	@PostConstruct
	public void init() {
		emailAddress = null;
		from = subject = to = text = name = null;
	}

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(ABC.charAt(rnd.nextInt(ABC.length())));
		return sb.toString();
	}

	public void sendEmail() {

		user = userFacade.findByUsername(username);
		if (user == null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "It is not a username.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().update("messages");
		} else {
			String newPassword = randomString(8);

			try {
				user.setPassword(Password.getSaltedHash(newPassword));
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
			user = userFacade.edit(user);

			emailAddress = (userFacade.findByUsername(username)).getEMail();
			name = "UNIZON WEBSHOP";
			from = "unizonwebshop@gmail.com";
			to = emailAddress;
			subject = "Your new password.";
			text = "Dear " + username
					+ ",\n\n You get a new password to UNIZON, because you forgot it. \n The new password is the following: "
					+ newPassword + "\nFrom now you can log in with it, and you can change it in the user profile.";

			emailSessionBean.sendEmail(from, to, subject, text, name);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "E-mail has been sent!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().update("messages");
			log.info("E-mail sent to {} , because he/she forgot the password.", username);

			init();
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}