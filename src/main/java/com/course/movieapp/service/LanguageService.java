package com.course.movieapp.service;

import com.course.movieapp.model.Language;
import com.course.movieapp.repository.LanguageRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    //ulazni argument validirati na endpoint-u sa hibernate anotacijom
    public Language save(Language language){
        return languageRepository.save(language);
    }

    public Language findById(int id) throws NotFoundException {
        return languageRepository.findById(id).orElseThrow(() -> new NotFoundException("Nije pronadjen jezik sa id-em:"+id));
    }

    public Language update(Language language) throws NotFoundException {
        if(languageRepository.existsById(language.getLanguageId())){
            return languageRepository.save(language);
        }
        throw new NotFoundException("Nije prondjen jezik sa id-em:"+language.getLanguageId());
    }

    public void delete(Language language) throws NotFoundException {
        if(languageRepository.existsById(language.getLanguageId())){
            languageRepository.delete(language);
        }
        throw new NotFoundException("Nije prondjen jezik sa id-em:"+language.getLanguageId());
    }
}
