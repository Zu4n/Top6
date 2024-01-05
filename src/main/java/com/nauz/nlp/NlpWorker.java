package com.nauz.nlp;


import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.*;

public class NlpWorker {

    public static void main(String[] args) {
        /*파일 읽어오기*/
        String path = "C:/dev/output/";
        String seperator = "\\|\\|";
        String[] wholeTitles = null;
        BufferedReader bufferedReader = null;
        List<Map<String, String>> articles = new ArrayList<>();
        try {
            File searchPath = new File(path);
            FilenameFilter filenameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("article");
                }
            };
            File files[] = searchPath.listFiles(filenameFilter);
            if(files.length>0){
                bufferedReader = new BufferedReader(new FileReader(files[0]));
                String text;
                while((text = bufferedReader.readLine()) != null){
                    Map<String, String> article = new HashMap<>();
                    String[] splitStr = text.split(seperator);
                    article.put("stdym",splitStr[0]);
                    article.put("page",splitStr[1]);
                    article.put("seq",splitStr[2]);
                    article.put("title",splitStr[3]);
                    articles.add(article);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }

        /*제목 자연어처리*/
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        int i = 0;
        for(Map map : articles){
            KomoranResult komoranResult = komoran.analyze(map.get("title").toString());
            System.out.println("title = "+map.get("title"));
            /*List<Token> tokens = komoranResult.getTokenList();
            for(Token token : tokens){
                System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
            }*/
            //명사추출
            List<String> nouns = komoranResult.getNouns();
            //추출된 명사 담기
            for(String noun : nouns){
                wholeTitles[i] = noun;
                i++;
            }
        }
        /*자연어 처리된 명사들 카운팅*/

    }

}
