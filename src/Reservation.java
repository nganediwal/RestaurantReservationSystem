import java.util.List;
import java.util.ArrayList;

public class Reservation {
    private String restaurant; // Combined restaurant name and unique identifier
    private int partySize;
    private String date;
    private String time;
    private int creditAmount;
    private boolean active;
    private List<MenuItem> orderList; //store menu items ordered during reservation

    // Constructor
    public Reservation(String restaurant, int partySize, String date, String time, int creditAmount, boolean active) {
        this.restaurant = restaurant;
        this.partySize = partySize;
        this.date = date;
        this.time = time;
        this.creditAmount = creditAmount;
        this.active = active;
        this.orderList = new ArrayList<>();
    }

    // Getters and Setters
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<MenuItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MenuItem> orderList) {
        this.orderList = orderList;
    }

    public void cancel() {
        this.active = false; //cancel reservation
    }

    public Object getRestaurantName() {
        return this.restaurant;
    }
}
