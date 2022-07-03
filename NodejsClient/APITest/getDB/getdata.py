import pandas as pd
import sys
import numpy
#db 연결단
import pymysql
conn = pymysql.connect(host='localhost', user='root', password='12345', db='prj-maindb', charset='utf8')
cur = conn.cursor()

#
#sheet_path = sys.argv[2]
#sheet_name = sheet_path

df = pd.read_excel('./testDT.xlsx',sheet_name=0,header=1,nrows=38182,names=['노선ID','노선명','순번','NODE_ID','ARS-ID','정류소명'],usecols=["노선ID","노선명","순번","NODE_ID","ARS-ID","정류소명"])

dataCnt = numpy.size(df.index)


for i in range(0,dataCnt):
    try :
        sql = 'insert into `way` values (%s,%s);'
        cur.execute(sql, (list(df["노선명"])[i] ,  list(df["노선ID"])[i] ))
    except :
        pass
    
conn.commit()


for i in range(0,dataCnt):
    try :
        sql = 'insert into wayInfo values(%s,%s,%s,%s,%s)'
        cur.execute(sql, ( list(df["노선명"])[i], list(df["순번"])[i] , list(df["NODE_ID"])[i] , list(df["ARS-ID"])[i] , list(df["정류소명"])[i] ))
    except :
        pass
    
conn.commit()
conn.close()