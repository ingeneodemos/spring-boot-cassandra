package com.grupoprimario.springboot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.grupoprimario.springboot.model.User;
import com.grupoprimario.springboot.service.UserService;
import com.grupoprimario.springboot.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService; 


	@RequestMapping(value = "/user/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}


	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("obteniendo el usuario con el id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.error("usuario con el id {} no encontrado.", id);
			return new ResponseEntity(new CustomErrorType("Usuario con el " + id 
					+ " no encontrado"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}


	@RequestMapping(value = "/user/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creado usuario : {}", user);

		if (userService.isUserExist(user)) {
			return new ResponseEntity(new CustomErrorType("Error al crear el usuario"),HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Actualizando usuaro con el id {}", id);

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return new ResponseEntity(new CustomErrorType("Error al actualizar el usuario"),HttpStatus.NOT_FOUND);
		}

		currentUser.setNombre(user.getNombre());
		currentUser.setEdad(user.getEdad());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}


	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Error al eliminar el usuario"),HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}


	@RequestMapping(value = "/user/", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Eliminando todos los usuarios");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

}