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

  @GET
  @Path("/{word}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getMessage(@PathParam("word") String word) {
    List<String> result = new ArrayList<String>();
    try {
      java.nio.file.Path pathBookName = FileSystems.getDefault().getPath(".", "src/main/resources/books_list.txt");
      List<String> books = Files.readAllLines(pathBookName, StandardCharsets.ISO_8859_1);
      for (String bookName : books) {
        String nameOnly = bookName.substring("src/main/resources/indexes/".length(), bookName.lastIndexOf('.'));
        RegEx regex = new RegEx(word);
        Automata automata = Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse()));
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        for (String line : lines) {
          if (automata.accept(line) != null) {
            result.add("{\n\tbookName: \"" + nameOnly + "\",\n\tbookContent: \"" + word + "\"\n\t}");
            break;
          }
        }
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }
    return result;
  }
}
