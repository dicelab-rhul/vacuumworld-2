package uk.ac.rhul.cs.dice.vacuumworld.mvc.view.buttons;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface OnClick {
    public void onClick(Clickable arg, MouseEvent e);
}