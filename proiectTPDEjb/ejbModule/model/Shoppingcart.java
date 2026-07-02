package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the shoppingcart database table.
 * 
 */
@Entity
@Table(name = "shoppingcarts")
@NamedQuery(name = "Shoppingcart.findAll", query = "SELECT s FROM Shoppingcart s")
public class Shoppingcart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean isValid;

	private int productQuantity;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Shoppingcart() {
	}

	public Shoppingcart(boolean isValid, int productQuantity, Product product, User user) {
		super();
		this.isValid = isValid;
		this.productQuantity = productQuantity;
		this.product = product;
		this.user = user;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Shoppingcart [id=" + id + ", isValid=" + isValid + ", productQuantity=" + productQuantity + ", product="
				+ product + ", user=" + user + "]";
	}

}