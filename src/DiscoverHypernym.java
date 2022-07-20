import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * DiscoverHypernym class.
 */
public class DiscoverHypernym {
    private List<Hypernym> lst;
    private Regex[] regexes;
    private String lemma;
    /**
     * Main function.
     * @param args arguments from command line.
     */
    public static void main(String[] args) {
        //build the lemma's string.
        String lemma = args[1];
        for (int i = 2; i < args.length; i++) {
            lemma += " " + args[i];
        }
        //create a discover hypernym object with our file.
        DiscoverHypernym discoverHypernym = new DiscoverHypernym(lemma);
        //we'll try to create the folder, create list from the folder, and print it.
        try {
            File f = new File(args[0]);
            discoverHypernym.createList(f);
            discoverHypernym.printList();
        } catch (IOException e) {
            //if something was wrong, we'll return.
            return;
        }
    }
    /**
     * Constructor.
     * @param lemma the given lemma.
     */
    public DiscoverHypernym(String lemma) {
         // We'll create the list, and all the regex objects. we'll also create the lemma and change it to lower case.
        this.lst = new ArrayList<Hypernym>();
        this.regexes = new Regex[5];
        this.regexes[0] = new FirstRegexType();
        this.regexes[1] = new SecondRegexType();
        this.regexes[2] = new ThirdRegexType();
        this.regexes[3] = new ForthRegexType();
        this.regexes[4] = new FifthRegexType();
        this.lemma = lemma;
        this.lemma = this.lemma.toLowerCase();
    }
    /**
     * creates the list of Hypernyms.
     * @param folder the folder of the corpus.
     * @throws IOException not finding the folder.
     */
    public void createList(File folder) throws IOException {
        //We'll get a list of the files.
        File[] files = folder.listFiles();
        //foreach file in the folder we'll read it using readFile method.
        for (File f: files) {
            BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            this.readFile(is);
        }
    }
    /**
     * prints the list of the hypernyms.
     */
    public void printList() {
        this.lst.sort(Hypernym::compareTo);
        for (int i = 0; i < this.lst.size(); i++) {
            System.out.print(this.lst.get(i).getHypernymStr() + ": (" + this.lst.get(i).getNumOfOccurrences() + ")");
            if (i != this.lst.size() - 1) {
                System.out.print("\n");
            }
        }
    }
    /**
     * reads the file that a reader was given for it.
     * @param is the connection to the file to read it.
     * @throws IOException an io error.
     */
    public void readFile(BufferedReader is) throws IOException {
        //we'll read row by row.
        String line;
        while ((line = is.readLine()) != null) {
            //we'll change the row to be in lower cases.
            line = line.toLowerCase();
            //we'll check if the lemma is contained in this line.
            if (line.contains(this.lemma)) {
                //if it is, we'll find all the hypernyms and hyponyms.
                for (Regex regex: this.regexes) {
                    if (line.contains(regex.toCheck())) {
                        Pattern pattern = Pattern.compile(regex.getReg());
                        Matcher matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            //if we found a match to the regex, we'll try to add the hypernym.
                            this.addHypernym(line.substring(matcher.start(),
                                    matcher.end()), regex);
                        }
                    }
                }
            }
        }
    }
    /**
     * checks if the given string is in our list.
     * @param str the string.
     * @return the index in the list if found, -1 otherwise.
     */
    private int isInList(String str) {
        for (Hypernym h: this.lst) {
            String str1 = h.getHypernymStr();
            if (str.equals(str1)) {
                return this.lst.indexOf(h);
            }
        }
        return -1;
    }
    /**
     * Add hypernym. if it's an hypernym that lemma is one of it's hyponyms, we'll add it to the list.
     * If already exists in the list: increase the counter, otherwise add it.
     * @param str
     * @param reg
     */
    private void addHypernym(String str, Regex reg) {
        //we'll split the text according to the regex.
        String[] parts = getHyponymsAndHypernymStr(str, reg);
        //we'll check if lemma is one of the hyponyms, and update found according this.
        boolean found = false;
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].equals(this.lemma)) {
                found = true;
            }
        }
        //we'll ask for index of the hypernym in the list, if it exists, we'll use it, else it'll be -1.
        int i1 = this.isInList(parts[0]);
        //if it exists and lemma was found in the hyponyms, we'll increase the number in that hypernym.
        if (i1 != -1 && found) {
            this.lst.get(i1).addToNumOfOccurrences(1);
        } else if (found) { //in that case, we'll add the hypernym to the list.
            this.lst.add(lst.size(), new Hypernym(parts[0], 1));
        }
    }
    /**
     * Splits the string according the regex.
     * @param str the string that matches to the regex.
     * @param reg the regex.
     * @return an array: in index 0 the hypernym, and after it, the hyponyms.
     */
    private static String[] getHyponymsAndHypernymStr(String str, Regex reg) {
        //the way an hypernym or hyponym can appear.
        String newReg = "<np>[^<]*</np>";
        Pattern pattern = Pattern.compile(newReg);
        Matcher matcher = pattern.matcher(str);
        int i;
        //if the hypernym in the start of the line, the first that will be put in the array is the hypernym.
        if (reg.isHypernymInStart()) {
            i = 0;
        } else { //else we'll need to move in the end the last one of the array parts to the start.
            i = 1;
        }
        //we'll save size as i.
        int size = i;
        String[] str1 = new String[1];
        while (matcher.find()) {
            //we'll increase size by 1.
            size++;
            //we'll create an array and copy into it the parts we have already found.
            String[] str2 = new String[size];
            for (int j = i; j < str1.length; j++) {
                str2[j] = str1[j];
            }
            //we'll add the word to the end of the array.
            str2[size - 1] = str.substring(matcher.start() + 4, matcher.end() - 5);
            //we'll insert the new array to be saved in ours.
            str1 = str2;
        }
        //if the hypernym was in the end, will move it to the start of the array and resize the array.
        if (i == 1 && size != 1) {
            str1[0] = str1[size - 1];
            String[] str2 = str1;
            str1 = new String[str2.length - 1];
            for (int j = 0; j < str1.length; j++) {
                str1[j] = str2[j];
            }
        }
        return str1;
    }
}
