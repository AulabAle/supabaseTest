package it.aulab.progettoblog.utils.mappings;

import org.modelmapper.PropertyMap;

import it.aulab.progettoblog.dtos.PostDto;
import it.aulab.progettoblog.models.Post;

public class PostToPostDtoPropertyMap extends PropertyMap<Post, PostDto>{

    @Override
    protected void configure() {
       using(new CollectionToIntegerConverter()).map(source.getComments()).setNumberOfComments(null);
    }
}
