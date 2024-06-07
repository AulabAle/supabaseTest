package it.aulab.progettoblog.utils.mappings;

import org.modelmapper.PropertyMap;

import it.aulab.progettoblog.dtos.AuthorDto;
import it.aulab.progettoblog.models.Author;

public class AuthorToAuthorDtoPropertyMap extends PropertyMap<Author, AuthorDto>{

    @Override
    protected void configure() {
        //map().setFullname(source.getFullname()); // vedere se funge
        map(source.getFullname()).setFullname(null);
        using(new CollectionToIntegerConverter()).map(source.getPosts()).setNumberOfPosts(null);
    }
    
}
