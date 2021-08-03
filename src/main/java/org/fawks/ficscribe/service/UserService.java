package org.fawks.ficscribe.service;

import org.fawks.ficscribe.domain.User;
import org.fawks.ficscribe.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/1/21
 */

@Service
public class UserService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public User saveUser(User user) {
        return userDetailsRepository.save(user);
    }

    public User getUser(String id) {
        return userDetailsRepository.getById(id);
    }

    public void deleteUser(User user) {
        userDetailsRepository.delete(user);
    }
}
