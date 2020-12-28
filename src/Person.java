/**
 * Person stores the user's information
 *
 * @author Simon Sørensen
 * @version 1.0
 */
public class Person {
    private String name;
    private int cup;
    private double mgCaffein;
    private double totalCaffein;

    public Person() {
        cup = 0;
        mgCaffein = 0.0;
        totalCaffein = 0.0;
    }

    /**
     * Get's the Person.name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Person.name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Person.cup
     */
    public int getCup() {
        return cup;
    }

    /**
     * Sets the Person.cup
     */
    public void setCup(int cup) {
        this.cup = cup;
    }

    /**
     * Gets the Person.mgCaffeine
     */
    public double getMgCaffein() {
        return mgCaffein;
    }

    /**
     * Sets the Person.mgCaffeine
     */
    public void setMgCaffein(double mgCaffein) {
        this.mgCaffein = mgCaffein;
    }

    /**
     * Fets the Person.totalCaffeine
     */
    public double getTotalCaffein() {
        return totalCaffein;
    }

    /**
     * Sets the Person.totalCaffeine
     */
    public void setTotalCaffein(double totalCaffein) {
        this.totalCaffein = totalCaffein;
    }
}