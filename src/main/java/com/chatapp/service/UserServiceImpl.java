package com.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.dao.UserDao;
import com.chatapp.model.UserProfile;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	@Override
	public List<UserProfile> listUser() {
		// TODO Auto-generated method stub
		return userDao.listUser();
	}

	@Override
	public void add(UserProfile user) {
		// TODO Auto-generated method stub
		userDao.add(user);
	}

	@Override
	public void update(UserProfile user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}

	@Override
	public void delete(UserProfile user) {
		// TODO Auto-generated method stub
		userDao.delete(user);
	}

	@Override
	public UserProfile findUserById(String id) {
		// TODO Auto-generated method stub
		return userDao.findUserById(id);
	}

	@Override
	public UserProfile findUserByUsername(String username) {

		return userDao.findUserByUsername(username);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		return userDao.checkCredentials(username, password);
	}

	
}
