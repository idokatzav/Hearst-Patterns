/**
 * ThirdRegexType class implements Regex.
 */
public class ThirdRegexType implements Regex {
    private String regex;
    /**
     * Constructor.
     */
    public ThirdRegexType() {
        //we'll create the regex as we can understand it from the list in the start of the assignmnts.
        this.regex = "<np>[^<]*</np>( ,)? including <np>[^<]*</np>( , <np>[^<]*</np>)*(( ,)? (and|or)?"
                + " <np>[^<]*</np>)?";
    }
    @Override
    public boolean isHypernymInStart() {
        return true;
    }
    @Override
    public String getReg() {
        return this.regex;
    }
    @Override
    public String toCheck() {
        return " including <np>";
    }
}
