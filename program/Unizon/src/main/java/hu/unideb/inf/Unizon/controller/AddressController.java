package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Address;
import hu.unideb.inf.Unizon.model.User;

@ManagedBean
@ViewScoped
public class AddressController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private AddressFacade addressFacade;

	@EJB
	private UserFacade userFacade;

	private Address newAddress;
	private Address originalAddress;
	private User user;

	@PostConstruct
	public void init() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		int addressId = Integer.parseInt(params.get("addressId"));

		originalAddress = addressFacade.find(addressId);

		newAddress = new Address();
		newAddress.setUsers(new ArrayList<>());

		if (originalAddress != null) {
			newAddress.setCity(originalAddress.getCity());
			newAddress.setCountry(originalAddress.getCountry());
			newAddress.setDoor(originalAddress.getDoor());
			newAddress.setFloor(originalAddress.getFloor());
			newAddress.setStreet(originalAddress.getStreet());
			newAddress.setStrNumber(originalAddress.getStrNumber());
			newAddress.setZip(originalAddress.getZip());
		}

		user = loginController.getUser();
	}

	public void addAddress() {
		Address address = createOrFindAddress(newAddress);

		addAddressToUser(address);

		redirect("/user/user.jsf?faces-redirect=true");
	}

	public void editAddress() {
		if (!newAddress.equals(originalAddress)) {
			newAddress = createOrFindAddress(newAddress);

			removeAddressFromUser(originalAddress);
			addAddressToUser(newAddress);

			deleteFromDatabaseIfRequired(originalAddress);
		}

		redirect("/user/user.jsf?faces-redirect=true");
	}

	public void removeAddress() {
		removeAddressFromUser(originalAddress);

		deleteFromDatabaseIfRequired(originalAddress);

		redirect("/user/user.jsf?faces-redirect=true");
	}

	private void removeAddressFromUser(Address address) {
		log.info("Removing address: {} from user: {}.", address, user.getUsername());

		user.getAddresses().remove(address);
		userFacade.edit(user);

		log.info("Address: {} has been removed from user: {}.", address, user.getUsername());
	}

	private Address createOrFindAddress(Address address) {
		Address result;

		Address existingAddress = addressFacade.findByAllAttributes(address);
		if (existingAddress == null) {
			log.info("Address {} does NOT exist in the database.", address);
			result = createAddress(address);
			result = address;
		} else {
			log.info("Address: {} exists in the database.", address);
			result = existingAddress;
		}

		return result;
	}

	private void addAddressToUser(Address address) {
		user.getAddresses().add(address);
		user.setAddresses(new ArrayList<>(new HashSet<>(user.getAddresses())));

		userFacade.edit(user);

		log.info("Address: {} has been added to user: {}.", address, user.getUsername());
	}

	private Address createAddress(Address address) {
		log.info("Creating address: {}.", address);

		addressFacade.create(address);

		log.info("Address: {} has been successfully created.", address);

		return address;
	}

	private void deleteFromDatabaseIfRequired(Address addressToDelete) {
		log.info("Trying to delete address: {} from the database.", addressToDelete);

		Address address = addressFacade.findByAllAttributes(addressToDelete);
		if (address == null) {
			log.info("Address: {} has already been deleted from the database.", addressToDelete);
		} else {
			log.info("Address {} has {} number of users, {} number of orders1 and {} number of orders2.", address,
					address.getUsers().size(), address.getOrders1().size(), address.getOrders2());

			if (address.getUsers().isEmpty() && address.getOrders1().isEmpty() && address.getOrders2().isEmpty()) {
				log.info("Deleting address: {} from the database.", address);
				addressFacade.remove(address);
				log.info("Address: {} has been deleted from the database.", address);
			}
		}
	}

	private void redirect(String url) {
		log.info("Redirecting user: {} to {}.", user.getUsername(), url);
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

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(Address address) {
		this.newAddress = address;
	}

}
