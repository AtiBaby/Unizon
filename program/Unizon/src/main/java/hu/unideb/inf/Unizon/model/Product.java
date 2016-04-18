package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the PRODUCT database table.
 *
 */
@Entity
@Table(name = "PRODUCT")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findAllByCat", query = "SELECT p FROM Product p INNER JOIN p.categories cats WHERE cats.categoryId = :catId"),
    @NamedQuery(name = "Product.findAllContain", query = "SELECT p FROM Product p WHERE p.title LIKE :term"),
    @NamedQuery(name = "Product.findAllByCatIdContain", query = "SELECT p FROM Product p INNER JOIN p.categories cats WHERE cats.categoryId = :catId AND p.title LIKE :term")
})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false)
    private int productId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    //bi-directional many-to-one association to CatToProd
    @OneToMany(mappedBy = "product1", fetch = FetchType.EAGER)
    private Set<CatToProd> catToProds1;

    //bi-directional many-to-one association to CatToProd
    @OneToMany(mappedBy = "product2", fetch = FetchType.EAGER)
    private Set<CatToProd> catToProds2;

    //bi-directional many-to-many association to Category
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CAT_TO_PROD", joinColumns = {
                @JoinColumn(name = "PRODUCT_ID", nullable = false)
            }, inverseJoinColumns = {
                @JoinColumn(name = "CATEGORY_ID", nullable = false)
            }
    )
    private Set<Category> categories;

    //bi-directional many-to-one association to Image
    @ManyToOne
    @JoinColumn(name = "DEFAULT_IMAGE_ID", nullable = false)
    private Image image;

    //bi-directional many-to-many association to Image
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PRODUCT_TO_IMAGE", joinColumns = {
                @JoinColumn(name = "PRODUCT_ID", nullable = false)
            }, inverseJoinColumns = {
                @JoinColumn(name = "IMAGE_ID", nullable = false)
            }
    )
    private Set<Image> images;

    //bi-directional many-to-one association to ProdToOrder
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProdToOrder> prodToOrders;

    //bi-directional many-to-one association to ProdToTag
    @OneToMany(mappedBy = "product1", fetch = FetchType.EAGER)
    private Set<ProdToTag> prodToTags1;

    //bi-directional many-to-one association to ProdToTag
    @OneToMany(mappedBy = "product2", fetch = FetchType.EAGER)
    private Set<ProdToTag> prodToTags2;

    //bi-directional many-to-many association to Tag
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Tag> tags;

    public Product() {
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<CatToProd> getCatToProds1() {
        return this.catToProds1;
    }

    public void setCatToProds1(Set<CatToProd> catToProds1) {
        this.catToProds1 = catToProds1;
    }

    public CatToProd addCatToProds1(CatToProd catToProds1) {
        getCatToProds1().add(catToProds1);
        catToProds1.setProduct1(this);

        return catToProds1;
    }

    public CatToProd removeCatToProds1(CatToProd catToProds1) {
        getCatToProds1().remove(catToProds1);
        catToProds1.setProduct1(null);

        return catToProds1;
    }

    public Set<CatToProd> getCatToProds2() {
        return this.catToProds2;
    }

    public void setCatToProds2(Set<CatToProd> catToProds2) {
        this.catToProds2 = catToProds2;
    }

    public CatToProd addCatToProds2(CatToProd catToProds2) {
        getCatToProds2().add(catToProds2);
        catToProds2.setProduct2(this);

        return catToProds2;
    }

    public CatToProd removeCatToProds2(CatToProd catToProds2) {
        getCatToProds2().remove(catToProds2);
        catToProds2.setProduct2(null);

        return catToProds2;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<ProdToOrder> getProdToOrders() {
        return this.prodToOrders;
    }

    public void setProdToOrders(Set<ProdToOrder> prodToOrders) {
        this.prodToOrders = prodToOrders;
    }

    public ProdToOrder addProdToOrder(ProdToOrder prodToOrder) {
        getProdToOrders().add(prodToOrder);
        prodToOrder.setProduct(this);

        return prodToOrder;
    }

    public ProdToOrder removeProdToOrder(ProdToOrder prodToOrder) {
        getProdToOrders().remove(prodToOrder);
        prodToOrder.setProduct(null);

        return prodToOrder;
    }

    public Set<ProdToTag> getProdToTags1() {
        return this.prodToTags1;
    }

    public void setProdToTags1(Set<ProdToTag> prodToTags1) {
        this.prodToTags1 = prodToTags1;
    }

    public ProdToTag addProdToTags1(ProdToTag prodToTags1) {
        getProdToTags1().add(prodToTags1);
        prodToTags1.setProduct1(this);

        return prodToTags1;
    }

    public ProdToTag removeProdToTags1(ProdToTag prodToTags1) {
        getProdToTags1().remove(prodToTags1);
        prodToTags1.setProduct1(null);

        return prodToTags1;
    }

    public Set<ProdToTag> getProdToTags2() {
        return this.prodToTags2;
    }

    public void setProdToTags2(Set<ProdToTag> prodToTags2) {
        this.prodToTags2 = prodToTags2;
    }

    public ProdToTag addProdToTags2(ProdToTag prodToTags2) {
        getProdToTags2().add(prodToTags2);
        prodToTags2.setProduct2(this);

        return prodToTags2;
    }

    public ProdToTag removeProdToTags2(ProdToTag prodToTags2) {
        getProdToTags2().remove(prodToTags2);
        prodToTags2.setProduct2(null);

        return prodToTags2;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
