/* Assumption: One cart per user.
 * All code by Laurent Weichberger ompoint@gmail.com
 */
package com.scylladb.shop;
import java.util.*;

public class Cart {
	
	//Unique Cart ID one per customer
	private String m_id = null; // userID
	
	private boolean m_isCheckedOut = false; // until the user checks the cart out
	
	private List<Item> m_cart = new ArrayList<Item>();
	
	private float m_total = 0.0F;
	
	protected Cart(String id) {
		
		m_id = id;
	}
	
	public String getId() {
		return m_id;
	}
	
	// Someone may want the entire cart
	public List<Item> getCart() {
		return m_cart;
	}

	public void addItem(Item item) {
		
		// place the item in the cart
		m_cart.add(item);
	}
	

	// What to remove from the cart
	public void removeItem(Item item) {
		
		// check to see if its there fist
		
		if (m_cart.contains(item)) {
			
			m_cart.remove(item);
		}
		// Removes first instance of this in the List
	}
	
	public void displayCart() {
		
		System.out.println("Cart belongs to: " + this.getId() + ", with item(s):");
		for (Item i : m_cart) {
			
			System.out.println(i);
		}
	}
	public float checkOut() {
				
		/* Calulate total */
		float total = 0.0F;
		
		for (Item item : this.getCart()) {
			
			total = total +	item.getCost();
		}
		m_total = total;
		
		return m_total;
	}	
	
	public boolean isCheckedOut() {
		
		return m_isCheckedOut;
	}
	public void isCheckedOut(boolean checkedOut) {
		
		this.m_isCheckedOut = checkedOut;
	}
	@Override
	public String toString() {
		return "Cart [m_id=" + m_id + ", m_isCheckedOut=" + m_isCheckedOut + ", m_cart=" + m_cart + "]";
	}

}
