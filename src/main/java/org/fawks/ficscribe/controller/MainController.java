package org.fawks.ficscribe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: MainController
 * @Description:
 * @Author: Alexander Konstantinov
 * @Create: 8/2/21
 */
@Controller
public class MainController {
    private final ControllerUtil controllerUtil;

    @Autowired
    public MainController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @GetMapping("/")
    public String index(Model model) {
        model = controllerUtil.getLoginInfo(model);
        return "index";
    }


}
