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

import it.aulab.progettoblog.dtos.PostDto;
import it.aulab.progettoblog.models.Post;
import it.aulab.progettoblog.services.CrudService;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
   
    // @Autowired
    // PostService postService;

    @Autowired
    @Qualifier("postService")
    CrudService<PostDto,Post,Long> postService;

    @GetMapping
    public List<PostDto> getAllPost(@RequestParam Map<String, String> paramListMap) {
        return postService.readAll(paramListMap);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        return postService.read(id);
    }

    @PostMapping
    public PostDto createPost(@RequestBody Post post){
        return postService.create(post);
    }

    @PutMapping("{id}")
    public PostDto updatePost(@PathVariable("id") Long id, @RequestBody Post post){
        return postService.update(id, post);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable("id") Long id){
        postService.delete(id);
    }
}
