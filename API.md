# Happy Bugs API document V1

MIT License

 Copyright (c) 2019 51bootcamp

 Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

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
> 201 : account creation success  
> 409 : account already exist

## Sign in
*Logs user into the system*  
Method: GET  
URI: /api/v1/user/login/    
Query string:  
- email (string)  
- password (string)  
> 200 : successfully login  
> 404 : user not found  

## Sign out
*Logs out current logged in user session*  
Method: GET  
URI: /api/v1/user/logout/
> 200 : successfully sign out  
> 403 : Not logined  

## Delete user
Method: DELETE  
URI: /api/v1/user/delete/    
> 200 : successfully sign out  
> 403 : Not logined  

## Create report
Method: POST  
URI:  /api/v1/report/create/  
Request body:  
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
> 201 : successfully created  
> 403 : Not logined  

## List reports
Method: GET  
URI: /api/v1/report/list/  
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
> 200 : successfully listed  
> 403 : Not logined   

## Read a report
Method: GET  
URI: /api/v1/report/read/:report_ID   

Response:
```
{
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
> 200 : successfully read a report  
> 403 : Not logined  

## Edit report 
Method: PUT  
URI: api/v1/report/update/   
Query string:
- report_id (string)

Request body: 
```
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
> 200 : successfully Edit  
> 403 : Not logined  

## Delete report
Method: DELETE  
URI: api/v1/report/delete/:report_ID   


> 204 : successfully delete a report  
> 403 : Not logined  


Reference: https://technologyconversations.com/2014/08/12/rest-api-with-json/
