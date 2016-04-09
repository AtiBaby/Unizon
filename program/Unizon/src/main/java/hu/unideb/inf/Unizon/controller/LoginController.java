package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AdministratorFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import password.Password;

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
	private boolean isAdministrator;
	private String username;
	private String password; // TODO password nem lehet String, helyette: char[] password kell!

	@PostConstruct
	public void nullProps() {
		this.user = null;
		this.isAdministrator = false;
		this.username = null;
		this.password = null;
	}

	public String login() {
		log.info("Authenticating user: {}.", username);

		User user = userFacade.findByUsername(username);
		if (user != null) {
			try {
				if (Password.check(password, user.getPassword())) {
					this.user = user;
					log.info("{} successfully authenticated.", user);

					this.isAdministrator = administratorFacade.isAdministrator(user.getUserId());
					log.info("Is {} administrator: {}", user, isAdministrator);

					return "/index.jsf?faces-redirect=true";
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		log.info("{} failed to authenticate.", user);
		nullProps();

		return "/user/userlogin?faces-redirect=true";
	}

	public String logout() {
		facesContext.getExternalContext().invalidateSession();
		log.info("{} logged out.", user);
		nullProps();
		return "/index.jsf?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return this.user != null;
	}

	public boolean isAdministrator() {
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