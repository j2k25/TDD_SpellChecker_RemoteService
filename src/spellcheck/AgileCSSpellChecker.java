package spellcheck;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AgileCSSpellChecker implements SpellChecker {
  final String _serviceURL = "http://agilec.cs.uh.edu/spell?check=";

  public String callService(String word) throws IOException {
      URL url = new URL(_serviceURL+word);

      try(Scanner scanner = new Scanner(url.openStream())) {
        return scanner.nextLine();
      }
  }

  public boolean parse(String response) {
    return response.equals("true");
  }

  @Override
  public boolean isSpellingCorrect(String word) {
      try {
        return parse(callService(word));
      } catch(Exception ex) {
        throw new RuntimeException(ex);
      }
  }
}


