package it.aulab.progettoblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progettoblog.dtos.CommentDto;
import it.aulab.progettoblog.models.Comment;
import it.aulab.progettoblog.services.CrudService;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    @Qualifier("commentService")
    CrudService<CommentDto,Comment,Long> service;

    
    @GetMapping
    public String commentsView(Model viewModel) {
        viewModel.addAttribute("title", "Comments");
        viewModel.addAttribute("comments", service.readAll(null));
        return "comments";
    }

}
