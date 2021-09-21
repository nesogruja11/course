package com.course.movieapp.service;

import com.course.movieapp.model.ContentType;
import com.course.movieapp.model.Country;
import com.course.movieapp.repository.ContentTypeRepository;
import com.course.movieapp.repository.CountryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentTypeService {

    @Autowired
    ContentTypeRepository contentTypeRepository;

    //ulazni argument validirati na endpoint-u sa hibernate anotacijom
    public ContentType save(ContentType contentType){
        return contentTypeRepository.save(contentType);
    }

    public ContentType findById(int id) throws NotFoundException {
        return contentTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Nije pronađen tip sadržaja sa id-em:"+id));
    }

    public ContentType update(ContentType contentType) throws NotFoundException {
        if(contentTypeRepository.existsById(contentType.getContentTypeId())){
            return contentTypeRepository.save(contentType);
        }
        throw new NotFoundException("Nije pronađen tip sadržaja sa id-em:"+contentType.getContentTypeId());
    }

    public void delete(ContentType contentType) throws NotFoundException {
        if(contentTypeRepository.existsById(contentType.getContentTypeId())){
            contentTypeRepository.delete(contentType);
        }
        throw new NotFoundException("Nije pronađen tip sadržaja sa id-em:"+contentType.getContentTypeId());
    }
}
