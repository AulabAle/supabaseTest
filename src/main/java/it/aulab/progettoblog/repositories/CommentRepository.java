package it.aulab.progettoblog.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import it.aulab.progettoblog.models.Comment;
import it.aulab.progettoblog.models.Post;

public interface CommentRepository extends ListCrudRepository<Comment, Long>{
    @Modifying
    @Query(value = "DELETE FROM comments WHERE email = ?1", nativeQuery = true)
    int eliminaCommentiDaMail(String email);

    @Modifying
    @Query(value = "DELETE FROM comments WHERE post_id = ?1", nativeQuery = true)
    int eliminaCommentiDaPost(Long idPost);

    void deleteByPostId(Post post);
    void deleteByPostId(Long postId);


}

