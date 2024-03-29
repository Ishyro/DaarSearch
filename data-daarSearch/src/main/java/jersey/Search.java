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

import java.io.IOException;
import java.lang.SecurityException;

import java.util.List;
import java.util.ArrayList;

import kmp.Facteur;

@Path("/search")
public class Search {

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
            addBook(nameOnly, result);
            break;
          }
        }
      }
    }

    catch(Exception e) {
      System.out.println(e);
    }

    // get the sublist with a given page from the list
    int sizeBooks = result.size();
    int windowBooks = sizeBooks / 30;
    int start_1 = windowBooks * ( page - 1);
    int end_1 = start_1 + windowBooks;
    if( end_1 >= sizeBooks) end_1 = sizeBooks;
    List<Book> ret = result.subList(start_1, end_1);

    return Response.status(Response.Status.OK)
                .entity(ret)
                .build();
  }

  @GET
  @Path("/recoSearch/{word}/{page}")
  public Response getSuggestion(@PathParam("word") String word, @PathParam("page") int page) {
    List<Book> result = new ArrayList<Book>();
    try {
      java.nio.file.Path pathJaccard = FileSystems.getDefault().getPath("src/main/resources/jaccards", word + ".jaccard");
      List<String> bookNames = Files.readAllLines(pathJaccard, StandardCharsets.ISO_8859_1);
      for (int i = 0; i < Math.min(3, bookNames.size()); i++) {
        addBook(bookNames.get(i), result);
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }

    // int sizeBooks = result.size();
    // int windowBooks = sizeBooks / 30;
    // int start_1 = windowBooks * ( page - 1);
    // int end_1 = start_1 + windowBooks;
    // if( end_1 >= sizeBooks) end_1 = sizeBooks;
    // List<Book> ret = result.subList(start_1, end_1);


    return Response.status(Response.Status.OK)
          .entity(result)
          .build();
  }

  private void addBook(String name, List<Book> result) throws IOException, SecurityException {
    java.nio.file.Path pathBookContent = FileSystems.getDefault().getPath("src/main/resources/books", name + ".txt");
    List<String> listContent = Files.readAllLines(pathBookContent, StandardCharsets.ISO_8859_1);
    String content = String.join("\n",listContent);
    result.add(new Book(name, content, content.substring(100,200)));
  }
}
