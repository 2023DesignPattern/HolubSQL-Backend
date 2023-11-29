package com.holub.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLImporter implements Table.Importer {

//    private String tableName = "anonymous";
//    private final ArrayList<String> columnNames = new ArrayList<>();
//    private final BufferedReader reader;
//
//    public XMLImporter(Reader reader){
//        if(reader instanceof BufferedReader){
//            this.reader = (BufferedReader) reader;
//        } else{
//            this.reader = new BufferedReader(reader);
//        }
//    }

    private String tableName;
    private Iterator columnNames;
    private List<String> currentRow;
    private Document document;
    private int rowIndex;
    private Reader reader;
    private Element rootElement;

    private String xmlContent;

    public XMLImporter(Reader reader){
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            document = builder.parse(inputStream);
//        } catch (Exception e) {
//            throw new RuntimeException("Error initializing XmlImporter", e);
//        }
        this.reader = reader;
    }

    private void initialize() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(reader));
            rootElement = document.getDocumentElement();

            tableName = rootElement.getNodeName();
            columnNames = loadColumnNames(); // ArrayList<String>으로 초기화
            currentRow = new ArrayList<>();
            rowIndex = 0;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing XmlImporter", e);
        }
    }

    @Override
    public void startTable() throws IOException {

        /**
        // xml 헤더 버림
        reader.readLine();

        // Table명 파싱
        String line = reader.readLine();
        int startIndex = line.indexOf('<') + 1;
        int endIndex = line.indexOf('>');
        tableName = line.substring(startIndex, endIndex);
         */

//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new InputSource(reader));
//
//            this.rootElement = document.getDocumentElement();
//            tableName = this.rootElement.getNodeName();
//
//            columnNames = (List<String>) loadColumnNames();
//            columnNames = List.of(new String[]{"1"});
//            currentRow = new ArrayList<>();
//            rowIndex = 0;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error initializing XmlImporter", e);
//        }

        initialize();

    }

    @Override
    public String loadTableName() throws IOException {
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException {
        int size = 0;
        while(columnNames.hasNext()){
            size++;
        }
        return new ArrayList<>(columnNames).size();
    }

    @Override
    public Iterator<String> loadColumnNames() throws IOException {
        List<String> names = new ArrayList<>();
        Element firstRowElement = getNextRowElement();

        if (firstRowElement != null) {
            NodeList columnElements = firstRowElement.getChildNodes();
            for (int i = 0; i < columnElements.getLength(); i++) {
                Node columnNode = columnElements.item(i);
                if (columnNode.getNodeType() == Node.ELEMENT_NODE) {
                    names.add(columnNode.getNodeName());
                }
            }
        }
        return names.iterator();
    }

    @Override
    public Iterator loadRow() throws IOException {
        Element rowElement = getNextRowElement();
        if (rowElement != null) {
            NodeList valueElements = rowElement.getChildNodes();
            for (int i = 0; i < valueElements.getLength(); i++) {
                Node valueNode = valueElements.item(i);
                if (valueNode.getNodeType() == Node.ELEMENT_NODE) {
                    currentRow.add(valueNode.getTextContent());
                }
            }
            return currentRow.iterator();
        }
        return null;
    }

    @Override
    public void endTable() throws IOException {
    }

    private Element getNextRowElement() {
        // Assume the row elements start from the second one

        NodeList rowElements = document.getDocumentElement().getElementsByTagName("row");

        if (rowIndex < rowElements.getLength()) {
            return (Element) rowElements.item(rowIndex++);
        }
        return null;
    }
}
