package org.fawks.ficscribe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fawks.ficscribe.domain.User;
import org.fawks.ficscribe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/2/21
 */

@Controller
public class LoginController {

    private static final Logger log = LogManager.getLogger(LoginController.class);
    private final UserService userService;
    private final ControllerUtil controllerUtil;

    @Autowired
    public LoginController(OAuth2AuthorizedClientService authorizedClientService, UserService userService, ControllerUtil controllerUtil) {
        this.userService = userService;
        this.controllerUtil = controllerUtil;
    }

    @GetMapping("/oauth_login")
    public String getLoginPage() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/oauth/create")
    public String createUser(Model model) throws JsonProcessingException {
        Map userAttributes = controllerUtil.extractGoogleUser();
        userAttributes.forEach((key, attr) -> log.info(key + " " + attr));

        User newUser = User.builder()
                .id(String.valueOf(userAttributes.get("sub")))
                .name(String.valueOf(userAttributes.get("name")))
                .email(String.valueOf(userAttributes.get("email")))
                .userpic(String.valueOf(userAttributes.get("picture")))
                .locale(String.valueOf(userAttributes.get("locale")))
                .gender(String.valueOf(userAttributes.get("gender")))
                .build();
        userService.saveUser(newUser);
        model = controllerUtil.getLoginInfo(model);
        return "redirect:/";

    }


}
