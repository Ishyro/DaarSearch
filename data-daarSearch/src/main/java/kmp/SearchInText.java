package kmp;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
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
      char[] facteur = args[0].toCharArray();
      int[] retenue = Facteur.createRetenue(facteur);
      List<char[]> lines = new ArrayList<char[]>();
      Path path = FileSystems.getDefault().getPath(".", args[1]);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (String str : text) {
        lines.add(str.toCharArray());
      }
      for (int i = 0; i < lines.size(); i++) {
        int indice;
        if ((indice = Facteur.matchingAlgo(facteur, retenue, lines.get(i))) != -1) {
          String line = text.get(i);
          System.out.println(line.substring(0, indice) + RED + line.substring(indice, indice + facteur.length) + NC + line.substring(indice + facteur.length, line.length()));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return;
  }

}
