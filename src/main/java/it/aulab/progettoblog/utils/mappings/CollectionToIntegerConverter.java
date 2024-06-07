package it.aulab.progettoblog.utils.mappings;

import java.util.Collection;

import org.modelmapper.AbstractConverter;

public class CollectionToIntegerConverter extends AbstractConverter<Collection<?>, Integer> {

    @Override
    protected Integer convert(Collection<?> source) {
        return source.size();
    }
    
}
