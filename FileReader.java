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
    private ArrayList<String[]> texts = new ArrayList<String[]>();

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
        String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        return words;
    }

    void amassTexts() {
        for (int i = 0; i < getFileCount(); i++) {
            String[] text = fileRead(files[i]);
            texts.add(text);
        }
    }

    public ArrayList<String[]> getTexts() {
        return texts;
    }

    void wordCounterMapper() {
        for (int i = 0; i < fileCount; i++) {
            HashMap<String, Integer> currentHashMap = arr.get(i);
            String[] currentText = texts.get(i);
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

}