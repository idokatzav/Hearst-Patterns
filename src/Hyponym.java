/**
 * Hyponym class - comparable.
 */
public class Hyponym implements Comparable<Hyponym> {
    private String hyponymStr;
    private int numOfOccurrences;
    /**
     * Constructor.
     * @param hyponymStr
     * @param numOfOccurrences
     */
    public Hyponym(String hyponymStr, int numOfOccurrences) {
        this.hyponymStr = hyponymStr;
        this.numOfOccurrences = numOfOccurrences;
    }
    /**
     * Getter for the hyponym string.
     * @return the string.
     */
    public String getHyponymStr() {
        return this.hyponymStr;
    }
    /**
     * getter for the number of occurrences.
     * @return the number.
     */
    public int getNumOfOccurrences() {
        return this.numOfOccurrences;
    }
    /**
     * add the given number to the number of occurrences.
     * @param numToAdd the number to add.
     */
    public void addToNumOfOccurrences(int numToAdd) {
        this.numOfOccurrences += numToAdd;
    }

    /**
     * take from the number of Occurences.
     * @param numToTake
     */
    public void takeFromNumOfOccurrences(int numToTake) {
        this.numOfOccurrences -= numToTake;
    }
    @Override
    public int compareTo(Hyponym other) {
        if (this.numOfOccurrences == other.numOfOccurrences) {
            return this.hyponymStr.compareTo(other.hyponymStr);
        } else {
            return other.numOfOccurrences - this.numOfOccurrences;
        }
    }
}
