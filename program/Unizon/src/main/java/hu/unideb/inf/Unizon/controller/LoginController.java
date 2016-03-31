package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.Unizon.facade.AdministratorFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import password.Password;

@Stateless
@ManagedBean(name = "LoginController")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@EJB
	private UserFacade userFacade;

	@EJB
	private AdministratorFacade administratorFacade;

	private String username;
	private String password;	// TODO password nem lehet String, helyette: char[] password kell!

	public String login() {
		logger.info("Authenticating user: {}.", username);

		User user = userFacade.findByUserName(username);
		if (user != null) {
			try {
				if (Password.check(password, user.getPassword())) {
					logger.info("User {} successfully authenticated.", username);

					boolean isAdministrator = administratorFacade.isAdministrator(user.getUserId());
					logger.info("Is user {} administrator: {}", username, isAdministrator);

					// TODO valaki tud jobbat?
					ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
					externalContext.getSessionMap().put("user", user);
					externalContext.getSessionMap().put("isAdministrator", isAdministrator);

					return "/index.jsf?faces-redirect=true";
				} else {
					logger.info("User {} failed to authenticate.", username);
					username = password = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				username = password = null;
			}
		}

		return "/user/userlogin?faces-redirect=true";
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		logger.info("User {} logged out.", username);

		return "/index.jsf?faces-redirect=true";
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