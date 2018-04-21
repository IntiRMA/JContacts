package org.X1C1B.software.JContacts.gui.event.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveWindowListener extends MouseAdapter
{
    private final JFrame frame;
    private Point mouseCoords;

    public MoveWindowListener(JFrame frame)
    {
        this.frame = frame;
    }

    public void mouseReleased(MouseEvent event)
    {
        mouseCoords = null;
    }

    public void mousePressed(MouseEvent event)
    {
        mouseCoords = event.getPoint();
    }

    public void mouseDragged(MouseEvent event)
    {
        Point currentCoords = event.getLocationOnScreen();
        frame.setLocation(currentCoords.x - mouseCoords.x, currentCoords.y - mouseCoords.y);
    }
}
