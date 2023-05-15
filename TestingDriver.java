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
                System.out.println("key: " + j + " value: " + current.get(j));
              }
        }
    }
}