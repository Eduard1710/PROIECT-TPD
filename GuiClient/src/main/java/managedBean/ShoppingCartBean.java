package managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.example.dao.ShoppingCartDAORemote;
import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.dto.UserDTO;

@Named(value = "shoppingCartBean")
@SessionScoped
public class ShoppingCartBean implements Serializable {

	private static final long serialVersionUID = 1L;

	ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
	List<ShoppingCartDTO> shoppingCart = new ArrayList<>();
	private int numberOfProducts;
	private double shoppingCartTotal;

	@EJB
	ShoppingCartDAORemote shoppingCartDAORemote;

	UserDTO userDTO;

	public ShoppingCartDTO getShoppingCartDTO() {
		return shoppingCartDTO;
	}

	@PostConstruct
	public void init() {
		shoppingCartDTO = new ShoppingCartDTO();
		shoppingCart = shoppingCartDAORemote.findAll();
		for (ShoppingCartDTO shoppingCartDTO : shoppingCart) {
			shoppingCartDTO
					.setTotalPrice(shoppingCartDTO.getProduct().getPrice() * shoppingCartDTO.getProductQuantity());
			numberOfProducts += shoppingCartDTO.getProductQuantity();
			shoppingCartTotal += shoppingCartDTO.getProduct().getPrice() * shoppingCartDTO.getProductQuantity();
		}
	}

	public void setShoppingCartDTO(ShoppingCartDTO shoppingCartDTO) {
		this.shoppingCartDTO = shoppingCartDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public List<ShoppingCartDTO> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<ShoppingCartDTO> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	public double getShoppingCartTotal() {
		return shoppingCartTotal;
	}

	public void setShoppingCartTotal(double shoppingCartTotal) {
		this.shoppingCartTotal = shoppingCartTotal;
	}

	public String addProductToShoppingCart(ProductShoppingCartDTO product) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			userDTO = (UserDTO) facesContext.getExternalContext().getSessionMap().get("userDTO");

			shoppingCartDTO.setUser(userDTO);

			Optional<ShoppingCartDTO> existingProduct = shoppingCart.stream()
					.filter(p -> p.getProduct().getId() == product.getId()).findFirst();

			if (existingProduct.isPresent()) {
				ShoppingCartDTO existingCartItem = existingProduct.get();
				existingCartItem
						.setProductQuantity(existingCartItem.getProductQuantity() + product.getDesiredQuantity());
				shoppingCartDAORemote.update(existingCartItem);
			} else {
				shoppingCartDTO = shoppingCartDAORemote.addProductToShoppingCart(product, shoppingCartDTO);
			}

			return "/customer/shopProducts.xhtml?faces-redirect=true&shopId=" + product.getShop().getId();

		} catch (Exception e) {
			facesContext.addMessage("createShopForm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database error: " + e.getMessage(), null));
			return "/customer/customer.xhtml?faces-redirect=true";
		}
	}

	public String remove(ShoppingCartDTO product) {
		shoppingCartDAORemote.delete(product.getId());
		numberOfProducts -= product.getProductQuantity();
		shoppingCartTotal -= product.getProductQuantity() * product.getProduct().getPrice();
		shoppingCart = shoppingCartDAORemote.findAll();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String increment(ShoppingCartDTO product) {
		product.setProductQuantity(product.getProductQuantity() + 1);
		product.setTotalPrice(product.getTotalPrice() + product.getProduct().getPrice());
		product = shoppingCartDAORemote.update(product);
		numberOfProducts += 1;
		shoppingCartTotal += product.getProduct().getPrice();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}

	public String decrement(ShoppingCartDTO product) {
		if (product.getProductQuantity() - 1 == 0) {
			shoppingCartDAORemote.delete(product.getId());
			numberOfProducts -= 1;
			shoppingCartTotal -= product.getProduct().getPrice();
			product.setTotalPrice(product.getTotalPrice() - product.getProduct().getPrice());
			shoppingCart = shoppingCartDAORemote.findAll();
			return "/customer/shoppingCart.xhtml?faces-redirect=true";
		}
		if (product.getProductQuantity() > 0) {
			product.setProductQuantity(product.getProductQuantity() - 1);
			numberOfProducts -= 1;
			shoppingCartTotal -= product.getProduct().getPrice();
			product.setTotalPrice(product.getTotalPrice() - product.getProduct().getPrice());
			product = shoppingCartDAORemote.update(product);
			return "/customer/shoppingCart.xhtml?faces-redirect=true";
		}

		return null;
	}

	public String clear() {
		for (ShoppingCartDTO shoppingCartDTO : shoppingCart) {
			shoppingCartDAORemote.delete(shoppingCartDTO.getId());
		}
		numberOfProducts = 0;
		shoppingCartTotal = 0;
		shoppingCart = shoppingCartDAORemote.findAll();
		return "/customer/shoppingCart.xhtml?faces-redirect=true";
	}
	
	public String placeOrder() {
		return "/customer/receiver.xhtml?faces-redirect=true";
	}
}
