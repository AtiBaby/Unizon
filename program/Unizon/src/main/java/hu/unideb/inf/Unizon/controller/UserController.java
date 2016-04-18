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

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import password.Password;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private UserFacade userFacade;

	private User user;
	private String currentPassword;
	private String newPassword;

	@PostConstruct
	public void init() {
		this.user = loginController.getUser();
		this.currentPassword = null;
		this.newPassword = null;
	}

	public void changePassword() {
		log.info("{} tries to change password.", user);
		try {
			if (Password.check(currentPassword, user.getPassword())) {
				log.info("{} matched the current password.", user);

				user.setPassword(Password.getSaltedHash(newPassword));
				userFacade.edit(user);

				log.info("{} successfully changed password.", user);
				addInfoMessage("Password successfully updated.");

//				facesContext.getExternalContext().getFlash().setKeepMessages(true);
				RequestContext.getCurrentInstance().update("messages");

				redirect("/user.jsf?faces-redirect=true");
			} else {
				log.info("{} failed to match current password.", user);
				addErrorMessage("Current password did not match!");
				RequestContext.getCurrentInstance().update("messages");
				return;
			}
			init();
		} catch (Exception e) {
			log.error(e.getMessage());
			addErrorMessage("Unkown error happened!");
			RequestContext.getCurrentInstance().update("messages");
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

	private void redirect(String url) {
		log.info("Redirecting {} to {}.", user, url);
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
