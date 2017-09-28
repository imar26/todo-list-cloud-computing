package com.csye6225.demo.Repositories;

import com.csye6225.demo.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

   User findByUserName(String userName);

}
