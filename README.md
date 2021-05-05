# scylla_shopping
ScyllaDB Shopping Cart

The original goal was to create a shopping cart Java application for ScyllaDB, using the ScyllaDB Java Driver.
For various reasons, this version is using the original DataStax Java Driver.
We will refactor this for the next version.
There are only a few classes and interfaces at work to accomplish this, and the ScyallaDB instance is in the Cloud.

PART I: Description of contents

In ScyllaDB I created:

1. A shopping keyspace: CREATE KEYSPACE IF NOT EXISTS shopping WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'AWS_US_EAST_1' : 3 }
2. A carts table:       CREATE TABLE carts (id text PRIMARY KEY, cart list<frozen <tuple<text, text, float>>>, total float, isCheckedOut boolean);

The Java types I created are:

1. class CartService :: HAS-A DAO to work with ScyllaDB, and manage Carts.

2. class Cart :: This is the Java side of the Cart which will be persisted to ScyllaDB.

3. class Item :: What you want to purchase (put in the Cart)

4. interface ScyllaDAO :: Contract for what it means to work with ScyllaDB.

5. class ScyllaDAOImpl :: Implements the DAO contract.

6. Two test classes to make sure this all works: TestFramework.java and another class to test the connection to the cluster.

I put some Object to ScyllaDB Mapping (OSDBM) code in this project. We used to use ORM for Object Relational Mapping, but this is not a relational database.
So the code I put here is to map a Java object to a ScyllaDB way of looking at life, basically an Adapter pattern.
If you have any questions, please reach out to me directly. All code is by Laurent Weichberger: ompoint (at) gmail (dot) com.

PART II: Instructions to Run

To run the ScyllaDB Shopping Cart example code use the class: /src/main/java/com/scylladb/shop/TestFramework.java
You can modify the contents of the TestFramework.java main() method, by commenting and uncommenting existing code, or writing new code to test the CartService, Cart, Item, and etc.

Run Examples provided below:

Example 1: Create and test Item object (Carts contain Items):

Item i1 = new Item("SKU-75299", "LEGO Star Wars: The Mandalorian Trouble on Tatooine 75299 Awesome Toy Building Kit for Kids Featuring The Child, New 2021 (277 Pieces)", 29.99F);
		
Item i2 = new Item("SKU-42122", "LEGO Technic Jeep Wrangler 42122; an Engaging Model Building Kit for Kids Who Love High-Performance Toy Vehicles, New 2021 (665 Pieces)", 49.95F);

Use toString() to see the contents:
System.out.println("Item 1 toString(): " + i1);
System.out.println("Item 2 toString(): " + i2);

Use toTuple() to see what will be sent to ScyllaDB:
System.out.println("Item 1 toTuple(): " + i1.toTuple());
System.out.println("Item 2 toTuple(): " + i2.toTuple());

Example 2: Create and test  Cart ojbect:
Cart c1 = new Cart("ompoint@gmail.com"); // Laurent's cart
		
c1.addItem(i1);
c1.addItem(i2);

// Write cart contents to System.out
c1.displayCart(); // Should be empty to test Service now...
		
// Test isCheckedOut ... should say false
System.out.println("Is the cart c1 checked out?: " + c1.isCheckedOut());
		
//Checkout the cart (currently this gives the total cost of the cart that's all):
float cost = c1.checkOut();
System.out.println("Cost of the cart belonging to: " + c1.getId() + ", is: $" + cost);
		
// Test remove Item from cart:
System.out.println("REMOVE ITEM TEST: ");
c1.displayCart();

// Remove that Jeep
c1.removeItem(i2);

// Jeep should be gone
c1.displayCart();

Example 3: Create and test CartService (which HAS-a ScyllaDAO)

System.out.println("\n\n\n*** CartService Test with DAO implemented *** \n\n\n");
CartService service = new CartService();
		
// Service has a display cart let's test it:
service.displayCart(c1);
service.addItemToCart(i1, c1);
service.addItemToCart(i2, c1);
service.displayCart(c1);

Example 4: Persist the Cart to ScyllaDB

/* TEST THE DAO */
System.out.println("INFO: About to persist using live connection to ScyllaDB Cloud...");
		
service.persist(c1);
		
// Remove the Jeep using the service:
service.removeItemFromCart(i2, c1);
service.displayCart(c1);
service.displayCarts();
		
Example 5: Close the CartService:		
service.close();

If additional information is needed please open an issue and I will resolve it ASAP.

Om.

Laurent
