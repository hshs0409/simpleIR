package com.github;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class getSnippet {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        // 변수 받아오기
        String path = args[1];
        String Query = args[3];

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        File file = new File(path);
        org.w3c.dom.Document docs = docBuilder.parse(file);
        NodeList bodyList = docs.getElementsByTagName("body");
        System.out.println(bodyList);
        for (int i = 0; i < bodyList.getLength(); i++) {
            Node body = bodyList.item(i);
            String bodyStr = body.getTextContent();
            System.out.println(bodyStr);
        }

        FileInputStream fileInputStream = new FileInputStream(path);
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

    }
}
