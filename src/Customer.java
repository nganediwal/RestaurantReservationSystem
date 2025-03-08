import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Customer extends People {
    private List<String> tags;
    private int missesCount;
    private int credits;
    private double currentFunds;
    private List<MenuItem> orderList;
    private double currentBills;
    private List<Reservation> reservationsMade; //list of active reservations
    private int currentPartySize; // Added to track the current party size

    private List<Restaurant> restaurants;


    // Constructor or method to set the list of restaurants
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Customer(String identifier, String firstName, String lastName, String city, String state, String zipCode,
                    double currentFunds) {
        super(identifier, firstName, lastName, city, state, zipCode);
        this.tags = new ArrayList<>();
        this.missesCount = 0;
        this.credits = 0;
        this.currentFunds = currentFunds;
        this.orderList = new ArrayList<>();
        this.currentBills = 0.0;
        this.reservationsMade = new ArrayList<>();
        this.currentPartySize = 0; // Initialize to 0
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }

    public void requestReservation(String customerId, String restaurantName, int tableSize, String date, String time) {
        Restaurant restaurant = findRestaurantByName(restaurantName);

        Customer customer = restaurant.findCustomerById(customerId);
        System.out.println("Reservation requested for " + customer.name);

        if (restaurant == null) {
            System.out.println("Restaurant not found: ");
            return;
        }
        if (!checkCustomerReservations(customer, date, time)) {
            System.out.println("Request denied. Customer already has reservation with another restaurant within 2 hours of the requested time");
            return;
        }
        boolean confirmed = false;
        confirmed = restaurant.confirmReservation(this.identifier, tableSize, date, time);

        if (confirmed) {
            System.out.println("Reservation confirmed.");
            System.out.println("Reservation made for " + customerId + " (" + customer.getName() + ")" + " at " + restaurantName);
        } else {
            System.out.println("Reservation request denied. There is another active reservation for the table within 2 hours of the requested time");
        }
    }
    public boolean checkCustomerReservations(Customer customer, String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime requestedTime = LocalTime.parse(time, formatter);

        for (Reservation reservation : customer.reservationsMade) {
            if (reservation.getDate().equals(date)) {
                LocalTime reservationTime = LocalTime.parse(reservation.getTime(), formatter);
                Duration duration = Duration.between(requestedTime, reservationTime);
                long differenceInMinutes = Math.abs(duration.toMinutes());

                if (differenceInMinutes <= 120) { // Check if times within a 2-hour window (120 minutes)
                    return false; // Found a conflicting reservation
                }
            }
        }
        return true; // No conflicting reservations found
    }

    public Restaurant findRestaurantByName(String restaurantName) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }
    public void orderItem(MenuItem item) {
        if (this.currentFunds - item.getPrice() >= 0) {
            this.orderList.add(item);
            this.currentFunds -= item.getPrice();
            this.currentBills += item.getPrice();
        } else {
            System.out.println("Not enough funds");
        }
    }

    public void payBill() {
        this.currentBills = 0.0; //set currentBills to 0
    }

    public void cancelReservation(Restaurant restaurant, int tableSize, String date, String time) {
        restaurant.cancelReservation(this, tableSize, date, time);
    }

    public void incrementMissesCount() {
        this.missesCount++;
    }

    public void resetMissesCount() {
        this.missesCount = 0;
    }

    public void resetCredits() {
        this.credits = 0;
    }

    public void addCredits(int amount) {
        this.credits += amount;
        System.out.println("Full credits rewarded");
    }

    public Reservation findReservation(String restaurantName, String date, String time) {
        for (Reservation reservation: reservationsMade) {
            if (reservation.getRestaurantName().equals(restaurantName) && reservation.getDate().equals(date) && reservation.getTime().equals(time)) {
                return reservation;
            }
        }
        return null;
    }

    public void addReservation(Reservation reservation) {
        this.reservationsMade.add(reservation);
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getMissesCount() {
        return missesCount;
    }

    public void setMissesCount(int missesCount) {
        this.missesCount = missesCount;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getCurrentFunds() {
        return currentFunds;
    }

    public void setCurrentFunds(double currentFunds) {
        this.currentFunds = currentFunds;
    }

    public List<MenuItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MenuItem> orderList) {
        this.orderList = orderList;
    }

    public double getCurrentBills() {
        return currentBills;
    }

    public void setCurrentBills(double currentBills) {
        this.currentBills = currentBills;
    }

    public List<Reservation> getReservationsMade() {
        return reservationsMade;
    }

    public void setReservationsMade(List<Reservation> reservationsMade) {
        this.reservationsMade = reservationsMade;
    }
    public int getPartySize() {
        return currentPartySize;
    }

    public void setPartySize(int partySize) {
        this.currentPartySize = partySize;
    }
}
