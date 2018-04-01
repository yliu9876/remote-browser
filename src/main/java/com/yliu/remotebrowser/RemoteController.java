package com.yliu.remotebrowser;


import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@RestController
public class RemoteController {

    static {
        System.setProperty("java.awt.headless", "false");
    }

    @PostMapping("/move")
    public String mouseMove(@RequestParam("dir") String direction, @RequestParam("speed") int diff) throws AWTException {
        Point point = MouseInfo.getPointerInfo().getLocation();
        int dx = 0, dy = 0;
        switch (direction) {
            case "up" :
                dy -= diff;
                break;
            case "down" :
                dy += diff;
                break;
            case "left" :
                dx -= diff;
                break;
            case "right" :
                dx += diff;
                break;
        }
        Robot bot = new Robot();
        bot.mouseMove((int)point.getX() + dx, (int)point.getY() + dy);

        return "OK";
    }

    @PostMapping("/swipe")
    public String mouseMove(@RequestParam("dx") int dx, @RequestParam("dy") int dy) throws AWTException {
        Point point = MouseInfo.getPointerInfo().getLocation();
        Robot bot = new Robot();
        bot.mouseMove((int)point.getX() + dx, (int)point.getY() + dy);
        return "OK";
    }


    @RequestMapping("/click")
    public String mouseClick() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        return "OK";
    }

    @PostMapping("/clickOn")
    public String mouseClickOn(@RequestParam("x") int x, @RequestParam("y") int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        return "OK";
    }

    @RequestMapping("/press/esc")
    public String pressEscKey() throws AWTException {
        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_ESCAPE);
        return "OK";
    }

}
