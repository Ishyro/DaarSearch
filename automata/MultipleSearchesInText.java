package automata;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.lang.StringBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

// this program is for test purposes and does not reproduce egrep usage

public class MultipleSearchesInText {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("This program require a file and some regexes in input.");
      return;
    }
    try {
      String RED = "\033[1;31m";
      String NC = "\033[0m";
      Path path = FileSystems.getDefault().getPath(".", args[0]);
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      List<Automata> automatas = new ArrayList<Automata>();
      for (int i = 1; i < args.length; i++) {
        RegEx regex = new RegEx(args[i]);
        automatas.add(Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse())));
      }
      for (String line : lines) {
        for (Automata automata : automatas) {
          int[] indices;
          if ((indices = automata.accept(line)) != null) {
            System.out.println(line.substring(0, indices[0]) + RED + line.substring(indices[0], indices[1]) + NC + line.substring(indices[1], line.length()));
          }
        }
      }
      // System.out.println(automata);
    } catch (Exception e) {
      System.out.println(e);
    }
    return;
  }
}
