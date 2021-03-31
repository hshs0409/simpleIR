package com.github;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static java.lang.Integer.parseInt;

public class indexer {
    @SuppressWarnings({"rawtypes", "unchecked", "nls"})
    public void makeIndexer(String path) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(path);

            HashMap<String, Integer> word = new HashMap<>();
            HashMap<String, ArrayList<Pair>> resultMap = new HashMap<>();
            NodeList bodyList = doc.getElementsByTagName("body");

            for (int i = 0; i < bodyList.getLength(); i++) {
                String[] keywordArr = bodyList.item(i).getTextContent().split("#");
                for (String keyword : keywordArr) {
                    String[] keywordInfo = keyword.split(":");
                    String kwd = keywordInfo[0];
                    if (word.containsKey(kwd)) {
                        word.put(kwd, 1 + word.get(kwd));
                    } else {
                        word.put(kwd, 1);
                    }
                }
            }
            for (int i = 0; i < bodyList.getLength(); i++) {
                String[] keywordArr = bodyList.item(i).getTextContent().split("#");
                for (String keyword : keywordArr) {
                    String[] keywordInfo = keyword.split(":");
                    String kwd = keywordInfo[0];
                    String frequency = keywordInfo[1];
                    double weight = Math.round(parseInt(frequency) * (Math.log(bodyList.getLength() / (double) word.get(kwd))) * 100.0) / 100.0;
                    Pair pair = new Pair(i, weight);
                    ArrayList<Pair> pairList;
                    if (resultMap.containsKey(kwd)) {
                        pairList = resultMap.get(kwd);
                    } else {
                        pairList = new ArrayList<>();
                    }
                    pairList.add(pair);
                    resultMap.put(kwd, pairList);
                }
            }


            FileOutputStream fileOutputStream = new FileOutputStream("./index.post");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(resultMap);
            objectOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream("./index.post");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            try {
                Object object = objectInputStream.readObject();
                objectInputStream.close();

                System.out.println("읽어온 객체의 type =>" + object.getClass());

                HashMap hashMap = (HashMap) object;

                for (String key : (Iterable<String>) hashMap.keySet()) {
                    ArrayList resultList = (ArrayList) hashMap.get(key);
                    System.out.print(key + " : ");
                    for (Object resultObj : resultList) {
                        Pair result = (Pair) resultObj;
                        System.out.print(result.id + " " + result.weight + " ");
                    }
                    System.out.println(" ");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Pair implements Serializable {
    public int id;
    public double weight;


    public Pair(int id, double weight) {
        this.id = id;
        this.weight = weight;
    }
}