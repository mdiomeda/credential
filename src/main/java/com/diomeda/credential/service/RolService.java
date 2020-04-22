package com.diomeda.credential.service;


import java.util.List;
import com.diomeda.credential.model.Rol;

public interface RolService {
	
	Rol findById(Long id);

	List<Rol> findByName(String name);

	void saveRol(Rol rol);

	void updateRol(Rol rol);

	void deleteRol(Rol rol);

	List<Rol> findAllRoles();

	boolean isRolExist(Rol rol);
}