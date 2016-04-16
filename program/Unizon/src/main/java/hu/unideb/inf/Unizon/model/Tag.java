package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the TAG database table.
 *
 */
@Entity
@Table(name = "TAG")
@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TAG_ID", unique = true, nullable = false)
    private int tagId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    //bi-directional many-to-one association to ProdToTag
    @OneToMany(mappedBy = "tag1", fetch = FetchType.EAGER)
    private List<ProdToTag> prodToTags1;

    //bi-directional many-to-one association to ProdToTag
    @OneToMany(mappedBy = "tag2", fetch = FetchType.EAGER)
    private List<ProdToTag> prodToTags2;

    //bi-directional many-to-many association to Product
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PROD_TO_TAG", joinColumns = {
                @JoinColumn(name = "TAG_ID", nullable = false)
            }, inverseJoinColumns = {
                @JoinColumn(name = "PRODUCT_ID", nullable = false)
            }
    )
    private Set<Product> products;

    public Tag() {
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProdToTag> getProdToTags1() {
        return this.prodToTags1;
    }

    public void setProdToTags1(List<ProdToTag> prodToTags1) {
        this.prodToTags1 = prodToTags1;
    }

    public ProdToTag addProdToTags1(ProdToTag prodToTags1) {
        getProdToTags1().add(prodToTags1);
        prodToTags1.setTag1(this);

        return prodToTags1;
    }

    public ProdToTag removeProdToTags1(ProdToTag prodToTags1) {
        getProdToTags1().remove(prodToTags1);
        prodToTags1.setTag1(null);

        return prodToTags1;
    }

    public List<ProdToTag> getProdToTags2() {
        return this.prodToTags2;
    }

    public void setProdToTags2(List<ProdToTag> prodToTags2) {
        this.prodToTags2 = prodToTags2;
    }

    public ProdToTag addProdToTags2(ProdToTag prodToTags2) {
        getProdToTags2().add(prodToTags2);
        prodToTags2.setTag2(this);

        return prodToTags2;
    }

    public ProdToTag removeProdToTags2(ProdToTag prodToTags2) {
        getProdToTags2().remove(prodToTags2);
        prodToTags2.setTag2(null);

        return prodToTags2;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
