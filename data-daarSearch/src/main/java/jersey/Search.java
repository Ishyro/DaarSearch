package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

import kmp.Facteur;

@Path("/search")
public class Search extends AbstractSearch {

  public Search(String query, List<String> books) {
    super(query, books);
  }

  @Override
  public void resolve() {
    for (String bookName : books) {
      try {
        char[] facteur = query.toCharArray();
        int[] retenue = Facteur.createRetenue(facteur);
        List<char[]> lines = new ArrayList<char[]>();
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> text = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (String str : text) {
          lines.add(str.toCharArray());
        }
        for (int i = 0; i < lines.size(); i++) {
          if (Facteur.matchingAlgo(facteur, retenue, lines.get(i)) != -1)
            result += bookName + ";";
        }
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }
}
