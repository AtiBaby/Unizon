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

import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.PhoneNumber;
import hu.unideb.inf.Unizon.model.User;

@ManagedBean
@ViewScoped
public class PhoneNumberController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private PhoneNumberFacade phoneNumberFacade;

	@EJB
	private UserFacade userFacade;

	private PhoneNumber originalPhoneNumber;
	private String newPhoneNumber;
	private User user;

	@PostConstruct
	public void init() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		int phoneNumberId = Integer.parseInt(params.get("phoneNumberId"));

		originalPhoneNumber = phoneNumberFacade.find(phoneNumberId);

		if (originalPhoneNumber == null) {
			originalPhoneNumber = new PhoneNumber();
		}

		newPhoneNumber = originalPhoneNumber.getPhoneNumber();
		user = loginController.getUser();
	}

	public void addPhoneNumber() {
		PhoneNumber phoneNumber = createOrFindPhoneNumber(newPhoneNumber);

		addPhoneNumberToUser(phoneNumber);

		redirect("/user/user.jsf?faces-redirect=true");
	}

	public void editPhoneNumber() {
		removePhoneNumberFromUser(originalPhoneNumber);

		PhoneNumber phoneNumber = createOrFindPhoneNumber(newPhoneNumber);
		addPhoneNumberToUser(phoneNumber);

		deleteFromDatabaseIfRequired(originalPhoneNumber.getPhoneNumber());

		redirect("/user/user.jsf?faces-redirect=true");
	}

	public void removePhoneNumberFromUser() {
		removePhoneNumberFromUser(originalPhoneNumber);

		deleteFromDatabaseIfRequired(originalPhoneNumber.getPhoneNumber());

		redirect("/user/user.jsf?faces-redirect=true");
	}

	private void removePhoneNumberFromUser(PhoneNumber phoneNumber) {
		log.info("Removing {} from {}.", phoneNumber, user);

		user.getPhoneNumbers().remove(originalPhoneNumber);
		user = userFacade.edit(user);
		loginController.setUser(user);

		log.info("{} has been removed from {}.", phoneNumber, user);
	}

	private PhoneNumber createOrFindPhoneNumber(String phoneNumber) {
		PhoneNumber result;

		PhoneNumber existingPhoneNumber = phoneNumberFacade.findByPhoneNumber(phoneNumber);
		if (existingPhoneNumber == null) {
			log.info("Phone number {} does NOT exist in the database.", phoneNumber);
			result = createPhoneNumber(phoneNumber);
		} else {
			log.info("Phone number {} exists in the database.", phoneNumber);
			result = existingPhoneNumber;
		}

		return result;
	}

	private void addPhoneNumberToUser(PhoneNumber phoneNumber) {
		user.getPhoneNumbers().add(phoneNumber);
		user.setPhoneNumbers(new HashSet<>(new HashSet<>(user.getPhoneNumbers())));

		user = userFacade.edit(user);
		loginController.setUser(user);

		log.info("{} has been added to {}.", phoneNumber, user);
	}

	private PhoneNumber createPhoneNumber(String newPhoneNumber) {
		log.info("Creating new phone number {}.", newPhoneNumber);

		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setPhoneNumber(newPhoneNumber);
		phoneNumberFacade.create(phoneNumber);

		log.info("New phone number {} has been successfully created.", phoneNumber);

		return phoneNumber;
	}

	private void deleteFromDatabaseIfRequired(String phoneNumberToDelete) {
		log.info("Trying to delete phone number {} from the database.", phoneNumberToDelete);

		PhoneNumber phoneNumber = phoneNumberFacade.findByPhoneNumber(phoneNumberToDelete);
		if (phoneNumber == null) {
			log.info("Phone number {} has already been deleted from the database.", phoneNumberToDelete);
		} else {
			log.info("{} has {} number of users and {} number of orders.", phoneNumber, phoneNumber.getUsers().size(),
					phoneNumber.getOrders().size());

			if (phoneNumber.getUsers().isEmpty() && phoneNumber.getOrders().isEmpty()) {
				log.info("Deleting {} from the database.", phoneNumber);
				phoneNumberFacade.remove(phoneNumber);
				log.info("{} has been deleted from the database.", phoneNumber);
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

	public String getNewPhoneNumber() {
		return newPhoneNumber;
	}

	public void setNewPhoneNumber(String newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}

}
