package hu.unideb.inf.Unizon.facade;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Image;

@Stateless
public class ImageFacade extends AbstractFacade<Image> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ImageFacade() {
		super(Image.class);
	}

	public Image findByImageUrl(String imageUrl) {
		List<Image> resultList;

		try {
			resultList = em.createNamedQuery("Image.findByImageUrl", Image.class).setParameter("imageUrl", imageUrl)
					.getResultList();
		} catch (Exception e) {
			resultList = Collections.emptyList();
		}

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
