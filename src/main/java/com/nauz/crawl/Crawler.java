package com.nauz.crawl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class Crawler {
    public static void main(String[] args) throws IOException {
        /* 네이버 기사 헤드라인 크롤링 */
        int page = 5;   //크롤링할 총 페이지 수
        String path = "C:/dev/output/"; //결과 저장 경로
        String output = "";
        String seperator = "||";
        Date date = new Date();
        String today = (1900+date.getYear())+String.format("%02d",(date.getMonth()+1))+String.format("%02d",date.getDate());
        for(int i=1; i<page; i++){
            //네이버 기사 메인화면 url
            String url = "https://news.naver.com/main/list.naver?mode=LPOD&sid2=140&sid1=001&mid=sec&oid=001&isYeonhapFlash=Y&aid=0014422676&date=20240103&page="+i;
            Document document = Jsoup.connect(url).get();
            //헤드라인 부분 class=list_body newsflash_body
            Elements elements = document.getElementsByAttributeValue("class","list_body newsflash_body");
            //제목에 해당하는 a태그 추출
            Elements aElements = elements.select("a");
            for(int j=0; j<aElements.size(); j++){
                Element element = aElements.get(j);
                //태그 내 html 문자열 추출
                String title = element.html();
                //문자열 내 html 태그 제거
                String tagRemovedTitle = title.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>","");
                //문자열 내 대괄호 제거
                String braketsRemovedTitle = tagRemovedTitle.replaceAll("\\[.*?\\]","");//대괄호제거
                if(braketsRemovedTitle.equals(" ")) continue;
                System.out.println(i+"페이지 / "+(j)+ " 번째 기사 제목 : "+braketsRemovedTitle);
                output+=today+seperator+i+seperator+j+seperator+braketsRemovedTitle+"\n";
            }
            System.out.println(i+"page 크롤링 종료");
        }
        String fileName = today+String.format("%02d", date.getHours())+String.format("%02d", date.getMinutes())+String.format("%02d", date.getSeconds());
        File file = new File(path+"articles_"+fileName+".txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(output.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fileOutputStream.close();
            System.out.println("파일저장완료");
        }
    }
}
