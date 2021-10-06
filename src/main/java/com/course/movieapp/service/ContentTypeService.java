package com.course.movieapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.dto.ContentTypeDto;
import com.course.movieapp.model.ContentType;
import com.course.movieapp.repository.ContentTypeRepository;

import javassist.NotFoundException;

@Service
public class ContentTypeService {

	@Autowired
	ContentTypeRepository contentTypeRepository;

	public ContentType save(ContentTypeDto contentTypeDto) {
		return contentTypeRepository.save(createContentTypeFromDto(contentTypeDto));
	}

	public ContentType findById(int id) throws NotFoundException {
		return contentTypeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađen tip sadržaja sa id-em:" + id));
	}

	public ContentType update(ContentType contentType) throws NotFoundException {
		if (contentTypeRepository.existsById(contentType.getContentTypeId())) {
			return contentTypeRepository.save(contentType);
		}
		throw new NotFoundException("Nije pronađen tip sadržaja sa id-em:" + contentType.getContentTypeId());
	}

	public void delete(int contentTypeId) throws NotFoundException {
		if (contentTypeRepository.existsById(contentTypeId)) {
			contentTypeRepository.deleteById(contentTypeId);
		} else {
			throw new NotFoundException("Nije pronađen tip sadržaja sa id-em:" + contentTypeId);
		}
	}

	private ContentType createContentTypeFromDto(ContentTypeDto contentTypeDto) {
		ContentType contentType = new ContentType();
		contentType.setName(contentTypeDto.getName());
		return contentType;
	}
}
