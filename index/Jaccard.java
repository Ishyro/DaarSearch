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
    System.out.println(RadixTree.jaccardDistance(tree1, tree2));
  }
}
