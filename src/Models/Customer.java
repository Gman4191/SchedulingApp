package Models;

public class Customer {

    /**
     * The customer id
     */
    private int id;
    /**
     * The customer name
     */
    private String name;
    /**
     * The customer address
     */
    private String address;
    /**
     * The customer postal code
     */
    private String postalCode;
    /**
     * The customer phone number
     */
    private String phone;
    /**
     * The customer's country name
     */
    private String country;
    /**
     * The id of the customer's country
     */
    private int countryId;
    /**
     * The id of the customer's first-level division
     */
    private int divisionId;

    /**
     * Create a new customer
     * @param id the customer id
     * @param name the customer name
     * @param address the customer address
     * @param postalCode the customer postal code
     * @param phone the customer phone number
     * @param divisionId the id of the customer first-level division
     * @param countryId the id of the customer country
     * @param country the customer country name
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId, int countryId, String country)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Get the customer id
     * @return the customer id
     */
    public int getId(){return id;}

    /**
     * Set the customer id
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the customer name
     * @return the customer name
     */
    public String getName(){return name;}

    /**
     * Set the customer name
     * @param name the name to set
     */
    public void setName(String name){this.name = name;}

    /**
     * Get the customer address
     * @return the customer address
     */
    public String getAddress(){return address;}

    /**
     * Set the customer address
     * @param address the custoemr address
     */
    public void setAddress(String address){this.address = address;}

    /**
     * Get the customer postal code
     * @return the customer postal code
     */
    public String getPostalCode(){return postalCode;}

    /**
     * Set the customer postal code
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}

    /**
     * Get the customer phone number
     * @return the customer phone number
     */
    public String getPhone(){return phone;}

    /**
     * Set the customer phone number
     * @param phone the phone number to set
     */
    public void setPhone(String phone){this.phone = phone;}

    /**
     * Get the customer first-level division id
     * @return the customer first-level division id
     */
    public int getDivisionId(){return divisionId;}

    /**
     * Set the customer first-level division id
     * @param divisionId the id to set
     */
    public void setDivisionId(int divisionId){this.divisionId = divisionId;}

    /**
     * Get the customer country id
     * @return the customer country id
     */
    public int getCountryId(){return countryId;}

    /**
     * Set the customer country id
     * @param countryId the id to set
     */
    public void setCountryId(int countryId){this.countryId = countryId;}

    /**
     * Get the customer country name
     * @return the customer country name
     */
    public String getCountry(){return country;}

    /**
     * Set the customer country name
     * @param country the customer country name to set
     */
    public void setCountry(String country){this.country = country;}
}
