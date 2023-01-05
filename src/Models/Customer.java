package Models;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private int countryId;
    private String country;

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

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getPostalCode(){return postalCode;}
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}
    public int getDivisionId(){return divisionId;}
    public void setDivisionId(int divisionId){this.divisionId = divisionId;}
    public int getCountryId(){return countryId;}
    public void setCountryId(int countryId){this.countryId = countryId;}
    public String getCountry(){return country;}
    public void setCountry(String country){this.country = country;}
}
