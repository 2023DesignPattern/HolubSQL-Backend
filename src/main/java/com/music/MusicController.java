package com.music;

import com.music.entity.Music;
import com.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("")
    //TODO : ResponseEntity 반환형 DTO로 고치기
    public ResponseEntity<?> getMusicList() {
        List<Music> musicList = musicService.getMusicList();
        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }
}
