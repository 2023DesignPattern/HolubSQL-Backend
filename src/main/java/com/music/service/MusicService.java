package com.music.service;

import com.music.entity.Music;
import com.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public List<Music> getCurrentMusicList() {
        return musicRepository.getCurrentMusicList();
    }

    public List<Music> getPopularMusicList() {
        return musicRepository.getPopularMusicList();
    }

    public List<Music> searchMusic(String type, String q) {
        return musicRepository.searchMusic(type, q);
    }
}
