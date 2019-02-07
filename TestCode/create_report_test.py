import requests
import concurrent.futures

BASE_URL = 'http://52.8.70.153'
session = requests.Session()

def sign_in():
    sign_url = BASE_URL + '/api/v1/user/signin/'
    sign_in_params = {
        'email': 'www@www.www',
        'password': '123123123'
    }
    response = session.get(sign_url, params=sign_in_params)
    return response

def create_report():
    create_report_url = BASE_URL + '/api/v1/report/create/'
    create_report_body = {
        "data": [{
            "what": "I was sexually assaulted.",
            "location": "in school",
            "time": "02/01/2019",
            "who": "Bob",
            "details": "blah",
            "facebook_url": "bob"
        }]
    }
    response = session.post(create_report_url, json=create_report_body)
    return response

def delete_report(reportId):
    delete_report_url = BASE_URL + '/api/v1/report/delete/'
    delete_params = {
        'reportId': reportId
    }
    response = session.delete(delete_report_url, params=delete_params)
    return response

response = sign_in()
print('sign in response', response)
response = create_report()
print('create report response', response)

with concurrent.futures.ThreadPoolExecutor(max_workers=5) as executor:
    response = {executor.submit(create_report) for _ in range(200)}
