/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2020 Gerrit Grunwald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vk.control.tile.tools;

import com.vk.control.tile.Tile;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


@DefaultProperty("children")
public class LowerRightRegion extends Region {
    private static final double  PREFERRED_WIDTH  = 52;
    private static final double  PREFERRED_HEIGHT = 52;
    private static final double  MINIMUM_WIDTH    = 1;
    private static final double  MINIMUM_HEIGHT   = 1;
    private static final double  MAXIMUM_WIDTH    = 1024;
    private static final double  MAXIMUM_HEIGHT   = 1024;
    private              double  size;
    private              double  width;
    private              double  height;
    private Path path;
    private Path icon;
    private Color backgroundColor;
    private Color foregroundColor;
    private              boolean roundedCorner;
    private Tooltip tooltip;


    // ******************** Constructors **************************************
    public LowerRightRegion() {
        backgroundColor = Tile.GRAY;
        foregroundColor = Tile.BACKGROUND;
        roundedCorner   = true;
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
            Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        path = new Path();
        path.setStroke(Color.TRANSPARENT);

        icon = new Path();
        icon.setStroke(Color.TRANSPARENT);

        tooltip = new Tooltip("");

        getChildren().setAll(path, icon);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override
    protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override
    protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override
    protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override
    protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override
    protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override
    protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }

    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(final Color COLOR) {
        backgroundColor = COLOR;
        redraw();
    }

    public Color getForegroundColor() { return foregroundColor; }
    public void setForegroundColor(final Color COLOR) {
        foregroundColor = COLOR;
        redraw();
    }

    public boolean isRoundedCorner() { return roundedCorner; }
    public void setRoundedCorner(final boolean ROUNDED) {
        roundedCorner = ROUNDED;
        redraw();
    }

    public String getToolTipText() { return tooltip.getText(); }
    public void setTooltipText(final String TEXT) {
        if (null == TEXT || TEXT.isEmpty()) {
            Tooltip.uninstall(path, tooltip);
        } else {
            tooltip.setText(TEXT);
            Tooltip.install(path, tooltip);
        }
    }

    @Override
    public ObservableList<Node> getChildren() { return super.getChildren(); }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            path.getElements().clear();
            if (isRoundedCorner()) {
                path.getElements().add(new MoveTo(size, 0));
                path.getElements().add(new LineTo(size, size * 0.81));
                path.getElements().add(new CubicCurveTo(size, size * 0.915, size * 0.915, size, size * 0.81, size));
                path.getElements().add(new LineTo(0, size));
                path.getElements().add(new ClosePath());
            } else {
                path.getElements().add(new MoveTo(size, 0));
                path.getElements().add(new LineTo(size, size));
                path.getElements().add(new LineTo(0, size));
                path.getElements().add(new ClosePath());
            }

            icon.getElements().clear();
            icon.getElements().add(new MoveTo(size * 0.688, size * 0.494));
            icon.getElements().add(new LineTo(size * 0.746, size * 0.494));
            icon.getElements().add(new LineTo(size * 0.746, size * 0.768));
            icon.getElements().add(new LineTo(size * 0.812, size * 0.704));
            icon.getElements().add(new LineTo(size * 0.852, size * 0.744));
            icon.getElements().add(new LineTo(size * 0.718, size * 0.878));
            icon.getElements().add(new LineTo(size * 0.582, size * 0.744));
            icon.getElements().add(new LineTo(size * 0.624, size * 0.704));
            icon.getElements().add(new LineTo(size * 0.688, size * 0.768));
            icon.getElements().add(new ClosePath());

            redraw();
        }
    }

    private void redraw() {
        path.setFill(getBackgroundColor());
        icon.setFill(getForegroundColor());
    }
}
