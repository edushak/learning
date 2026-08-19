package com.learning.scraping

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class SimpleScraper {

    static void main(String[] args) {
        SimpleScraper.scrape('https://www.foxtrot.com.ua/ru/shop/holodilniki.html')
    }

    static void scrape(String urlStr) {
        Document doc = Jsoup.connect(urlStr).get();

        Elements elements = doc.select("a[class='card__title']")
        System.out.println("Found ${elements.size()} elements:")
        elements.forEach(System.out::println);
    }

}
