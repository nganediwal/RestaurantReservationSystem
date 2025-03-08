import java.util.List;

public class Table {
    private int uniqueIdentifier;
    private int capacity;
    private Restaurant restaurant;
    private List<Reservation> reservationsList;

    // Constructor
    public Table(int uniqueIdentifier, int capacity, Restaurant restaurant, List<Reservation> reservationsList) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.capacity = capacity;
        this.restaurant = restaurant;
        this.reservationsList = reservationsList;
    }

    // Getters and Setters
    public int getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(int uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Reservation> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }

    // Method to check if the table is available at a given time
    public boolean isAvailable(String time) {
        for (Reservation reservation : reservationsList) {
            if (reservation.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }
}



