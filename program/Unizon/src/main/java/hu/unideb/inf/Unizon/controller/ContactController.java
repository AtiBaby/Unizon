package hu.unideb.inf.Unizon.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.email.EmailSessionBean;

/**
 *
 * @author Czuczi
 */
@ManagedBean
@ViewScoped
public class ContactController {

	@Inject
	private Logger log;

	@EJB
	EmailSessionBean emailSessionBean;

	private String from;
	private String subject;
	private String text;
	private String name;

	@PostConstruct
	public void init() {
		from = subject = text = name = null;
	}

	public void sendEmail() {
		if (from == null || subject == null || text == null || name == null || from.isEmpty() || subject.isEmpty()
				|| text.isEmpty() || name.isEmpty()) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "All fields are required!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().update("messages");
			log.info("Somebody tried to send e-mail without providing all fields.");
		} else {
			emailSessionBean.sendEmail(from, subject, text, name);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "E-mail has been sent!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().update("messages");
			log.info("E-mail sent from contact page from {} with the subject {} and name {}", from, subject, name);
			init();
		}
	}

	/**
	 * Creates a new instance of ContactController
	 */
	public ContactController() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
