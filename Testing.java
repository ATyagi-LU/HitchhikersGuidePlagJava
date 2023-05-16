import java.util.ArrayList;


public class Testing
{
  

  public static void main(String[] args)
  {

      String text = "It is an important and popular fact that things are not always what they seem. It is well recognised that things are not always what they seem. as said 'this is a good test' in the book and they say 'this is a good test'"; //test
      String textB = "It is well recognised that things are not always what they seem. Spoken 'this is a good test'"; //test
      int currentIndex = 0;
      int endIndex = 0;

      int phraseWordCount = 0;
      int phraseMinWordCount = 3;

      String[] words = text.split("\\s+");
      String phrase = "";
      ArrayList<String> phrases = new ArrayList<String>();
      Boolean match = false;
      while (currentIndex < words.length)
      {
        String currentWord = words[currentIndex];

        if (currentWord.startsWith("'") || currentWord.startsWith("\"")) {
          int startOfQuoteIndex = currentWord.length() + endIndex;
          int endOfSingleQuoteIndex = text.indexOf("'", startOfQuoteIndex);
          int endOfDoubleQuoteIndex = text.indexOf("\"", startOfQuoteIndex);

          // check for closing quotes and skip no of words
          if (endOfSingleQuoteIndex > -1) {
            currentIndex += text.substring(endIndex, endOfSingleQuoteIndex).split("\\s+").length;
          }
          if (endOfDoubleQuoteIndex > -1) {
            currentIndex += text.substring(endIndex, endOfDoubleQuoteIndex).split("\\s+").length;
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
        int foundphraseMatchIndex = textB.indexOf(phrase);

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