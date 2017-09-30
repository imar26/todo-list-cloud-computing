/*
Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
Aadesh Randeria,001224139,randeria.a@husky.neu.edu
Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu
 */


package com.csye6225.demo.service;

import com.csye6225.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csye6225.demo.pojo.User;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

}
