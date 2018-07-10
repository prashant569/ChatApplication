package com.chatapp.dao;

import java.util.*;

import com.chatapp.model.UserProfile;


public interface UserDao {

	public List<UserProfile> listUser();
	public void add(UserProfile user);
	public void update(UserProfile user);
	public void delete(UserProfile user);
	public UserProfile findUserById(String id);
	public UserProfile findUserByUsername(String username);
	public boolean checkCredentials(String username,String password);
	public HashMap<String,Boolean> getUsersState();
	public void updateUserState(String username,boolean isOnline);
	
}
