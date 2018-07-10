package com.chatapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.chatapp.model.UserProfile;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoTemplate mongoTemplate;
	public static final String COLLECTION_NAME = "user";
	
	@Override
	public List<UserProfile> listUser() {
		// TODO Auto-generated method stub		
		return mongoTemplate.findAll(UserProfile.class, COLLECTION_NAME);
	}

	@Override
	public void add(UserProfile user) {
		// TODO Auto-generated method stub
		
		if(!mongoTemplate.collectionExists(UserProfile.class)){
			mongoTemplate.createCollection(UserProfile.class);
		}
		
		user.setId(UUID.randomUUID().toString());
		mongoTemplate.insert(user,COLLECTION_NAME);
	}

	@Override
	public void update(UserProfile user) {
		// TODO Auto-generated method stub
		mongoTemplate.save(user,COLLECTION_NAME);
	}

	@Override
	public void delete(UserProfile user) {
		// TODO Auto-generated method stub
		mongoTemplate.remove(user,COLLECTION_NAME);
		
	}

	@Override
	public UserProfile findUserById(String id) {
		// TODO Auto-generated method stub
		return mongoTemplate.findById(id, UserProfile.class,COLLECTION_NAME);
	}

	@Override
	public UserProfile findUserByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, UserProfile.class,COLLECTION_NAME);
	}

	@Override
	public boolean checkCredentials(String username,String password) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		query.addCriteria(Criteria.where("password").is(password));
		UserProfile user  = null;
		try {
			user =  mongoTemplate.findOne(query, UserProfile.class,COLLECTION_NAME);
		}
		catch(Exception ex) {
			System.out.println("Exception = " + ex);
		}
		finally {
			
			if(user==null)
				return false;
			else
				return true;
		}
	
	}

	@Override
	public HashMap<String, Boolean> getUsersState() {		
		HashMap<String,Boolean> hashmap = null;
		List<UserProfile> users = null;
		try {
			users = mongoTemplate.findAll(UserProfile.class, COLLECTION_NAME);
			hashmap = new HashMap<>();
			for(UserProfile user : users) {
				hashmap.put(user.getUsername(), user.isOnline());
			}
		}
		catch(Exception ex) {
			System.out.println("Exception = " + ex);
		}
		finally {
			return hashmap;
		}
	}

	@Override
	public void updateUserState(String username, boolean isOnline) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		Update update = new Update();
		update.set("isOnline", isOnline);
		mongoTemplate.findAndModify(query, update,UserProfile.class,COLLECTION_NAME);
	}
	
	
}
