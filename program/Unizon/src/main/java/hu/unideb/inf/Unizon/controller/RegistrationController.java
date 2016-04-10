package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Address;
import hu.unideb.inf.Unizon.model.PhoneNumber;
import hu.unideb.inf.Unizon.model.User;
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
	private PhoneNumberFacade phoneNumberFacade;

	private User newUser;
	private PhoneNumber newPhoneNumber;
	private Address newAddress;
        private boolean isAgree;

        
	@PostConstruct
	public void init() {
		this.newUser = new User();
		this.newUser.setAddresses(new ArrayList<>());
		this.newUser.setPhoneNumbers(new ArrayList<>());

		this.newPhoneNumber = new PhoneNumber();
		this.newAddress = new Address();
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

		PhoneNumber existingPhoneNumber = phoneNumberFacade.findByPhoneNumber(newPhoneNumber.getPhoneNumber());
		if (existingPhoneNumber == null) {
			phoneNumberFacade.create(newPhoneNumber);
		} else {
			newPhoneNumber = existingPhoneNumber;
		}

		Address existingAddress = addressFacade.findByAllAttributes(newAddress);
		if (existingAddress == null) {
			addressFacade.create(newAddress);
		} else {
			newAddress = existingAddress;
		}

		try {
			newUser.setPassword(Password.getSaltedHash(newUser.getPassword()));
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		newUser.setRegistrationDate(new Date());
		newUser.getAddresses().add(newAddress);
		newUser.getPhoneNumbers().add(newPhoneNumber);

		userFacade.create(newUser);

		init();

		try {
			ExternalContext ec = facesContext.getExternalContext();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
					"Registration is successful, now you can login!");
			facesContext.addMessage(null, msg);
			ec.getFlash().setKeepMessages(true);
			ec.redirect(ec.getRequestContextPath() + "/user/userlogin.jsf?faces-redirect=true");
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

	public PhoneNumber getNewPhoneNumber() {
		return newPhoneNumber;
	}

	public void setNewPhoneNumber(PhoneNumber newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(Address newAddress) {
		this.newAddress = newAddress;
	}
        
        public void setIsAgree(boolean isAgree) {
                this.isAgree = isAgree;
        }

        public boolean getIsAgree() {
            return this.isAgree;
        }

}
