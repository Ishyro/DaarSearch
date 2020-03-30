package jersey;

public class Book {
    private String name;
    private String content;
    private String contentBubble;

    Book(String name, String content, String contentBubble) {
        this.name = name;
        this.content = content;
        this.contentBubble = contentBubble;
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
