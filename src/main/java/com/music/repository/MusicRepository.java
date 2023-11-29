package com.music.repository;

import com.holub.HolubRepository;
import com.holub.database.ConcreteTable;
import com.holub.database.Table;
import com.holub.database.UnmodifiableTable;
import com.music.entity.Music;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MusicRepository {
    HolubRepository holubRepository = new HolubRepository();

    public List<Music> getCurrentMusicList() {
        Table musicTable = holubRepository.getTable("select * from music");
        UnmodifiableTable unmodifiableTable = (UnmodifiableTable) musicTable;
        ConcreteTable concreteTable = (ConcreteTable) (unmodifiableTable.extract());
        List<Object[]> rowSet = new LinkedList<>(concreteTable.getRowSet());
        List<Music> currentMusicList = rowSet.stream()
                .map(rowData -> Music.builder()
                        .id(Long.valueOf(rowData[0].toString()))
                        .title(rowData[1].toString())
                        .singer(rowData[2].toString())
                        .hits(Long.valueOf(rowData[3].toString()))
                        .type(rowData[4].toString())
                        .build())
                .collect(Collectors.toList());

        return  currentMusicList.stream()
                .sorted(Comparator.comparingLong(Music::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<Music> getPopularMusicList() {
        Table musicTable = holubRepository.getTable("select * from music");
        UnmodifiableTable unmodifiableTable = (UnmodifiableTable) musicTable;
        ConcreteTable concreteTable = (ConcreteTable) (unmodifiableTable.extract());
        List<Object[]> rowSet = new LinkedList<>(concreteTable.getRowSet());
        List<Music> currentMusicList = rowSet.stream()
                .map(rowData -> Music.builder()
                        .id(Long.valueOf(rowData[0].toString()))
                        .title(rowData[1].toString())
                        .singer(rowData[2].toString())
                        .hits(Long.valueOf(rowData[3].toString()))
                        .type(rowData[4].toString())
                        .build())
                .collect(Collectors.toList());

        return  currentMusicList.stream()
                .sorted(Comparator.comparingLong(Music::getHits).reversed())
                .collect(Collectors.toList());
    }

    public List<Music> searchMusic(String type, String q) {
        Table musicTable = null;

        if(type.equals("title")) {
            musicTable = holubRepository.getTable("select * from music where title = '" + q + "'");
        }
        else {
            musicTable = holubRepository.getTable("select * from music where id = " + q);
        }

        UnmodifiableTable unmodifiableTable = (UnmodifiableTable) musicTable;
        ConcreteTable concreteTable = (ConcreteTable) (unmodifiableTable.extract());
        List<Object[]> rowSet = new LinkedList<>(concreteTable.getRowSet());
        return rowSet.stream()
                .map(rowData -> Music.builder()
                        .id(Long.valueOf(rowData[0].toString()))
                        .title(rowData[1].toString())
                        .singer(rowData[2].toString())
                        .hits(Long.valueOf(rowData[3].toString()))
                        .type(rowData[4].toString())
                        .build())
                .collect(Collectors.toList());
    }
}
