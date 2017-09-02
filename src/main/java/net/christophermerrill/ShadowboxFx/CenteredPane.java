package net.christophermerrill.ShadowboxFx;

import javafx.scene.*;
import javafx.scene.layout.*;

/**
 * @author Christopher L Merrill (see LICENSE.txt for license details)
 */
@SuppressWarnings("WeakerAccess")  // public API
public class CenteredPane extends Pane
    {
    public CenteredPane()
        {
        }

    @SuppressWarnings("unused")  // public API
    public CenteredPane(Node child)
        {
        super(child);
        }

    @Override
    protected double computeMinWidth(double height)
        {
        return getManagedChildren().get(0).minWidth(getHeight());
        }

    @Override
    protected double computeMinHeight(double width)
        {
        return getManagedChildren().get(0).minHeight(getWidth());
        }

    @Override
    protected double computePrefWidth(double height)
        {
        return getManagedChildren().get(0).prefWidth(getHeight());
        }

    @Override
    protected double computePrefHeight(double width)
        {
        return getManagedChildren().get(0).prefHeight(getWidth());
        }

    @Override
    protected void layoutChildren()
        {
        Node child = getManagedChildren().get(0);
        double width = Math.min(child.prefWidth(getHeight()), getWidth());
        double height = Math.min(child.prefHeight(getWidth()), getHeight());
        double x;
        double y;
        if (width == getWidth())
            x = 0;
        else
            x = (getWidth() - width) / 2;
        if (height == getHeight())
            y = 0;
        else
            y = (getHeight() - height) / 2;
        child.relocate(x,y);
        child.resize(width, height);
        }
    }
