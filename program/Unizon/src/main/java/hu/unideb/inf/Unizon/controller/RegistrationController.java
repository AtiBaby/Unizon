package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.exceptions.ActivationEmailException;
import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.UserActivationFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.facade.UserStatusFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.model.UserActivation;
import hu.unideb.inf.Unizon.model.UserStatus;
import hu.unideb.inf.Unizon.util.Password;

@ManagedBean
@ViewScoped
public class RegistrationController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{emailActivationController}")
	private EmailActivationController emailActivationController;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@EJB
	private UserFacade userFacade;

	@EJB
	private AddressFacade addressFacade;

	@EJB
	private UserStatusFacade userStatusFacade;

	@EJB
	private UserActivationFacade userActivationFacade;

	@EJB
	private PhoneNumberFacade phoneNumberFacade;

	private User newUser;

	@PostConstruct
	public void init() {
		this.newUser = new User();
		this.newUser.setAddresses(new HashSet<>());
		this.newUser.setPhoneNumbers(new HashSet<>());
	}

	public void register() {
		if (userFacade.findByUsername(newUser.getUsername()) != null) {
			addErrorMessage("Username already exists!");
			return;
		}

		if (userFacade.findByEmail(newUser.getEMail()) != null) {
			addErrorMessage("E-mail address already exists!");
			return;
		}

		UserStatus userStatus = userStatusFacade.findByStatusName("inactive");

		try {
			newUser.setPassword(Password.getSaltedHash(newUser.getPassword()));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		newUser.setRegistrationDate(new Date());
		newUser.setUserStatus(userStatus);
		userFacade.create(newUser);

		UserActivation userActivation = new UserActivation();
		userActivation.setActivationKey(UUID.randomUUID().toString());
		userActivation.setUser(newUser);
		userActivation.setUserId(newUser.getUserId());
		userActivationFacade.create(userActivation);

		newUser.setUserActivation(userActivation);

		try {
			emailActivationController.sendActivationEmail(newUser);
		} catch (ActivationEmailException e) {
			log.error(e.getMessage());
		}

		try {
			addInfoMessage("Registration is successful, now you can login!");
			addInfoMessage("Activation e-mail has been sent to your e-mail address.");

			ExternalContext ec = facesContext.getExternalContext();
			ec.getFlash().setKeepMessages(true);
			ec.redirect(ec.getRequestContextPath());

			RequestContext.getCurrentInstance().update("messages");

			init();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void addInfoMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_INFO, "INFO", detail);
	}

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
	}

	public EmailActivationController getEmailActivationController() {
		return emailActivationController;
	}

	public void setEmailActivationController(EmailActivationController emailActivationController) {
		this.emailActivationController = emailActivationController;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

}
