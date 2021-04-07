package com.github;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class searcher {
    public void calcSim(String path, String query) throws Exception {

        class Element implements Comparable<Element> {

            int index;
            double value;

            Element(int index, double value) {
                this.index = index;
                this.value = value;
            }

            public int compareTo(Element e) {
                return (int) (this.value - e.value);
            }
        }
        HashMap<String, Double> map = new HashMap<>();
        KeywordExtractor keywordExtractor = new KeywordExtractor();
        KeywordList keywordList = keywordExtractor.extractKeyword(query, true);

        // query 받아서 키워드 추출하고
        for (int i = 0; i < keywordList.size(); i++) {
            Keyword keyword = keywordList.get(i);
            map.put(keyword.getString(), (double) keyword.getCnt());
        }

        // index.post 받아와서
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        HashMap hashMap = (HashMap) object;

        double[] sims = {0.0, 0.0, 0.0, 0.0, 0.0};

        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String keyword = (String) it.next();
            if (hashMap.containsKey(keyword)) {
                List<Double> list = (ArrayList<Double>) hashMap.get(keyword);
                for (int j = 0; j < list.size(); j = j + 2) {
                    sims[(int) Math.round(list.get(j))] += list.get(j + 1) * map.get(keyword);
                }
            }
        }
        List<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < sims.length; i++) {
            elements.add(new Element(i, sims[i]));
        }

        String[] titles = {"떡", "라면", "아이스크림", "초밥", "파스타"};
        Collections.sort(elements);
        for (int i = 4; i > 1; i--) {
            System.out.println(titles[elements.get(i).index]);
        }
    }
}
