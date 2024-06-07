package it.aulab.progettoblog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progettoblog.dtos.PostDto;
import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.models.Post;
import it.aulab.progettoblog.repositories.AuthorRepository;
import it.aulab.progettoblog.repositories.CommentRepository;
import it.aulab.progettoblog.repositories.PostRepository;

@Service("postService")
public class PostServiceImpl implements CrudService<PostDto,Post,Long>{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<PostDto> readAll(Map<String, String> params) {
        List<PostDto> dtos = new ArrayList<PostDto>();
        List<Post> posts = null;
        
        if (params != null && params.get("title") != null) {
            posts = postRepository.findByTitle(params.get("title"));
        } else if (params != null && params.get("title_containing") != null) {
            posts = postRepository.findByTitleContaining(params.get("title_containing"));
        } else if (params != null && params.get("author_name") != null && params.get("author_surname") != null) {
            posts = postRepository.findByAuthorNameAndAuthorSurname(params.get("author_name"),
                    params.get("author_surname"));
        } else if (params != null && params.get("author_id") != null) {
            posts = postRepository.findByAuthorId(Long.parseLong(params.get("author_id")));
        } else {
            posts = postRepository.findAll();
        }

        for (Post post : posts) {
            dtos.add(mapper.map(post, PostDto.class));
        }
        return dtos;
    }

    // @Override
    // public Post create(Post post) {
    //     // Validation -> potrebbe esserci della validazione
    //     return postRepository.save(post);
    // }


    @Override
    public PostDto create(Post post) {
        // Validation
        if (post.getAuthor() != null && post.getAuthor().getId() != null) {
            Optional<Author> optAuthor = authorRepository.findById(post.getAuthor().getId());
            if (optAuthor.isPresent()) {
                post.setAuthor(optAuthor.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author id=" + post.getAuthor().getId() + " not found");
            }
        }
        return mapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (postRepository.existsById(id)) {
            commentRepository.deleteByPostId(id);
            postRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    public PostDto read(Long id) {
        Optional<Post> optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            return mapper.map(optPost.get(), PostDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    public PostDto update(Long id, Post post) {
        if (postRepository.existsById(id)) {
            post.setId(id);
            return mapper.map(postRepository.save(post), PostDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @Override
    public PostDto createWithImage(Post model, MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    }

    @Override
    public PostDto createWithImage(Post model, ArrayList<MultipartFile> files) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    }

    
}
