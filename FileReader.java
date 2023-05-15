import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class FileReader
{
    private int fileCount;
    private String[] files;
    private ArrayList<Map<String, Integer>> arr = new ArrayList<Map<String, Integer>>();

    String getPath()
    {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        return s;
    }

    void getTxtFiles(String path)
    {
        File f = new File(path);
        
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File f, String name) 
            {
                return name.endsWith(".txt");
            }
        };
        files = f.list(filter);

        for (String file : files) {
            fileCount++;
            System.out.println(file);
        }
    }

    void wordCountingMaps(){
        
        for (int i = 0; i < fileCount; i++)
        {
            HashMap<String,Integer> file = new HashMap<String, Integer>();
            arr.add(file);
        }
    }

    public ArrayList<Map<String, Integer>> getArr() {
        return arr;
    }

}