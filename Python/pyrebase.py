
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

db_url = 'https://eveproject-624f6-default-rtdb.asia-southeast1.firebasedatabase.app/'
cred = credentials.Certificate("eveproject-624f6-firebase-adminsdk-uugft-84202cf8b1.json")
default_app = firebase_admin.initialize_app(cred, {
    'databaseURL': db_url
})

ref = db.reference() # 기본 위치 지
ref.update({'자동차':['기아','현대']})