package org.fawks.ficscribe.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fawks.ficscribe.domain.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

/**
 * @ClassName: MainController
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/2/21
 */

@CrossOrigin
@Controller
public class MainController {
    private final ControllerUtil controllerUtil;
    private static final Logger log = LogManager.getLogger(MainController.class);

    @Autowired
    public MainController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @GetMapping("/")
    public String index(Authentication auth, Model model) throws IOException {
        log.info("USER ==> " + auth.getName());
        model = controllerUtil.getLoginInfo(model);
        return "index";
    }


}
