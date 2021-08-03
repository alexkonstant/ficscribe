package org.fawks.ficscribe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.fawks.ficscribe.domain.User;
import org.fawks.ficscribe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @ClassName: ControllerUtil
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/2/21
 */
@Component
public class ControllerUtil {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserService userService;

    @Autowired
    public ControllerUtil(OAuth2AuthorizedClientService authorizedClientService, UserService userService) {
        this.authorizedClientService = authorizedClientService;
        this.userService = userService;
    }

    public Model getLoginInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equals("anonymousUser")) {
            User currentUser = userService.getUser(authentication.getName());
            model.addAttribute("exists", true);
            model.addAttribute("id", currentUser.getId());
            model.addAttribute("name", currentUser.getName());
            model.addAttribute("email", currentUser.getEmail());
            model.addAttribute("userpic", currentUser.getUserpic());

        } else {
            model.addAttribute("exists", false);
        }

        return model;
    }


    public Map extractGoogleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();

        if (StringUtils.hasLength(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> response = restTemplate
                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes;
            userAttributes = response.getBody();

            return userAttributes;
        }
        return null;
    }
}
