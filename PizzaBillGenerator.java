import java.util.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;

class Pizza {
    private String name;
    private double price;

    public Pizza(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class OrderItem {
    private Pizza pizza;
    private int quantity;

    public OrderItem(Pizza pizza, int quantity) {
        this.pizza = pizza;
        this.quantity = quantity;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemTotal() {
        return pizza.getPrice() * quantity;
    }
}

class Customer {
    private String name;
    private String address;
    private String phoneNumber;
    private int loyaltyPoints;

    public Customer(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = 0;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void addLoyaltyPoints(int points) {
        loyaltyPoints += points;
    }
}

class Order {
    private List<OrderItem> orderItems;
    private Customer customer;

    public Order(Customer customer) {
        orderItems = new ArrayList<>();
        this.customer = customer;
    }

    public void addItem(OrderItem item) {
        orderItems.add(item);
    }

    public void removeItem(OrderItem item) {
        orderItems.remove(item);
    }

    public double getTotalBill() {
        double totalBill = 0.0;
        for (OrderItem item : orderItems) {
            totalBill += item.getItemTotal();
        }
        return totalBill;
    }

    public void printBill() {
        System.out.println("----------- Bill -----------");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Phone Number: " + customer.getPhoneNumber());
        System.out.println("Loyalty Points: " + customer.getLoyaltyPoints());
        System.out.println("----------------------------");
        for (OrderItem item : orderItems) {
            System.out.println(item.getPizza().getName() + " - Quantity: " + item.getQuantity()
                    + " - Price: $" + item.getPizza().getPrice() + " - Total: $" + item.getItemTotal());
        }
        System.out.println("----------------------------");
        System.out.println("Total Bill: $" + getTotalBill());
    }
}

class PizzaBillGenerator {
    public static void main(String[] args) {
        // Create pizza menu
        List<Pizza> pizzaMenu = new ArrayList<>();
        pizzaMenu.add(new Pizza("Margherita", 8.99));
        pizzaMenu.add(new Pizza("Pepperoni", 9.99));
        pizzaMenu.add(new Pizza("Vegetarian", 10.99));
        pizzaMenu.add(new Pizza("Hawaiian", 11.99));

        // Get customer information
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Customer Phone Number: ");
        String phoneNumber = scanner.nextLine();

        // Create customer object
        Customer customer = new Customer(name, address, phoneNumber);

        // Create order
        Order order = new Order(customer);

        int choice;
        int quantity;
        boolean isFinished = false;

        while (!isFinished) {
            System.out.println("------- Pizza Menu -------");
            for (int i = 0; i < pizzaMenu.size(); i++) {
                System.out.println((i + 1) + ". " + pizzaMenu.get(i).getName() + " - $" + pizzaMenu.get(i).getPrice());
            }
            System.out.println("0. Finish Order");

            System.out.print("Enter choice (0-" + pizzaMenu.size() + "): ");
            choice = scanner.nextInt();

            if (choice > 0 && choice <= pizzaMenu.size()) {
                System.out.print("Enter quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();

                Pizza selectedPizza = pizzaMenu.get(choice - 1);
                OrderItem item = new OrderItem(selectedPizza, quantity);
                order.addItem(item);
                System.out.println("Added " + quantity + " " + selectedPizza.getName() + " to the order.");
                System.out.println();
            } 
            else if (choice == 0) {
                isFinished = true;
            }
        }

        // Apply loyalty points
        int loyaltyPoints = customer.getLoyaltyPoints();
        double totalBill = order.getTotalBill();
        double discount = loyaltyPoints * 0.01; // Assuming 1 point is equivalent to a 1% discount

        if (discount > totalBill) {
            discount = totalBill;
        }

        // Update the final bill after applying the loyalty discount
        double finalBill = totalBill - discount;

        // Print final bill
        order.printBill();
        System.out.println("Loyalty Points Applied: " + loyaltyPoints);
        System.out.println("Discount Applied: $" + discount);
        System.out.println("Final Bill: $" + finalBill);

        // Calculate loyalty points earned based on the final bill
        int earnedPoints = (int) Math.floor(finalBill / 10); // Assuming 1 point is earned for every $10 spent

        // Update the loyalty points for the customer
        customer.addLoyaltyPoints(earnedPoints);
        System.out.println("Loyalty Points Earned: " + earnedPoints);

        System.out.println("Thank you for using the Pizza Bill Generator!");
    }
}
