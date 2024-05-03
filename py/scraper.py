from bs4 import BeautifulSoup
import requests
from csv import writer

feed = []
for number in range(1, 7): # останній не включається
    # todo make a dynamic substitution to a 404 error or an end
    page = f'?page={number}'
    url = ('https://www.foxtrot.com.ua/ru/shop/holodilniki.html?page=' + str(page))
    page = requests.get(url)

    soup = BeautifulSoup(page.content, 'html.parser')
#     data_page
    lists = soup.find_all('div', class_='card')
    with open('foxtrot.csv', 'w', encoding='utf8', newline='') as faleFoxtrot:

        thewriter = writer(faleFoxtrot)
        header = ['Title','Price']
        thewriter.writerow(header)

        for lists in lists:
            title = lists.find('a', class_='card__title').text.replace('\n','')
            price = lists.find('div', class_='card-price').text.replace('\n','').strip()
            feed = [title, price]
            print(feed)

# Issuing a list of pages
for number in range(1, 7):
    page = f'?page={number}'
    url = ('https://www.foxtrot.com.ua/ru/shop/holodilniki.html' + str(page))
    page = requests.get(url)
    print(url)
