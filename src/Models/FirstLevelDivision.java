package Models;

public class FirstLevelDivision {
    private int id;
    private String division;

    public FirstLevelDivision(int id, String division){
        this.id = id;
        this.division = division;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getDivision(){return division;}
    public void setDivision(String division){this.division = division;}

    @Override
    public String toString()
    {
        return division;
    }
}
