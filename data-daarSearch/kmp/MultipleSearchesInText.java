package kmp;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
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
      List<char[]> lines = new ArrayList<char[]>();
      Path path = FileSystems.getDefault().getPath(".", args[0]);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      char[][] facteurs = new char[args.length - 1][];
      int[][] retenues = new int[args.length - 1][];
      for (int i = 1; i < args.length; i++) {
        facteurs[i - 1] = args[i].toCharArray();
        retenues[i - 1] = Facteur.createRetenue(facteurs[i - 1]);
      }
      for (String str : text) {
        lines.add(str.toCharArray());
      }
      for (int i = 0; i < lines.size(); i++) {
        for (int j = 0; j < facteurs.length; j++) {
          int indice;
          if ((indice = Facteur.matchingAlgo(facteurs[j], retenues[j], lines.get(i))) != -1) {
            String line = text.get(i);
            System.out.println(line.substring(0, indice) + RED + line.substring(indice, indice + facteurs[j].length) + NC + line.substring(indice + facteurs[j].length, line.length()));
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return;
  }

}
