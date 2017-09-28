package com.csye6225.demo.controllers;


import com.csye6225.demo.pojo.User;
import com.csye6225.demo.repositories.UserRepository;
import com.csye6225.demo.service.UserService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;

@Controller
public class HomeController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private UserService userService;

  @Autowired
  public HomeController(UserService userService) {
    this.userService = userService;
  }

  private final static Logger logger = LoggerFactory.getLogger(HomeController.class);

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String welcome() {

    JsonObject jsonObject = new JsonObject();

    if (SecurityContextHolder.getContext().getAuthentication() != null
        && SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
      jsonObject.addProperty("message", "you are not logged in!!!");
    } else {
      jsonObject.addProperty("message", "you are logged in. current time is " + new Date().toString());
    }
    return jsonObject.toString();
  }


  @RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String save(HttpServletRequest request) {
    JsonObject json = new JsonObject();



    System.out.println("Inside Register Method");
    final String auth = request.getHeader("Authorization");
    HttpSession session = request.getSession();
    if (auth != null && auth.startsWith("Basic")) {
      String base64Credentials = auth.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials),
              Charset.forName("UTF-8"));

      final String[] values = credentials.split(":", 2);

      System.out.println("User is : " + values[0]);
      System.out.println(" Password is : " + values[1]);

      System.out.println("Adding email address");

      String userName = values[0];
      String password = values[1];

      password = bCryptPasswordEncoder.encode(password);

      try {
        User userExists = userService.findByUserName(userName);
        System.out.println("User Exixts in the DB " +userExists);

        if(userExists==null){
          User user = new User();
          user.setUserName(userName);
          user.setPassword(password);
          userRepository.save(user);
          json.addProperty("message", "User Added Successfully");
        }else{
          json.addProperty("message", "User Already Exists in DB");
        }
      }catch(DataIntegrityViolationException e){
        json.addProperty("message", "User Already Exists in DB");
      }
    }
    return json.toString();
  }


  @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String test() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "authorized for /test");
    return jsonObject.toString();
  }

  @RequestMapping(value = "/testPost", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String testPost() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("message", "authorized for /testPost");
    return jsonObject.toString();
  }

}
