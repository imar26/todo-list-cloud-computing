# csye6225-fall2017

# Team Members

Aadesh Randeria     001224139   randeria.a@husky.neu.edu
Bhumika Khatri      001284560   prabhune.y@husky.neu.edu
Siddhant Chandiwal  001286480   chandiwal.s@husky.neu.edu
Yashodhan Prabhune  001220710   prabhune.y@husky.neu.edu

# Make Unauthenticated HTTP Request

Execute following command on your bash shell
``` bash
$ curl http://localhost:8080
```

## Expected Response:
```
{"message":"you are not logged in!!!"}
```

# Authenticate for HTTP Request

Execute following command on your bash shell
``` bash
$ curl -u user:password http://localhost:8080
```

where *user* is the username and *password* is the password.

## Expected Response:
 ```
 {"message":"you are logged in. current time is Tue Sep 19 20:03:49 EDT 2017"}
 ```
