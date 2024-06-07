package it.aulab.progettoblog.dtos;

public class PostDto {
    //associazione 1:1, il model apper sa gia' come riempire questi campi
    private Long id;
    private String title;
    private String body;
    private String publishDate;
    //non piu' 1:1
    private String authorFullName;
    private Integer numberOfComments;
    private Long authorId;

    public PostDto() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getAuthorFullName() {
        return authorFullName;
    }
    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }
    public Integer getNumberOfComments() {
        return numberOfComments;
    }
    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
