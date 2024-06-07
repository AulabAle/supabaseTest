package it.aulab.progettoblog.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.aulab.progettoblog.dtos.CommentDto;
import it.aulab.progettoblog.models.Comment;
import it.aulab.progettoblog.repositories.PostRepository;
import it.aulab.progettoblog.services.CrudService;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController{
   
    @Autowired
    @Qualifier("commentService")
    CrudService<CommentDto,Comment, Long> service;

    @Autowired
    PostRepository postRepository;

    @GetMapping
    public List<CommentDto> getAllComments() {
        return service.readAll(null);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("{id}")
    public CommentDto getComment(@PathVariable("id") Long id){
        return service.read(id);
    }

    @PostMapping
    public CommentDto createComment(@RequestBody Comment comment){
        return service.create(comment);
    }

    @DeleteMapping("{id}")
    public void deleteComment(@PathVariable("id") Long id){
        service.delete(id);
    }

    @PutMapping("{id}")
    public CommentDto updateComment(@PathVariable("id") Long id, @RequestBody Comment comment){
        return service.update(id, comment);
    }
}

