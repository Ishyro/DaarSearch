package automata;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.lang.StringBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

public class SearchInText {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("This program require a regex and a file in input.");
      return;
    }
    try {
      // constant strings for coloring the matching word in the lines
      String RED = "\033[1;31m";
      String NC = "\033[0m";
      RegEx regex = new RegEx(args[0]);
      Automata automata = Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse()));
      Path path = FileSystems.getDefault().getPath(".", args[1]);
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (String line : lines) {
        int[] indices;
        if ((indices = automata.accept(line)) != null) {
          System.out.println(line.substring(0, indices[0]) + RED + line.substring(indices[0], indices[1]) + NC + line.substring(indices[1], line.length()));
        }
      }
      // System.out.println(automata);
    } catch (Exception e) {
      System.out.println(e);
    }
    return;
  }
}
