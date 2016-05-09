package hu.unideb.inf.Unizon.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import hu.unideb.inf.Unizon.email.EmailSessionBean;
import hu.unideb.inf.Unizon.exceptions.ActivationEmailException;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.util.Password;

@ManagedBean
@ApplicationScoped
public class ForgotPasswordController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SUBJECT = "Forgot Password";

	private static final String NAME = "UNIZON WEBSHOP";

	private Configuration cfg;
	
	private static final String ABC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();

	@Inject
	private Logger log;
	
	@EJB
	private UserFacade userFacade;

	@EJB
	EmailSessionBean emailSessionBean;
	
	private String username;

	@PostConstruct
	public void init() {
		username=null;
		cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setClassForTemplateLoading(ForgotPasswordController.class, "/templates/");
		cfg.setDefaultEncoding(StandardCharsets.UTF_8.toString());
	}

	public void sendEmail() throws ActivationEmailException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Writer out = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
			Template template = cfg.getTemplate("forgotPasswordTemplate.ftl");
			User user = userFacade.findByUsername(username);
			String newPassword = randomString(8);
			try {
				user.setPassword(Password.getSaltedHash(newPassword));
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
			user = userFacade.edit(user);
			
			Map<String, Object> data = new HashMap<>();
			data.put("user", user);
			data.put("newPassword", newPassword);
			data.put("from", NAME);

			template.process(data, out);
			out.flush();

			emailSessionBean.sendEmail(EmailSessionBean.E_MAIL, user.getEMail(), SUBJECT, baos.toString(), NAME);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "E-mail has been sent!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RequestContext.getCurrentInstance().update("messages");
			log.info("E-mail sent to {} , because he/she forgot the password.", username);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActivationEmailException("Failed to send the activation e-mail to user: " + username + ".");
		}
	}

	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(ABC.charAt(rnd.nextInt(ABC.length())));
		return sb.toString();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}