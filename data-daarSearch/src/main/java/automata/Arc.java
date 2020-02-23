package automata;

// on utilise un modèle objet plus simple à coder

public class Arc implements Comparable {
  public static final int EPSILON = 0xFFFFFFFF;
  public int origin;
  public int destination;
  public int character;

  public Arc(int origin, int destination, int character) {
    this.origin = origin;
    this.destination = destination;
    this.character = character;
  }

  public String toString() {
    String chara;
    if (character == EPSILON) {
      chara = "epsilon";
    }
    else if (character == RegEx.DOT) {
      chara = "dot";
    }
    else {
      chara = Character.toString((char)character);
    }
    return "From " + origin + " to " + destination + " : " + chara;
  }

  public int compareTo(Object o) {
    Arc a = (Arc) o;
    if (origin != a.origin)
      return ((Integer) origin).compareTo(a.origin);
    if (destination != a.destination)
      return ((Integer) destination).compareTo(a.destination);
    if (character != a.character)
      return ((Integer) character).compareTo(a.character);
    return 0;
  }
}
