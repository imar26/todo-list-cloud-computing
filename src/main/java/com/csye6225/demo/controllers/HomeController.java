/*
Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
Aadesh Randeria,001224139,randeria.a@husky.neu.edu
Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu
 */

package com.csye6225.demo.controllers;
import com.csye6225.demo.pojo.User;
import com.csye6225.demo.repositories.UserRepository;
import com.csye6225.demo.service.UserService;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
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
  public String welcome(HttpServletRequest request) {
    JsonObject jsonObject = new JsonObject();
    final String auth = request.getHeader("Authorization");
    if (auth != null && auth.startsWith("Basic")) {
      String base64Credentials = auth.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials),Charset.forName("UTF-8"));

      final String[] values = credentials.split(":", 2);
      String userName = values[0];
      String password = values[1];

      if(userName.isEmpty() || password.isEmpty()){
        jsonObject.addProperty("message", "Please Enter Credentials");
      }
      else{
        User user = userService.findByUserName(userName);
        if (user == null) {
          jsonObject.addProperty("message", "Please Enter Valid User Name");
        } else {
          String pass = user.getPassword();
          if (bCryptPasswordEncoder.matches(password, pass)) {
            jsonObject.addProperty("message", "You are Logged In. Current Time is : " + new Date().toString());
          } else {
            jsonObject.addProperty("message", "Wrong Credentials!!!");
          }
        }
      }
    }else {
      jsonObject.addProperty("message", "you are not authorized!!!");
    }
    return jsonObject.toString();
  }

  @RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String save(HttpServletRequest request) {
    JsonObject json = new JsonObject();
    final String auth = request.getHeader("Authorization");
    if (auth != null && auth.startsWith("Basic")) {
      String base64Credentials = auth.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials),Charset.forName("UTF-8"));
      System.out.println("friday check"+auth);
      final String[] values = credentials.split(":", 2);

      String userName = values[0];
      String password = values[1];

      password = bCryptPasswordEncoder.encode(password);
      try {
        User userExists = userService.findByUserName(userName);
        if(userExists==null){
          User user = new User();
          user.setUserName(userName);
          user.setPassword(password);
          userRepository.save(user);
          json.addProperty("message", "User Added Successfully");
        }else{
          json.addProperty("message", "User Account Already Exists!!!");
        }
      }catch(DataIntegrityViolationException e){
        json.addProperty("message", "User Account Already Exists!!!");
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
