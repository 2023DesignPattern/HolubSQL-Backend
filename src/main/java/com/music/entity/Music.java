package com.music.entity;

import lombok.Getter;

@Getter
public class Music {
    private Long id;
    private String title;
    private String singer;
    private Long hits;
    private String type;    //TODO : 추후 ENUM으로 빼면 좋을 것 같음!
}
