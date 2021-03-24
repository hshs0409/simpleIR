package com.github;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class makeKeyword {
    public void makeKeyword(String path) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            File file = new File(path);
            org.w3c.dom.Document docs = docBuilder.parse(file);
            NodeList bodyList = docs.getElementsByTagName("body");
            for (int i = 0; i < bodyList.getLength(); i++) {
                Node body = bodyList.item(i);
                String bodyStr = body.getTextContent();
                KeywordExtractor ke = new KeywordExtractor();
                KeywordList kl = ke.extractKeyword(bodyStr, true);
                String newBody = "";
                for (int j = 0; j < kl.size(); j++) {
                    Keyword kwrd = kl.get(j);
                    newBody += kwrd.getString() + ":" + kwrd.getCnt() + "#";
                }
                body.setTextContent(newBody);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                DOMSource source = new DOMSource(docs);
                StreamResult result = null;
                result =
                        new StreamResult(
                                new FileOutputStream(new File("./index.xml"))
                        );
                transformer.transform(source, result);
            } catch (FileNotFoundException | TransformerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
