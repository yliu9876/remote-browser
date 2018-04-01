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


    @MessageMapping("/click")
    public void mouseClick() throws AWTException {
        localIOService.mouseClick();
    }

    @MessageMapping("/clickOn/{x}/{y}")
    public void mouseClickOn(@DestinationVariable("x") int x, @DestinationVariable("y") int y) throws AWTException {
        localIOService.mouseClickOn(x, y);
    }

    @MessageMapping("/press/esc")
    public void pressEscKey() throws AWTException {
        localIOService.pressEscKey();
    }



}
