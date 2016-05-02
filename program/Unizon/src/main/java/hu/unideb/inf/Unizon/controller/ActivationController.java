package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.exceptions.NoSuchActivationKeyException;
import hu.unideb.inf.Unizon.facade.UserActivationFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.facade.UserStatusFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.model.UserActivation;
import hu.unideb.inf.Unizon.model.UserStatus;

@ManagedBean
@ViewScoped
public class ActivationController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@EJB
	private UserFacade userFacade;

	@EJB
	private UserStatusFacade userStatusFacade;

	@EJB
	private UserActivationFacade userActivationFacade;

	private UserActivation userActivation;

	@PostConstruct
	public void init() {
		userActivation = new UserActivation();
	}

	public void activationListener() {
		log.info("Passed activation key: {}.", userActivation.getActivationKey());

		try {
			userActivation = userActivationFacade.findByActivationKey(userActivation.getActivationKey());
			User user = userActivation.getUser();
			log.info("Activation key {} belongs to {}", userActivation.getActivationKey(), user);

			if (user.getUserStatus().getStatusName().equals("inactive")) {
				log.info("Trying to get user status active from the database.");
				UserStatus activeUserStatus = userStatusFacade.findByStatusName("active");
				log.info("User status active has been received from the database.");

				log.info("Updating {}'s status to {}.", user, activeUserStatus);
				user.setUserStatus(activeUserStatus);
				userFacade.edit(user);
				log.info("{}'s status has been successfully updated to.", user, activeUserStatus);

				log.info("Trying to remove {} from the database.", userActivation);
				userActivationFacade.remove(userActivation);
				user.setUserActivation(null);
				log.info("{} has been successfully removed from the database.", userActivation);

				loginController.updateUser();

				// TODO uzenet index.jsf-hez: INFO - Your profile has been successfully activated!
			}
		} catch (NoSuchActivationKeyException e) {
			log.info("No such activation key exception catched.");
			// TODO uzenet index.jsf-hez: ERROR - Invalid activation key!
		} finally {
			redirect("/index.jsf?faces-redirect=true");
		}
	}

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
	}

	private void redirect(String url) {
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public UserActivation getUserActivation() {
		return userActivation;
	}

	public void setUserActivation(UserActivation userActivation) {
		this.userActivation = userActivation;
	}

}
