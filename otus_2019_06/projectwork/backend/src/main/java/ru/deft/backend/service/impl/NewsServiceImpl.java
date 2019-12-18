package ru.deft.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.backend.dto.NewsDto;
import ru.deft.backend.model.News;
import ru.deft.backend.repository.NewsRepository;
import ru.deft.backend.service.NewsService;

import java.util.List;
import java.util.UUID;

/*
 * Created by sgolitsyn on 12/17/19
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;


    @Override
    public UUID createNews(NewsDto dto) {
        return null;
    }

    @Override
    public UUID updateNews(NewsDto dto) {
        return null;
    }

    @Override
    public News getNewsById(UUID id) {
        return null;
    }

    @Override
    public Iterable<News> getAllNews() {
        return newsRepository.findAll();
    }
}
