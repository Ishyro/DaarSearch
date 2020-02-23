package automata;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import java.lang.Exception;

public class Automata {

  private int states;
  private ArrayList<Arc> arcs;
  private boolean[] init;
  private boolean[] accept;

  public Automata(int states) {
    this.states = states;
    arcs = new ArrayList<Arc>();
    init = new boolean[states];
    accept = new boolean[states];
    init[0] = true;
    accept[states - 1] = true;
  }

  // just Aho-Ullman algorithm
  public static Automata concat(Automata a1, Automata a2) {
    Automata automata = new Automata(a1.states + a2.states);
    for (Arc arc : a1.arcs)
      automata.arcs.add(arc);
    for (Arc arc : a2.arcs)
      automata.arcs.add(new Arc(a1.states + arc.origin, a1.states + arc.destination, arc.character));
    automata.arcs.add(new Arc(a1.states - 1, a1.states, Arc.EPSILON));
    return automata;
  }

  // just Aho-Ullman algorithm
  public static Automata union(Automata a1, Automata a2) {
    Automata automata = new Automata(a1.states + a2.states + 2);
    for (Arc arc : a1.arcs)
      automata.arcs.add(new Arc(arc.origin + 1, arc.destination + 1, arc.character));
    for (Arc arc : a2.arcs)
      automata.arcs.add(new Arc(a1.states + arc.origin + 1, a1.states + arc.destination + 1, arc.character));
    automata.arcs.add(new Arc(0, 1, Arc.EPSILON));
    automata.arcs.add(new Arc(0, a1.states + 1, Arc.EPSILON));
    automata.arcs.add(new Arc(a1.states, automata.states - 1, Arc.EPSILON));
    automata.arcs.add(new Arc(a1.states + a2.states, automata.states - 1, Arc.EPSILON));
    return automata;
  }

  // just Aho-Ullman algorithm
  public static Automata closure(Automata a) {
    Automata automata = new Automata(a.states + 2);
    for (Arc arc : a.arcs)
      automata.arcs.add(new Arc(arc.origin + 1, arc.destination + 1, arc.character));
    automata.arcs.add(new Arc(0, 1, Arc.EPSILON));
    automata.arcs.add(new Arc(0, automata.states - 1, Arc.EPSILON));
    automata.arcs.add(new Arc(a.states, automata.states - 1, Arc.EPSILON));
    automata.arcs.add(new Arc(a.states, 1, Arc.EPSILON));
    return automata;
  }

  // just Aho-Ullman algorithm, in a recursive function
  public static Automata fromRegExTree(RegExTree regex) {
    Automata automata = null;
    Automata branch1;
    Automata branch2;
    switch (regex.root) {
      case RegEx.CONCAT :
        branch1 = fromRegExTree(regex.subTrees.get(0));
        branch2 = fromRegExTree(regex.subTrees.get(1));
        automata = concat(branch1, branch2);
        break;
      case RegEx.ETOILE :
        branch1 = fromRegExTree(regex.subTrees.get(0));
        automata = closure(branch1);
        break;
      case RegEx.ALTERN :
        branch1 = fromRegExTree(regex.subTrees.get(0));
        branch2 = fromRegExTree(regex.subTrees.get(1));
        automata = union(branch1, branch2);
        break;
      case RegEx.DOT : // same as default but it's still a peculiar case so we duplicate it anyway
        automata = new Automata(2);
        automata.arcs.add(new Arc(0,1,RegEx.DOT));
        break;
      default :
        automata = new Automata(2);
        automata.arcs.add(new Arc(0,1,regex.root));
    }
    Collections.sort(automata.arcs);
    return automata;
  }

  public static Automata fromEpsilonAutomata(Automata a) {
    Automata automata;
    ArrayList<Arc> epsilons = new ArrayList<Arc>();
    ArrayList<Arc> no_epsilons = new ArrayList<Arc>();
    for (Arc arc : a.arcs) {
      if (arc.character == Arc.EPSILON)
        epsilons.add(arc);
      else
        no_epsilons.add(arc);
    }
    //keep the states we can access without any espilon transition
    boolean[] real_states = new boolean[a.states];
    real_states[0] = true; // we need the origin of the automata
    int states = 1;
    for (Arc arc : no_epsilons) {
      real_states[arc.destination] = true;
      states++;
    }
    automata = new Automata(states);
    boolean done = false;
    ArrayList<Arc> current = new ArrayList<Arc>();
    while (!done) {
      done = true;
      for (int i = 0; i < a.states; i++) {
        if (!real_states[i]) {
          for (Arc epsilon : epsilons) {
            if (epsilon.destination == i) {
              for (Arc tochange : no_epsilons) {
                if (tochange.origin == i) {
                  current.add(new Arc(epsilon.origin, tochange.destination, tochange.character));
                  done = false;
                }
              }
            }
          }
        }
        else {
          for (Arc tokeep : no_epsilons) {
            if (tokeep.origin == i && real_states[tokeep.destination]) {
              current.add(new Arc(tokeep.origin, tokeep.destination, tokeep.character));
            }
          }
        }
      }
      no_epsilons.clear();
      no_epsilons.addAll(current);
      current.clear();
    }
    // changing states names, an automata should not have states with random numbers as label
    for (Arc arc : no_epsilons) {
      int cpt_origin = 0;
      int cpt_destination = 0;
      for (int i = 0; i < a.states; i++) {
        if (real_states[i] && i < arc.origin)
          cpt_origin++;
        if (real_states[i] && i < arc.destination)
          cpt_destination++;
      }
      automata.arcs.add(new Arc(cpt_origin, cpt_destination, arc.character));
    }
    // selecting accepting states
    int cpt_states = 0;
    for (int i = 0; i < a.states; i++) {
      if (real_states[i]) {
        if (reach(i, a.states - 1, epsilons))
          automata.accept[cpt_states] = true;
        cpt_states++;
      }
    }
    // sort mainly for printing purposes
    // the automata should not have a huge number of arcs anyway
    Collections.sort(automata.arcs);
    return automata;
  }

  // test if we can reach a state from another using arcs from a list
  public static boolean reach(int origin, int destination, ArrayList<Arc> arcs) {
    for (Arc arc : arcs) {
      if (arc.origin == origin) {
        if (arc.destination == destination)
          return true;
        if (reach(arc.destination, destination, arcs))
          return true;
      }
    }
    return false;
  }

  // Warning : use only without epsilon arcs
  // return start and end of the match inside the line
  public int[] accept(String line) {
    for (int i = 0; i < states; i++) {
      int[] res;
      if (accept[i] && (res = reachString(0, i, 0, 1, line)) != null)
        return res;
    }
    return null;
  }
  // Warning : use only without epsilon arcs
  public int[] reachString(int origin, int destination, int current, int next, String line) {
    if (line.length() == 0)
      return null;
    int[] res = new int[2];
    res[0] = current;
    res[1] = next;
    for (Arc arc : arcs) {
      if (arc.origin == origin && (arc.character == line.charAt(0) || arc.character == RegEx.DOT)) {
        if (arc.destination == destination)
          return res;
        return reachString(arc.destination, destination, current, next + 1, line.substring(1));
      }
    }
    return reachString(0, destination, next, next + 1, line.substring(1));
  }

  // a human-friendly string to print
  public String toString() {
    String ret = "Number of States : " + states + "\n";
    ret += "Accepting states : ";
    for (int i = 0; i < states; i++) {
      if (accept[i])
        ret += i + " ";
    }
    ret += "\nArcs :\n";
    for (Arc arc : arcs)
      ret += arc + "\n";
    return ret;
  }
}
