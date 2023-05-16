import java.util.ArrayList;
import java.util.HashMap;

public class TestingDriver
{
    public static void main(String[] args)
    {   
        FileReader fileReader = new FileReader();
        fileReader.getTxtFiles(fileReader.getPath());
        fileReader.wordCountingMaps();
        fileReader.amassTexts();
        fileReader.wordCounterMapper();
        ArrayList<HashMap<String, Integer>> arr = fileReader.getArr();
        for(int i = 0; i<fileReader.getFileCount(); i++){
            System.out.println(arr.get(i).isEmpty());
            HashMap<String, Integer> current = arr.get(i);
            for (String j : current.keySet()) {
                System.out.println("key: " + j + "; value: " + current.get(j));
              }
        }
        String textB = "It is an important and popular fact that things are not always what they seem. It is well recognised that things are not always what they seem. as said 'this is a good test' in the book and they say 'this is a good test'"; //test
        String textA = "It is well recognised that things are not always what they seem. Spoken 'this is a good test'"; //test
        fileReader.phraseMatcher(3, textA, textB);
    }
}