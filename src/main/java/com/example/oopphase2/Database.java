package com.example.oopphase2;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private static AtomicInteger orderIdCounter = new AtomicInteger(1);
    public static ArrayList<Customer> customers = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();

    public Database() {
        savedData();
        addExistingProductsToCategories();
    }

    public void savedData() {
        Admin<Product> admin1=new Admin<Product>(Product.class,"zayed","1234","30/1/2006");
        Admin<Category> admin2=new Admin<Category>(Category.class,"jana","12345","30/11/2006");
        Customer customer1=new Customer("mariam","12344","9/4/2005",500000.0,"New Cairo",Gender.FEMALE);
        Customer customer2=new Customer("hassan","12334","4/7/2005",300000.0,"New Cairo",Gender.MALE);
        Customer customer3=new Customer("Ahmad","12234","18/1/2005",200000.0,"New Cairo",Gender.MALE);

        Category electronicsCategory = new Category("Electronics", "Devices and gadgets");
        Category fashionCategory = new Category("Fashion", "Clothing and accessories");
        Category homeAppliancesCategory = new Category("Home Appliances", "Appliances for home use");
        Category gamingCategory = new Category("Gaming", "Gaming consoles and accessories");
        Category sportsCategory = new Category("Sports", "Sports equipment and accessories");
        Product laptop = new Product(1, "Laptop", "High-performance laptop", 30000.0, 10, "Electronics", "src/main/resources/com/example/oopphase2/Images/Laptop.png");
        Product mobile = new Product(2, "Mobile", "Smartphone", 35000.0, 15, "Electronics", "src/main/resources/com/example/oopphase2/Images/Mobile.png");
        Product cap = new Product(3, "Baseball Cap", "Stylish baseball cap", 300.0, 35, "Fashion", "src/main/resources/com/example/oopphase2/Images/BaseballCap.png");
        Product tshirt = new Product(4, "T-Shirt", "Cotton T-shirt", 500.0, 50, "Fashion", "src/main/resources/com/example/oopphase2/Images/Tshirt.png");
        Product jeans = new Product(5, "Jeans", "Denim jeans", 1200.0, 30, "Fashion", "src/main/resources/com/example/oopphase2/Images/Jeans.png");
        Product shoes = new Product(6, "Shoes", "Comfortable shoes", 2500.0, 20, "Fashion", "src/main/resources/com/example/oopphase2/Images/Shoes.png");
        Product microwave = new Product(7, "Microwave Oven", "Compact microwave oven", 4500.0, 20, "Home Appliances", "src/main/resources/com/example/oopphase2/Images/microwave.png");
        Product vacuumCleaner = new Product(8, "Vacuum Cleaner", "Efficient vacuum cleaner", 3500.0, 10, "Home Appliances", "src/main/resources/com/example/oopphase2/Images/vaccumcleaner.png");
        Product gamingConsole = new Product(9, "Gaming Console", "Next-gen gaming console", 20000.0, 15, "Gaming", "src/main/resources/com/example/oopphase2/Images/gamingconsole.png");
        Product gamingHeadset = new Product(10, "Gaming Headset", "Immersive surround sound headset", 3000.0, 25, "Gaming", "src/main/resources/com/example/oopphase2/Images/gamingheadset.png");
        Product football = new Product(11, "Football", "Standard size football", 800.0, 20, "Sports", "src/main/resources/com/example/oopphase2/Images/football.png");
        Product tennisRacket = new Product(12, "Tennis Racket", "Lightweight tennis racket", 14500.0, 15, "Sports", "src/main/resources/com/example/oopphase2/Images/tennisracket.png");
        Product squashRacket = new Product(13, "Squash Racket", "High-performance racket", 11000.0, 10, "Sports", "src/main/resources/com/example/oopphase2/Images/squashracket.png");

        categories.add(electronicsCategory);
        categories.add(fashionCategory);
        categories.add(homeAppliancesCategory);
        categories.add(gamingCategory);
        categories.add(sportsCategory);
        products.add(laptop);
        products.add(mobile);
        products.add(cap);
        products.add(tshirt);
        products.add(jeans);
        products.add(shoes);
        products.add(microwave);
        products.add(vacuumCleaner);
        products.add(gamingConsole);
        products.add(gamingHeadset);
        products.add(football);
        products.add(tennisRacket);
        products.add(squashRacket);

    }

    public static void addExistingProductsToCategories() {
        for (Product product : products) {
            for (Category category : categories) {
                if (category.getName().equals(product.getCategory())) {
                    category.addProduct(product);
                }
            }
        }
    }
    public static int generateOrderId() {
        return orderIdCounter.getAndIncrement();
    }
}