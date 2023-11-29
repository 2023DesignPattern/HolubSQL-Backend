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

    private String tableName;
    private Iterator columnNames;
    private List<String> currentRow;
    private Document document;
    private int rowIndex;
    private Reader reader;
    private Element rootElement;

    public XMLImporter(Reader reader){
        this.reader = reader;
    }

    @Override
    public void startTable() throws IOException {
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
    public String loadTableName() throws IOException {
        return tableName;
    }

    @Override
    public int loadWidth() throws IOException {
        int size = 0;
        while(columnNames.hasNext()) {
            size+=1;
            columnNames.next();
        }
        return size;
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
        currentRow = new ArrayList<>();
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

    public void getPrevRowIndex() {
        rowIndex-=1;
    }

    public void getNextRowIndex() {
        rowIndex+=1;
    }
}
