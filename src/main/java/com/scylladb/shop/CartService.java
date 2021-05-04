// All code by Laurent Weichberger ompoint@gmail.com

package com.scylladb.shop;

import java.util.*;

/* Stateless CartService for working with Carts */

public class CartService {
	
	private ScyllaDAO dao = null;
	
	public CartService() {
		
		dao = new ScyllaDAOImpl();
	}
	
	public void addItemToCart(Item item, Cart cart) {
		
		System.out.println("INFO: Service adding item: " + item + "to cart: " + cart.getId());

		cart.addItem(item);		
		
	}
	
	public void removeItemFromCart(Item item, Cart cart) {
		
		System.out.println("INFO: Service removing item: " + item + "from cart: " + cart.getId());
		cart.removeItem(item);		
	}

	
	public void displayCart(Cart c) {
		
		// Display the contents of the cart
		
		c.displayCart();
		
	}
	
	public void persist(Cart c){
		
		dao.connect();
		/* Save this cart to ScyllaDB */
		
		// Get the cart ID from the cart:
		String id = c.getId();
		
		float total = c.checkOut();
		
		c.isCheckedOut(true);
		
		// Get each Item out of the cart and turn it into a tupple, because the ScyllaDB has a cart list<tuple<text, text, float>> 
		
		/* DATA MODEL: One row per cart, cart ID (userID) and its Items */
		
		// Turn the List<Item> from the cart into a list tuple:
		
		List<Item> cart = c.getCart();
		
		StringBuilder sb = new StringBuilder();
        
		ListIterator<Item> iter = cart.listIterator();
			
		int index = 1;
		int size = cart.size();
		
			while (iter.hasNext()) {
				
				Item item = iter.next();
		
				String tuple = item.toTuple();
			
				if (index == size) {
					
					sb.append(tuple); 
					break;
				}
				// because we only need a comma if there are more than one tuples	
				sb.append(tuple + ",");
				
				index++;
			
			}
		
		/* TO DO: TURN THE CART INTO A tuple<text, text, float> for CQL */
		
		/* Example: (“SKU-75299”, “LEGO Star Wars: The Mandalorian Trouble on Tatooine 75299 Awesome Toy Building Kit for Kids Featuring The Child, New 2021 (277 Pieces)”, 29.99) */
		
		//UPSERT
			
		String CQL = "INSERT INTO shopping.carts (id, cart, total, isCheckedOut) VALUES('" + id + "', [" + sb.toString() + "], " + total + ", true);";

		dao.execute(CQL);
	}
	public void displayCarts() {
		
		dao.displayTable("carts");
	}
	protected void close() {
		
		dao.closeSession();
	}
}
