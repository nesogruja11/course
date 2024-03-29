package com.course.movieapp.service;

import com.course.movieapp.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.movieapp.model.Role;
import com.course.movieapp.repository.RoleRepository;

import javassist.NotFoundException;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	// ulazni argument validirati na endpoint-u sa hibernate anotacijom
	public Role save(RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		return roleRepository.save(role);
	}

	public Role findById(int id) throws NotFoundException {
		return roleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nije pronađena rola sa id-em:" + id));
	}

	public Role update(Role role) throws NotFoundException {
		if (roleRepository.existsById(role.getRoleId())) {
			return roleRepository.save(role);
		}
		throw new NotFoundException("Nije pronađena rola sa id-em:" + role.getRoleId());
	}

	public void delete(Role role) throws NotFoundException {
		if(roleRepository.existsById(role.getRoleId())){
			roleRepository.delete(role);
		}else{
			throw new NotFoundException("Nije pronađena rola sa id-em:" + role.getRoleId());
		}
	}
}
