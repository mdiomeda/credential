package com.diomeda.credential.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.diomeda.credential.model.Rol;
import com.diomeda.credential.repository.RolRepository;

@Service("rolService")
@Transactional
public class RolServiceImpl implements RolService{

	@Autowired
	private RolRepository rolRepository;

	public Rol findById(Long id) {
		return rolRepository.findById(id).orElse(null);
	}

	public List<Rol> findByName(String name) {
		return rolRepository.findByName(name);
	}

	public void saveRol(Rol rol) {
		rolRepository.save(rol);
	}

	public void updateRol(Rol rol){
		saveRol(rol);
	}

	public void deleteRol(Rol rol){
		rolRepository.delete(rol);
	}

	public List<Rol> findAllRoles(){
		return rolRepository.findAll();
	}

	public boolean isRolExist(Rol rol) {
		return findByName(rol.getName()) != null;
	}

}
