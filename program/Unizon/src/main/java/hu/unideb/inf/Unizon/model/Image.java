package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the IMAGE database table.
 *
 */
@Entity
@Table(name = "IMAGE")
@NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")
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
    private List<Product> products1;

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

    public List<Product> getProducts1() {
        return this.products1;
    }

    public void setProducts1(List<Product> products1) {
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

}
