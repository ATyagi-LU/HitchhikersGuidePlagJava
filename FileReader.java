import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader
{
    private int fileCount;
    private ArrayList<String> files = new ArrayList<String>();

    String getPath(){

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        return s;
    }

}