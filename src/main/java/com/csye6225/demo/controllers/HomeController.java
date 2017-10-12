/*
Yashodhan Prabhune,001220710,prabhune.y@husky.neu.edu
Bhumika Khatri,001284560,khatri.bh@husky.neu.edu
Aadesh Randeria,001224139,randeria.a@husky.neu.edu
Siddhant Chandiwal,001286480,chandiwal.s@husky.neu.edu
 */

package com.csye6225.demo.controllers;

import com.csye6225.demo.pojo.Attachment;
import com.csye6225.demo.pojo.Tasks;
import com.csye6225.demo.pojo.User;
import com.csye6225.demo.repositories.AttachmentRepository;
import com.csye6225.demo.repositories.TaskRepository;
import com.csye6225.demo.repositories.UserRepository;
import com.csye6225.demo.service.AttachmentService;
import com.csye6225.demo.service.TaskService;
import com.csye6225.demo.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.*;

@Controller
public class HomeController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private AttachmentRepository attachmentRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private UserService userService;

  private TaskService taskService;

  //added last
  private AttachmentService attachmentService;

  @Autowired
  public HomeController(UserService userService, TaskService taskService) {
    this.userService = userService;
    this.taskService = taskService;
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

      final String[] values = credentials.split(":", 2);

      String userName = values[0];
      String password = values[1];

      if (userName.isEmpty()) {
        json.addProperty("message", "Please enter username/password");
      } else if (password.isEmpty()) {
        json.addProperty("message", "Please enter username/password");
      } else {
        password = bCryptPasswordEncoder.encode(password);
        try {
          User userExists = userService.findByUserName(userName);
          if (userExists == null) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            userRepository.save(user);
            json.addProperty("message", "User Added Successfully");
          } else {
            json.addProperty("message", "User Account Already Exists!!!");
          }
        } catch (DataIntegrityViolationException e) {
          json.addProperty("message", "User Account Already Exists!!!");
        }
      }
    } else {
      json.addProperty("message", "Please enter username/password");
    }
    return json.toString();
  }



  @RequestMapping(value="/tasks", method=RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String addTask(HttpServletRequest request, HttpServletResponse response, @RequestBody Tasks task){
      response.setStatus(HttpServletResponse.SC_CREATED);
      String taskId;
      UUID uuid = UUID.randomUUID();
      taskId = uuid.toString();
      JsonObject json = new JsonObject();
      Tasks t = new Tasks();
      t.setTaskId(taskId);
      String desc = task.getDescription();
      t.setDescription(desc);
      taskRepository.save(t);
      json.addProperty("taskId", taskId);
      json.addProperty("description", desc);
      return json.toString();
  }

  @RequestMapping(value="/tasks", method=RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getAllTasks(HttpServletRequest request, HttpServletResponse response){
    response.setStatus(HttpServletResponse.SC_OK);
    JsonArray jarr =new JsonArray();
    ArrayList<Tasks> taskList = taskService.getTasks();
    for(Tasks t: taskList) {
      JsonObject json = new JsonObject();
      json.addProperty("taskId", t.getTaskId());
      json.addProperty("description", t.getDescription());
      jarr.add(json);
    }
    return jarr.toString();
  }


  @RequestMapping(value="/tasks/{id}", method=RequestMethod.PUT, produces = "application/json")
  @ResponseBody
  public String updateTask(HttpServletRequest request, HttpServletResponse response, @RequestBody Tasks task, @PathVariable String id){
    response.setStatus(HttpServletResponse.SC_OK);

    System.out.println("Id params is - "+id);

    JsonObject json = new JsonObject();
   // Tasks t = new Tasks();
    Tasks taskExists = taskService.findByTaskId(id);
    if (taskExists==null){
      json.addProperty("message", "Please Enter Valid Task ID");
    }else {
      String desc = task.getDescription();
      taskExists.setDescription(desc);
      System.out.println("GettingDesc "+desc);
      taskService.updateTask(taskExists);
      json.addProperty("taskId", id);
      json.addProperty("description", desc);
    }
    return json.toString();
  }
  @RequestMapping(value="/tasks/{id}", method=RequestMethod.DELETE, produces = "application/json")
  @ResponseBody
  public String deleteTask(HttpServletRequest request, HttpServletResponse response, @PathVariable String id){
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);



    JsonObject json = new JsonObject();

    Tasks taskExists = taskService.findByTaskId(id);
    if (taskExists==null){
      json.addProperty("message", "Please Enter Valid Task ID");

    }else {

      taskService.deleteTask(taskExists);

      json.addProperty("message", "Task Deleted Successfully");
      //System.out.println("Bhumika"+json.toString());
    }
    return json.toString();
  }
  @RequestMapping(value="/tasks/{id}/attachments", method=RequestMethod.POST)
  @ResponseBody
  public String addAttachment(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile[] file1, @PathVariable String id){
    response.setStatus(HttpServletResponse.SC_CREATED);

    JsonArray jsonArray = new JsonArray();
    JsonObject json = new JsonObject();
    Tasks taskExists = taskService.findByTaskId(id);
    if (taskExists==null){
      json.addProperty("message", "Please Enter Valid Task ID");
    }else {
      try {
        for(MultipartFile file : file1) {
          JsonObject json1 = new JsonObject();
          System.out.println("file"+file.getOriginalFilename());
          UUID uuid = UUID.randomUUID();
          byte[] bytes = file.getBytes();
          String rootPath = System.getProperty("user.home");
          File dir = new File(rootPath + File.separator + "tmpFiles");
          if (!dir.exists())
            dir.mkdirs();


          File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
          BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
          stream.write(bytes);
          stream.close();
          Attachment att = new Attachment();
          String att_id = uuid.toString();

          att.setAttachmentId(att_id);

          att.setName(serverFile.toString());
          att.setTasks(taskExists);

          attachmentRepository.save(att);

          json1.addProperty("Attachment id", att_id);
          json1.addProperty("Attachment name", serverFile.toString());
          jsonArray.add(json1);
        }

      } catch(Exception e){
        return null;
      }

    }
    return jsonArray.toString();

  }

  //added last
  @RequestMapping(value="/tasks/{id}/attachments", method=RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getAttachmentByTaskId(HttpServletRequest request, HttpServletResponse response, @PathVariable String id){
    response.setStatus(HttpServletResponse.SC_OK);

    System.out.println("Id params is - "+id);

    JsonArray jArr = new JsonArray();
    JsonObject json = new JsonObject();
    // Tasks t = new Tasks();
    Tasks taskExists = taskService.findByTaskId(id);
    if (taskExists==null){
      json.addProperty("message", "Please Enter Valid Task ID");
    }else {
      //System.out.println("tasks coming in"+taskExists.getTaskId());
      //System.out.println("task attachments are"+taskExists.getAttachment());
      Set<Attachment> listAttachment = taskService.getAttachmentsByTaskId(taskExists);

      for(Attachment att1 : listAttachment) {
        JsonObject json1 = new JsonObject();
        json1.addProperty( "attachment id", att1.getAttachmentId());
        json1.addProperty("path", att1.getName());
        jArr.add(json1);
      }
    }
    return jArr.toString();
  }

}
