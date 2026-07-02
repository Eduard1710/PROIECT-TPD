package com.example.util;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.dto.ProductShoppingCartDTO;
import com.example.dto.ReceiverDTO;
import com.example.dto.RoleDTO;
import com.example.dto.ShopDTO;
import com.example.dto.ShoppingCartDTO;
import com.example.dto.UserDTO;

import model.Category;
import model.Product;
import model.Receiver;
import model.Role;
import model.Shop;
import model.Shoppingcart;
import model.User;

public class DtoToEntity {

	// all classes doesn't take primary key in account

	public Role convertRole(RoleDTO roleDTO) {
		Role role = new Role(roleDTO.getName());
		role.setId(roleDTO.getId());
		return role;
	}

	public User convertUser(UserDTO userDTO) {
		User user = new User(userDTO.getEmail(), userDTO.getPassword(), userDTO.getFirstname(), userDTO.getLastname(),
				convertRole(userDTO.getRole()));
		user.setApproved(userDTO.isApproved());
		user.setId(userDTO.getId());
		return user;
	}

	public Shop convertShop(ShopDTO shopDTO) {
		Shop shop = new Shop(shopDTO.getName(), shopDTO.getAddress(), shopDTO.getContact(),
				convertUser(shopDTO.getUser()));

		shop.getUser().setId(shopDTO.getUser().getId());

		return shop;
	}

	public Category convertCategory(CategoryDTO categoryDTO) {
		Category category = new Category(categoryDTO.getName());

		return category;
	}

	public Product convertProduct(ProductDTO productDTO) {
		Product product = new Product(productDTO.getName(), productDTO.getDescription(),
				convertCategory(productDTO.getCategory()), convertShop(productDTO.getShop()), productDTO.getPrice(),
				productDTO.getQuantity());

		product.setId(productDTO.getId());
		product.getCategory().setId(productDTO.getCategory().getId());
		product.getShop().setId(productDTO.getShop().getId());
		return product;
	}

	public Shoppingcart convertShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		System.out.print("convertShoppingCart:       " + shoppingCartDTO);
		Shoppingcart globalShop = new Shoppingcart(shoppingCartDTO.getIsValid(), shoppingCartDTO.getProductQuantity(),
				convertProduct(shoppingCartDTO.getProduct()), convertUser(shoppingCartDTO.getUser()));

		globalShop.setId(shoppingCartDTO.getId());
		globalShop.setUser(convertUser(shoppingCartDTO.getUser()));
		System.out.print("Shoppingcart globalShop:    " + globalShop);
		return globalShop;
	}

	public Shoppingcart convertShoppingCartWithProduct(ProductShoppingCartDTO productDTO,
			ShoppingCartDTO shoppingCartDTO) {
		System.out.print("convertShoppingCart:       " + shoppingCartDTO);
		Product product = new Product(productDTO.getName(), productDTO.getDescription(),
				convertCategory(productDTO.getCategory()), convertShop(productDTO.getShop()), productDTO.getPrice(),
				productDTO.getQuantity());

		product.setId(productDTO.getId());

		Shoppingcart globalShop = new Shoppingcart(shoppingCartDTO.getIsValid(), productDTO.getDesiredQuantity(),
				product, convertUser(shoppingCartDTO.getUser()));

		globalShop.setId(shoppingCartDTO.getId());
		globalShop.setUser(convertUser(shoppingCartDTO.getUser()));
		System.out.print("Shoppingcart globalShop:    " + globalShop);
		return globalShop;
	}

	public Receiver convertReceiver(ReceiverDTO receiverDTO) {
		Receiver receiver = new Receiver(receiverDTO.getAddress(), receiverDTO.getFirstname(),
				receiverDTO.getLastname());
		receiver.setId(receiverDTO.getId());
		return receiver;
	}
}
