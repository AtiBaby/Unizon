package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import java.util.Set;

/**
 * The persistent class for the TAG database table.
 *
 */
@Entity
@Table(name = "TAG")
@NamedQueries({
	@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
	@NamedQuery(name = "Tag.findByNameStartingWith", query = "SELECT t FROM Tag t WHERE t.name LIKE :term"),
	@NamedQuery(name = "Tag.findByName", query = "SELECT t FROM Tag t WHERE t.name = :name")
})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TAG_ID", unique = true, nullable = false)
    private int tagId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.tagId;
        hash = 53 * hash + Objects.hashCode(this.name);
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
        final Tag other = (Tag) obj;
        if (this.tagId != other.tagId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
