package com.skilldistillery.skillswap.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.skillswap.entities.User;
import com.skilldistillery.skillswap.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> index() {
		return userRepo.findAll();
	}
	
	@Override
	public User show(int id) {
		User user = null;
		Optional<User> userOpt = userRepo.findById(id);
		if (userOpt.isPresent()) {
			user = userOpt.get();
		}
		return user;
	}

	@Override
	public User register(User user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	public User updateAdmin(int id, User user) {
		User userUpdate = show(id);
		userUpdate.setFirstName(user.getFirstName());
		userUpdate.setLastName(user.getLastName());
		userUpdate.setEnabled(user.getEnabled());
		userUpdate.setAvailability(user.getAvailability());
		userUpdate.setEmail(user.getEmail());
		userUpdate.setBio(user.getBio());
		userUpdate.setProfileImage(user.getProfileImage());
		//might have to look at Address
		userUpdate.setAddress(user.getAddress());
		return userRepo.save(userUpdate);
	}
	
	@Override
	public User updateOwn(String username, User user) {
		User userUpdate = userRepo.findByUsername(username);
		userUpdate.setFirstName(user.getFirstName());
		userUpdate.setLastName(user.getLastName());
		userUpdate.setEnabled(user.getEnabled());
		userUpdate.setAvailability(user.getAvailability());
		userUpdate.setEmail(user.getEmail());
		userUpdate.setBio(user.getBio());
		userUpdate.setProfileImage(user.getProfileImage());
		//might have to look at Address
		userUpdate.setAddress(user.getAddress());
		return userRepo.save(userUpdate);
	}

	@Override
	public boolean archiveUser(int id) {
		Optional<User> userOpt = userRepo.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			user.setEnabled(false);
			userRepo.saveAndFlush(user);
		}
		return true;
	}

	@Override
	public boolean deleteAdmin(int id) {
		userRepo.deleteById(id);
		return !userRepo.existsById(id);
	}
}
