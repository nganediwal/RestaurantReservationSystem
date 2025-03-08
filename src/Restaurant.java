import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Restaurant {
    private String name;
    private String unifiedLicenseIdentifier;
    private String address; // Combined city, state, and zipCode
    private int rating;
    private boolean top10;
    private int capacity;
    private List<MenuItem> menuItems;
    private List<Reservation> activeReservations;

    private List<Customer> customers;

    public Restaurant(String name, String unifiedLicenseIdentifier, String city, String state, String zipCode,
                      int rating, boolean top10, int capacity) {
        this.name = name;
        this.unifiedLicenseIdentifier = unifiedLicenseIdentifier;
        this.address = city + ", " + state + " " + zipCode;
        this.rating = rating;
        this.top10 = top10;
        this.capacity = capacity;
        this.menuItems = new ArrayList<>();
        this.activeReservations = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnifiedLicenseIdentifier() {
        return unifiedLicenseIdentifier;
    }

    public void setUnifiedLicenseIdentifier(String unifiedLicenseIdentifier) {
        this.unifiedLicenseIdentifier = unifiedLicenseIdentifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String city, String state, String zipCode) {
        this.address = city + ", " + state + " " + zipCode;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isTop10() {
        return top10;
    }

    public void setTop10(boolean top10) {
        this.top10 = top10;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Reservation> getActiveReservations() {
        return activeReservations;
    }

    public void setActiveReservations(List<Reservation> activeReservations) {
        this.activeReservations = activeReservations;
    }

    public boolean confirmReservation(String customerId, int partySize, String date, String time) {
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return false;
        }
        // Check if table is available within +/- 2 hours of the requested time
        if (checkTableAvailability(date, time, partySize)) {
            Reservation reservation = new Reservation(name, partySize, date, time, 5 * partySize, true);
            activeReservations.add(reservation);
            customer.addReservation(reservation);
            return true;
        }
        return false;
    }

    public Customer findCustomerById(String customerId) {
        for (Customer customer: customers) {
            if (customer.getIdentifier().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    public void handleCustomerArrival(String customerId, String restaurantName, String date, String arrivalTime, String reservationTime, int creditAmount) {
        Customer customer = findCustomerById(customerId);
        Reservation reservation = customer.findReservation(restaurantName, date, reservationTime);

        // add reservation.isActive() clause
        if (reservation != null) { // reservation exists in system
            if (withinTimeFrame(reservation.getTime(), arrivalTime)) { // Case A, arrival within acceptable window
                System.out.println(customer.name + " - Successfully completed reservation");
                System.out.println("Customer " + customerId + " (" + customer.getName() + ") " +  "has arrived at " + restaurantName);
                System.out.println("Customer has successfully made the reservation.");
                customer.addCredits(creditAmount);
                seatCustomer(customer);
                reservation.setActive(false); // reservation completed, so not active and remove from activeReservations
                activeReservations.remove(reservation);
            } else if (missedReservationTime(arrivalTime, reservation.getTime())) { // Case B, reservation but not within acceptable window
                System.out.println(customer.name + " - Missed reservation");
                System.out.println("Customer " + customerId + " (" + customer.getName() + ") " +  "has arrived late at " + restaurantName);
                System.out.println("No credits rewarded and 1 miss added");
//                reservation.setActive(false);
//                activeReservations.remove(reservation);
                customer.incrementMissesCount();
                if (customer.getMissesCount() >= 3) {
                    System.out.println("Misses: " + customer.getMissesCount());
                    System.out.println(customer.name + " - 3 Misses reached, both misses and credits will reset back to 0.");
                    customer.resetCredits();
                    customer.resetMissesCount();
                }
                // if table available then seat customer but reward no credits
                if (checkTableAvailability(date, arrivalTime, customer.getPartySize())) {
                    seatCustomer(customer);
                }
            } else { //earlier than 30 minutes:
                System.out.println("Customer " + customerId + " (" + customer.getName() + ") " +  "has arrived early at " + restaurantName);
                System.out.println("Please come back during the reservation window.");
                System.out.println("No credits rewarded and no misses added");
            }

        } else { // Case C, no reservation
            System.out.println(customer.name + " - Walk-in party");
            // if table available then seat customer but reward no credits
            if (checkTableAvailability(date, arrivalTime, customer.getPartySize())) {
//                System.out.println(customer.name + " - Successfully completed");
                System.out.println("No reservation, however open table so request validated.");
                System.out.println("No credits rewarded and no misses added");
                seatCustomer(customer);
            } else {
                System.out.println(customer.name + " - Request denied");
            }
        }
        System.out.println("Credits: " + customer.getCredits());
        System.out.println("Misses: " + customer.getMissesCount());
    }

    private boolean withinTimeFrame(String reservationTime, String currentTime) {
        // Implement logic to check if the current time is within 30 minutes before to 15 minutes after the reservation time
        int reservationHour = Integer.parseInt(reservationTime.split(":")[0]); //extract hour component
        int reservationMinute = Integer.parseInt(reservationTime.split(":")[1]); //extract minute component
        int currentHour = Integer.parseInt(currentTime.split(":")[0]);
        int currentMinute = Integer.parseInt(currentTime.split(":")[1]);

        int reservationTimeInMinutes = reservationHour * 60 + reservationMinute;
        int currentTimeInMinutes = currentHour * 60 + currentMinute;

        return currentTimeInMinutes >= (reservationTimeInMinutes - 30) && currentTimeInMinutes <= (reservationTimeInMinutes + 15);
    }

    private boolean missedReservationTime(String time1, String time2) {
        LocalTime startTime = LocalTime.parse(time2);
        LocalTime endTime = LocalTime.parse(time1);

        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes() > 15; //more than 15 minutes after
    }


    // check if there is a table available with no reservation within a 2 hour window
    private boolean checkTableAvailability(String date, String time, int partySize) {
        int availableCapacity = this.capacity;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime requestedTime = LocalTime.parse(time, formatter);

        for (Reservation reservation : activeReservations) {
            if (reservation.getDate().equals(date)) {
                LocalTime reservationTime = LocalTime.parse(reservation.getTime(), formatter);
                Duration duration = Duration.between(reservationTime, requestedTime);
                long differenceInMinutes = Math.abs(duration.toMinutes());

                // Check if the times are within a 2-hour window (120 minutes)
                if (differenceInMinutes <= 120) {
                    availableCapacity -= reservation.getPartySize();
                }
            }
        }
        return availableCapacity >= partySize;
    }




    private void seatCustomer(Customer customer) {
        System.out.println("Seats were available. " + customer.name +  " seated.");
    }

    public double retrieveTotalBill(List<MenuItem> orderList) {
        double totalBill = 0.0;
        for (MenuItem item: orderList) {
            totalBill += item.getPrice();
        }
        return totalBill;
    }

    // calculate revenue by summing up order list for all reservations
    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        for (Reservation reservation: activeReservations) {
            totalRevenue += retrieveTotalBill(reservation.getOrderList());
        }
        return totalRevenue;
    }

    public void cancelReservation(Customer customer, int partySize, String date, String time) {
        Reservation cancel = null;
        for (Reservation res: activeReservations) {
            if (res.getRestaurant().equals(this.name) &&
                    res.getDate().equals(date) &&
                    res.getTime().equals(time) &&
                    res.getPartySize() == partySize &&
                    res.isActive()) {
                cancel = res;
                break;
            }
        }
        if (cancel != null) {
            cancel.cancel(); //change active state to false
            activeReservations.remove(cancel); //remove that reservation from active reservations
        }
    }
    // Static method to determine top 10 restaurants
    public static void updateTop10Status(List<Restaurant> restaurants) {
        Collections.sort(restaurants, new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return Integer.compare(r2.getRating(), r1.getRating()); // Descending order of rating
            }
        });
        // Reset all to not top 10
        for (Restaurant restaurant : restaurants) {
            restaurant.setTop10(false);
        }
        // Set top 10 based on sorted order
        for (int i = 0; i < Math.min(10, restaurants.size()); i++) {
            restaurants.get(i).setTop10(true);
        }
    }
}
