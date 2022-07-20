/**
 * SecondRegexType class implements Regex.
 */
public class SecondRegexType implements Regex {
    private String regex;
    /**
     * Constructor.
     */
    public SecondRegexType() {
        //we'll create the regex as we can understand it from the list in the start of the assignmnts.
        this.regex = "such <np>[^<]*</np> as <np>[^>]*</np>( , <np>[^<]*</np>)*(( ,)? (and|or)? <np>[^<]*</np>)?";
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
        return "</np> as <np>";
    }
}
