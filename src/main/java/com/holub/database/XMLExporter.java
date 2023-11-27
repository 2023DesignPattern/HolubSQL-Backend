package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 *   데이터베이스 테이블 정보를 XML로 Export 해주는 class
 * */

public class XMLExporter implements Table.Exporter {

    private final Writer writer;
    private String tableName;
    private String columnName;
    private ArrayList<String> columnNames = new ArrayList<>();

    public XMLExporter(Writer writer){
        this.writer = writer;
    }

    @Override
    public void startTable() throws IOException {

    }

    @Override
    public void storeMetadata(String tableName, int width, int height, Iterator columnNames) throws IOException {
        this.tableName = Objects.requireNonNullElse(tableName, "anonymous");

        writer.write( "<?xml version=\"1.0\"?>\n");
        writer.write("<" + tableName + ">" + "\n");

        while(columnNames.hasNext()){
            columnName = columnNames.next().toString();
            this.columnNames.add(columnName);
        }
    }

    @Override
    public void storeRow(Iterator data) throws IOException {

        int i = 0;
        writer.write("\t<row>\n");
        while(data.hasNext()) {
            Object datum = data.next();
            Object columnName = columnNames.get(i);
            writer.write(String.format("\t\t<%s>%s</%s>\n", columnName, datum, columnName));
            i++;
        }
        writer.write("\t</row>\n");
    }

    @Override
    public void endTable() throws IOException {
        writer.write("</" + this.tableName + ">");
    }
}
