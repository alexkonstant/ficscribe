package org.fawks.ficscribe.controller;

import org.fawks.ficscribe.domain.Story;
import org.fawks.ficscribe.service.StoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @ClassName:StoryController
 * @Description:
 * @Author : Alexander Konstantinov
 * @Create: 20.07.21 16:31
 */

@CrossOrigin
@RestController
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<Story> getStories() {
        return storyService.getStories();
    }

    @GetMapping("/{id}")
    public Story getStory(@PathVariable("id") Story story) {
        return story;
    }

    @PostMapping
    public ResponseEntity addStory(@RequestBody Story story) throws URISyntaxException {
        Story addedStory = storyService.saveStory(story);
        return ResponseEntity.created(new URI("/stories/" + addedStory.getId())).body(addedStory);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStory(@PathVariable("id") Story storyFromDB, @RequestBody Story story) {
        BeanUtils.copyProperties(story, storyFromDB, "id");
        storyService.saveStory(storyFromDB);
        return ResponseEntity.ok(storyFromDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStory(@PathVariable("id") Story story) {
        storyService.delete(story);
        return ResponseEntity.ok().build();
    }
}
