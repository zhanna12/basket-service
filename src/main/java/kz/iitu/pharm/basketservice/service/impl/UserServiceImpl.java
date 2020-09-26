package kz.iitu.pharm.basketservice.service.impl;

import kz.iitu.pharm.basketservice.entity.User;
import kz.iitu.pharm.basketservice.repository.UserRepository;
import kz.iitu.pharm.basketservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserbyId(Long userId) {
        return userRepository.findById(userId);
    }

}
