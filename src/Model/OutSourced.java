package Model;
/**
 * Model of OutSourced Part Class
 * @author Darian Chen
 */
public class OutSourced extends Part {

    private String companyName;

    /**
     * OutSourced Parametrized Constructor.
     * @param id Part Id
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Stock
     * @param min Minimum Inventory
     * @param max Maximum Inventory
     * @param companyName Part Company Name
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max,String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets company name.
     * @param companyName The company name to set
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Sets company name.
     * @return Returns the company name
     */
    public String getCompany(){
        return companyName;
    }
}