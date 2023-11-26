package com.example.demo;

import com.example.demo.holub.database.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HTMLExporterTest {

    Table people = TableFactory.create("name", new String[] { "FIRST", "LAST", "ADDRESS_ID" });
    Table address = TableFactory.create("address", new String[] { "ADDRESS_ID", "STREET", "CITY", "STATE", "ZIP" });
    StringWriter writer = new StringWriter();
    IteratorConverter iteratorConverter = new IteratorConverter();
    Database database;

    @BeforeAll
    @DisplayName("테스트를 위한 더미데이터 세팅")
    private void setDummyData() {
        try{
            people.insert(new Object[]{"기태", "이", 1});
            people.insert(new Object[]{"예빈", "이", 1});
            people.insert(new Object[]{"제원", "신", 0});

            address.insert(new Object[]{0, "상도로", "동작구", "서울시", "72335"});
            address.insert(new Object[]{1, "성남대로", "분당구", "경기 성남시", "05094"});

            database = new Database(new File("."));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("HTMLExporter 테스트 -> StringWriter를 통해 export한 HTML 코드가 잘 나오는지 확인")
    public void htmlExporterTest() throws IOException {
        String expected = "<html>\n" +
                "<body>\n" +
                "\t<table>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th>ADDRESS_ID</th>\n" +
                "\t\t\t<th>STREET</th>\n" +
                "\t\t\t<th>CITY</th>\n" +
                "\t\t\t<th>STATE</th>\n" +
                "\t\t\t<th>ZIP</th>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>0</td>\n" +
                "\t\t\t<td>상도로</td>\n" +
                "\t\t\t<td>동작구</td>\n" +
                "\t\t\t<td>서울시</td>\n" +
                "\t\t\t<td>72335</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>1</td>\n" +
                "\t\t\t<td>성남대로</td>\n" +
                "\t\t\t<td>분당구</td>\n" +
                "\t\t\t<td>경기 성남시</td>\n" +
                "\t\t\t<td>05094</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                "</body>\n" +
                "</html>";

        address.export(new HTMLExporter(writer, iteratorConverter));
        assertEquals(expected, writer.toString());
    }
}
