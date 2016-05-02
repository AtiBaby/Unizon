package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.AdministratorFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Administrator;
import hu.unideb.inf.Unizon.model.User;

/**
 *
 * @author Czuczi
 */
@ManagedBean
@ViewScoped
public class ManageAdminController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private AdministratorFacade administratorFacade;
	@EJB
	private UserFacade userFacade;

	private List<Administrator> admins;
	private List<User> usersWithoutAdmins;
	private List<User> filteredUser;

	private Administrator selectedAdministrator;
	private User selectedUser;

	@PostConstruct
	public void init() {
		selectedAdministrator = null;
		selectedUser = null;
		admins = administratorFacade.findAll();
		usersWithoutAdmins = userFacade.findUsersWithoutAdmins();
	}

	public void selectAdministrator(SelectEvent selectEvent) {
		selectedAdministrator = (Administrator) selectEvent.getObject();
	}

	public void selectUser(SelectEvent selectEvent) {
		selectedUser = (User) selectEvent.getObject();
	}

	public void makeAdministrator() {
		if (selectedUser == null) {
			log.info("{} tried to create an admin without selecting a user.", loginController.getUser());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Please select a user!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		Administrator admin = new Administrator();
		admin.setUserId(selectedUser.getUserId());
		admin.setUser(selectedUser);
		administratorFacade.create(admin);
		log.info("{} has added admin rights to {}.", loginController.getUser(), selectedUser);
		init();
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.update("adminTabView:adminTableForm");
		rc.update("adminTabView:userTableForm");
	}

	public void deleteAdministrator() {
		if (selectedAdministrator == null) {
			log.info("{} tried to remove an admin without selecting one.", loginController.getUser());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
					"Please select an administrator!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		administratorFacade.remove(selectedAdministrator);
		log.info("{} has removed {} from administrators.", loginController.getUser(), selectedAdministrator);
		init();
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.update("adminTabView:adminTableForm");
		rc.update("adminTabView:userTableForm");
	}

	/**
	 * Creates a new instance of ManageAdminController
	 */
	public ManageAdminController() {
	}

	public List<Administrator> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Administrator> admins) {
		this.admins = admins;
	}

	public List<User> getUsersWithoutAdmins() {
		return usersWithoutAdmins;
	}

	public void setUsersWithoutAdmins(List<User> usersWithoutAdmins) {
		this.usersWithoutAdmins = usersWithoutAdmins;
	}

	public Administrator getSelectedAdministrator() {
		return selectedAdministrator;
	}

	public void setSelectedAdministrator(Administrator selectedAdministrator) {
		this.selectedAdministrator = selectedAdministrator;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public List<User> getFilteredUser() {
		return filteredUser;
	}

	public void setFilteredUser(List<User> filteredUser) {
		this.filteredUser = filteredUser;
	}
}
