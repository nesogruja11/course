package com.course.movieapp.service;

import com.course.movieapp.model.Genre;
import com.course.movieapp.repository.GenreRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    //ulazni argument validirati na endpoint-u sa hibernate anotacijom
    public Genre save(Genre genre){
        return genreRepository.save(genre);
    }

    public Genre findById(int id) throws NotFoundException {
        return genreRepository.findById(id).orElseThrow(() -> new NotFoundException("Nije pronađen žanr sa id-em:"+id));
    }

    public Genre update(Genre genre) throws NotFoundException {
        if(genreRepository.existsById(genre.getGenreId())){
            return genreRepository.save(genre);
        }
        throw new NotFoundException("Nije pronađen žanr sa id-em:"+genre.getGenreId());
    }

    public void delete(Genre genre) throws NotFoundException {
        if(genreRepository.existsById(genre.getGenreId())){
            genreRepository.delete(genre);
        }
        throw new NotFoundException("Nije pronađen žanr sa id-em:"+genre.getGenreId());
    }
}
