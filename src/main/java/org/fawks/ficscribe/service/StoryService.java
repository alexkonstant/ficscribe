package org.fawks.ficscribe.service;

import org.fawks.ficscribe.domain.Story;
import org.fawks.ficscribe.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:StoryService
 * @Description:
 * @Author : Alexander Konstantinov
 * @Create: 20.07.21 16:20
 */

@Service
public class StoryService {
    private final StoryRepository storyRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public List<Story> getStories() {
        return storyRepository.findAll();
    }

    public Story saveStory(Story story) {
        return storyRepository.save(story);
    }

    public Story getStory(Story story) {
        return storyRepository.getById(story.getId());
    }

    public void delete(Story story) {
        storyRepository.delete(story);
    }
}
