/*
Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
Aadesh Randeria,001224139,randeria.a@husky.neu.edu
Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu
 */

package com.csye6225.demo.repositories;

import com.csye6225.demo.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

   User findByUserName(String userName);

   User findByUserNameAndPassword(String userName, String password);
}
