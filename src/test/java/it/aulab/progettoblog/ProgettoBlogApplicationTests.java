package it.aulab.progettoblog;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;

import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.models.Comment;
import it.aulab.progettoblog.models.Post;
import it.aulab.progettoblog.repositories.AuthorRepository;
import it.aulab.progettoblog.repositories.CommentRepository;
import it.aulab.progettoblog.repositories.PostRepository;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProgettoBlogApplicationTests {

	@Autowired
    private AuthorRepository authorRepository;

	@Autowired
    private PostRepository postRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    

	// @BeforeEach
	// void load() {
	// 	System.out.println("Loading data...");

    //     Author a1 = new Author();
    //     a1.setName("Mario");
    //     a1.setSurname("Rossi");
    //     a1.setEmail("mariorossi@aulab.it");

    //     authorRepository.save(a1);

    //     // se vuoi far vedere id
    //     // a1 = authorRepository.save(a1);
    //     // System.out.println(a1);

    //     Author a2 = new Author();
    //     a2.setName("Luigi");
    //     a2.setSurname("Verdi");
    //     a2.setEmail("luigiverdi@aulab.it");

    //     authorRepository.save(a2);

    //     // se vuoi far vedere id
    //     // a2 = authorRepository.save(a2);
    //     // System.out.println(a2);

	// 	Post p1 = new Post();
    //     p1.setTitle("Titolo 1");
    //     p1.setBody("Corpo 1");
    //     p1.setAuthor(a1);
    //     postRepository.save(p1);

    //     Post p2 = new Post();
    //     p2.setTitle("Titolo 2");
    //     p2.setBody("Corpo 2");
    //     p2.setAuthor(a2);
    //     postRepository.save(p2);

    //     Post p3 = new Post();
    //     p3.setTitle("Titolo 3");
    //     p3.setBody("Corpo 3");
    //     p3.setAuthor(a1);
    //     postRepository.save(p3);
	// }

	// @Test
    // void countAuthors() {
    //     System.out.println("Counting authors...");
    //     // System.out.println("Authors: " + authorRepository.count());
    //     assertThat(authorRepository.count()).isEqualTo(2);
    // }

	// @Test
    // void countPosts() {
    //     System.out.println("Counting posts...");
    //     // System.out.println("Posts: " + postRepository.count());
    //     // assertThat(postRepository.count()).isEqualTo(3);
    //     //! Oppure
    //     assertThat(postRepository.findAll()).hasSize(3);
    // }

	// @Test
    // void checkAuthor () {
    //     System.out.println("Checking author...");
    //     assertThat(postRepository.findAll())
    //         .extracting("author")
    //         .extracting("name")
    //         .containsOnly("Mario", "Luigi");
    // }

	// @Test
    // void deletePost(){
    //     System.out.println("Deleting post...");
    //     Iterable<Post> posts = postRepository.findAll();
    //     Post p = posts.iterator().next();
    //     postRepository.delete(p);
    //     assertThat(postRepository.findAll()).hasSize(2);
    // }

	// @Test
    // void updatePost() {
    //     System.out.println("Updating post...");
    //     Iterable<Post> posts = postRepository.findAll();
    //     Post p = posts.iterator().next();
    //     p.setTitle("Titolo 1 modificato");
    //     postRepository.save(p);
    //     assertThat(postRepository.findById(p.getId()).get())
    //         .extracting("title")
    //         .isEqualTo("Titolo 1 modificato");
   
    //     //! Oppure
    //     assertThat(postRepository.findAll()).element(0)
    //         .extracting("title")
    //         .isEqualTo("Titolo 1 modificato");
    // }

    @Test
	void findByFirstNameAndLastName() {
		assertThat(authorRepository.findByNameAndSurname("Vincenzo", "Bianchi"))
				.extracting("surname")
				.containsOnly("Bianchi");
	}

    @Test
	void findByTitle() {
		assertThat(postRepository.findByTitle("Lorem ipsum .....")).hasSize(2);
	}

    @Test
	void findByTitleContaining() {
		assertThat(postRepository.findByTitleContaining("ipsum")).hasSize(2);
	}

    @Test
	void eliminaCommentiDaMail() {
		List<Post> posts = postRepository.findAll();

		Comment c1 = new Comment();
		c1.setEmail("vincblanche@tin.it");
		c1.setBody("Lorem ipsum...");
		c1.setDate("20230707");
		c1.setPost(posts.get(0));

		commentRepository.save(c1);

		Comment c2 = new Comment();
		c2.setEmail("vincblanche@tin.it");
		c2.setBody("Lorem ipsum...");
		c2.setDate("20230707");
		c2.setPost(posts.get(1));

		commentRepository.save(c2);

		assertThat(commentRepository.eliminaCommentiDaMail("testautore@tin.it"))
				.isEqualTo(0);

		assertThat(commentRepository.eliminaCommentiDaMail("vincblanche@tin.it"))
				.isEqualTo(2);

		assertThat(commentRepository.findAll()).hasSize(2);

	}

    @Test
	void aggiornaNomi() {
		Author a = authorRepository.findAll().get(0);
		assertThat(authorRepository.aggiornaNomi("Vincenzo", "Bianchi", a.getId())).isEqualTo(1);

		// Con richiamo a metodo statico
        // assertThat(authorRepository.findById(a.getId()).get()).extracting(Author::getSurname).isEqualTo("Bianchi");

        //Senza metodo statico
		assertThat(authorRepository.findById(a.getId()).get())
			.extracting("surname")
			.isEqualTo("Bianchi");
	}

    @Test
	void findByAuthor() {
		List<Author> authors = authorRepository.findAll();
		Author a1 = authors.get(0);
		Author a2 = authors.get(1);

		assertThat(postRepository.findByAuthor(a1)).hasSize(2);
		assertThat(postRepository.findByAuthor(a2)).hasSize(0);

	}

    @Test
	void findByAuthorId() {
		List<Author> authors = authorRepository.findAll();
		Author a1 = authors.get(0);
		Author a2 = authors.get(1);

		assertThat(postRepository.findByAuthorId(a1.getId())).hasSize(2);
		assertThat(postRepository.findByAuthorId(a2.getId())).hasSize(0);

	}

    @Test
	void findByAuthorNameAndAuthorSurname() {
		assertThat(postRepository.findByAuthorNameAndAuthorSurname("Vincenzo", "Bianchi"))
			.hasSize(2);

	}

}
