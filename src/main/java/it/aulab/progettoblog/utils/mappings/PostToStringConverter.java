package it.aulab.progettoblog.utils.mappings;

import org.modelmapper.AbstractConverter;

import it.aulab.progettoblog.models.Post;

public class PostToStringConverter extends AbstractConverter<Post, String> {
    @Override
    protected String convert(Post source) {
        if(source.getPublishDate() != null){
            return source.getTitle() + "(" + source.getPublishDate() + ")";
        } else{
            return source.getTitle() + "non ancora pubblicato";
        }
    }

}
