package org.fawks.ficscribe.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fawks.ficscribe.domain.Story;
import org.fawks.ficscribe.domain.User;
import org.fawks.ficscribe.service.StoryService;
import org.fawks.ficscribe.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final UserService userService;
    private final ControllerUtil controllerUtil;
    private static final Logger log = LogManager.getLogger(StoryController.class);

    @Autowired
    public StoryController(StoryService storyService, UserService userService, ControllerUtil controllerUtil) {
        this.storyService = storyService;
        this.userService = userService;
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
    public String addStory(@RequestBody String url, Authentication auth) throws IOException {
        Document doc = Jsoup.connect(url + "?view_adult=true").get();
        Element root = doc.select("body > #outer > #inner > #main > #workskin > div.preface").first();
        if (root == null) {
            root = doc.select("body > #outer > #inner > #main > div.work > #workskin > div.preface").first();
        }
        User user = userService.getUser(auth.getName());
        log.info("USER ==> " + user.getName());
        Story story = new Story();
        story.setUrl(url);
        story.setAuthor(root.select("h3.byline").text());
        story.setName(root.select("h2").text());
        story.setSummary(root.select("div.summary > blockquote > p").text());
        story.setUser(user);
        storyService.saveStory(story);
        return "story-list";
    }

    @PutMapping
    public String updateStory(@RequestBody Story story) {
        storyService.saveStory(story);
        return "story-list";
    }

    @DeleteMapping("/{id}")
    public String deleteStory(@PathVariable("id") Story story) {
        storyService.delete(story);
        return "story-list";
    }
}
