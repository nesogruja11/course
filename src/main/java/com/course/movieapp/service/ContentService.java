package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ContentDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;
import com.course.movieapp.model.Genre;
import com.course.movieapp.repository.ContentGenreRepository;
import com.course.movieapp.repository.ContentRepository;
import com.course.movieapp.repository.GenreRepository;

import javassist.NotFoundException;

@Service
public class ContentService {

	@Autowired
	ContentRepository contentRepository;

	@Autowired
	ContentTypeService contentTypeService;

	@Autowired
	LanguageService languageService;

	@Autowired
	CountryService countryService;

	@Autowired
	ContentGenreRepository contentGenreRepository;

	@Autowired
	GenreRepository genreRepository;

	// ulazni argument validirati na endpoint-u sa hibernate anotacijom
	public Content save(ContentDto contentDto) throws NotFoundException {
		Content content = contentRepository.save(createContentFromDto(contentDto));
		contentDto.getGenreIds().forEach(id -> {
			ContentGenreKey key = new ContentGenreKey(content.getContentId(), id);
			Genre genre = genreRepository.getById(id);
			contentGenreRepository.save(new ContentGenre(key, genre, content));
		});
		return content;
	}

	public Content findById(int id) throws NotFoundException {
		return contentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen sadržaj sa id-em:" + id));
	}

	public Content update(ContentDto contentDto) throws NotFoundException {
		if (contentRepository.existsById(contentDto.getContentId())) {
			contentGenreRepository.deleteByContent(findById(contentDto.getContentId()));
			Content content = contentRepository.save(createContentFromDto(contentDto));
			contentDto.getGenreIds().forEach(id -> {
				ContentGenreKey key = new ContentGenreKey(content.getContentId(), id);
				Genre genre = genreRepository.getById(id);
				contentGenreRepository.save(new ContentGenre(key, genre, content));
			});

			return content;
		}
		throw new NotFoundException("Nije pronađen sadržaj sa id-em:" + contentDto.getContentId());
	}

	public void delete(int id) throws NotFoundException {

		Content content = findById(id);
		content.setActive(false);
		contentRepository.save(content);
	}

	private Content createContentFromDto(ContentDto contentDto) throws NotFoundException {
		Content content = new Content();
		if (contentDto.getContentId() != null)
			content.setContentId(contentDto.getContentId());
		content.setContentType(contentTypeService.findById(contentDto.getContentTypeId()));
		content.setLanguage(languageService.findById(contentDto.getLanguageId()));
		content.setCountry(countryService.findById(contentDto.getCoutryId()));
		content.setCoverLink(contentDto.getCoverLink());
		content.setDuration(contentDto.getDuration());
		content.setReleaseDate(contentDto.getReleaseDate());
		content.setTitle(contentDto.getTitle());
		content.setTrailerLink(contentDto.getTrailerLink());
		content.setYear(contentDto.getYear());

		return content;
	}
}
