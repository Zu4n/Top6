package com.nauz.nlp;


import java.io.BufferedReader;
import java.io.FileReader;

public class NlpWorker {

    public static void main(String[] args) {
        String path = "C:/dev/output/articles.csv";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String text;
            String data = "";
            while((text = bufferedReader.readLine()) != null){
                data+=text+"\n";
            }
            System.out.println(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }

}
