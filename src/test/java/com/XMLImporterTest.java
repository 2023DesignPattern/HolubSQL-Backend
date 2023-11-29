package com;

import com.holub.database.*;
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

        Iterator iterator = xmlImporter.loadColumnNames();

        String[] columnNames = {"addrId", "street", "city", "state", "zip"};
        int iteratorIndex = 0;
        System.out.println();

        while(iterator.hasNext()) {
            String actualColumnName = (String) iterator.next();
            System.out.print(actualColumnName + " ");
            assertEquals(columnNames[iteratorIndex], actualColumnName);
            iteratorIndex+=1;
        }

        System.out.println();
        System.out.println("-----------------------------");

        xmlImporter.getPrevRowIndex();  // 첫번째 row부터 출력하기 위해 임의로 rowIndex 감소

        Iterator rowIterator = xmlImporter.loadRow();

        while(rowIterator != null) {
            while(rowIterator.hasNext()) {
                System.out.print(rowIterator.next() + " ");
            }
            rowIterator = xmlImporter.loadRow();
            System.out.println();
        }

        System.out.println();
    }
}
