package com.music;

import com.music.entity.Music;
import com.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("")
    public ResponseEntity<?> getCurrentMusicList() {
        List<Music> musicList = musicService.getCurrentMusicList();
        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularMusicList() {
        List<Music> musicList = musicService.getPopularMusicList();
        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }

    @GetMapping("{type}")
    public ResponseEntity<?> searchMusic(@PathVariable("type") String type, @RequestParam("q") String q) {
        List<Music> musicList = musicService.searchMusic(type, q);
        return new ResponseEntity<>(musicList, HttpStatus.OK);
    }
}
