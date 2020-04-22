package com.diomeda.credential.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.diomeda.credential.model.Rol;
import com.diomeda.credential.service.RolService;
import com.diomeda.credential.util.CustomErrorType;
import java.util.List;

@RestController
@RequestMapping("api")
public class RolController extends BaseController{

	@Autowired
	RolService rolService;

	// -------------------Retrieve All Roles -------------------

	@GetMapping(value = "/rol")
	public ResponseEntity<?> listAllRoles() {
		List<Rol> roles = rolService.findAllRoles();
		if (roles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Rol>>(roles, HttpStatus.OK);
	}

	// -------------------Retrieve Single Rol -------------------

	@GetMapping(value = "/rol/{id}")
	public ResponseEntity<?> getRol(@PathVariable("id") Long id) {
		Rol rol = rolService.findById(id);
		if (rol == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("role.single.error", id)), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(rol, HttpStatus.OK);
	}

	// -------------------Create a Rol -------------------

	@PostMapping(value = "/rol",  consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> createRol(Rol rol, UriComponentsBuilder ucBuilder) {

		rolService.saveRol(rol);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/rol/{id}").buildAndExpand(rol.getName()).toUri());
		return new ResponseEntity<>(rol, headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Rol -------------------

	@PutMapping(value = "/rol/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateRol(@PathVariable("id") Long id, Rol rol) {
		Rol currentRol = rolService.findById(id);

		if (currentRol == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("role.single.error", id)),
					HttpStatus.NOT_FOUND);
		}

		currentRol.setId(rol.getId());
		currentRol.setName(rol.getName());
		currentRol.setDescription(rol.getDescription());
		

		rolService.updateRol(currentRol);
		return new ResponseEntity<>(currentRol, HttpStatus.OK);
	}

	// ------------------- Delete a Rol -------------------

	@DeleteMapping(value = "/rol/{id}")
	public ResponseEntity<?> deleteRol(@PathVariable("id") Long id) {
		Rol rol = rolService.findById(id);
		if (rol == null) {
			return new ResponseEntity<>(new CustomErrorType(getMessage("role.single.error", id)),
					HttpStatus.NOT_FOUND);
		}
		rolService.deleteRol(rol);
		return new ResponseEntity<Rol>(HttpStatus.NO_CONTENT);
	}

}