package com.course.movieapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.SeasonDto;
import com.course.movieapp.model.Content;
import com.course.movieapp.model.Season;
import com.course.movieapp.repository.EpisodeRepository;
import com.course.movieapp.repository.SeasonRepository;
import com.course.movieapp.repository.SerieCastRepository;

import javassist.NotFoundException;

@Service
public class SeasonService {

	@Autowired
	SeasonRepository seasonRepository;

	@Autowired
	ContentService contentService;

	@Autowired
	EpisodeRepository episodeRepository;

	@Autowired
	SerieCastRepository serieCastRepository;

	public Season save(SeasonDto seasonDto) throws NotFoundException {
		return seasonRepository.save(buildSeasonFromDto(seasonDto));
	}

	public Season update(SeasonDto seasonDto) throws NotFoundException {
		if (seasonRepository.existsById(seasonDto.getSeasonId())) {
			return seasonRepository.save(buildSeasonFromDto(seasonDto));
		}
		throw new NotFoundException("Nije pronađena sezona sa id-em:" + seasonDto.getSeasonId());
	}

	public Season findById(int id) throws NotFoundException {
		return seasonRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena sezona sa id-em:" + id));
	}

	public void delete(int id) throws NotFoundException {
		if (seasonRepository.existsById(id)) {
			Season season = findById(id);
			serieCastRepository.deleteBySeason(season);
			episodeRepository.deleteBySeason(season);
			seasonRepository.deleteById(id);
		}
		throw new NotFoundException("Nije pronađena sezona sa id-em:" + id);
	}

	public void deleteByContent(Content content) {
		List<Season> seasons = seasonRepository.findAllByContent(content);
		seasons.forEach(season -> {
			serieCastRepository.deleteBySeason(season);
			episodeRepository.deleteBySeason(season);
			seasonRepository.delete(season);
		});
	}

	private Season buildSeasonFromDto(SeasonDto seasonDto) throws NotFoundException {
		Season season = new Season();
		if (seasonDto.getSeasonId() != null)
			season.setSeasonId(seasonDto.getSeasonId());
		season.setContent(contentService.findById(seasonDto.getContentId()));
		season.setName(seasonDto.getName());
		season.setSeasonNumber(seasonDto.getSeasonNumber());

		return season;
	}
}
