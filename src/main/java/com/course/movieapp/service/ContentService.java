package com.course.movieapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ContentByCategoryDto;
import com.course.movieapp.dto.ContentDto;
import com.course.movieapp.dto.ContentUpdateDto;
import com.course.movieapp.dto.MovieDetailsDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;
import com.course.movieapp.model.Genre;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.model.MovieCastKey;
import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.repository.ContentCommentRepository;
import com.course.movieapp.repository.ContentGenreRepository;
import com.course.movieapp.repository.ContentRepository;
import com.course.movieapp.repository.GenreRepository;
import com.course.movieapp.repository.MovieCastRepository;
import com.course.movieapp.repository.MoviePeopleRepository;
import com.course.movieapp.repository.MovieRoleRepository;
import com.course.movieapp.repository.ReviewRepository;

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

	@Autowired
	MovieCastRepository movieCastRepository;

	@Autowired
	MoviePeopleRepository moviePeopleRepository;

	@Autowired
	MovieRoleRepository movieRoleRepository;

	@Autowired
	ContentCommentRepository contentCommentRepository;

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	ContentGenreService contentgenreGenreService;

	@Autowired
	MovieCastService movieCastService;

//	// ulazni argument validirati na endpoint-u sa hibernate anotacijom
//	public Content save(ContentDto contentDto) throws NotFoundException {
//		Content content = contentRepository.save(createContentFromDto(contentDto));
//		contentDto.getGenreIds().forEach(id -> {
//			ContentGenreKey key = new ContentGenreKey(content.getContentId(), id);
//			Genre genre = genreRepository.getById(id);
//			contentGenreRepository.save(new ContentGenre(key, genre, content));
//		});
//		return content;
//	}

	public Content saveMovie(ContentDto contentDto) throws NotFoundException {
		Content content = contentRepository.save(createContentFromDto(contentDto));

		contentDto.getGenreIds().forEach(genreId -> {
			ContentGenreKey key = new ContentGenreKey(content.getContentId(), genreId);
			Genre genre = genreRepository.getById(genreId);
			contentGenreRepository.save(new ContentGenre(key, genre, content));
		});

		contentDto.getMovieCastList().forEach(movieCast -> {
			MovieCastKey key = new MovieCastKey(content.getContentId(), movieCast.getMovieRoleId(),
					movieCast.getMoviePeopleId());
			MoviePeople moviePeople = moviePeopleRepository.getById(movieCast.getMoviePeopleId());
			MovieRole movieRole = movieRoleRepository.getById(movieCast.getMovieRoleId());
			movieCastRepository.save(new MovieCast(key, content, moviePeople, movieRole));
		});

		return content;
	}

	public void deleteMovie(int contentId) throws NotFoundException {
		Content content = findById(contentId);
		deleteRecordsFromLinkedTables(content, true);
		contentRepository.delete(content);
	}

	public boolean existById(int contentId) {
		if (contentRepository.existsById(contentId))
			return true;
		else
			return false;
	}

	public Content updateMovie(ContentUpdateDto contentDto) throws NotFoundException {

		if (contentRepository.existsById(contentDto.getContentId())) {
			Content content = contentRepository.save(updateContentFromDto(contentDto));
			deleteRecordsFromLinkedTables(content, false);

			contentDto.getGenreIds().forEach(id -> {
				ContentGenreKey key = new ContentGenreKey(content.getContentId(), id);
				Genre genre = genreRepository.getById(id);
				contentGenreRepository.save(new ContentGenre(key, genre, content));
			});

			contentDto.getMovieCastList().forEach(movieCast -> {
				MovieCastKey key = new MovieCastKey(content.getContentId(), movieCast.getMovieRoleId(),
						movieCast.getMoviePeopleId());
				MoviePeople moviePeople = moviePeopleRepository.getById(movieCast.getMoviePeopleId());
				MovieRole movieRole = movieRoleRepository.getById(movieCast.getMovieRoleId());
				movieCastRepository.save(new MovieCast(key, content, moviePeople, movieRole));
			});

			return content;
		}

		throw new NotFoundException("Nije pronađen sadržaj sa id-em:" + contentDto.getContentId());
	}

	public Content findById(int id) throws NotFoundException {
		return contentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen sadržaj sa id-em:" + id));
	}

	public List<Content> getContentByCategory(ContentByCategoryDto contentByCategoryDto) throws NotFoundException {
		List<ContentGenre> contentGenreList = contentgenreGenreService
				.findAllByGenreId(contentByCategoryDto.getGenreId());
		if (contentByCategoryDto.getNumberOfElements() > 0) {
			List<Content> contentList = contentGenreList.stream().map(e -> e.getContent())
					.limit(contentByCategoryDto.getNumberOfElements()).collect(Collectors.toList());
			return contentList;
		}
		List<Content> contentList = contentGenreList.stream().map(e -> e.getContent()).collect(Collectors.toList());
		return contentList;
	}

	public MovieDetailsDto getMovieDetails(int contentId) throws NotFoundException {
		Content content = findById(contentId);
		List<Genre> genres = contentgenreGenreService.findAllByContent(content).stream().map(e -> e.getGenre())
				.collect(Collectors.toList());
		List<MovieCast> movieCastList = movieCastService.findAllByContent(content);
		return new MovieDetailsDto(content, genres, movieCastList);
	}

	private Content createContentFromDto(ContentDto contentDto) throws NotFoundException {
		Content content = new Content();
		copyFromDtoToObject(content, contentDto);
		return content;
	}

	private Content updateContentFromDto(ContentUpdateDto contentDto) throws NotFoundException {
		Content content = new Content();
		content.setContentId(contentDto.getContentId());
		copyFromDtoToObject(content, contentDto);
		return content;
	}

	private void copyFromDtoToObject(Content content, ContentDto contentDto) throws NotFoundException {
		content.setContentType(contentTypeService.findById(contentDto.getContentTypeId()));
		content.setLanguage(languageService.findById(contentDto.getLanguageId()));
		content.setCountry(countryService.findById(contentDto.getCoutryId()));
		content.setCoverLink(contentDto.getCoverLink());
		content.setDuration(contentDto.getDuration());
		content.setReleaseDate(contentDto.getReleaseDate());
		content.setTitle(contentDto.getTitle());
		content.setTrailerLink(contentDto.getTrailerLink());
		content.setYear(contentDto.getYear());
	}

	private void deleteRecordsFromLinkedTables(Content content, boolean delete) {
		if (delete) {
			reviewRepository.deleteByContent(content);
			contentCommentRepository.deleteByContent(content);
		}
		contentGenreRepository.deleteByContent(content);
		movieCastRepository.deleteByContent(content);
	}
}
