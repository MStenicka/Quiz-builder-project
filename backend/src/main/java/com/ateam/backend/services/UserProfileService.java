package com.ateam.backend.services;

import com.ateam.backend.exceptions.QuizNotFoundException;
import com.ateam.backend.exceptions.UserNotFoundException;
import com.ateam.backend.models.appUser.AppUser;
import com.ateam.backend.models.appUser.UserDetailsProfile;
import com.ateam.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {
    public final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsProfile getUserProfile(String username){
        Optional<AppUser> myUser = userRepository.findByUsername(username);
        if(!myUser.isPresent()) {
            throw new UserNotFoundException();
        }
        AppUser myObj = myUser.get();
        UserDetailsProfile userDetailsProfile = new UserDetailsProfile(myObj.getUsername(), myObj.getEmail(), myObj.getPhone(), myObj.getCity(), myObj.getRole());
        return userDetailsProfile;
    }
}
