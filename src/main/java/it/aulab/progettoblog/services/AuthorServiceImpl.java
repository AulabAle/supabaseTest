package it.aulab.progettoblog.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import it.aulab.progettoblog.dtos.AuthorDto;
import it.aulab.progettoblog.models.Author;
import it.aulab.progettoblog.repositories.AuthorRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Service("authorService")
public class AuthorServiceImpl implements CrudService<AuthorDto,Author,Long>{

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    private final RestTemplate restTemplate = new RestTemplate();
    

    // @Override
    // public List<Author> readAll(String email, String firstname, String lastname) {
    //     if(email != null){
    //         return read(email);
    //     }else if (firstname != null && lastname != null){
    //         return read(firstname,lastname);
    //     }else{
    //         return authorRepository.findAll();
    //     }
    // }

    @Override
    public List<AuthorDto> readAll(Map<String, String> params) {
        if (params != null && params.get("email") != null) {
            return read(params.get("email"));
        } else if (params != null && params.get("firstname") != null && params.get("lastname") != null) {
            return read(params.get("firstname"), params.get("lastname"));
        } else {
            List<AuthorDto> dtos = new ArrayList<AuthorDto>();
            for(Author author: authorRepository.findAll()){
            dtos.add(mapper.map(author, AuthorDto.class));
            }
            return dtos;
        }
    }

    @Override
    public AuthorDto read(Long id) {
        Optional<Author> optAuthor = authorRepository.findById(id);
        if (optAuthor.isPresent()) {
            return mapper.map(optAuthor.get(), AuthorDto.class);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author id=" + id + " not found");
        }
    }

    private List<AuthorDto> read(String email) {
      if(email == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        List<AuthorDto> dtos = new ArrayList<AuthorDto>();
        for(Author author: authorRepository.findByEmail(email)){
            dtos.add(mapper.map(author, AuthorDto.class));
        }
        return dtos ;
    }
    

    private List<AuthorDto> read(String firstname, String lastname) {
        if (firstname == null || lastname == null)
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        //   return authorRepository.findByFirstnameAndLastname(firstname, lastname);
        List<AuthorDto> dtos = new ArrayList<AuthorDto>();
        for(Author author: authorRepository.findByNameAndSurname(firstname, lastname)){
        dtos.add(mapper.map(author, AuthorDto.class));
        }
        return dtos ;
    }
    
    @Override
    public AuthorDto create(Author author) {
        // Validation
        if (author.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return mapper.map(authorRepository.save(author), AuthorDto.class);
    }
    
    @Override
    public AuthorDto update(Long id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            return mapper.map(authorRepository.save(author), AuthorDto.class) ;

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public AuthorDto createWithImage(Author author, MultipartFile file) {

        AuthorDto authorDto = new AuthorDto();

        // Validation
        if (author.getEmail() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // String url = imageService.upload(file);
        authorDto = mapper.map(authorRepository.save(author), AuthorDto.class);
        try {
            uploadImage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // imageRepository.save(ImageData.builder().path(url).author(author).build());

        return authorDto;
    }

    // @Override
    // public AuthorDto createWithImage(Author author, ArrayList<MultipartFile> files) {
    //     AuthorDto authorDto = new AuthorDto();

    //     // Validation
    //     if (author.getEmail() == null)
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    //     for (MultipartFile multipartFile : files) {
    //         // String url = imageService.upload(multipartFile);
    //         authorDto = mapper.map(authorRepository.save(author), AuthorDto.class);
    //         // imageRepository.save(ImageData.builder().path(url).author(author).build());
    //     }

    //     return authorDto;
    // }

    // @Override
    // public AuthorDto createWithImage(Author model, MultipartFile file) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    // }


     public String uploadImage(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String url = supabaseUrl + "/storage/v1/object/bucket/supasuca/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + supabaseKey);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (response.getStatusCode() == HttpStatus.OK) {
        //     return url;
        // } else {
        //     throw new Exception("Errore durante l'upload dell'immagine: " + response.getBody());
        // }
        return "ok";
    }

    @Override
    public AuthorDto createWithImage(Author model, ArrayList<MultipartFile> files) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createWithImage'");
    }

}
