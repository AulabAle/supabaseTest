package it.aulab.progettoblog.repositories;

import java.util.List;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.models.Post;

public interface PostRepository extends ListCrudRepository<Post, Long>{
    List<Post> findByTitle(String title);
    List<Post> findByTitleContaining(String title);
    List<Post> findByAuthor(Author author);
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByAuthorNameAndAuthorSurname(String firstName, String lastName);
}

