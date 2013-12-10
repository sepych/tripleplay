//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2013, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.demo.ui;

import playn.core.Font;
import playn.core.PlayN;

import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Label;
import tripleplay.ui.Shim;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

import tripleplay.demo.DemoScreen;

/**
 * Various label tests.
 */
public class LabelDemo extends DemoScreen
{
    @Override protected String name () {
        return "Labels";
    }
    @Override protected String title () {
        return "UI: Labels";
    }

    @Override protected Group createIface () {
        Icon smiley = Icons.image(PlayN.assets().getImage("images/smiley.png"));
        Styles wrapped = Styles.make(Style.TEXT_WRAP.is(true));
        Styles greenBg = Styles.make(Style.BACKGROUND.is(Background.solid(0xFF99CC66).inset(5)));
        Styles smallUnderlined = Styles.make(
            Style.FONT.is(PlayN.graphics().createFont("Times New Roman", Font.Style.PLAIN, 20)),
            Style.HALIGN.center, Style.UNDERLINE.is(true));
        Styles bigLabel = Styles.make(
            Style.FONT.is(PlayN.graphics().createFont("Times New Roman", Font.Style.PLAIN, 32)),
            Style.HALIGN.center);
        return new Group(AxisLayout.vertical()).add(
            // display some wrapped text
            new Shim(15, 15),
            new Label("Wrapped text").addStyles(Style.HALIGN.center),
            new Group(AxisLayout.horizontal(), greenBg.add(Style.VALIGN.top)).add(
                AxisLayout.stretch(new Label(TEXT1, smiley).
                                   addStyles(wrapped.add(Style.ICON_GAP.is(5)))),
                AxisLayout.stretch(new Label(TEXT2).addStyles(wrapped)),
                AxisLayout.stretch(new Label(TEXT3).addStyles(wrapped))),
            new Shim(15, 15),
            new Label("Styled text").addStyles(Style.HALIGN.center),
            new Group(AxisLayout.horizontal().gap(10)).add(
                new Label("Plain").addStyles(bigLabel),
                new Label("Pixel Outline").addStyles(
                                       bigLabel.add(Style.TEXT_EFFECT.pixelOutline).
                                       add(Style.COLOR.is(Colors.WHITE)).
                                       add(Style.HIGHLIGHT.is(Colors.GRAY))),
                new Label("Vector Outline").addStyles(
                                       bigLabel.add(Style.TEXT_EFFECT.vectorOutline,
                                                    Style.OUTLINE_WIDTH.is(2f))),
                new Label("Shadow").addStyles(
                                       bigLabel.add(Style.TEXT_EFFECT.shadow))),
            new Label("Underlining").addStyles(Style.HALIGN.center),
            new Group(AxisLayout.horizontal().gap(10)).add(
                new Label("Plain").addStyles(smallUnderlined),
                new Label("gjpqy").addStyles(smallUnderlined),
                new Label("Pixel Outline").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.pixelOutline)),
                new Label("gjpqy").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.pixelOutline))),
            new Group(AxisLayout.horizontal().gap(10)).add(
                new Label("Vector Outline").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.vectorOutline,
                                                           Style.OUTLINE_WIDTH.is(2f))),
                new Label("gjpqy").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.vectorOutline,
                                                           Style.OUTLINE_WIDTH.is(2f))),
                new Label("Shadow").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.shadow)),
                new Label("gjpqy").addStyles(
                                       smallUnderlined.add(Style.TEXT_EFFECT.shadow))));
    }

    protected static final String TEXT1 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
    protected static final String TEXT2 = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.";
    protected static final String TEXT3 = "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness.";
}
