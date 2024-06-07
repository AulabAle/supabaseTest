package it.aulab.progettoblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progettoblog.dtos.AuthorDto;
import it.aulab.progettoblog.dtos.PostDto;
import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.models.Post;
import it.aulab.progettoblog.services.CrudService;

@Controller
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    @Qualifier("postService")
    CrudService<PostDto,Post, Long> service;

    @Autowired
    @Qualifier("authorService")
    CrudService<AuthorDto,Author, Long> authorService;
    
    @GetMapping
    public String postsView(Model model) {
        model.addAttribute("title", "Posts");
        model.addAttribute("posts", service.readAll(null));
        return "posts";
    }

    @GetMapping("add")
    public String postAddView(Model model) {
        model.addAttribute("title", "Add Post");
        model.addAttribute("post", new Post());
        model.addAttribute("authors", authorService.readAll(null));
        return "postAdd";
    }

    @PostMapping
    public String addPost(@ModelAttribute("post") Post post) {
        service.create(post);
        return "redirect:/posts";
    }

    @GetMapping("remove/{id}")
    public String removePost(@PathVariable("id") Long id){
        service.delete(id);
        return "redirect:/posts";
    }

    @GetMapping("modify/{id}")
    public String postModifyView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Modify Post");
        model.addAttribute("post", service.read(id));
        model.addAttribute("authors", authorService.readAll(null));
        return "postModify";
    }

    @PostMapping("{id}")
    public String modifyPost(@PathVariable("id")Long id, @ModelAttribute("post") Post post){
        service.update(id, post);
        return "redirect:/posts";
    }

}

