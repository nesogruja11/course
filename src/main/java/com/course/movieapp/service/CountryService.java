package com.course.movieapp.service;

import com.course.movieapp.model.Country;
import com.course.movieapp.repository.CountryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    //ulazni argument validirati na endpoint-u sa hibernate anotacijom
    public Country save(Country country){
        return countryRepository.save(country);
    }

    public Country findById(int id) throws NotFoundException {
        return countryRepository.findById(id).orElseThrow(() -> new NotFoundException("Nije pronađena zemlja sa id-em:"+id));
    }

    public Country update(Country country) throws NotFoundException {
        if(countryRepository.existsById(country.getCountryId())){
            return countryRepository.save(country);
        }
        throw new NotFoundException("Nije pronađena zemlja sa id-em:"+country.getCountryId());
    }

    public void delete(Country country) throws NotFoundException {
        if(countryRepository.existsById(country.getCountryId())){
            countryRepository.delete(country);
        }
        throw new NotFoundException("Nije pronađena zemlja sa id-em:"+country.getCountryId());
    }
}
