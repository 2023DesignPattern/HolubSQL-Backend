package com;

import com.holub.database.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class XMLExporterTest {

    Table people = TableFactory.create("name", new String[] { "last", "first", "addrId" });
    Table address = TableFactory.create("address", new String[] { "addrId", "street", "city", "state", "zip" });
    StringWriter writer = new StringWriter();
    Database database;

    @BeforeAll
    @DisplayName("테스트를 위한 더미데이터 세팅")
    private void setDummyDate() {
        try{
            people.insert(new Object[]{"Holub", "Allen", 1});
            people.insert(new Object[]{"Flintstone", "Wilma", 2});
            people.insert(new Object[]{"Flintstone", "Fred", 2});

            address.insert(new Object[]{1, "123 MyStreet", "Berkeley", "CA", 99999});
            address.insert(new Object[]{2, "123 Quarry Ln.", "Bedrock", "XX", 12345});
            address.insert(new Object[]{3, "Bogus", "Bad", "XX", 12345});

            database = new Database(new File("."));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("XMLExporter 테스트 -> StringWriter를 통해 export한 XML 코드가 잘 나오는지 확인")
    public void xmlExporterTest() throws IOException {
        String expected = "<?xml version=\"1.0\"?>\n" +
                "<address>\n" +
                "\t<row>\n" +
                "\t\t<addrId>1</addrId>\n" +
                "\t\t<street>123 MyStreet</street>\n" +
                "\t\t<city>Berkeley</city>\n" +
                "\t\t<state>CA</state>\n" +
                "\t\t<zip>99999</zip>\n" +
                "\t</row>\n" +
                "\t<row>\n" +
                "\t\t<addrId>2</addrId>\n" +
                "\t\t<street>123 Quarry Ln.</street>\n" +
                "\t\t<city>Bedrock</city>\n" +
                "\t\t<state>XX</state>\n" +
                "\t\t<zip>12345</zip>\n" +
                "\t</row>\n" +
                "\t<row>\n" +
                "\t\t<addrId>3</addrId>\n" +
                "\t\t<street>Bogus</street>\n" +
                "\t\t<city>Bad</city>\n" +
                "\t\t<state>XX</state>\n" +
                "\t\t<zip>12345</zip>\n" +
                "\t</row>\n" +
                "</address>";

        address.export(new XMLExporter(writer));

        System.out.println("expected:\n\n" + expected + "\n\n");
        System.out.println("actual:\n\n" + writer.toString());

        assertEquals(expected, writer.toString());
    }
}
