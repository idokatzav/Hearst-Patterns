/**
 * FifthRegexType Class implements Regex.
 */
public class FifthRegexType implements Regex {
    private String regex;
    /**
     * Constructor.
     */
    public FifthRegexType() {
        //we'll create the regex as we can understand it from the list in the start of the assignmnts.
        this.regex = "<np>[^<]*</np>( ,)? which is( (an example|a kind|a class) of)? <np>[^<]*</np>";
    }
    @Override
    public boolean isHypernymInStart() {
        return false;
    }
    @Override
    public String getReg() {
        return this.regex;
    }
    @Override
    public String toCheck() {
        return " which is";
    }
}
