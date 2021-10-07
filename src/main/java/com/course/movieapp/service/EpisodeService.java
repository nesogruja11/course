package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.Episode;
import com.course.movieapp.repository.EpisodeRepository;

import javassist.NotFoundException;

@Service
public class EpisodeService {

	@Autowired
	EpisodeRepository episodeRepository;

	@Autowired
	SeasonService seasonService;

//	public Episode save(EpisodeDto episodeDto) throws NotFoundException {
//		return episodeRepository.save(buildEpisodeFromDto(episodeDto));
//	}

//    public Episode update(EpisodeDto episodeDto) throws NotFoundException {
//        if(episodeRepository.existsById(episodeDto.getEpisodeId())){
//            return episodeRepository.save(buildEpisodeFromDto(episodeDto));
//        }
//        throw new NotFoundException("Nije pronađena epizoda sa id-em:"+episodeDto.getEpisodeId());
//    }

	public Episode findById(int id) throws NotFoundException {
		return episodeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena epizoda sa id-em:" + id));
	}

	public void delete(int id) throws NotFoundException {
		if (episodeRepository.existsById(id)) {
			episodeRepository.deleteById(id);
		}
		throw new NotFoundException("Nije pronađena epizoda sa id-em:" + id);
	}

//    private Episode buildEpisodeFromDto(EpisodeDto episodeDto) throws NotFoundException {
//        Episode episode = new Episode();
//        if(episodeDto.getEpisodeId() != null)
//            episode.setEpisodeId(episodeDto.getEpisodeId());
//        episode.setDuration(episodeDto.getDuration());
//        episode.setEpisodeNumber(episodeDto.getEpisodeNumber());
//        episode.setName(episodeDto.getName());
//        episode.setSeason(seasonService.findById(episodeDto.getSeasonId()));
//
//        return episode;
//    }
}
