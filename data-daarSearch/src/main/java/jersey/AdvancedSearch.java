package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.lang.SecurityException;

import java.util.List;
import java.util.ArrayList;

import automata.RegEx;
import automata.Automata;

@Path("/advancedsearch")
public class AdvancedSearch {

  @GET
  @Path("bookSearch/{word}/{page}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMessage(@PathParam("word") String word, @PathParam("page") int page) {
    List<Book> result = new ArrayList<Book>();
    try {
      // this file is already sorted by betweenness
      java.nio.file.Path pathBookName = FileSystems.getDefault().getPath("src/main/resources/", "books_list.txt");
      List<String> books = Files.readAllLines(pathBookName, StandardCharsets.ISO_8859_1);
      for (String bookName : books) {
        String nameOnly = bookName.substring("src/main/resources/indexes/".length(), bookName.lastIndexOf('.'));
        RegEx regex = new RegEx(word);
        Automata automata = Automata.fromEpsilonAutomata(Automata.fromRegExTree(RegEx.parse()));
        java.nio.file.Path path = FileSystems.getDefault().getPath(".", bookName);
        List<String> lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        for (String line : lines) {
          if (automata.accept(line) != null) {
            addBook(nameOnly, result);
            break;
          }
        }
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }

    int sizeBooks = result.size();
    int windowBooks = sizeBooks / 30; // it's a hell of big size list !
    int start_1 = windowBooks * ( page - 1);
    int end_1 = start_1 + 10;
    if( end_1 >= sizeBooks) end_1 = sizeBooks;
    List<Book> ret = result.subList(start_1, end_1);

    System.out.println("hello sil");

    return Response.status(Response.Status.OK)
             .entity(ret)
             .build();
  }

  // @Path("/recoAdvancedSearch/{word}")
  // public Response getSuggestion(@PathParam("word") String word) {
  //   List<Book> result = new ArrayList<Book>();
  //   try {
  //     java.nio.file.Path pathJaccard = FileSystems.getDefault().getPath("src/main/resources/jaccards", word + ".jaccard");
  //     List<String> bookNames = Files.readAllLines(pathJaccard, StandardCharsets.ISO_8859_1);
  //     for (int i = 0; i < Math.min(3, bookNames.size()); i++) {
  //       addBook(bookNames.get(i), result);
  //     }
  //   }
  //   catch(Exception e) {
  //     System.out.println(e);
  //   }
  //
  //   return Response.status(Response.Status.OK)
  //       .entity(result)
  //       .build();
  // }

  private void addBook(String name, List<Book> result) throws IOException, SecurityException {
    java.nio.file.Path pathBookContent = FileSystems.getDefault().getPath("src/main/resources/books", name + ".txt");
    List<String> listContent = Files.readAllLines(pathBookContent, StandardCharsets.ISO_8859_1);
    String content = String.join("\n",listContent);
    result.add(new Book(name, content, content.substring(100,200)));
  }
}
