package hu.unideb.inf.Unizon.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import hu.unideb.inf.Unizon.facade.ImageFacade;
import hu.unideb.inf.Unizon.model.Image;

@RequestScoped
public class ImageListProducer {

	@EJB
	private ImageFacade imageFacade;

	private List<Image> images;

	@Produces
	@Named
	public List<Image> getImages() {
		return images;
	}

	public void onImageListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Image image) {
		retrieveAllImages();
	}

	@PostConstruct
	public void retrieveAllImages() {
		images = imageFacade.findAll();
	}

}
