package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("")
public abstract class AbstractSearch {
  protected String query;
  protected String result;
  protected List<String> books;

  public AbstractSearch(String query, List<String> books) {
    this.query = query;
    this.books = books;
  }

  public abstract void resolve();

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getMessage() {
      return result + "\n";
  }
}
