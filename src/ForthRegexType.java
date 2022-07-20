/**
 * ForthRegexType class implements Regex.
 */
public class ForthRegexType implements Regex {
    private String regex;
    /**
     * Constructor.
     */
    public ForthRegexType() {
        //we'll create the regex as we can understand it from the list in the start of the assignmnts.
        this.regex = "<np>[^<]*</np>( ,)? especially <np>[^<]*</np>( , <np>[^<]*</np>)*(( ,)? "
                + "(and|or)? <np>[^<]*</np>)?";
    }
    @Override
    public String getReg() {
        return this.regex;
    }
    @Override
    public boolean isHypernymInStart() {
        return true;
    }
    @Override
    public String toCheck() {
        return " especially <np>";
    }
}
