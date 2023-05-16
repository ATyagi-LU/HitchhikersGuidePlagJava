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
    private ArrayList<HashMap<String, Integer>> arr = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<String[]> splitTexts = new ArrayList<String[]>();
    private ArrayList<String> texts = new ArrayList<String>();
    

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

        for (String file : files) {
            fileCount++;
            System.out.println(file);
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
            arr.add(file);
        }
    }

    public ArrayList<HashMap<String, Integer>> getArr() {
        return arr;
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
            HashMap<String, Integer> currentHashMap = arr.get(i);
            String[] currentText = splitTexts.get(i);
            for(String word : currentText){
                boolean exists = currentHashMap.containsKey(word);
                if (exists){
                    currentHashMap.put(word, currentHashMap.get(word) + 1);
                }
                else{
                    currentHashMap.put(word,1);
                }

            }
        }
    }

    void phraseMatcher(int phraseMinWordCount, String textA, String textB){
        int currentIndex = 0;
        int endIndex = 0;
        int phraseWordCount = 0;
        String splitText;
        String smallText;
        if (textA.length() > textB.length()){
            splitText = textA;
            smallText = textB;
        }
        else{
            splitText = textB;
            smallText = textA;
        }
        String[] words = splitText.split("\\s+");
        String phrase = "";
        ArrayList<String> phrases = new ArrayList<String>();
        Boolean match = false;
        while (currentIndex < words.length)
        {
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
            if (currentWord.endsWith(".")) {
                phrases.add(phrase);        
                match = false;
                phrase = "";
                phraseWordCount = 0;
            }

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

        for (String p : phrases) {
            System.out.println(p);
        }
    }
    
}