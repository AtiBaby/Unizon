package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the IMAGE database table.
 *
 */
@Entity
@Table(name = "IMAGE")
@NamedQueries({
	@NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
	@NamedQuery(name = "Image.findByImageUrl", query = "SELECT i FROM Image i WHERE i.imageUrl = :imageUrl")
})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IMAGE_ID", unique = true, nullable = false)
    private int imageId;

    @Column(name = "IMAGE_URL", length = 1000)
    private String imageUrl;

    //bi-directional many-to-one association to Product
    @OneToMany(mappedBy = "image", fetch = FetchType.EAGER)
    private Set<Product> products1;

    //bi-directional many-to-many association to Product
    @ManyToMany(mappedBy = "images", fetch = FetchType.EAGER)
    private Set<Product> products2;

    public Image() {
    }

    public int getImageId() {
        return this.imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Product> getProducts1() {
        return this.products1;
    }

    public void setProducts1(Set<Product> products1) {
        this.products1 = products1;
    }

    public Product addProducts1(Product products1) {
        getProducts1().add(products1);
        products1.setImage(this);

        return products1;
    }

    public Product removeProducts1(Product products1) {
        getProducts1().remove(products1);
        products1.setImage(null);

        return products1;
    }

    public Set<Product> getProducts2() {
        return products2;
    }

    public void setProducts2(Set<Product> products2) {
        this.products2 = products2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.imageId;
        hash = 89 * hash + Objects.hashCode(this.imageUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Image other = (Image) obj;
        if (this.imageId != other.imageId) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return String.format("Image [imageId=%s, imageUrl=%s]", imageId, imageUrl);
	}

}