package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.List;

import automata.RegEx;
import automata.Automata;

@javax.ws.rs.Path("/advancedsearch")
public class AdvancedSearch extends AbstractSearch {

  public AdvancedSearch(String query, List<String> books) {
    super(query, books);
  }

  @Override
  public void resolve() {
    for (String bookName : books) {
      try {
        RegEx regex = new RegEx(query);
        Automata automata = Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse()));
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (String line : lines) {
          if (automata.accept(line) != null)
            result += bookName + ";";
        }
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }
}
