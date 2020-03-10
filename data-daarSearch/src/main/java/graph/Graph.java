package graph;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.Map;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

public class Graph {
  private List<String> books;
  private List<List<List<Integer>>> paths;
  private double[][] weights;
  private Map<String,Double> betweenness;

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Need an input file containing Jaccard distances, and a size.");
      return;
    }
    Graph graph = new Graph(args[0], Integer.parseInt(args[1]));
    List<String> result =
      graph.betweenness.entrySet()
      .stream()
      .parallel()
      .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
      .map( (Map.Entry<String, Double> entry) -> {
        return entry.getKey() + " " + entry.getValue();
      })
      .collect(Collectors.toList());
    for (String line : result) {
      System.out.println(line);
    }
  }

  private Graph(String filename, int size) {
    try {
      weights = new double[size][size];
      for (int i = 0; i < size; i++) {
        Arrays.fill(weights[i], Double.POSITIVE_INFINITY);
      }
      books = new ArrayList<String>();
      paths = new ArrayList<List<List<Integer>>>(size);
      for (int i = 0; i < size; i++) {
        paths.add(new ArrayList<List<Integer>>(size));
        for (int j = 0; j < size; j++) {
          paths.get(i).add(new ArrayList<Integer>());
        }
      }
      betweenness = new HashMap<String,Double>(size);
      Map<String,Integer> map = new HashMap<String,Integer>(size);
      Path path = FileSystems.getDefault().getPath(".", filename);
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (String line : lines) {
        String[] str = line.split("\\s+");
        if (!map.containsKey(str[0])) {
          map.put(str[0],map.size());
          books.add(str[0]);
        }
        if (!map.containsKey(str[1])) {
          map.put(str[1],map.size());
          books.add(str[1]);
        }
        weights[map.get(str[0])][map.get(str[1])] = Double.parseDouble(str[2]);
        weights[map.get(str[1])][map.get(str[0])] = Double.parseDouble(str[2]);
      }
      map.clear();
      Floyd_Warshall();
      for (int i=0;i<books.size();i++) {
        for (int j=0;j<books.size();j++) {
          System.out.print(paths.get(i).get(j) + "\t");
        }
        System.out.println();
      }
      for (int v = 0; v < books.size(); v++) {
        betweenness.put(books.get(v),betweenness(v));
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void Floyd_Warshall() {
    double[][] dists=new double[books.size()][books.size()];
    for (int i=0;i<books.size();i++) {
      for (int j=0;j<books.size();j++) {
        paths.get(i).get(j).add(j);
        if (i != j) {
          dists[i][j] = weights[i][j];
        }
        else {
          dists[i][j] = 0.;
        }
      }
    }
    for (int k = 0; k < books.size(); k++) {
    	for (int i = 0; i < books.size(); i++) {
    		for (int j = 0; j < books.size(); j++) {
    			double next = dists[i][k] + dists[k][j];
          if (i != j && i != k && j != k && next != Double.POSITIVE_INFINITY) {
            if (next == dists[i][j]) {
              System.out.println(i + " " + j + " " + k);
              paths.get(i).get(j).addAll(paths.get(i).get(k));
            }
      			else if (next < dists[i][j]) {
      				dists[i][j] = next;
      				paths.get(i).get(j).clear();
              paths.get(i).get(j).addAll(paths.get(i).get(k));
      			}
          }
          paths.get(i).set(j, paths.get(i).get(j).stream().distinct().collect(Collectors.toList()));
    		}
    	}
    }
  }

  public double betweenness(int v) {
    double result = 0.;
    for (int i=0;i<books.size();i++) {
      for (int j=0;j<books.size();j++) {
        if (i != j && i != v && j != v) {
          int pathsThroughV = 0;
          List<List<Integer>> IJPaths = getPaths(i,j);
          if (IJPaths.isEmpty()) {
            //System.out.println(books.get(i) + " " + books.get(j));
            continue;
          }
          for (List<Integer> path : IJPaths) {
            if (path.contains(v)) {
              System.out.println("oui");
              pathsThroughV++;
            }
          }
          result += ((double) pathsThroughV) / ((double) IJPaths.size());
        }
      }
    }
    return result;
  }

  private List<List<Integer>> getPaths(int i, int j) {
    List<List<Integer>> startPaths = new ArrayList<List<Integer>>();
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    List<Integer> startPath = new ArrayList<Integer>();
    startPath.add(i);
    startPaths.add(startPath);
    List<List<Integer>> temp = getPathsStep(j,startPaths);
    for (List<Integer> path : temp) {
      // TODO
      System.out.println(path);
      if (path.size() > 2) {
        System.out.println(path);
      }
      if (path.get(path.size() - 1) == j) {
        result.add(path);
      }
    }
    return result;
  }

  private List<List<Integer>> getPathsStep(int j, List<List<Integer>> current_paths) {
    // TODO
    boolean nochange = true;
    List<List<Integer>> new_paths = new ArrayList<List<Integer>>();
    for (int n = 0; n < current_paths.size(); n++) {
      if (!current_paths.get(n).contains(j)) {
        if (paths.get(n).get(j).contains(j)) {
          current_paths.get(n).add(j);
          nochange=false;
        }
        else {
          if (!current_paths.get(n).contains(paths.get(n).get(j).get(0))) {
            current_paths.get(n).add(paths.get(n).get(j).get(0));
            nochange=false;
          }
          if (paths.get(n).get(j).size() > 1) {
            for (int l = 1; l < paths.get(n).get(j).size(); l++) {
              if (!current_paths.get(n).contains(paths.get(n).get(j).get(l))) {
                List<Integer> new_path = new ArrayList<Integer>(current_paths.get(n));
                new_path.add(paths.get(n).get(j).get(l));
                new_paths.add(new_path);
                nochange=false;
              }
            }
          }
        }
      }
    }
    if (nochange) {
      return current_paths;
    }
    else {
      current_paths.addAll(new_paths);
      return getPathsStep(j, current_paths);
    }
  }

  private void printPaths(List<List<Integer>> toprint) {
    for (List<Integer> path : toprint) {
      String str = "";
      for (int node : path) {
        str += (node + " ");
      }
      System.out.println(str);
    }
  }
}
