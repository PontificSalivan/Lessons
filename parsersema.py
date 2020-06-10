import requests
from pymongo import MongoClient
from bs4 import BeautifulSoup
from multiprocessing import Pool
from datetime import datetime
from tqdm import trange

import sys

from pymongo import MongoClient

client = MongoClient("mongodb+srv://root:root@cluster0-xuev7.mongodb.net/sema?retryWrites=true&w=majority")

db=client.sema
news = db.sema

mainurl = 'http://www.volgograd.ru/news'
mainpage = requests.get(mainurl).text
mainsoup = BeautifulSoup(mainpage, 'html.parser')
newssoup = mainsoup.find("div", {"class":"col-md-12 news-item"}).find('a').get('href')


numarticle=""
for char in newssoup:
  if char.isdigit():
    numarticle+=char

all_links = []

while int(numarticle)>0:
  all_links.append(mainurl+"/"+str(numarticle)) 
  numarticle = int(numarticle)-1


def findcontent(url, soup):
  maincontent = soup.find("div", {"class":"col-md-9 main-content"})

  if maincontent.find("div", {"class":"news-detail"}) !=None :

    header = maincontent.find('h1').text
    date = maincontent.find("div", {"class":"date"}).text
    texts = maincontent.find("div", {"class":"news-detail"}).find('p').text + maincontent.find("div", {"class":"news-detail"}).find("div", {"id":"full_text"}).text
    new = {
      "header": header,
      "date": date,
      "text": texts,
      "linkOfNew": url
    }
    news.insert_one(new)
  else:
    print("я ничего не нашел")


def make_all(url):
  page = requests.get(url).text
  soup = BeautifulSoup(page, 'html.parser') 
  findcontent(url, soup)

def main():
  start = datetime.now()

  n = len(all_links)
  listOfList = []

  count_of_thread = 10

  for i in range(0, count_of_thread):
    listOfList.append(all_links[int(i*n/count_of_thread):int((i+1)*n/count_of_thread)])

  for i in trange(count_of_thread, file=sys.stdout, desc='outer loop'):
    with Pool(count_of_thread) as p:
      p.map(make_all, listOfList[i])

  end = datetime.now()
  total = end - start
  print(total)






if __name__ == '__main__':
  main() 