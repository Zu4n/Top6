package com.nauz.crawl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {
    public static void main(String[] args) throws IOException {

        int page = 5;

        for(int i=1; i<page; i++){
            String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=265&sid1=100&date=20210811&page="+i;
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByAttributeValue("class","list_body newsflash_body");

            Element element = elements.get(0);
            Elements photoElements = element.getElementsByAttributeValue("class","photo");

            for(int j=0; j< photoElements.size(); j++){
                Element articleElement = photoElements.get(j);
                Elements _articleElements = articleElement.select("a");
                Element _articleElement = _articleElements.get(0);

                String articleUrl = _articleElement.attr("href"); //기사 링크

                Element imgElement = _articleElement.select("img").get(0);
                String imgUrl = imgElement.attr("src"); //사진링크
                String title = imgElement.attr("alt"); //기사제목

                Document subDoc = Jsoup.connect(articleUrl).get();

                System.out.println(title);
                System.out.println();

            }
            System.out.println(i+"page 크롤링 종료");
        }

    }
}
