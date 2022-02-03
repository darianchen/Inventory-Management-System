package Helper;

/**
 * This is a help class that validates the form items.
 * @author Darian Chen
 */
public class Validator {
    /** This method validates the inventory input.
     * @param inv The inventory
     * @return Returns the inventory
     */
    public static int validateInv(String inv) {
        try {
            return Integer.parseInt(inv);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Inventory field must be a numeric entry not greater than max or less than min.");
        }
    }

    /** Validates the name.
     * @param name The name
     * @return Returns the name
     */
    public static String validateName(String name) {
        try {
            if (name == "") {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Name cannot be blank.");
        }
        return name;
    }

    /**
     * Validates the price input.
     * @param price The price
     * @return Returns the price
     */
    public static double validatePrice(String price) {
        try {
            return Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Price field must be a numeric entry.");
        }
    }

    /**
     * Validates the max input.
     * @param max The maximum value
     * @return Returns the maximum value
     */
    public static int validateMax(String max) {
        try {
            return Integer.parseInt(max);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Max field must be a numeric entry.");
        }
    }

    /**
     * Validates the min input.
     * @param min The minimum value
     * @return Returns the minimum value
     */
    public static int validateMin(String min) {
        try {
            return Integer.parseInt(min);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Min field must be a numeric entry.");
        }
    }

    /**
     * Validates the company name input.
     * @param companyName The company Name
     * @return Returns the company name
     */
    public static String validateCompanyName(String companyName){
        try{
            if(companyName == ""){
             throw new NumberFormatException();
            }
        }
        catch(NumberFormatException e){
            throw new NumberFormatException("Company Name cannot be empty.");
        }
        return companyName;
    }

    /**
     * Validates the machine Id input.
     * @param machineId The machine Id
     * @return Returns the machine Id
     */
    public static int validateMachineId(String machineId) {
        try {
            return Integer.parseInt(machineId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Machine ID must be a numeric entry.");
        }
    }
}