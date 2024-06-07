package it.aulab.progettoblog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.aulab.progettoblog.dtos.AuthorDto;
import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.services.CrudService;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    @Autowired
    @Qualifier("authorService")
    CrudService<AuthorDto,Author,Long> authorService;

    // @GetMapping
    // public List<Author> getAllAuthors(
    //     @RequestParam(value = "email", required = false) String email, 
    //     @RequestParam(value = "firstname", required = false) String firstname, 
    //     @RequestParam(value = "lastname", required = false) String lastname){
    //     return authorService.readAll(email,firstname,lastname);
    // }

    // @GetMapping
    // public List<Author> getAllAuthors(@RequestParam Map<String,String> paramsList){
    //     return authorService.readAll(
    //         paramsList.get("email"),
    //         paramsList.get("firstname"),
    //         paramsList.get("lastname"));
    // }
    @GetMapping
    public List<AuthorDto> getAllAuthors(@RequestParam Map<String, String> paramsList) {
        return authorService.readAll(paramsList);
    }


    // @ResponseStatus(HttpStatus.ACCEPTED)
    // @GetMapping("{id}")
    // public Author getAuthor(@PathVariable("id") Long id){
    //     if(authorRepository.findById(id).isPresent()){
    //         return authorRepository.findById(id).get();
    //     }else{
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
    //     }
    // }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("{id}")
    public AuthorDto getAuthor(@PathVariable("id") Long id){
        return authorService.read(id);
    }

    @PostMapping
    public AuthorDto createAuthor(@RequestBody Author author){
        return authorService.create(author);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
    }

    @PutMapping("{id}")
    public AuthorDto updateAuthor(@PathVariable("id") Long id, @RequestBody Author author){
        return authorService.update(id, author);
    }

    // @PatchMapping("{id}")
    // public ResponseEntity<Author> updateSinglePartAuthor(@PathVariable("id") Long id, @RequestBody Author updatedAuthor) {
    //     // Trova l'utente nel repository
    //     Author existingAuthor = authorRepository.findById(id).orElse(null);

    //     if (existingAuthor == null) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     // Aggiorna solo i campi non nulli dell'utente esistente con quelli della richiesta
    //     if (updatedAuthor.getName() != null) {
    //         existingAuthor.setName(updatedAuthor.getName());
    //     }

    //     if (updatedAuthor.getSurname() != null) {
    //         existingAuthor.setSurname(updatedAuthor.getSurname());
    //     }

    //     if (updatedAuthor.getEmail() != null) {
    //         existingAuthor.setEmail(updatedAuthor.getEmail());
    //     }

    //     // Salva l'utente aggiornato nel repository
    //     authorRepository.save(existingAuthor);

    //     // Restituisce una risposta con l'utente aggiornato
    //     return ResponseEntity.ok(existingAuthor);
    // }

}


