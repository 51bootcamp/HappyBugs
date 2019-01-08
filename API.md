# Happy Bugs API document
***Response Content Type: application/json***

## Sign up
Method: POST   
URI: /api/v1/user/sign_up/  
Request body:
```
{    
    "email": "heeji@gmail.com",
    "password": "12345"
}
```

Response Messages  
HTTP Status Code

> 201: account creation success  
> 409: account creation conflict

## Sign in (Logs user into the system)
Method: GET  
URI: /api/v1/user/login/    
Query Parameter:  
- email (string)  
- password (string)  
> Response status code: 202 (successfully login)


## Sign out (Logs out current logged in user session)
Method: GET
URI: /api/v1/user/logout/
Response status code: 

## Delete user (This can only be done by the logged in user.)
Method: DELETE
URI: /api/v1/user/delete
Request body: Login User’s Session
Response status code: 

## Create report
Method: POST  
URI:  /api/v1/report/create/
redirect URI : /api/v1/report/list

Request body: Login User’s Session
```
{
    "data": [{
        "what": "I was sexually assaulted.",
	  "where": "school",
	  "when": "12/12/2018",
	  "who": "J",
        "details": "blahblah"
    }]
}
```

Response body:
```
{
    "data": [{
	"uri": "/api/v1/report/report_id=12"
    }]
}
```

## Show the list of reports (Requests all reports to be retrieved from the server.)
Method: GET
URI: /api/v1/report/list/
Request body: Login User’s Session
Response:
```
{
    "data": [{
        "created_date": "2019-01-01",
        "what": "I was sexually assaulted.",
        "report_ID": "485",
    }, {
        "created_date": "2018-10-08",
	  "what": "I was sexually assaulted.",
        "report_ID": "8561",
    }]
}
```

## Read a certain report

Method: GET
URI: /api/v1/report/read?report_id=<report_id>/
Request body: Login User’s Session

Response:
```{
    "data": [{
        "report_ID" : "100"
        "what": "I was sexually assaulted.",
        "where": "3rd floor female restroom of school",
        "when": "12/12/2018 at 11:30 pm",
        "who": "Minho who are the Data Structure professor",
        "details": "blahblah",
        "created_date": "2018-12-28",
    }]
}
```

## Edit report

Method: PUT
URI: api/v1/report/update?report_id=<report_id>/
Request body: Login User’s Session

```
Request body: 
{
    "data":[{
        "what": "I was sexually assaulted.",
        "where": "school",
        "when": "12/15/2018",
        "who": "Jay",
        "details": "blahblah",
    }]
}
```

## Delete report

Method: POST
URI: api/v1/report/delete?report_id=<report_id>/  
Request body: Login User’s Session
Method: DELETE

Request body:
```
{
   "data": [{
       "report_ID" : "7587",
   }],
   "user_ID" : "1554", 
}
```


Reference: https://technologyconversations.com/2014/08/12/rest-api-with-json/
