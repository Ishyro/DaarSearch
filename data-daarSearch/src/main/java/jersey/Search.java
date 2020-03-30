package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.ArrayList;

import kmp.Facteur;

@Path("/search")
public class Search {

  @GET
  @Path("/{word}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMessage(@PathParam("word") String word) {
    List<String> result = new ArrayList<String>();
    List<Book> ret = new ArrayList<Book>();

    try {
      java.nio.file.Path pathBookName = FileSystems.getDefault().getPath(".", "src/main/resources/books_list.txt");
      List<String> books = Files.readAllLines(pathBookName, StandardCharsets.ISO_8859_1);
      for (String bookName : books) {
        String nameOnly = bookName.substring("src/main/resources/indexes/".length(), bookName.lastIndexOf('.'));
        char[] facteur = word.toCharArray();
        int[] retenue = Facteur.createRetenue(facteur);
        List<char[]> lines = new ArrayList<char[]>();
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> text = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        for (String str : text) {
          lines.add(str.toCharArray());
        }
        for (int i = 0; i < lines.size(); i++) {
          if (Facteur.matchingAlgo(facteur, retenue, lines.get(i)) != -1) {
            //result.add("{\n\tbookName: \"" + nameOnly + "\",\n\tbookContent: \"" + word + "\"\n\t}");
            ret.add(new Book(nameOnly, word));
            break;
          }
        }
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }
    //return result;
    return Response.status(Response.Status.OK)
                .entity(ret)
                .build();
  }
}
