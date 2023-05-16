public class TestingDriver
{
    public static void main(String[] args)
    {   
        FileReader fileReader = new FileReader();
        fileReader.getTxtFiles(fileReader.getPath());
        fileReader.wordCountingMaps();
        fileReader.amassTexts();
        fileReader.wordCounterMapper();
        fileReader.arrPrinter();
        String textB = "It is an important and popular fact that things are not always what they seem. It is well recognised that things are not always what they seem. as said 'this is a good test' in the book and they say 'this is a good test'";
        String textA = "It is well recognised that things are not always what they seem. Spoken 'this is a good test'";
        double matchPercent = fileReader.phraseMatcher(textA, textB);
        System.out.println("This is the matching percent for the file:");
        System.out.println(matchPercent + "%");
    }
}