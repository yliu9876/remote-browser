package com.yliu.remotebrowser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@Controller
public class RemoteBrowserController {
    @Autowired
    BrowserService browserService;
    @Autowired
    LocalIOService localIOService;


    @MessageMapping("/browse")
    public void play(String url) {
        System.out.println(url);
        if (url == null || url.isEmpty()) return;
        browserService.browse(HtmlUtils.htmlUnescape(url));
    }

    @MessageMapping("/move/{dir}/{diff}")
    public void mouseMove(@DestinationVariable("dir") String dir, @DestinationVariable("diff") int diff) throws AWTException {
        localIOService.mouseMove(dir, diff);
    }

    @MessageMapping("/swipe/{dx}/{dy}")
    public void mouseMove(@DestinationVariable("dx") int dx, @DestinationVariable("dy") int dy) throws AWTException {
        localIOService.mouseMove(dx, dy);
    }


    @MessageMapping("/click/{keyPressed}")
    public void mouseClick(@DestinationVariable("keyPressed") String keyPressed) throws AWTException {
        localIOService.mouseClick(keyPressed);
    }

    @MessageMapping("/clickOn/{x}/{y}")
    public void mouseClickOn(@DestinationVariable("x") int x, @DestinationVariable("y") int y) throws AWTException {
        localIOService.mouseClickOn(x, y);
    }

    @MessageMapping("/press/esc")
    public void pressEscKey() throws AWTException {
        localIOService.pressEscKey();
    }

    @MessageMapping("/type")
    public void enterText(String text) throws AWTException {
        System.out.println(text);
        localIOService.enterText(HtmlUtils.htmlUnescape(text));
    }

    @MessageMapping("/scroll/{direction}")
    public void mouseScroll(@DestinationVariable("direction") String direction) throws AWTException {
        localIOService.mouseScroll(direction);
    }



}
