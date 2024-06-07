package it.aulab.progettoblog.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import it.aulab.progettoblog.dtos.AuthorDto;
import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.services.CrudService;

@Controller
@RequestMapping("/authors")
public class AuhtorController {

    // @Autowired
    // AuthorService service;

    @Autowired
    @Qualifier("authorService")
    CrudService<AuthorDto,Author,Long> service;

    
    @GetMapping
    public String authorsView(Model viewModel) {
        viewModel.addAttribute("title", "Authors");
        viewModel.addAttribute("authors", service.readAll(null));
        return "authors";
    }

    @GetMapping("x")
        public String authorsFake() {
        return "redirect:/authors";
    }

    @GetMapping("add")
    public String authorAddView(Model viewModel) {
        viewModel.addAttribute("title", "Add Author");
        viewModel.addAttribute("author", new Author());
        return "authorAdd";
    }

    @PostMapping
    public String addAuthor(@ModelAttribute("author") Author author, MultipartFile file) {
        //service.create(author);
        service.createWithImage(author, file);
        return "redirect:/authors"; // quando faccio redirect il browser poi fa una get 
    }

    //MultipartFile file
    // @PostMapping
    // public String addAuthor(@ModelAttribute("author") Author author, ArrayList<MultipartFile> files) {
    //     //service.create(author);
    //     service.createWithImage(author, files);
    //     return "redirect:/authors"; // quando faccio redirect il browser poi fa una get 
    // }
    
    // @GetMapping("remove/{id}")
    // public String removeAuthor(@PathVariable("id") Long id) {
    //     service.delete(id);
    //     return "redirect:/authors";
    // }


    @GetMapping("remove/{id}")
    public String removeAuthor(@PathVariable("id") Long id) {
       
        // Author author = service.read(id);

        // if (author.getPosts() != null && !author.getPosts().isEmpty()) {
        //     Iterable<Post> posts = author.getPosts();
        //     for (Post post: posts) {
        //         post.setAuthor(null);
        //     }
        // }
    
        service.delete(id);
    
        return "redirect:/authors";
    }

    @GetMapping("modify/{id}")
    public String modifyAuthorView(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Modify Author");
        viewModel.addAttribute("author", service.read(id));
        return "authorModify";
    }

    @PostMapping("modify")
    public String modifyAuthor(@ModelAttribute("author") Author author) {
        service.update(author.getId(), author);
        return "redirect:/authors";
    }

    @GetMapping("detail/{id}")
    public String detailAuthorView(@PathVariable("id") Long id, Model viewModel) {
        viewModel.addAttribute("title", "Modify Author");
        viewModel.addAttribute("author", service.read(id));
        return "authorDetail";
    }


}
