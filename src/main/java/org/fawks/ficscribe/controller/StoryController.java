package org.fawks.ficscribe.controller;

import org.fawks.ficscribe.domain.Story;
import org.fawks.ficscribe.service.StoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Controller
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;
    private final ControllerUtil controllerUtil;

    @Autowired
    public StoryController(StoryService storyService, ControllerUtil controllerUtil) {
        this.storyService = storyService;
        this.controllerUtil = controllerUtil;
    }

    @GetMapping
    public String getStories(Model model) {
        List<Story> stories = storyService.getStories();
        model = controllerUtil.getLoginInfo(model);
        model.addAttribute("stories", stories);
        return "story-list";
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

    @PutMapping
    public String updateStory(@RequestBody Story story) {
        storyService.saveStory(story);
        return "story-list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStory(@PathVariable("id") Story story) {
        storyService.delete(story);
        return ResponseEntity.ok().build();
    }
}
