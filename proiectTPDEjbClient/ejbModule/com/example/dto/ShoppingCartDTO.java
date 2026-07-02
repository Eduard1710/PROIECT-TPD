package com.example.dto;

public class ShoppingCartDTO {
	private int id;
	private boolean isValid;
	private int productQuantity;
	private ProductDTO product;
	private UserDTO user;
	private double totalPrice;

	public ShoppingCartDTO(boolean isValid, int productQuantity, ProductDTO product, UserDTO user) {
		super();
		this.isValid = isValid;
		this.productQuantity = productQuantity;
		this.product = product;
		this.user = user;
	}

	public ShoppingCartDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "ShoppingCartDTO [id=" + id + ", isValid=" + isValid + ", productQuantity=" + productQuantity
				+ ", product=" + product + ", user=" + user + ", totalPrice=" + totalPrice + "]";
	}
}