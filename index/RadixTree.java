package index;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class RadixTree {
  private String word;
  private List<RadixTree> children;
  private boolean accept;
  private List<Integer> textIndices;
  private List<Integer> lineIndices;

  // We can't use recursion in a constructor in Java,
  // so we will use a default constructor and an init method as a "real" constructor
  public void init(Trie trie) {
    if (!trie.getAccept() && trie.getChildren().size() == 1) {
      init(trie.getChildren().get(0));
    }
    else {
      children = new ArrayList<RadixTree>();
      word = trie.getWord();
      accept = trie.getAccept();
      textIndices = trie.getTextIndices();
      lineIndices = trie.getLineIndices();
      for (Trie child : trie.getChildren()) {
        RadixTree next = new RadixTree();
        next.init(child);
        children.add(next);
      }
    }
  }

  public List<List<Integer>> contains(String word) {
    return contains(word, 0);
  }

  // index is the current caracter we need to check in the word
  public List<List<Integer>> contains(String word, int index) {
    if (accept && word.equals(this.word)) {
      List<List<Integer>> res = new ArrayList<List<Integer>>();
      res.add(textIndices);
      res.add(lineIndices);
      return res;
    }
    for (RadixTree tree : children) {
      if (word.length() >= tree.word.length() && word.substring(index, tree.word.length()).equals(tree.word.substring(index, tree.word.length())))
        return tree.contains(word, index + 1);
    }
    return null;
  }

  public String toString() {
    String ret =  accept ? word + "\n" : "";
    for (RadixTree tree : children)
      ret += tree.toString();
    return ret;
  }

  public List<String> toList() {
    return Arrays.asList(this.toString().split("[^a-zA-Z]"));
  }

  public int size() {
    int ret =  accept ? 1 : 0;
    for (RadixTree tree : children)
      ret += tree.size();
    return ret;
  }

  public static double jaccardDistance(RadixTree tree1, RadixTree tree2) {
    int inter = 0;
    int union = tree1.size(); // we start with all the words in tree1
    for (String word : tree1.toList()) {
      if (tree2.contains(word) != null) // in tree1 and in tree2
        inter++;
    }
    for (String word : tree2.toList()) {
      if (tree1.contains(word) == null) // not in tree1 but in tree2, since we already counted the words in tree1 
        union++;
    }
    return ( (double) (tree1.size() + tree2.size() - 2 * inter) ) / ( (double) (union) );
  }
}
