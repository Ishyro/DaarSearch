package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

import automata.RegEx;
import automata.Automata;

@Path("/advancedsearch")
public class AdvancedSearch {
  private List<String> books;

  public AdvancedSearch(List<String> books) {
    this.books = new ArrayList<String>(books);
  }

  @GET
  @Path("/word")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getMessage(@PathParam("word") String word) {
    List<String> result = new ArrayList<String>();
    for (String bookName : books) {
      try {
        String nameOnly = bookName.substring("src/main/resources/indexes/".length(), bookName.lastIndexOf('.'));
        RegEx regex = new RegEx(word);
        Automata automata = Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse()));
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (String line : lines) {
          if (automata.accept(line) != null) {
            result.add("{\n\tbookName: \"" + nameOnly + "\",\n\tbookContent: \"" + word + "\"\n\t}");
            break;
          }
        }
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
    return result;
  }
}
