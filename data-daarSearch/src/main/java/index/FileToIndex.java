package index;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.stream.Collectors;
import java.util.Map;

public class FileToIndex {

  public static Path BLACKLIST_PATH = FileSystems.getDefault().getPath(".", "src/main/resources/blacklist.txt");

  public static void main(String args[]) {
    if (args.length < 1) {
      System.out.println("Need an input file.");
      return;
    }
    List<String> blacklist;
    try {
      blacklist = Files.readAllLines(BLACKLIST_PATH, StandardCharsets.UTF_8);
    }
    catch (Exception e) {
      blacklist = new ArrayList<String>();
    }
    List<String> index = index(args[0], blacklist);
    for (String line : index)
      System.out.println(line); // just use > to write in a file
  }

  public static List<String> indexWithIndices(String filename, List<String> blacklist) {
    try {
      // String is the word, List is Number of appearances, (line of appearance, char of appearance) * n
      HashMap<String, List<Integer>> ret = new HashMap<String, List<Integer>>();
      Path path = FileSystems.getDefault().getPath(".", filename);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      int textIndice = 0;
      for (String str : text) {
        String[] temp = str.split("[^a-zA-Z]");
        int lineIndice = 0;
        for (int i = 0; i < temp.length; i++) {
          if (temp[i].length() > 2 && ! blacklist.contains(temp[i].toLowerCase())) { // ignore all words with less than 3 characters
            // String word = temp[i].toLowerCase(); // that's not how egrep works, but we may prefer lowercase for Jaccard distance
            String word = temp[i];
            if (ret.containsKey(word)) {
              ret.get(word).set(0, ret.get(word).get(0) + 1);
              ret.get(word).add(textIndice);
              ret.get(word).add(lineIndice);
            }
            else {
              List<Integer> list = new ArrayList<Integer>();
              list.add(1);
              list.add(textIndice);
              list.add(lineIndice);
              ret.put(word, list);
            }
          }
          lineIndice += temp[i].length() + 1; // don't forget the separation between words
        }
        textIndice++;
      }
      // associate each word with its number of appearance and where it appears (line and char number).
      // sort by ascending number of appearances
      // build a list of string for easy printing
      return ret
        .entrySet()
        .stream()
        .parallel()
        .sorted(Map.Entry.comparingByValue((List<Integer> a, List<Integer> b) -> a.get(0) - b.get(0)))
        .map( (Map.Entry<String, List<Integer>> entry) -> {
          String str = entry.getKey();
          for (Integer i : entry.getValue()) {
            str += " " + i;
          }
          return str;})
        .collect(Collectors.toList());
    } catch (IOException e){
      System.out.println(e);
    }
    return null;
  }

  public static List<String> index(String filename, List<String> blacklist) {
    try {
      // String is the word, List is Number of appearances, (line of appearance, char of appearance) * n
      long nbWords = 0;
      HashMap<String, Integer> ret = new HashMap<String, Integer>();
      Path path = FileSystems.getDefault().getPath(".", filename);
      List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (String str : text) {
        String[] temp = str.split("[^a-zA-Z]");
        for (int i = 0; i < temp.length; i++) {
          String word = temp[i].toLowerCase();
          if (word.length() > 2 && ! blacklist.contains(word)) { // ignore all words with less than 3 characters
            nbWords++;
            if (ret.containsKey(word)) {
              ret.put(word, ret.get(word) + 1);
            }
            else {
              ret.put(word, 1);
            }
          }
        }
      }
      // associate each word with its number of appearance and where it appears (line and char number).
      // sort by ascending number of appearances
      // build a list of string for easy printing
      List<String> result =
        ret.entrySet()
        .stream()
        .parallel()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .map( (Map.Entry<String, Integer> entry) -> {
          return entry.getKey() + " " + entry.getValue();
        })
        .collect(Collectors.toList());
      result.add(0,"# " + nbWords);
      return result;
    } catch (IOException e){
      System.out.println(e);
    }
    return null;
  }
}
