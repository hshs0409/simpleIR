package com.github;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.jsoup.Jsoup;

public class makeCollection {

    public void makeCollection(String path) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = docBuilder.newDocument();
        Element docs = document.createElement("docs");
        document.appendChild(docs);
        docs.appendChild(document.createTextNode("\n"));


        int index = 0;
        String line = null;

        File folder = new File(path); // file list
        System.out. println(folder);
        File[] fileList = folder.listFiles();
        for (File file : fileList) {
            if (file.isFile()) {
                Element doc = document.createElement("doc");
                doc.setAttribute("id", Integer.toString(index));
                docs.appendChild(doc);
                doc.appendChild(document.createTextNode("\n"));
                FileReader fileReader = null;
                try {
                    fileReader = new FileReader(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                Element body = document.createElement("body");
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.indexOf("<title>") != -1) {
                            Element title = document.createElement("title");
                            line = line.replace("<title>", "").trim();
                            line = line.replace("</title>", "");
                            title.appendChild(document.createTextNode(line));
                            doc.appendChild(title);
                            doc.appendChild(document.createTextNode("\n"));
                        }

                        if (line.indexOf("<p>") != -1) {
                            line = line.replace("<p>", "").trim();
                            line = line.replace("</p>", " ");
                            body.appendChild(document.createTextNode(line + "\n"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                doc.appendChild(body);
                index += 1;
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource source = new DOMSource(document);
            StreamResult result = null;
            result =
                    new StreamResult(
                            new FileOutputStream(new File("./collection.xml"))
                    );
            transformer.transform(source, result);
        } catch ( FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
