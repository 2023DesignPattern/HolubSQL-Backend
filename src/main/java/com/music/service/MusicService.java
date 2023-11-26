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

    public List<Music> getMusicList() {
        return musicRepository.getMusicList();
    }
}
