package graph;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
      Arrays.fill(weights, Double.POSITIVE_INFINITY);
      books = new ArrayList<String>(size);
      paths = new ArrayList<List<List<Integer>>>(size);
      betweenness = new HashMap<String,Double>(size);
      Map<String,Integer> map = new HashMap<String,Integer>(size);
      Path path = FileSystems.getDefault().getPath(".", filename);
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      for (String line : lines) {
        if (line.charAt(0) == '#') {
          continue;
        }
        String[] str = line.split("[ ]");
        if (!map.containsKey(str[0])) {
          map.put(str[0],map.size());
        }
        if (!map.containsKey(str[1])) {
          map.put(str[1],map.size());
        }
        books.set(map.get(str[0]),str[0]);
        books.set(map.get(str[1]),str[1]);
        weights[map.get(str[0])][map.get(str[1])] = Double.parseDouble(str[2]);
        weights[map.get(str[1])][map.get(str[0])] = Double.parseDouble(str[2]);
      }
      Floyd_Warshall();
      for (int v = 0; v < size; v++) {
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
        paths.get(i).set(j,new ArrayList<Integer>());
        paths.get(i).get(j).add(j);
      	dists[i][j] = weights[i][j];
      }
    }
    for (int k = 1; k < books.size(); k++) {
    	for (int i = 0; i < books.size(); i++) {
    		for (int j = 0; j < books.size(); j++) {
    			double next = dists[i][k] + dists[k][j];
          if (next > 0. && next == dists[i][j]) {
            paths.get(i).get(j).addAll(paths.get(i).get(k));
          }
    			else if (next > 0. && next < dists[i][j]) {
    				dists[i][j] = next;
    				paths.get(i).get(j).clear();
            paths.get(i).get(j).addAll(paths.get(i).get(k));
    			}
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
          List<Integer> start = new ArrayList<Integer>();
          List<List<Integer>> IJPaths = getPaths(i,j,start);
          for (List<Integer> path : IJPaths) {
            if (path.contains(v)) {
              pathsThroughV++;
            }
          }
          result += ((double) pathsThroughV) / ((double) IJPaths.size());
        }
      }
    }
    return result;
  }

  private List<List<Integer>> getPaths(int i, int j, List<Integer> offset) {
    List<Integer> current = new ArrayList<Integer>(offset);
    current.add(i);
    List<Integer> path = paths.get(i).get(j);
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    while(!path.contains(j)) {
      for (int next : path) {
        result.addAll(getPaths(next,j,current));
      }
    }
    current.add(j);
    result.add(current);
    return result;
  }
}
