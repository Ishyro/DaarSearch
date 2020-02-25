package index;

import java.nio.file.Paths;

public class Jaccard {

  public static void main(String args[]) {
    if (args.length < 2) {
      System.out.println("Need two input files.");
      return;
    }
    RadixTree tree1 = new RadixTree();
    tree1.init(new Trie(args[0]));
    RadixTree tree2 = new RadixTree();
    tree2.init(new Trie(args[1]));
    String file1 = Paths.get(args[0]).getFileName().toString();
    String file2 = Paths.get(args[1]).getFileName().toString();
    System.out.println(file1.substring(0, file1.lastIndexOf('.')) + " " +
      file2.substring(0, file2.lastIndexOf('.')) + " " + RadixTree.jaccardDistance(tree1, tree2));
  }
}
