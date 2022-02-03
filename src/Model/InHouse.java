package Model;
/**
 * Model of InHouse Part Class
 * @author Darian Chen
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * InHouse parametrized constructor
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Part Stock
     * @param min Minimum inventory
     * @param max Maximum inventory
     * @param machineId Part Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id,name,price,stock,min,max);
        this.machineId = machineId;
    }

    /**
     * Sets machine Id.
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Gets machineId.
     * @return the machineId
     */
    public int getMachineId(){
        return machineId;
    }
}