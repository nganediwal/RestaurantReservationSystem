import java.util.List;

class MenuItem {
    private String name;
    private int price;
    private List<String> ingredients;
    private int quantity;

    public MenuItem(String name, int price, List<String> ingredients, int quantity) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
