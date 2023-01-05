package Models;

public class Country {
    /**
     * The country id
     */
    private int id;

    /**
     * The country name
     */
    private String country;

    /**
     * Create a new country
     * @param id the country id
     * @param country the country name
     */
    public Country(int id, String country)
    {
        this.id = id;
        this.country = country;
    }

    /**
     * Get the country id
     * @return the country id
     */
    public int getId(){return id;}

    /**
     * Set the country id
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the country name
     * @return the country name
     */
    public String getCountry(){return country;}

    /**
     * Set the country name
     * @param country the country name to set
     */
    public void setCountry(String country){this.country = country;}

    @Override
    public String toString()
    {
        return country;
    }
}
