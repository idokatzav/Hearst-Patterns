/**
 * Hypernym class - comparable.
 */
public class Hypernym implements Comparable<Hypernym> {
    private String hypernymStr;
    private int numOfOccurrences;
    /**
     * Constructor.
     * @param hypernymStr
     * @param numOfOccurrences
     */
    public Hypernym(String hypernymStr, int numOfOccurrences) {
        this.hypernymStr = hypernymStr;
        this.numOfOccurrences = numOfOccurrences;
    }
    /**
     * Getter for the hypernym string.
     * @return the string.
     */
    public String getHypernymStr() {
        return this.hypernymStr;
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
    public int compareTo(Hypernym other) {
        if (this.numOfOccurrences == other.numOfOccurrences) {
            return this.hypernymStr.compareTo(other.hypernymStr);
        } else {
            return other.numOfOccurrences - this.numOfOccurrences;
        }
    }
}
