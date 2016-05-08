package hu.unideb.inf.Unizon.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import hu.unideb.inf.Unizon.facade.ImageFacade;
import hu.unideb.inf.Unizon.model.Image;

@ManagedBean
@ApplicationScoped
public class ImageStreamer {

	@EJB
	private ImageFacade imageFacade;

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the image bytes.
			String imageId = context.getExternalContext().getRequestParameterMap().get("imageId");
			Image image = imageFacade.find(Integer.valueOf(imageId));
			return new DefaultStreamedContent(new FileInputStream((System.getProperty("user.home") + image.getImageUrl()).replaceAll("\\\\", "/")));
		}
	}

}