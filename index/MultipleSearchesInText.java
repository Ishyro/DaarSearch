package index;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;
import java.util.Map;

// this program is for test purposes and does not reproduce egrep usage
// we build the tree once, and use it for all the words on each line

public class MultipleSearchesInText {

  public static void main(String args[]) {
    if (args.length < 3) {
      // index in first input for easier bash script
      System.out.println("This program require an file, a text file, and some regexes in input.");
      return;
    }
    try {
      String RED = "\033[1;31m";
      String NC = "\033[0m";
      RadixTree tree = new RadixTree();
      tree.init(new Trie(args[0]));
      Path path = FileSystems.getDefault().getPath(".", args[1]);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (int i = 2; i < args.length; i++) {
        List<List<Integer>> indices = tree.contains(args[i]);
        if (indices != null) {
          int previous = -1;
          for (int j = 0; j < indices.get(0).size(); j++) {
            int textIndice = indices.get(0).get(j);
            int lineIndice = indices.get(1).get(j);
            String line = text.get(textIndice);
            if (previous != textIndice) // don't print the same line multiple times
              System.out.println(line.substring(0, lineIndice) + RED + line.substring(lineIndice, lineIndice + args[i].length()) + NC + line.substring(lineIndice + args[i].length(), line.length()));
            previous = textIndice;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
