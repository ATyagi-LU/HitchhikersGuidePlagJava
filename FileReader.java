import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class FileReader {
    private int fileCount;
    private String[] files;
    private ArrayList<HashMap<String, Integer>> wordCountMaps = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<String[]> splitTexts = new ArrayList<String[]>();
    private ArrayList<String> texts = new ArrayList<String>();
    private int phraseMinWordCount;
    private ArrayList<String[]> filePairs = new ArrayList<String[]>();
    private HashMap<String, Double> matchPercents = new HashMap<String, Double>();

    int findIndexInFiles(String keyword){
        int counter = 0;
        for (String file : files){
            if (file == keyword){
                return counter;
            }
            counter++;    
        }
        return -1;
    }

    void filePairMatches(){
        for (int i = 0; i < filePairs.size();i++)
        {
            String[] fileMatch = filePairs.get(i);
            String filePair = (fileMatch[0] + "," + fileMatch[1]);
            int textIndexA = findIndexInFiles(fileMatch[0]);
            int textIndexB = findIndexInFiles(fileMatch[1]);
            double matchPercent = phraseMatcher(texts.get(textIndexA), texts.get(textIndexB));
            matchPercents.put(filePair,matchPercent);
        }
    }

    void printMatchPercents(){
        String highestPair = "";
        double highestPairMatchPercent = 0;
        for (String j : matchPercents.keySet()) {
            System.out.println("Pair: " + j + "; Percent match for File Pair: " + matchPercents.get(j) +"%");
            if (matchPercents.get(j)>highestPairMatchPercent){
                highestPair = j;
                highestPairMatchPercent = matchPercents.get(j);
            }
        }
        System.out.println("Highest match pair: " + highestPair + "; Percent match for File Pair: "+ highestPairMatchPercent+"%");
    }

    void filePair() {
        int endIndex = files.length;
        for(int i = 0; i <endIndex; i++){
            int j=0;
            while(j<endIndex){
                if (j<=i){
                    j++;
                }
                else{
                    String[] catc = {files[i],files[j]};
                    filePairs.add(catc);
                    j++;
                }
            }
        }
    }

    void printFilePair() {
        for (int i = 0; i < filePairs.size(); i++) {
            String[] current = filePairs.get(i);
            for (String word : current) {
                System.out.print(word + ",");
            }
            System.out.println();
        }
    }

    String getPath() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        return s;
    }

    void getTxtFiles(String path) {
        File f = new File(path);

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) {
                return name.endsWith(".txt");
            }
        };
        files = f.list(filter);

        for (int i =0; i< files.length; i++) {
            fileCount++;
        }
    }

    public int getFileCount() {
        return fileCount;
    }

    public String[] getFiles() {
        return files;
    }

    void wordCountingMaps() {

        for (int i = 0; i < fileCount; i++) {
            HashMap<String, Integer> file = new HashMap<String, Integer>();
            wordCountMaps.add(file);
        }
    }

    public ArrayList<HashMap<String, Integer>> getArr() {
        return wordCountMaps;
    }

    String[] fileRead(String filename) {
        File file = new File(filename);
        String text = "";
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                text += reader.nextLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        texts.add(text);
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        return words;
    }

    void amassTexts() {
        for (int i = 0; i < getFileCount(); i++) {
            String[] text = fileRead(files[i]);
            splitTexts.add(text);
        }
    }

    public ArrayList<String[]> getTexts() {
        return splitTexts;
    }

    void wordCounterMapper() {
        for (int i = 0; i < fileCount; i++) {
            HashMap<String, Integer> currentHashMap = wordCountMaps.get(i);
            String[] currentText = splitTexts.get(i);
            for (String word : currentText) {
                boolean exists = currentHashMap.containsKey(word);
                if (exists) {
                    currentHashMap.put(word, currentHashMap.get(word) + 1);
                } else {
                    currentHashMap.put(word, 1);
                }

            }
        }
    }

    public void setPhraseMinWordCount() {
        boolean valid = false;
        while (!valid) {
            try {
                Scanner myObj = new Scanner(System.in); // Create a Scanner object
                System.out.println("Please input min word count:");
                phraseMinWordCount = Integer.parseInt(myObj.nextLine());
                if (phraseMinWordCount == 0|| phraseMinWordCount == 1) {
                    System.out.println("Not a valid input, please try again.");
                    valid = false;
                    continue;
                }
                myObj.close();
                valid = true;
            } catch (Exception e) {
                System.out.println("Not a valid input, please try again.");
            }
        }

    }

    double phraseMatcher(String textA, String textB) {
        int currentIndex = 0;
        int endIndex = 0;
        int phraseWordCount = 0;
        String splitText;
        String smallText;
        if (textA.length() > textB.length()) {
            splitText = textA;
            smallText = textB;
        } else {
            splitText = textB;
            smallText = textA;
        }
        String[] words = splitText.split("\\s+");
        String phrase = "";
        ArrayList<String> phrases = new ArrayList<String>();
        Boolean match = false;
        while (currentIndex < words.length) {
            String currentWord = words[currentIndex];

            if (currentWord.startsWith("'") || currentWord.startsWith("\"")) {
                int startOfQuoteIndex = currentWord.length() + endIndex;
                int endOfSingleQuoteIndex = splitText.indexOf("'", startOfQuoteIndex);
                int endOfDoubleQuoteIndex = splitText.indexOf("\"", startOfQuoteIndex);

                // check for closing quotes and skip no. of words
                if (endOfSingleQuoteIndex > -1) {
                    currentIndex += splitText.substring(endIndex, endOfSingleQuoteIndex).split("\\s+").length;
                }
                if (endOfDoubleQuoteIndex > -1) {
                    currentIndex += splitText.substring(endIndex, endOfDoubleQuoteIndex).split("\\s+").length;
                }
                continue;
            }

            // set starting point for phrase matching
            if (phraseWordCount < phraseMinWordCount) {
                phrase += currentWord + " ";
                endIndex += currentWord.length() + 1;
                phraseWordCount++;
                currentIndex++;
                continue;
            }

            // once we have a min length phrase, we can start matching
            // find the current phrase in the following content.
            int foundphraseMatchIndex = smallText.indexOf(phrase);

            if (foundphraseMatchIndex > -1) {
                // if we find a match, we expand the phrase
                phrase += currentWord + " ";
                endIndex += currentWord.length() + 1;
                phraseWordCount++;
                currentIndex++;
                match = true;

                // If its the end of the sentence then reset the phrase search
                /*if (currentWord.endsWith(".")) {
                    phrases.add(phrase.replaceAll("\\.(?=\\s|$)", ""));
                    match = false;
                    phrase = "";
                    phraseWordCount = 0;
                }*/

                continue;
            }

            if (match) {
                phrases.add(phrase);
            }

            // move to next phrase
            phrase = phrase.substring(phrase.indexOf(" ") + 1);
            phraseWordCount--;
            match = false;
        }
        String matches = "";
        for (int i = 0; i < phrases.size(); i++) {
            matches += phrases.get(i) + " ";
        }
        String[] matchesLength = matches.split("\\s+");
        int matchNum = matchesLength.length;
        double matchPercent = Math.round((((double) matchNum) / ((double) splitText.split("\\s+").length)) * 100.0);
        return matchPercent;
    }

    void wordCountingMapsPrinter() {
        for (int i = 0; i < getFileCount(); i++) {
            System.out.println(files[i]);
            HashMap<String, Integer> current = wordCountMaps.get(i);
            for (String j : current.keySet()) {
                System.out.println("key: " + j + "; value: " + current.get(j));
            }
        }
    }

}