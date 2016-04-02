/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import javax.faces.bean.ManagedBean;
import password.Password;

@ManagedBean
@ViewScoped
public class RegistrationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

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

	private String username;
	private String password;
	private String email;
	private String name;
	private String phoneNumber;

	private String addressDescription;
	private String zip;
	private String country;
	private String city;
	private String street;
	private Integer streetNumber;
	private Integer floor;
	private Integer door;

	private void nullProps() {
		username = null;
		password = null;
		email = null;
		name = null;
		phoneNumber = null;

		addressDescription = null;
		zip = null;
		country = null;
		city = null;
		street = null;
		streetNumber = null;
		floor = null;
		door = null;
	}

	public void register() {
		if (userFacade.findByUsername(username) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Username already exists!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		if (userFacade.findByEmail(email) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
					"E-mail address already exists!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		if (phoneNumberFacade.findByPhoneNumber(phoneNumber) != null) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Phone number already exists!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		PhoneNumber pn = new PhoneNumber();
		pn.setPhoneNumber(phoneNumber);

		phoneNumberFacade.create(pn);

		List<Address> addresses = addressFacade.findAll();
		Address address = new Address();
		address.setCity(city);
		address.setZip(zip);
		address.setCountry(country);
		address.setStreet(street);
		address.setStrNumber(streetNumber);
		address.setFloor(floor);
		address.setDoor(door);

		boolean existingAddress = false;
		for (Address a : addresses) {
			if (a.equals(address)) {
				address = a;
				existingAddress = true;
				break;
			}
		}
		if (!existingAddress) {
			addressFacade.create(address);
		}

		User user = new User();
		user.setEMail(email);
		try {
			user.setPassword(Password.getSaltedHash(password));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		user.setName(name);
		user.setRegistrationDate(new Date());
		user.setUsername(username);
		userFacade.create(user);

		UserData userData = new UserData();
		userData.setDefaultAddress(address);
		userData.setPhoneNumber(pn);
		userData.setUser(user);
		userData.setUserId(user.getUserId());
		userDataFacade.create(userData);

		AddressesOfUser addressesOfUser = new AddressesOfUser();
		addressesOfUser.setDescription(addressDescription);
		addressesOfUser.setAddress(address);
		addressesOfUser.setUser(user);

		AddressesOfUserPK addressesOfUserPK = new AddressesOfUserPK();
		addressesOfUserPK.setAddressId(address.getAddressId());
		addressesOfUserPK.setUserId(user.getUserId());

		addressesOfUser.setId(addressesOfUserPK);
		addressesOfUserFacade.create(addressesOfUser);
		nullProps();
	}

	/**
	 * Creates a new instance of RegistrationController
	 */
	public RegistrationController() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressDescription() {
		return addressDescription;
	}

	public void setAddressDescription(String addressDescription) {
		this.addressDescription = addressDescription;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(Integer streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getDoor() {
		return door;
	}

	public void setDoor(Integer door) {
		this.door = door;
	}

}
