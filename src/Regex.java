/**
 * Regex interface.
 */
public interface Regex {
    /**
     * getter for Reg.
     * @return the regex.
     */
    String getReg();
    /**
     * Let us know if hypernym is in the start or the end of that part.
     * @return true if in start, false otherwise.
     */
    boolean isHypernymInStart();
    /**
     * Gives us a string to find out if we have to search this regex in that paragraph.
     * @return the string.
     */
    String toCheck();
}
