public class TestingDriver
{
    public static void main(String[] args)
    {   
        FileReader fileReader = new FileReader();
        fileReader.getTxtFiles(fileReader.getPath());
        fileReader.wordCountingMaps();
        fileReader.amassTexts();
        fileReader.wordCounterMapper();
        fileReader.wordCountingMapsPrinter();
        fileReader.filePair();
        fileReader.setPhraseMinWordCount();
        fileReader.filePairMatches();
        fileReader.printMatchPercents();
    }
}