package hu.unideb.inf.Unizon.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import hu.unideb.inf.Unizon.facade.OrderFacade;
import hu.unideb.inf.Unizon.model.Order;
import hu.unideb.inf.Unizon.model.ProdToOrder;
import hu.unideb.inf.Unizon.model.Product;
import hu.unideb.inf.Unizon.model.User;

@ManagedBean
@ApplicationScoped
public class OrderEmailController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SUBJECT = "Order on UNIZON";

	private static final String NAME = "UNIZON WEBSHOP";

	private Configuration cfg;

	@Inject
	private Logger log;

	@EJB
	EmailSessionBean emailSessionBean;

	@PostConstruct
	public void init() {
		cfg = new Configuration(Configuration.VERSION_2_3_24);
		cfg.setClassForTemplateLoading(OrderEmailController.class, "/templates/");
		cfg.setDefaultEncoding(StandardCharsets.UTF_8.toString());
	}
	
	static class AmountProduct{
		Product products;
		int amount;
		
		public AmountProduct(Product product,int amount) {
			this.products = product;
			this.amount = amount;
		}
		
	}
	
	public void sendEmail(Order order) throws ActivationEmailException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Writer out = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
			Template template = cfg.getTemplate("orderTemplate.ftl");
			User user = order.getUser();
			Map<String, Object> data = new HashMap<>();
			
			Set<ProdToOrder> list = order.getProdToOrders();
			String text = "";
			for(ProdToOrder p: list){
				text += "\nTitle: " + p.getProduct().getTitle() + "\nPrice: " + p.getProduct().getPrice() + "\nAmount: " + p.getAmount() + "\n";
			}
			
			data.put("user", user);
			data.put("orderproducts", text);
			data.put("shippingAddress", order.getAddress1());
			data.put("billingAddress", order.getAddress2());
			data.put("orderDate", order.getOrderDate().toString());
			data.put("from", NAME);

			template.process(data, out);
			out.flush();
			
			emailSessionBean.sendEmail(EmailSessionBean.E_MAIL, user.getEMail(), SUBJECT, baos.toString(), NAME);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActivationEmailException("Failed to send the e-mail about order.");
		}
	}

}
