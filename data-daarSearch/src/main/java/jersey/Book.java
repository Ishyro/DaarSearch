package jersey;

public class Book {
    private String name;
    private String content;
    private String contentBubble;
    private String shortName;

    Book(String name, String content, String contentBubble) {
        this.name = name;
        this.content = content;
        this.contentBubble = contentBubble;
        int start = content.indexOf("Title");
        int end =  content.indexOf("Author");
        this.shortName = content.substring(start + 6, start + 26).replace("\n","");
    }

    public String getShortName(){
      return this.shortName;
    }

    public void setShortName(String shortName){
      this.shortName = shortName;
    }
    public String getContentBubble(){
      return contentBubble;
    }

    public String getName() {
        return name;
    }

    public void setContentBubble(String contentBubble){
      this.contentBubble = contentBubble;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
