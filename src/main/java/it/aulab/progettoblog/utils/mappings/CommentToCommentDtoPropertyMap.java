package it.aulab.progettoblog.utils.mappings;

import org.modelmapper.PropertyMap;

import it.aulab.progettoblog.dtos.CommentDto;
import it.aulab.progettoblog.models.Comment;

public class CommentToCommentDtoPropertyMap extends PropertyMap<Comment, CommentDto> {
    @Override
    protected void configure() {
        //prendo il converter
        using(new PostToStringConverter()).map(source.getPost()).setPostTitle(null);;
    }
}
