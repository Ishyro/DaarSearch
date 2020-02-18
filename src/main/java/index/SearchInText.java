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

public class SearchInText {

  public static void main(String args[]) {
    if (args.length < 3) {
    System.out.println("This program require a regex, a text file and an index file in input.");
    return;
    }
    try {
      // constant strings for coloring the matching word in the lines
      String RED = "\033[1;31m";
      String NC = "\033[0m";
      String word = args[0];
      Path path = FileSystems.getDefault().getPath(".", args[1]);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      RadixTree tree = new RadixTree();
      tree.init(new Trie(args[2]));
      List<List<Integer>> indices = tree.contains(word);
      if (indices != null) {
        int previous = -1;
        for (int i = 0; i < indices.get(0).size(); i++) {
          int textIndice = indices.get(0).get(i);
          int lineIndice = indices.get(1).get(i);
          String line = text.get(textIndice);
          if (previous != textIndice) // don't print the same line multiple times
            System.out.println(line.substring(0, lineIndice) + RED + line.substring(lineIndice, lineIndice + word.length()) + NC + line.substring(lineIndice + word.length(), line.length()));
          previous = textIndice;
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
