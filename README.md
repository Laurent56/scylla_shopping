# scylla_shopping
ScyllaDB Shopping Cart

The original goal was to create a shopping cart Java application for ScyllaDB, using the ScyllaDB Java Driver.
For various reasons, this version is using the original DataStax Java Driver.
We will refactor this for the next version.
There are only a few classes and interfaces at work to accomplish this, and the ScyallaDB instance is in the Cloud.

The ScyllaDB table I created was:

CREATE TABLE carts (id text PRIMARY KEY, cart list<frozen <tuple<text, text, float>>>, total float, isCheckedOut boolean);

The Java types I created are:

1. class CartService :: HAS-A DAO to work with ScyllaDB, and manage Carts.

2. class Cart :: This is the Java side of the Cart which will be persisted to ScyllaDB.

3. class Item :: What you want to purchase (put in the Cart)

4. interface ScyllaDAO :: Contract for what it means to work with ScyllaDB.

5. class ScyllaDAOImpl :: Implements the DAO contract.

6. Two test classes to make sure this all works.

I put some Object to ScyllaDB Mapping (OSDBM) code in this project. We used to use ORM for Object Relational Mapping, but this is not a relational database.
So the code I put here is to map a Java object to a ScyllaDB way of looking at life, basically an Adapter pattern.
If you have any questions, please reach out to me directly. All code is by Laurent Weichberger: ompoint (at) gmail (dot) com.

Om.

Laurent
