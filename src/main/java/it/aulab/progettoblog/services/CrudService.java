package it.aulab.progettoblog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CrudService<ReadDto, Model, Key> {
    List<ReadDto> readAll(Map<String, String> params);
    ReadDto read(Key key);
    ReadDto create(Model model);
    ReadDto createWithImage(Model model, MultipartFile file);
    ReadDto createWithImage(Model model, ArrayList<MultipartFile> files);
    ReadDto update(Key key, Model model);
    void delete(Key key);
}
