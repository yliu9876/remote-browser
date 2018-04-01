package com.yliu.remotebrowser;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@Service
public class LocalIOService {

    public void mouseMove(@RequestParam("dir") String direction, @RequestParam("speed") int diff) throws AWTException {
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
    }

    public void mouseMove(@RequestParam("dx") int dx, @RequestParam("dy") int dy) throws AWTException {
        Point point = MouseInfo.getPointerInfo().getLocation();
        Robot bot = new Robot();
        bot.mouseMove((int)point.getX() + dx, (int)point.getY() + dy);
    }


    public void mouseClick() throws AWTException {
        Robot bot = new Robot();
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void mouseClickOn(@RequestParam("x") int x, @RequestParam("y") int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void pressEscKey() throws AWTException {
        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_ESCAPE);
    }

}
