package hu.unideb.inf.Unizon.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.slf4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import hu.unideb.inf.Unizon.email.EmailSessionBean;
import hu.unideb.inf.Unizon.exceptions.ActivationEmailException;
import hu.unideb.inf.Unizon.model.User;

@ManagedBean
@ApplicationScoped
public class EmailActivationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String BASE_ACTIVATION_LINK = "http://localhost:8080/Unizon/activate.jsf?activationKey=";

	private static final String SUBJECT = "Activation required";

	private static final String NAME = "UNIZON WEBSHOP";

	private Configuration cfg;

	@Inject
	private Logger log;

	@EJB
	EmailSessionBean emailSessionBean;

	@PostConstruct
	public void init() {
		cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setClassForTemplateLoading(EmailActivationController.class, "/templates/");
		cfg.setDefaultEncoding(StandardCharsets.UTF_8.toString());
	}

	public void sendActivationEmail(User user) throws ActivationEmailException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Writer out = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
			Template template = cfg.getTemplate("activationTemplate.ftl");

			Map<String, Object> data = new HashMap<>();
			data.put("user", user);
			data.put("baseActivationLink", BASE_ACTIVATION_LINK);
			data.put("from", NAME);

			template.process(data, out);
			out.flush();

			emailSessionBean.sendEmail(EmailSessionBean.E_MAIL, user.getEMail(), SUBJECT, baos.toString(), NAME);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActivationEmailException("Failed to send the activation e-mail to user: " + user + ".");
		}
	}

}
