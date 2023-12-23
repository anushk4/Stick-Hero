package com.example.sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.io.IOException;

public abstract class SideMenu extends Menu {

    public abstract void returntohome(MouseEvent event , Line cancel) throws IOException;

}
