package index;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


// each node contains :
// the word
// is this word in the text?
// where?
public class Trie {
  private String word;
  private List<Trie> children;
  private boolean accept;
  private List<Integer> textIndices; // which line?
  private List<Integer> lineIndices; // which character is the start of the word?

  public Trie(String word, List<Integer> textIndices, List<Integer> lineIndices, boolean accept) {
    this.word = word;
    this.accept = accept;
    this.textIndices = textIndices;
    this.lineIndices = lineIndices;
    children = new ArrayList<Trie>();
  }

  public Trie(String filename) {
    word = "";
    accept = false;
    children = new ArrayList<Trie>();
    textIndices = new ArrayList<Integer>();
    lineIndices = new ArrayList<Integer>();
    try {
      Path path = FileSystems.getDefault().getPath(".", filename);
      List<String> text = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
      for (String line : text) {
        List<Integer> nextTextIndices = new ArrayList<Integer>();
        List<Integer> nextLineIndices = new ArrayList<Integer>();
        String[] parsed = line.split("\\s+");
        for (int i = 2; i < parsed.length; i+=2) {
          nextTextIndices.add(Integer.valueOf(parsed[i]));
          nextLineIndices.add(Integer.valueOf(parsed[i + 1]));
        }
        add(parsed[0], nextTextIndices, nextLineIndices);
      }
    } catch (IOException e){
      System.out.println(e);
    }
  }

  public void add(String word, List<Integer> textIndices, List<Integer> lineIndices) {
    if (word.equals(this.word)) {
      accept = true;
      this.textIndices = textIndices;
      this.lineIndices = lineIndices;
      return;
    }
    for (Trie trie : children) {
      if (word.charAt(trie.word.length() - 1) == trie.word.charAt(trie.word.length() - 1)) {
        trie.add(word, textIndices, lineIndices);
        return;
      }
    }
    if (this.word.length() == word.length() - 1) {
      children.add(new Trie(word, textIndices, lineIndices, true));
      return;
    } else {
      Trie next = new Trie(word.substring(0, this.word.length() + 1), null, null, false);
      next.add(word, textIndices, lineIndices);
      children.add(next);
    }
  }

  public boolean contains(String word) {
    if (word.equals(this.word))
      return true;
    for (Trie trie : children) {
      if (word.charAt(trie.word.length() - 1) == trie.word.charAt(trie.word.length() - 1))
        return trie.contains(word);
    }
    return false;
  }

  public String toString() {
    String ret =  accept ? word + "\n" : "";
    for (Trie trie : children)
      ret += trie.toString();
    return ret;
  }

  public List<String> toList() {
    return Arrays.asList(this.toString().split("[^a-zA-Z]"));
  }

  public int size() {
    int ret =  accept ? 1 : 0;
    for (Trie trie : children)
      ret += trie.size();
    return ret;
  }

  public boolean getAccept() {
    return accept;
  }

  public String getWord() {
    return word;
  }

  public List<Trie> getChildren() {
    return children;
  }

  public List<Integer> getTextIndices() {
    return textIndices;
  }

  public List<Integer> getLineIndices() {
    return lineIndices;
  }
}
