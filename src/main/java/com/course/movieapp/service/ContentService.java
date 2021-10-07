package com.course.movieapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ContentByCategoryDto;
import com.course.movieapp.dto.ContentDto;
import com.course.movieapp.dto.MovieDetailsDto;
import com.course.movieapp.dto.SaveMovieDto;
import com.course.movieapp.dto.SaveSeasonDto;
import com.course.movieapp.dto.SaveSerieDto;
import com.course.movieapp.dto.UpdateMovieDto;
import com.course.movieapp.dto.UpdateSerieDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.ContentGenre;
import com.course.movieapp.model.ContentGenreKey;
import com.course.movieapp.model.Episode;
import com.course.movieapp.model.Genre;
import com.course.movieapp.model.MovieCast;
import com.course.movieapp.model.MovieCastKey;
import com.course.movieapp.model.MoviePeople;
import com.course.movieapp.model.MovieRole;
import com.course.movieapp.model.Season;
import com.course.movieapp.model.SerieCast;
import com.course.movieapp.model.SerieCastKey;
import com.course.movieapp.repository.ContentCommentRepository;
import com.course.movieapp.repository.ContentGenreRepository;
import com.course.movieapp.repository.ContentRepository;
import com.course.movieapp.repository.EpisodeRepository;
import com.course.movieapp.repository.GenreRepository;
import com.course.movieapp.repository.MovieCastRepository;
import com.course.movieapp.repository.MoviePeopleRepository;
import com.course.movieapp.repository.MovieRoleRepository;
import com.course.movieapp.repository.ReviewRepository;
import com.course.movieapp.repository.SeasonRepository;
import com.course.movieapp.repository.SerieCastRepository;

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

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	SeasonService seasonService;

	@Autowired
	SerieCastRepository seriecasCastRepository;

	@Autowired
	EpisodeRepository episodeRepository;

	public Content saveMovie(SaveMovieDto saveMovieDto) throws NotFoundException {
		Content content = contentRepository.save(createContentFromDto(saveMovieDto));

		saveMovieDto.getGenreIds().forEach(genreId -> {
			ContentGenreKey key = new ContentGenreKey(content.getContentId(), genreId);
			Genre genre = genreRepository.getById(genreId);
			contentGenreRepository.save(new ContentGenre(key, genre, content));
		});

		saveMovieDto.getMovieCastList().forEach(movieCast -> {
			MovieCastKey key = new MovieCastKey(content.getContentId(), movieCast.getMovieRoleId(),
					movieCast.getMoviePeopleId());
			MoviePeople moviePeople = moviePeopleRepository.getById(movieCast.getMoviePeopleId());
			MovieRole movieRole = movieRoleRepository.getById(movieCast.getMovieRoleId());
			movieCastRepository.save(new MovieCast(key, content, moviePeople, movieRole));
		});

		return content;
	}

	public void deleteContent(int contentId) throws NotFoundException {
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

	public Content updateMovie(UpdateMovieDto updateMovieDto) throws NotFoundException {

		if (contentRepository.existsById(updateMovieDto.getContentId())) {
			Content content = contentRepository.save(updateContentFromDto(updateMovieDto));
			deleteRecordsFromLinkedTables(content, false);

			updateMovieDto.getGenreIds().forEach(id -> {
				ContentGenreKey key = new ContentGenreKey(content.getContentId(), id);
				Genre genre = genreRepository.getById(id);
				contentGenreRepository.save(new ContentGenre(key, genre, content));
			});

			updateMovieDto.getMovieCastList().forEach(movieCast -> {
				MovieCastKey key = new MovieCastKey(content.getContentId(), movieCast.getMovieRoleId(),
						movieCast.getMoviePeopleId());
				MoviePeople moviePeople = moviePeopleRepository.getById(movieCast.getMoviePeopleId());
				MovieRole movieRole = movieRoleRepository.getById(movieCast.getMovieRoleId());
				movieCastRepository.save(new MovieCast(key, content, moviePeople, movieRole));
			});

			return content;
		}

		throw new NotFoundException("Nije pronađen sadržaj sa id-em:" + updateMovieDto.getContentId());
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

	public Content saveSerie(SaveSerieDto saveSerieDto) throws NotFoundException {
		Content content = createContentFromSaveSerieDto(saveSerieDto);
		contentRepository.save(content);
		List<SaveSeasonDto> saveSeasonList = saveSerieDto.getSaveSeasonList();
		insertIntoSeasonLinkedTables(content, saveSerieDto.getGenreIds(), saveSeasonList);
		return content;
	}

	public Content updateSerie(UpdateSerieDto updateSerieDto) throws NotFoundException {
		if (existById(updateSerieDto.getContentId())) {
			Content content = createContentFromSaveSerieDto(updateSerieDto);
			content.setContentId(updateSerieDto.getContentId());
			contentRepository.save(content);
			deleteRecordsFromLinkedTables(content, false);
			List<SaveSeasonDto> saveSeasonList = updateSerieDto.getSaveSeasonList();
			insertIntoSeasonLinkedTables(content, updateSerieDto.getGenreIds(), saveSeasonList);
			return content;
		}
		throw new NotFoundException("Ne postoji sadržaj sa traženim id-em:" + updateSerieDto.getContentId());
	}

	private void insertIntoSeasonLinkedTables(Content content, List<Integer> genreIds,
			List<SaveSeasonDto> saveSeasonList) {
		genreIds.forEach(genreId -> {
			ContentGenreKey key = new ContentGenreKey(content.getContentId(), genreId);
			Genre genre = genreRepository.getById(genreId);
			contentGenreRepository.save(new ContentGenre(key, genre, content));
		});
		saveSeasonList.forEach(saveSeasonDto -> {
			Season season = saveSeasonDto.createSeasonFromDto();
			season.setContent(content);
			seasonRepository.save(season);
			saveSeasonDto.getSerieCastList().forEach(serieCastDto -> {
				SerieCastKey key = new SerieCastKey(season.getSeasonId(), serieCastDto.getMovieRoleId(),
						serieCastDto.getMoviePeopleId());
				MoviePeople moviePeople = moviePeopleRepository.getById(serieCastDto.getMoviePeopleId());
				MovieRole movieRole = movieRoleRepository.getById(serieCastDto.getMovieRoleId());
				seriecasCastRepository.save(new SerieCast(key, season, moviePeople, movieRole));
			});
			saveSeasonDto.getEpisodes().forEach(episodeDto -> {
				Episode episode = new Episode();
				episode.setSeason(season);
				episode.setDuration(episodeDto.getDuration());
				episode.setEpisodeNumber(episodeDto.getEpisodeNumber());
				episode.setName(episodeDto.getName());
				episodeRepository.save(episode);
			});
		});
	}

	private Content createContentFromDto(ContentDto contentDto) throws NotFoundException {
		Content content = new Content();
		copyFromDtoToObject(content, contentDto);
		return content;
	}

	private Content createContentFromSaveSerieDto(SaveSerieDto saveSerieDto) throws NotFoundException {
		Content content = new Content();
		copyFromDtoToObject(content, saveSerieDto);
		return content;
	}

	private Content updateContentFromDto(UpdateMovieDto contentDto) throws NotFoundException {
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
		seasonService.deleteByContent(content);
	}
}
