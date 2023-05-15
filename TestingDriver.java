import java.util.Map;
import java.util.ArrayList;


public class TestingDriver
{
    public static void main(String[] args)
    {   
        FileReader fileReader = new FileReader();
        System.out.println(fileReader.getPath());
        fileReader.getTxtFiles(fileReader.getPath());
        fileReader.wordCountingMaps();
        ArrayList<Map<String, Integer>> jeff = fileReader.getArr();
        int length = jeff.size();
        for(int i = 0; i<length; i++)
        {
            System.out.println(jeff.get(i).isEmpty());
        }

    }
}