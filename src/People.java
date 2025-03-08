public class People {
    protected String identifier;
    protected String name;
    protected String address;

    public People(String identifier, String firstName, String lastName, String city, String state, String zipCode) {
        this.identifier = identifier;
        this.name = firstName + " " + lastName;
        this.address = city + ", " + state + " " + zipCode;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAddress() {
        return address;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAddress(String city, String state, String zipCode) {
        this.address = city + ", " + state + " " + zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
    }
}
