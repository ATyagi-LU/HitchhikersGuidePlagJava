import java.util.ArrayList;


public class Testing
{
  

  public static void main(String[] args)
  {
      System.out.println("Starting running the code");

      String text = "It is an important and popular fact that things are not always what they seem. It is well recognised that things are not always what they seem. as said 'this is a good test' in the book and they say 'this is a good test'";

      int currentIndex = 0;
      int endIndex = 0;
  
      int phaseWordCount = 0;
      int phaseMinWordCount = 3;

      String[] words = text.split("\\s+");
      String phase = "";
      ArrayList<String> phases = new ArrayList<String>();
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

        // set starting point for phase matching
        if (phaseWordCount < phaseMinWordCount) {
          phase += currentWord + " ";
          endIndex += currentWord.length() + 1;
          phaseWordCount++;
          currentIndex++;
          continue;
        }
        
        // once we have a min length phase, we can start matching
        // find the current phase in the following content.
        int foundPhaseMatchIndex = text.indexOf(phase, endIndex);

        if (foundPhaseMatchIndex > -1) {
          // if we find a match, we expand the phase
          phase += currentWord + " ";
          endIndex += currentWord.length() + 1;
          phaseWordCount++;
          currentIndex++;
          match = true;

          // If its  end of the sentence then reset the phase search
          if (currentWord.endsWith(".")) {
            phases.add(phase);        
            match = false;
            phase = "";
            phaseWordCount = 0;
          }

          continue;
        }
        
        if (match) {
          phases.add(phase);
        }

        // move to next phase
        phase = phase.substring(phase.indexOf(" ") + 1);
        phaseWordCount--;
        match = false;
      }

      for (String p : phases) {
        System.out.println(p);
      }
      System.out.println("Finished running the code");
  }
}