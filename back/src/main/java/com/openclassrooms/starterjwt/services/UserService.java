package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No user with this id"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
                "This user doesn't exist"));
    }

    public void save(SignupRequest signupRequest, String encodedPassword){
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EntityExistsException("This email already exists");
        }

        User user = new User(signupRequest.getEmail(),
                signupRequest.getLastName(),
                signupRequest.getFirstName(),
                encodedPassword,
                false);

        userRepository.save(user);
    }
}
