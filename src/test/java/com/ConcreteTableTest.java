package com;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConcreteTableTest {

    Database database = new Database(new File("src/main/java/com/holub/database/Dbase"));

    public ConcreteTableTest() throws IOException {
    }

    /**
     *   2개의 table에서 'street' column 선택
     *   select street from address, name where address.addrId = name.addrId
     * */
    @Test
    public void ConcreteTable_테스트1() throws IOException, ParseFailure {
        String query = "select street from address, name where address.addrId = name.addrId";
        Table table = database.execute(query);

        String expected = "<anonymous>\n" +
                "street\t\n" +
                "----------------------------------------\n" +
                "12 MyStreet\t\n" +
                "34 Quarry Ln.\t\n" +
                "34 Quarry Ln.\t\n";

        System.out.println("expected:\n\n" + expected + "\n\n");
        System.out.println("actual:\n\n" + table.toString());

        assertEquals(expected, table.toString());
    }

    /**
     *   2개의 table에서 전체 column 선택
     *   select * from address, name where address.addrId = name.addrId
     * */
    @Test
    public void ConcreteTable_테스트2() throws IOException, ParseFailure {
        String query = "select * from address, name where address.addrId = name.addrId";
        Table table = database.execute(query);

        String expected = "<anonymous>\n" +
                "addrId\tstreet\tcity\tstate\tzip\tfirst\tlast\t\n" +
                "----------------------------------------\n" +
                "0\t12 MyStreet\tBerkeley\tCA\t99998\tAllen\tHolub\t\n" +
                "1\t34 Quarry Ln.\tBedrock\tAZ\t00000\tFred\tFlintstone\t\n" +
                "1\t34 Quarry Ln.\tBedrock\tAZ\t00000\tWilma\tFlintstone\t\n";

        System.out.println("expected:\n\n" + expected + "\n\n");
        System.out.println("actual:\n\n" + table.toString());

        assertEquals(expected, table.toString());
    }
}
