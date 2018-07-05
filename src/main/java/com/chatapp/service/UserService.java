package com.chatapp.service;

import java.util.List;

import com.chatapp.model.UserProfile;



public interface UserService {

	public List<UserProfile> listUser();
	public void add(UserProfile user);
	public void update(UserProfile user);
	public void delete(UserProfile user);
	public UserProfile findUserById(String id);
	public UserProfile findUserByUsername(String username);
	public boolean checkCredentials(String username,String password);
}
