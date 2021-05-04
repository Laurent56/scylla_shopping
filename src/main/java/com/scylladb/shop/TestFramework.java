package com.scylladb.shop;

public class TestFramework {

	public static void main(String[] args) {
		
		/* TEST ITEMS */
		
		Item i1 = new Item("SKU-75299", "LEGO Star Wars: The Mandalorian Trouble on Tatooine 75299 Awesome Toy Building Kit for Kids Featuring The Child, New 2021 (277 Pieces)", 29.99F);
		
		Item i2 = new Item("SKU-42122", "LEGO Technic Jeep Wrangler 42122; an Engaging Model Building Kit for Kids Who Love High-Performance Toy Vehicles, New 2021 (665 Pieces)", 49.95F);
		
		//System.out.println("Item 1 toString(): " + i1);
		//System.out.println("Item 2 toString(): " + i2);
		
		//System.out.println("Item 1 toTuple(): " + i1.toTuple());
		//System.out.println("Item 2 toTuple(): " + i2.toTuple());
		
		/* TEST CART */
		
		Cart c1 = new Cart("ompoint@gmail.com"); // Laurent's cart
		Cart c2 = new Cart("tzach@scylladb.com"); // Tzach's cart (not used yet)
		
		//c1.addItem(i1);
		//c1.addItem(i2);
		
		// Should write cart contents to System.out
		c1.displayCart(); // Should be empty to test Service now...
		
		// Should say false
		System.out.println("Is the cart c1 checked out?: " + c1.isCheckedOut());
		
		//Checkout the cart (currently this gives the total cost of the cart that's all):
		float cost = c1.checkOut();
		System.out.println("Cost of the cart belonging to: " + c1.getId() + ", is: $" + cost);
		
		// Test remove Item from cart:
		//System.out.println("REMOVE ITEM TEST: "); // WORKED!
		
		//c1.displayCart();
		
		// Remove that Jeep
		//c1.removeItem(i2);
		
		// Jeep should be gone
		//c1.displayCart();
		
		/* TEST CART SERVICE :: THIS ALL WORKED with no connection */
		System.out.println("\n\n\n*** CartService Test with DAO implemented *** \n\n\n");
		CartService service = new CartService();
		
		// Service has a display cart let's test it:
		service.displayCart(c1);

		service.addItemToCart(i1, c1);
		service.addItemToCart(i2, c1);

		service.displayCart(c1);

		// Service will persist the cart to ScyllaDB
		/* TEST THE DAO */
		System.out.println("INFO: About to persist using live connection to ScyllaDB Cloud...");
		
		service.persist(c1);
		
		// Remove the Jeep using the service and see if it worked:
		// service.removeItemFromCart(i2, c1);
		// service.displayCart(c1);
		
		service.displayCarts();
		
		System.out.println("SUCCESS!");
		
		service.close();
		
	}
}
