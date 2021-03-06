package com.spring.start.controller;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j
@Getter
public class BaseController {

    protected static final String SLASH = "/";
    protected static final String PAGES = "pages";
    protected static final String ADMIN = "admin";

    @Autowired
    private Environment env;

    public void addMessage(RedirectAttributes redirectAttributes, MessageType type, String messageCode){
        addMessage(redirectAttributes, type, messageCode, null);
    }

    public void addMessage(RedirectAttributes redirectAttributes, MessageType type, String messageCode, List<String> additionalMessage) {
        String additional = additionalMessage != null ? "- " + String.join("; ", additionalMessage) : "";
        redirectAttributes.addFlashAttribute("info", env.getProperty(messageCode) + additional);
        redirectAttributes.addFlashAttribute("alertType", type.toString());
    }

}
