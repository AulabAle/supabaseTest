package it.aulab.progettoblog.services;

import java.util.List;
import java.util.Map;

import it.aulab.progettoblog.models.Author;

@Deprecated(since = "1.1. Use generics CrudService interface")
public interface AuthorService {
    List<Author> readAll(Map<String, String> paramas);
    Author read(Long id);
    Author create(Author author);
    Author update(Long id, Author author);
    void delete(Long id);
}
