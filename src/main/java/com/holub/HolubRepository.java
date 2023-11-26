package com.holub;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.text.ParseFailure;

import java.io.File;
import java.io.IOException;

public class HolubRepository {

    Database database;

    public HolubRepository() {
        try {
            database = new Database(new File("src/main/java/com/holub/database/Dbase"));
        } catch (IOException e) {
            throw new RuntimeException("DB CONNECTION ERROR");
        }
    }

    /**
     * Table을 가져오는 메소드
     * */
    public Table getTable(String query){
        try {
            return database.execute(query);
        } catch (IOException | ParseFailure e) {
            throw new RuntimeException(e);
        }
    }
}
