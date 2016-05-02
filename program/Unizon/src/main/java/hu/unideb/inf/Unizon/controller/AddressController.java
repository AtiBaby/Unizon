package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
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

	public void init() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();

		System.out.println("addressId: " + params.get("addressId"));

		if (params.get("addressId") != null) {
			int addressId = Integer.parseInt(params.get("addressId"));
			originalAddress = addressFacade.find(addressId);
		} else {
			originalAddress = null;
		}

		newAddress = new Address();
		newAddress.setUsers(new HashSet<>());

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

	public void addAddressListen() {
		this.newAddress = new Address();
		this.user = loginController.getUser();
	}

	public void editAddressListen() {
		init();
	}

	public void addAddress() {
		if (user.getAddresses().contains(newAddress)) {
			addErrorMessage("Address already added to your profile!");
			return;
		}

		Address address = createOrFindAddress(newAddress);

		addAddressToUser(address);

		redirect("/user.jsf?faces-redirect=true");
	}

	public void editAddress() {
		if (user.getAddresses().contains(newAddress)) {
			addErrorMessage("Address already added to your profile!");
			return;
		}

		if (!newAddress.equals(originalAddress)) {
			newAddress = createOrFindAddress(newAddress);

			removeAddressFromUser(originalAddress);
			addAddressToUser(newAddress);

			deleteFromDatabaseIfRequired(originalAddress);
		}

		redirect("/user.jsf?faces-redirect=true");
	}

	public void removeAddress() {
		init();

		removeAddressFromUser(originalAddress);

		deleteFromDatabaseIfRequired(originalAddress);

		redirect("/user.jsf?faces-redirect=true");
	}

	private void removeAddressFromUser(Address address) {
		log.info("Removing {} from {}.", address, user);

		user.getAddresses().remove(address);
		user = userFacade.edit(user);
		loginController.setUser(user);

		log.info("{} has been removed from {}.", address, user);
	}

	private Address createOrFindAddress(Address address) {
		Address result;

		Address existingAddress = addressFacade.findByAllAttributes(address);
		if (existingAddress == null) {
			log.info("{} does NOT exist in the database.", address);
			result = createAddress(address);
			result = address;
		} else {
			log.info("{} exists in the database.", address);
			result = existingAddress;
		}

		return result;
	}

	private void addAddressToUser(Address address) {
		user.getAddresses().add(address);
		user.setAddresses(new HashSet<>(new HashSet<>(user.getAddresses())));

		user = userFacade.edit(user);
		loginController.setUser(user);

		log.info("{} has been added to {}.", address, user);
	}

	private Address createAddress(Address address) {
		log.info("Creating {}.", address);

		addressFacade.create(address);

		log.info("{} has been successfully created.", address);

		return address;
	}

	private void deleteFromDatabaseIfRequired(Address addressToDelete) {
		log.info("Trying to delete {} from the database.", addressToDelete);

		Address address = addressFacade.findByAllAttributes(addressToDelete);
		if (address == null) {
			log.info("{} has already been deleted from the database.", addressToDelete);
		} else {
			log.info("{} has {} number of users, {} number of orders1 and {} number of orders2.", address,
					address.getUsers().size(), address.getOrders1().size(), address.getOrders2().size());

			if (address.getUsers().isEmpty() && address.getOrders1().isEmpty() && address.getOrders2().isEmpty()) {
				log.info("Deleting {} from the database.", address);
				addressFacade.remove(address);
				log.info("{} has been deleted from the database.", address);
			}
		}
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

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
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
