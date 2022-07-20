/**
 * FirstRegexType implements Regex interface.
 */
public class FirstRegexType implements Regex {
    private String regex;
    /**
     * Constructor.
     */
    public FirstRegexType() {
        //we'll create the regex as we can understand it from the list in the start of the assignmnts.
        this.regex = "<np>[^<]*</np>( ,)? such as <np>[^<]*</np>( , <np>[^<]*</np>)*(( ,)? (and|or)?"
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
        return "such as <np>";
    }
}
