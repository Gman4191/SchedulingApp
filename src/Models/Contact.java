package Models;

public class Contact {
    /**
     * The contact id
     */
    private int id;
    /**
     * The contact name
     */
    private String name;
    /**
     * The contact email
     */
    private String email;

    /**
     * Create a new contact
     * @param id the contact id
     * @param name the contact name
     * @param email the contact email
     */
    public Contact(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Get the contact id
     * @return the contact id
     */
    public int getId(){return id;};

    /**
     * Set the contact id
     * @param id the id to set
     */
    public void setId(int id){this.id = id;}

    /**
     * Get the contact name
     * @return the contact name
     */
    public String getName(){return name;}

    /**
     * Set the contact name
     * @param name the name to set
     */
    public void setName(String name){this.name = name;}

    /**
     * Get the contact email
     * @return the contact email
     */
    public String getEmail(){return email;}

    /**
     * Set the contact email
     * @param email the email to set
     */
    public void setEmail(String email){this.email = email;}

    @Override
    public String toString(){
        return name;
    }
}
