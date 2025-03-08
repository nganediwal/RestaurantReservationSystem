import java.util.List;

public class Owner extends People {
    private String beginDate; //when they managed their first restaurant
    private List<String> restaurantGroup;
    private List<String> unifiedLicensesList; //unified identifiers for the restaurants managed

    public Owner(String id, String firstName, String lastName, String city, String state, String zipCode,
                 String beginDate, List<String> restaurantGroup, List<String> unifiedLicensesList) {
        super(id, firstName, lastName, city, state, zipCode);
        this.beginDate = beginDate;
        this.restaurantGroup = restaurantGroup;
        this.unifiedLicensesList = unifiedLicensesList;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public List<String> getRestaurantGroup() {
        return restaurantGroup;
    }

    public void setRestaurantGroup(List<String> restaurantGroup) {
        this.restaurantGroup = restaurantGroup;
    }

    public List<String> getUnifiedLicensesList() {
        return unifiedLicensesList;
    }

    public void setUnifiedLicensesList(List<String> unifiedLicensesList) {
        this.unifiedLicensesList = unifiedLicensesList;
    }

    public double getAverageCurrentPrice(MenuItem menuItem, List<Restaurant> restaurants) {
        int quantity = 0;
        double totalPrice = 0.0;
        for (Restaurant restaurant: restaurants) {
            List<MenuItem> menuItems = restaurant.getMenuItems();
            for (MenuItem item: menuItems) {
                if (item.getName().equals(menuItem.getName())) {
                    quantity++;
                    totalPrice += item.getPrice();
                }
            }
        }
        return quantity > 0 ? totalPrice / quantity : 0.0;
    }
}
