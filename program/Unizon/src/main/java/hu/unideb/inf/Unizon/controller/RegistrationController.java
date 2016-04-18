package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.UserActivationFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.facade.UserStatusFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.model.UserActivation;
import hu.unideb.inf.Unizon.model.UserStatus;
import password.Password;

@ManagedBean
@ViewScoped
public class RegistrationController implements Serializable {

	private static final long serialVersionUID = 1L;

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
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Username already exists!");
			facesContext.addMessage(null, msg);
			return;
		}

		if (userFacade.findByEmail(newUser.getEMail()) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
					"E-mail address already exists!");
			facesContext.addMessage(null, msg);
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
		
		init();
	
	    

		try {
			
			ExternalContext ec = facesContext.getExternalContext();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
					"Registration is successful, now you can login!");
			facesContext.addMessage(null, msg);
			ec.getFlash().setKeepMessages(true);
			ec.redirect(ec.getRequestContextPath());
			RequestContext.getCurrentInstance().update("messages");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
}
