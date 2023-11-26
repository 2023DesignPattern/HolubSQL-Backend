package com.holub.database;

import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class IteratorConverter {

    public Iterable<String> convertToIterable(Iterator columnNames) {
        return () -> new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return columnNames.hasNext();
            }

            @Override
            public String next() {
                return String.valueOf(columnNames.next());
            }
        };
    }
}
