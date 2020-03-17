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
    if (args.length < 3) {
      System.out.println("Need an input file containing Jaccard distances, the number of books (can be bigger than the actual number), and a jaccard precision.");
      return;
    }
    Graph graph = new Graph(args[0], Integer.parseInt(args[1]), Double.parseDouble(args[2]));
    List<String> result =
      graph.betweenness.entrySet()
      .stream()
      .parallel()
      .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
      .map( (Map.Entry<String, Double> entry) -> {
        return String.format("%-29s %f",entry.getKey(),entry.getValue());
      })
      .collect(Collectors.toList());
    for (String line : result) {
      System.out.println(line);
    }
  }

  private Graph(String filename, int size, double jaccardPrecision) {
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
      List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
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
      Floyd_Warshall(jaccardPrecision);
      for (int v = 0; v < books.size(); v++) {
        betweenness.put(books.get(v),betweenness(v));
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void Floyd_Warshall(double jaccardPrecision) {
    double[][] dists=new double[books.size()][books.size()];
    for (int i=0;i<books.size();i++) {
      for (int j=0;j<books.size();j++) {
        if (i != j) {
          dists[i][j] = weights[i][j];
        }
        else {
          dists[i][j] = 0.;
        }
        if (dists[i][j] != Double.POSITIVE_INFINITY) {
          paths.get(i).get(j).add(j);
        }
      }
    }
    for (int k = 0; k < books.size(); k++) {
    	for (int i = 0; i < books.size(); i++) {
    		for (int j = 0; j < books.size(); j++) {
    			double next = dists[i][k] + dists[k][j];
          if (i != j && i != k && j != k && next != Double.POSITIVE_INFINITY) {
      			if (next < dists[i][j]) {
      				dists[i][j] = next;
      				paths.get(i).get(j).clear();
              paths.get(i).get(j).addAll(paths.get(i).get(k));
      			}
            else if (next <= dists[i][j] + jaccardPrecision) {
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
          for (List<Integer> path : IJPaths) {
            if (path.contains(v)) {
              pathsThroughV++;
            }
          }
          if (!IJPaths.isEmpty()) { // to avoid 0/0
            result += ((double) pathsThroughV) / ((double) IJPaths.size());
          }
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
      if (path.get(path.size() - 1) == j) { // getPathsStep is easier to write if we let it add some wrong paths (but all the good ones)
        result.add(path);
      }
    }
    return result;
  }

  private List<List<Integer>> getPathsStep(int j, List<List<Integer>> current_paths) {
    boolean nochange = true;
    List<List<Integer>> new_paths = new ArrayList<List<Integer>>();
    for (List<Integer> path : current_paths) {
      int last = path.get(path.size() - 1); // this should be either j or a node between i and j
      if (last != j) { // we need to go on on this path
        if (paths.get(last).get(j).contains(j)) { // check if we can end this path on this step
          path.add(j);
          nochange=false;
        }
        else {
          if (!paths.get(last).get(j).isEmpty()) { // that would mean this path doesn't work
            if (!path.contains(paths.get(last).get(j).get(0))) { // 2 cases separated just to avoid needless copies when the size of the list is 1
              path.add(paths.get(last).get(j).get(0));
              nochange=false;
            }
            if (paths.get(last).get(j).size() > 1) {
              for (int l = 1; l < paths.get(last).get(j).size(); l++) {
                if (!path.contains(paths.get(last).get(j).get(l))) {
                  List<Integer> new_path = new ArrayList<Integer>(path);
                  new_path.add(paths.get(last).get(j).get(l));
                  new_paths.add(new_path);
                  nochange=false;
                }
              }
            }
          }
        }
      }
    }
    if (nochange) { // this step didn't do anything, our path set is stable
      return current_paths;
    }
    else {
      current_paths.addAll(new_paths);
      return getPathsStep(j, current_paths);
    }
  }
}
