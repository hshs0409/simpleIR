package com.github;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class searcher {
    private List<String> contents = new ArrayList<String>(5);

    public void calcSim(String path, String query) throws Exception {

        HashMap<String, Double> map = new HashMap<>();
        KeywordExtractor keywordExtractor = new KeywordExtractor();
        KeywordList keywordList = keywordExtractor.extractKeyword(query, true);

        // query 받아서 키워드 추출하고
        for (int i = 0; i < keywordList.size(); i++) {
            Keyword keyword = keywordList.get(i);
            map.put(keyword.getString(), (double) keyword.getCnt());
        }

        for (Object object: map.keySet()) {
            System.out.println(object + " " + map.get(object));
        }

        // index.post 받아와서
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        HashMap hashMap = (HashMap) object;

        for (Object object2: hashMap.keySet()) {
            System.out.println(object2 + " " + map.get(object2));
        }

        double[] Sim = {0.0 ,0.0 ,0.0 ,0.0 ,0.0};

        Iterator it = map.keySet().iterator();
        while(it.hasNext()) {
            String str = (String) it.next();

            if(hashMap.containsKey(str)) {
                List<Double> list = (ArrayList<Double>)hashMap.get(str);
                for(int j =0; j< list.size(); j=j+2) {

                    int index = (int)Math.round(list.get(j));
                    Sim[index] += list.get(j+1) * map.get(str);
                }
            }
        }
        for(int i=0; i<5; i++){
            System.out.println(Sim[i]);
        }

        int[] arr= {0,1,2,3,4};
        for(int i =0;i<4;i++) {
            int index = i;
            for(int j =i;j<5;j++) {
                if(Sim[index] < Sim[j]) {
                    index = j;
                }
            }
            double temp;
            temp = Sim[index];
            Sim[index] = Sim[i];
            Sim[i] = temp;
            int temp2;
            temp2 = arr[index];
            arr[index] = arr[i];
            arr[i] = temp2;
        }
        for(int i =0; i<5; i++) {
            org.jsoup.nodes.Document html = Jsoup.parse(new File("./collection.xml"), "UTF-8");

            org.jsoup.select.Elements content = html.select("docs > doc");

            String text = content.get(i).text();
            contents.add(text);
        }
        for(int i =0; i<3; i++) {
            System.out.println(contents.get(arr[i]).split(" ")[0]);
        }
    }
}
