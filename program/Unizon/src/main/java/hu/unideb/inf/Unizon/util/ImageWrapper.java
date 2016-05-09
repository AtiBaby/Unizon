package hu.unideb.inf.Unizon.util;

import hu.unideb.inf.Unizon.model.Image;

public class ImageWrapper {

	private Image image;
	private boolean selected;

	public ImageWrapper(Image image, boolean selected) {
		this.image = image;
		this.selected = selected;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ImageWrapper)) {
			return false;
		}
		ImageWrapper other = (ImageWrapper) obj;
		if (image == null) {
			if (other.image != null) {
				return false;
			}
		} else if (!image.equals(other.image)) {
			return false;
		}
		return true;
	}

}
