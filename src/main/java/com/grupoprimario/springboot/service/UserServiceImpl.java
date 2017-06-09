package com.grupoprimario.springboot.service;
import com.grupoprimario.springboot.cassandra.SimpleClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.grupoprimario.springboot.model.User;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	private static final AtomicLong counter = new AtomicLong();
	private static SimpleClient client;
	private static List<User> users;
	
	static{
		client = new SimpleClient();
		client.connect("grupoprimario", "52.24.63.31");
	}

	public List<User> findAllUsers() {
		ResultSet rs = client.executeQuery("SELECT * FROM usuario LIMIT 10");
		System.out.println(rs.toString());
		List<User> usuarios = new ArrayList();
		List<Row> rows = rs.all();

		for (Row row : rows) {
			usuarios.add(new User(row.getLong("id"),row.getString("nombre"),row.getInt("edad")));
		}

		return usuarios;
	}
	
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String name) {
		for(User user : users){
			if(user.getNombre().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getNombre())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

}
