package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.CountryDto;
import com.course.movieapp.model.Country;
import com.course.movieapp.repository.CountryRepository;

import javassist.NotFoundException;

@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepository;

	public Country save(CountryDto countryDto) {
		return countryRepository.save(createCountryFromDto(countryDto));
	}

	public Country findById(int id) throws NotFoundException {
		return countryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena zemlja sa id-em:" + id));
	}

	public Country update(Country country) throws NotFoundException {
		if (countryRepository.existsById(country.getCountryId())) {
			return countryRepository.save(country);
		}
		throw new NotFoundException("Nije pronađena zemlja sa id-em:" + country.getCountryId());
	}

	public void delete(int countryId) throws NotFoundException {
		if (countryRepository.existsById(countryId)) {
			countryRepository.deleteById(countryId);
		} else {
			throw new NotFoundException("Nije pronađena zemlja sa id-em:" + countryId);
		}
	}

	private Country createCountryFromDto(CountryDto countryDto) {
		Country country = new Country();
		country.setName(countryDto.getName());
		country.setCode(countryDto.getCode());
		return country;
	}
}
