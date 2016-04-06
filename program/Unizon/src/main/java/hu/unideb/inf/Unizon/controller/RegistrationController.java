package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.AddressesOfUserFacade;
import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.UserDataFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Address;
import hu.unideb.inf.Unizon.model.AddressesOfUser;
import hu.unideb.inf.Unizon.model.AddressesOfUserPK;
import hu.unideb.inf.Unizon.model.PhoneNumber;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.model.UserData;
import org.primefaces.context.RequestContext;
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
	private UserDataFacade userDataFacade;

	@EJB
	private AddressFacade addressFacade;

	@EJB
	private AddressesOfUserFacade addressesOfUserFacade;

	@EJB
	private PhoneNumberFacade phoneNumberFacade;

	private User newUser;
	private PhoneNumber newPhoneNumber;
	private Address newAddress;
	private String addressDescription;

	@PostConstruct
	public void init() {
		this.newUser = new User();
		this.newPhoneNumber = new PhoneNumber();
		this.newAddress = new Address();
		this.addressDescription = null;
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

		if (phoneNumberFacade.findByPhoneNumber(newPhoneNumber.getPhoneNumber()) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Phone number already exists!");
			facesContext.addMessage(null, msg);
			return;
		}

		phoneNumberFacade.create(newPhoneNumber);

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
		userFacade.create(newUser);

		UserData userData = new UserData();
		userData.setDefaultAddress(newAddress);
		userData.setPhoneNumber(newPhoneNumber);
		userData.setUser(newUser);
		userData.setUserId(newUser.getUserId());
		userDataFacade.create(userData);

		AddressesOfUser addressesOfUser = new AddressesOfUser();
		addressesOfUser.setDescription(addressDescription);
		addressesOfUser.setAddress(newAddress);
		addressesOfUser.setUser(newUser);

		AddressesOfUserPK addressesOfUserPK = new AddressesOfUserPK();
		addressesOfUserPK.setAddressId(newAddress.getAddressId());
		addressesOfUserPK.setUserId(newUser.getUserId());
		addressesOfUser.setId(addressesOfUserPK);

		addressesOfUserFacade.create(addressesOfUser);

		init();

		try {
			ExternalContext ec = facesContext.getExternalContext();
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Registration is successful, now you can login!");
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

	public String getAddressDescription() {
		return addressDescription;
	}

	public void setAddressDescription(String addressDescription) {
		this.addressDescription = addressDescription;
	}

}
