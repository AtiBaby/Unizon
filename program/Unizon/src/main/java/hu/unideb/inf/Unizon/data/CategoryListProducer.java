package hu.unideb.inf.Unizon.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import hu.unideb.inf.Unizon.facade.CategoryFacade;
import hu.unideb.inf.Unizon.model.Category;

@RequestScoped
public class CategoryListProducer {

	@EJB
	private CategoryFacade categoryFacade;

	private List<Category> categories;

	@Produces
	@Named
	public List<Category> getCategories() {
		return categories;
	}

	public void onCategoryListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Category category) {
		retrieveAllCategories();
	}

	@PostConstruct
	public void retrieveAllCategories() {
		categories = categoryFacade.findAll();
	}

}
