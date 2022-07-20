import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * CreateHypernymDatabase class.
 */
public class CreateHypernymDatabase {
    private TreeMap<String, List<Hyponym>> hypernymMap;
    private Regex[] regexes;
    /**
     * main function. Creates the database.
     * @param args arguments rom command line.
     */
    public static void main(String[] args) {
        //there can be problems with the files that will be given to us so we'll use try-catch.
        try {
            //System.out.print("0%");
            //for (int i = 0; i < 81; i++) {
            //    System.out.print(" ");
            //}
            //System.out.println("100%");
            //System.out.print("|");
            //we'll use the files paths
            String pathOfDirectory = args[0];
            String outputFile = args[1];
            //we'll create an object of that class.
            CreateHypernymDatabase createHypernymDatabase = new CreateHypernymDatabase();
            //we'll open the directory.
            File f1 = new File(pathOfDirectory);
            //we'll read all the files.
            createHypernymDatabase.manageFileReading(f1);
            //we'll print all the input we've got.
            PrintWriter os = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
            createHypernymDatabase.writeToFile(os);
            os.close();
            //System.out.println("*|");
        } catch (Exception e) {
            //if a file opening is failed, return.
            return;
        }
        //String s = "<np>Object oriented programming</np> , which is an example of a <np>computer science course</np>";
        //Regex regex = new FifthRegexType();
        //String[] st = getHyponymsAndHypernymStr(s, regex);
        //for (String stri: st) {
        //    System.out.println(stri);
        //}
    }
    /**
     * Constructor.
     */
    public CreateHypernymDatabase() {
        this.hypernymMap = new TreeMap<String, List<Hyponym>>();
        this.regexes = new Regex[5];
        this.regexes[0] = new FirstRegexType();
        this.regexes[1] = new SecondRegexType();
        this.regexes[2] = new ThirdRegexType();
        this.regexes[3] = new ForthRegexType();
        this.regexes[4] = new FifthRegexType();
    }
    /**
     * manages the files reading: reads file by file.
     * @param folder the folder with the files.
     * @throws IOException an io exception.
     */
    public void manageFileReading(File folder) throws IOException {
        //we'll run on all files an read them.
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                this.readFile(is);
                //System.out.print("*");
                is.close();
            }
        }
    }
    /**
     * Reads the file that the reading object is in type of it.
     * @param is the reader.
     * @throws IOException an io exception.
     */
    public void readFile(BufferedReader is) throws IOException {
        //we'll read a line and check that it's not null, while it's not null we'll continue
        String line;
        while ((line = is.readLine()) != null) {
            //we'll convert it to lower cases.
            line = line.toLowerCase();
            //we'll do all the possible options on each regex, optimized in run time.
            for (Regex regex: this.regexes) {
                if (line.contains(regex.toCheck())) {
                    //for each sentence found we'll take the hypernym and hyponyms from it.
                    Pattern pattern = Pattern.compile(regex.getReg());
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        this.addHypernym(line.substring(matcher.start(),
                                matcher.end()), regex);
                    }
                }
            }
        }
    }
    /**
     * Writes the List of Hypernyms sorted.
     * @param os output connection the file.
     */
    public void writeToFile(PrintWriter os) {
        boolean a = false;
        //we'll print line by line. From the second we'll also print an enter before.
        for (Map.Entry<String, List<Hyponym>> map: this.hypernymMap.entrySet()) {
            String hypernymHere = map.getKey();
            List<Hyponym> lst = map.getValue();
            if (lst.size() >= 3) {
                if (a) {
                    os.print("\n");
                } else {
                    a = true;
                }
                lst.sort(Hyponym::compareTo);
                os.print(hypernymHere);
                for (int i = 0; i < lst.size(); i++) {
                    if (i == 0) {
                        os.print(": ");
                    } else {
                        os.print(", ");
                    }
                    Hyponym h = lst.get(i);
                    os.print(h.getHyponymStr() + " (" + h.getNumOfOccurrences() + ")");
                }
            }
        }
    }
    /**
     * Adds an hypernym to the hypernym list by given a string that is like the regex and the regex.
     * @param str the string found like the regex.
     * @param reg the regex.
     */
    private void addHypernym(String str, Regex reg) {
        //we'll get the parts of that sentence.
        String[] parts = getHyponymsAndHypernymStr(str, reg);
        //if the hypernym exists, we'll update it's hyponyms.
        if (this.hypernymMap.containsKey(parts[0])) {
            List<Hyponym> lst = this.hypernymMap.get(parts[0]);
            //foreach hyponym we'll update or add it to the list in that hypernym.
            for (int i = 1; i < parts.length; i++) {
                String s = parts[i];
                boolean a = false;
                for (Hyponym hyponyms: lst) {
                    if (hyponyms.getHyponymStr().equals(s)) {
                        hyponyms.addToNumOfOccurrences(1);
                        a = true;
                        break;
                    }
                }
                if (!a) {
                    lst.add(lst.size(), new Hyponym(s, 1));
                }
            }
        } else { //else, the hypernym isn't alreadiy exists, so we'll create it.
            List<Hyponym> hyponymList = new ArrayList<Hyponym>();
            String hypernym = parts[0];
            //we'll add the hyponyms one by one.
            for (int i = 1; i < parts.length; i++) {
                String s = parts[i];
                boolean a = false;
                for (Hyponym hyponyms: hyponymList) {
                    if (hyponyms.getHyponymStr().equals(s)) {
                        hyponyms.addToNumOfOccurrences(1);
                        a = true;
                        break;
                    }
                }
                if (!a) {
                    hyponymList.add(hyponymList.size(), new Hyponym(s, 1));
                }
            }
            this.hypernymMap.put(hypernym, hyponymList);
        }
    }
    /**
     * returns the split of the sentence.
     * @param str the sentence to split with the hyponyms or hypernymss.
     * @param reg the regex for that version.
     * @return the splited string array.
     */
    private static String[] getHyponymsAndHypernymStr(String str, Regex reg) {
        String newReg = "<np>[^<]*</np>";
        Pattern pattern = Pattern.compile(newReg);
        Matcher matcher = pattern.matcher(str);
        int i;
        if (reg.isHypernymInStart()) {
            i = 0;
        } else {
            i = 1;
        }
        int size = i;
        String[] str1 = new String[1];
        while (matcher.find()) {
            size++;
            String[] str2 = new String[size];
            for (int j = i; j < str1.length; j++) {
                str2[j] = str1[j];
            }
            str2[size - 1] = str.substring(matcher.start() + 4, matcher.end() - 5);
            str1 = str2;
        }
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
