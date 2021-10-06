package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.LanguageDto;
import com.course.movieapp.model.Language;
import com.course.movieapp.repository.LanguageRepository;

import javassist.NotFoundException;

@Service
public class LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	public Language save(LanguageDto languageDto) {
		return languageRepository.save(createLanguageFromDto(languageDto));
	}

	public Language findById(int id) throws NotFoundException {
		return languageRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen jezik sa id-em:" + id));
	}

	public Language update(Language language) throws NotFoundException {
		if (languageRepository.existsById(language.getLanguageId())) {
			return languageRepository.save(language);
		}
		throw new NotFoundException("Nije pronađen jezik sa id-em:" + language.getLanguageId());
	}

	public void delete(int languageId) throws NotFoundException {
		if (languageRepository.existsById(languageId)) {
			languageRepository.deleteById(languageId);
		} else {
			throw new NotFoundException("Nije pronađen jezik sa id-em:" + languageId);
		}
	}

	private Language createLanguageFromDto(LanguageDto languageDto) {
		Language language = new Language();
		language.setCode(languageDto.getCode());
		language.setName(languageDto.getName());

		return language;
	}
}
