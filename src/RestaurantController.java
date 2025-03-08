import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class RestaurantController {
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    public void commandLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter commands (type 'exit' to finish):");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            handleCommand(command);
            System.out.println("Enter command: ");
        }
    }

    public void handleCommand(String command) {
        String[] tokens = command.split(", ");
        String action = tokens[0];

        switch (action) {
            case "create_customer":
                createCustomer(tokens);
                break;
            case "create_restaurant":
                createRestaurant(tokens);
                break;
            case "make_reservation":
                makeReservation(tokens);
                break;
            case "customer_arrival":
                customerArrival(tokens);
                break;
            default:
                System.out.println("Invalid command");
        }
    }

    private void createRestaurant(String[] tokens) {
        String name = tokens[1];
        String uniqueId = tokens[2];
        String city = tokens[3];
        String state = tokens[4];
        String zip = tokens[5];
        int rating = Integer.parseInt(tokens[6]);
        boolean top10 = Boolean.parseBoolean(tokens[7]);
        int capacity = Integer.parseInt(tokens[8]);

        Restaurant restaurant = new Restaurant(name, uniqueId, city, state, zip, rating, top10, capacity);
        restaurants.add(restaurant);

        System.out.println("Restaurant created: " + uniqueId + " (" + name + ")" + " - " + restaurant.getAddress());
    }

    private void createCustomer(String[] tokens) {
        String uniqueId = tokens[1];
        String firstName = tokens[2];
        String lastName = tokens[3];
        String city = tokens[4];
        String state = tokens[5];
        String zip = tokens[6];
        double funds = Double.parseDouble(tokens[7]);

        Customer customer = new Customer(uniqueId, firstName, lastName, city, state, zip, funds);
        customers.add(customer);
        for (Restaurant restaurant : restaurants) {
            restaurant.addCustomer(customer); //adds customer to the customers list for that restaurant
        }
        customer.setRestaurants(restaurants); //populate customer list with restaurant objects

        System.out.println("Customer added: " + uniqueId + " - " + firstName + " " + lastName);
    }

    private void makeReservation(String[] tokens) {
        String customerId = tokens[1]; // customer id
        Character custVal = customerId.charAt(customerId.length() - 1);
        int custIndex = custVal - '0';

        String restaurantName = tokens[2];
        int partySize = Integer.parseInt(tokens[3]);
        String date = tokens[4];
        String time = tokens[5];

        Customer customer = customers.get(custIndex - 1);
        customer.requestReservation(customerId, restaurantName, partySize, date, time);

        Reservation reservation = new Reservation(restaurantName, partySize, date, time, 0,true);
        customer.addReservation(reservation);
    }

    private void customerArrival(String[] tokens) {
        String customerId = tokens[1]; // customer id

        String restaurantId = tokens[2];
        Character restVal = restaurantId.charAt(restaurantId.length() - 1);
        int restIndex = restVal - '0';

        String restaurantName = tokens[3]; //which restaurant
        String date = tokens[4];
        String arrivalTime = tokens[5];
        String reservationTime = tokens[6];
        int creditAmount = Integer.parseInt(tokens[7]);

        Restaurant restaurant = restaurants.get(restIndex - 1);

        restaurant.handleCustomerArrival(customerId, restaurantName, date, arrivalTime, reservationTime, creditAmount);
    }
}