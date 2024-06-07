package it.aulab.progettoblog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progettoblog.dtos.CommentDto;
import it.aulab.progettoblog.models.Comment;
import it.aulab.progettoblog.models.Post;
import it.aulab.progettoblog.repositories.CommentRepository;
import it.aulab.progettoblog.repositories.PostRepository;

@Service("commentService")
public class CommentServiceImpl implements CrudService<CommentDto, Comment,Long>{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto create(Comment comment) {
        // Validation
        if (comment.getPost() != null && comment.getPost().getId() != null) {
            Optional<Post> optPost = postRepository.findById(comment.getPost().getId());
            if (optPost.isPresent()) {
                comment.setPost(optPost.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Post id=" + comment.getPost().getId() + " not found");
            }
        }
        return mapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public void delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public CommentDto read(Long id) {
        Optional<Comment> optComment = commentRepository.findById(id);
        if (optComment.isPresent()) {
            return mapper.map(optComment.get(), CommentDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public List<CommentDto> readAll(Map<String, String> params) {
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        // for (Comment comment : commentRepository.findAll()) {
        //     dtos.add(mapper.map(comment, CommentDto.class));
        // }

        //Oppure
        //????
        //commentRepository.findAll().stream().forEach((comment)-> mapper.map(comment, CommentDto.class));

        // commentRepository.findAll().stream().forEach((comment)-> {
        //     mapper.map(comment, CommentDto.class);
        // });

        dtos = commentRepository.findAll().stream().map((comment)-> mapper.map(comment, CommentDto.class)).toList();

        // dtos = commentRepository.findAll().stream().map((comment)->{ 
        //     return mapper.map(comment, CommentDto.class);
        // }).toList();

        return dtos;
    }

    @Override
    public CommentDto update(Long id, Comment comment) {
        if (commentRepository.existsById(id)) {
            comment.setId(id);
            return mapper.map(commentRepository.save(comment), CommentDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }

    @Override
    public CommentDto createWithImage(Comment model, MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    }

    @Override
    public CommentDto createWithImage(Comment model, ArrayList<MultipartFile> files) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    }
    
}
