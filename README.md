# CSYE 6225 - FALL 2017

# Team Members for Assignment4

Aadesh Randeria   001224139  randeria.a@husky.neu.edu

Bhumika Khatri   001284560  khatri.bh@husky.neu.edu

Siddhant Chandiwal 001286480  chandiwal.s@husky.neu.edu

Yashodhan Prabhune 001220710  prabhune.y@husky.neu.edu


# Prerequisites for building and deploying application locally

IntelliJ IDEA Ultimate, MySQL Workbench 6.3, Apache JMeter 3.2, Apache Tomcat 8, Postman, OpenJDK8, Ubuntu 16.04 

# Build and Deploy instructions for web application

Import this gradle based project in IntelliJ IDEA Ultimate and create a local Tomcat Server and give some Unique name to the server(for quick identification) to run this web application. Create a database named "clouddb" to persist the user data
in MySQL. Right click on war file inside Gradle Projects listed on right side. Wait until the war gets successfully build.
Now run the local server, a new browser window will be opened with a JSON object like "{"message":"you are not authorized!!!"}"
Follow the steps mentioned below to test this application
- Open Postman
- Create a new POST request with URL - http://localhost:8080/user/register
- Under Authorization tab select type as Basic Auth and provide valid username(Email address) and password to register user account and press Send request Button at the top
- You will get a response saying "{"message": "User Added Successfully"}"
- Now create a new GET request with URL - http://localhost:8080
- Under same Authorization tab select previously used credentials to login the account and press Send Request button at the top
- You will get a response saying "{"message": "You are Logged In. Current Time is : Sat Sep 30 09:39:20 EDT 2017"}"
- You can further test the application by changing the body parameters to check user login validations
We can further test this application from browser or by running the commands on bash shell by giving curl commands

# Make Unauthenticated HTTP Request

Execute following command on your bash shell
``` bash
$ curl http://localhost:8080
```
## Expected Response:
```
{"message":"you are not authorized!!!"}
```
# Authenticate for HTTP Request

Execute following command on your bash shell
``` bash
$ curl -u user:password http://localhost:8080
```
where *user* is the username and *password* is the password.

## Expected Response:
 ```
 {"message":"You are Logged In. Current Time is : Sat Sep 30 09:39:20 EDT 2017"}
 ```

# Instructions to Run Unit, Integration Tests

Unit tests and integration tests are present in separate Java Class inside "test" folder and annotate with required testing platform dependencies.
For running locally, right click on the test functions and run them.
For running on manual build trigger through Travis-CI server, ensure travis.yml contains scripts linked to the folder that contains unit tests. Commit & Push the code to Git Repository to trigger a build or else trigger manual build from the travis-CI console to get the results.

# Instructions to Run Load Based tests

Install Apache-jmeter as a load test functional behavior and measuring performance.
Run ./jmeter.sh through command line to open jmeter GUI console.
Create a new thread group by browsing to appropriate ".jmx" file to load the test and set-up load testing platform like number of users and ramp-up time on the server for performance measurement
We can test this application on 100 different randomly generated users. 
View results in tree: View results on running the application and jmeter test server.

# Link to TravisCI build for the project

https://travis-ci.com/siddhantchandiwal/csye6225-fall2017

https://travis-ci.com/aadeshkranderia/csye6225-fall2017

https://travis-ci.com/khatribh/csye6225-fall2017

https://travis-ci.com/yashodhan-p/csye6225-fall2017