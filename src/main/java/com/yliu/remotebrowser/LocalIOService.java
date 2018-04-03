package com.yliu.remotebrowser;


import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@Service
public class LocalIOService {

    public void mouseMove(String direction, int diff) throws AWTException {
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


    public void mouseClick(String keyPressed) throws AWTException {
        Robot bot = new Robot();
        switch (keyPressed.toLowerCase()) {
            case "left" :
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                break;
            case "right" :
                bot.mousePress(InputEvent.BUTTON3_MASK);
                bot.mouseRelease(InputEvent.BUTTON3_MASK);
                break;
        }


    }

    public void mouseClickOn(int x, int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void pressEscKey() throws AWTException {
        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_ESCAPE);
    }

    public void enterText(String text) throws AWTException {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        Robot robot = new Robot();
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1)
            robot.keyPress(KeyEvent.VK_META);
        else
            robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        if (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1)
            robot.keyRelease(KeyEvent.VK_META);
        else
            robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void mouseScroll(String direction) throws AWTException {
        System.out.println(direction);
        Robot bot = new Robot();
        switch (direction.toLowerCase()) {
            case "up":
                bot.mouseWheel(-10);
                break;
            case "down":
                bot.mouseWheel(10);
                break;
        }
    }

}
