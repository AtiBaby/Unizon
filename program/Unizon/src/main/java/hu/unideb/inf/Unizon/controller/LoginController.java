package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.Unizon.facade.AdministratorFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import password.Password;

@Stateless
@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

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
		logger.info("Authenticating user: {}.", username);

		User user = userFacade.findByUsername(username);
		if (user != null) {
			try {
				if (Password.check(password, user.getPassword())) {
					this.user = user;
					logger.info("User {} successfully authenticated.", username);

					this.isAdministrator = administratorFacade.isAdministrator(user.getUserId());
					logger.info("Is user {} administrator: {}", username, isAdministrator);

					return "/index.jsf?faces-redirect=true";
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		logger.info("User {} failed to authenticate.", username);
		nullProps();

		return "/user/userlogin?faces-redirect=true";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("User {} logged out.", username);
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