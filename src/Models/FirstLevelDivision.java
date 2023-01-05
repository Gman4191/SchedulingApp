package Models;

public class FirstLevelDivision {

    /**
     * The first-level division id
     */
    private int id;
    /**
     * The first-level division name
     */
    private String division;
    /**
     * The first-level division country id
     */
    private int countryId;

    /**
     * Create a new first-level division
     * @param id the first-level division id
     * @param division the first-level division name
     * @param countryId the first-level division country id
     */
    public FirstLevelDivision(int id, String division, int countryId){
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Get the first-level division id
     * @return the division id
     */
    public int getId(){return id;}

    /**
     * Set the first-level division id
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the first-level division name
     * @return the division name
     */
    public String getDivision(){return division;}

    /**
     * Set the first-level division name
     * @param division the division name to set
     */
    public void setDivision(String division){this.division = division;}

    /**
     * Get the first-level division country id
     * @return the country id
     */
    public int getCountryId(){return countryId;}

    /***
     * Set the first-level division country id
     * @param countryId the country id
     */
    public void setCountryId(int countryId){this.countryId = countryId;}

    @Override
    public String toString()
    {
        return division;
    }
}
