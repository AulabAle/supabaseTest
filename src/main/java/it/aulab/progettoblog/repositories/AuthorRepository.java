package it.aulab.progettoblog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import it.aulab.progettoblog.models.Author;

public interface AuthorRepository extends ListCrudRepository<Author, Long>{
    List<Author> findByNameAndSurname(String name, String surname);
    List<Author> findByEmail(String email);
    
    @Modifying
    @Query(value = "UPDATE authors SET firstname = :firstname, lastname = :lastname WHERE id = :id", nativeQuery = true)
    int aggiornaNomi(
        @Param("firstname") String pippo1,
        @Param("lastname") String pippo2,
        @Param("id") Long authorId);
}
