package kmp;

public class Facteur {

  // mainly for testing purposes
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Need an argument");
      return;
    }
    toPrint(args[0]);
  }

  public static int matchingAlgo(char[] facteur, int[] retenue, char[] texte) {
    int i = 0;
    int j = 0;
    while (i < texte.length) {
      if (j == facteur.length) {
        return i - facteur.length;
      }
      if (texte[i] == facteur[j]) {
        i++;
        j++;
      }
      else {
        if (retenue[j] == -1) {
          i++;
          j = 0;
        }
        else {
          j = retenue[j];
        }
      }
    }
    return -1;
  }

  public static int[] createRetenue(char[] facteur) {
    int[] retenue = new int[facteur.length + 1];
    int cnd = 0;
    retenue[0] = -1;
    for (int i = 1; i < facteur.length; i++) {
      if (facteur[i] == facteur[cnd]) {
        retenue[i] = retenue[cnd];
      }
      else {
        retenue[i] = cnd;
        while (cnd >= 0 && facteur[i] != facteur[cnd]) {
          cnd = retenue[cnd];
        }
      }
      cnd++;
    }
    return retenue;
  }

  // mainly for testing purposes
  public static void toPrint(String word) {
    char[] facteur = word.toCharArray();
    String ret = "";
    int[] retenue = createRetenue(facteur);
    for (int i = 0; i < facteur.length; i++) {
      ret += facteur[i] + " " + retenue[i] + "\n";
    }
    ret += "  " + retenue[retenue.length - 1];
    System.out.println(ret);
  }
}
