package com.holub.database;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * 데이터베이스 테이블 정보를 HTML로 Export 해주는 class
 * */

@RequiredArgsConstructor
public class HTMLExporter implements Table.Exporter {

    private final Writer writer;
    private final IteratorConverter iteratorConverter;

    public void startTable() throws IOException {
        writer.write("<html>\n");
        writer.write("<body>\n");
    }
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        writer.write("\t<table>\n");
        writer.write("\t\t<tr>\n");

        Iterable<String> iterableColumnNames = iteratorConverter.convertToIterable(columnNames);

        for (String columnName : iterableColumnNames) {
            writer.write(String.format("\t\t\t<th>%s</th>\n", columnName));
        }
        writer.write("\t\t</tr>\n");
    }

    public void storeRow(Iterator data) throws IOException {
        writer.write("\t\t<tr>\n");

        Iterable<String> iterableDatas = iteratorConverter.convertToIterable(data);

        for(String iterableData : iterableDatas) {
            writer.write(String.format("\t\t\t<td>%s</td>\n", iterableData));
        }

        writer.write("\t\t</tr>\n");
    }
    public void endTable() throws IOException {
        writer.write("\t</table>\n");
        writer.write("</body>\n");
        writer.write("</html>");
    }
}