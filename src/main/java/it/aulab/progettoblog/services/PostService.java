//file services/PostService.java
package it.aulab.progettoblog.services;
import java.util.List;
import java.util.Map;
import it.aulab.progettoblog.models.Post;

@Deprecated(since = "1.1. Use generics CrudService interface")
public interface PostService {
    List<Post> readAll(Map<String, String> paramas);
    Post read(Long id);
    Post create(Post post);
    Post update(Long id, Post post);
    void delete(Long id);
}
