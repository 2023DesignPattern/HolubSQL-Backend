package com;

import com.holub.database.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class XMLImporterTest {
    Reader reader = new FileReader("src/main/java/com/holub/database/Dbase/address.xml");

    XMLImporter xmlImporter = new XMLImporter(reader);

    public XMLImporterTest() throws FileNotFoundException {
    }

    @Test
    @DisplayName("XMLImporter 테스트")
    public void XMLImporterTest() throws IOException {
        xmlImporter.startTable();

        assertEquals(xmlImporter.loadTableName(), "address");
        assertEquals(xmlImporter.loadWidth(), 5);

//        Iterator iterator = xmlImporter.loadColumnNames();
//        if(iterator.hasNext()) System.out.println(iterator.next());

//        String[] columnNames = {"addrId", "street", "city", "state", "zip"};
//        for(String columnName : columnNames){
//            if(iterator.hasNext()){
//                assertEquals(iterator.next(), columnName);
//            }
//        }
    }
}
