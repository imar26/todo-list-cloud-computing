/*
Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
Aadesh Randeria,001224139,randeria.a@husky.neu.edu
Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu
 */

package com.csye6225.demo.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptPasswordEncoderBean {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
