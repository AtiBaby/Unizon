package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AdministratorFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.util.Password;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@EJB
	private UserFacade userFacade;

	@EJB
	private AdministratorFacade administratorFacade;

	private User user;
	private String username;
	private String password;

	@PostConstruct
	public void init() {
		this.user = null;
		this.username = null;
		this.password = null;
	}

	public void showUserProfile() {
		redirect(isLoggedIn() ? "/user.jsf?faces-redirect=true" : "/index.jsf?faces-redirect=true");
	}

	public void login() {
		log.info("Authenticating user: {}.", username);

		User user = userFacade.findByUsername(username);
		if (user != null) {
			try {
				if (Password.check(password, user.getPassword())) {
					this.user = user;
					log.info("{} successfully authenticated.", user);

					redirect(isAdministrator() ? "/admin.jsf?faces-redirect=true" : "/index.jsf?faces-redirect=true");
					return;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				addErrorMessage("Unknown error happened!");
			}
		}

		log.info("{} failed to authenticate.", user);
		addErrorMessage("Wrong username or password!");
		init();
	}

	public void logout() {
		facesContext.getExternalContext().invalidateSession();
		log.info("{} logged out.", user);
		redirect("/index.jsf?faces-redirect=true");
		init();
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

	public boolean isLoggedIn() {
		return this.user != null;
	}

	public boolean isAdministrator() {
		boolean isAdministrator = administratorFacade.isAdministrator(user.getUserId());
		log.trace("Checking whether {} is administrator: {}", user, isAdministrator);
		return isAdministrator;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
