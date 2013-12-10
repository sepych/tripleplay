//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2013, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.demo;

import react.UnitSlot;

import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;

import tripleplay.demo.anim.*;
import tripleplay.demo.entity.*;
import tripleplay.demo.flump.*;
import tripleplay.demo.game.*;
import tripleplay.demo.particle.*;
import tripleplay.demo.ui.*;
import tripleplay.demo.util.*;

/**
 * Displays a top-level menu of our various demo screens.
 */
public class DemoMenuScreen extends UIScreen
{
    public DemoMenuScreen (ScreenStack stack) {
        _stack = stack;
        _rlabels = new String[] {
            "tripleplay.ui", "", "", "",
            "tripleplay.anim",
            "tripleplay.game",
            "tripleplay.entity",
            "tripleplay.particle",
            "tripleplay.flump",
            "tripleplay.util"
        };
        _screens = new DemoScreen[] {
            // tripleplay.ui
            new MiscDemo(), new LabelDemo(), new SliderDemo(),
            new BackgroundDemo(), new LayoutDemo(), new BorderLayoutDemo(),
            new FlowLayoutDemo(), new SelectorDemo(), new MenuDemo(),
            new ScrollerDemo(), new TabsDemo(), new TableLayoutDemo(),
            // tripleplay.anim
            new FramesDemo(), new AnimDemo(), new FlickerDemo(),
            // tripleplay.game
            new ScreensDemo(stack), null, null,
            // tripleplay.entity
            new AsteroidsDemo(), null, null,
            // tripleplay.particle
            new FountainDemo(), new FireworksDemo(), null,
            // tripleplay.flump
            new FlumpDemo(), null, null,
            // tripleplay.util
            new ColorsDemo(), null, null,
        };
    }

    @Override public void wasShown () {
        super.wasShown();
        _root = iface.createRoot(AxisLayout.vertical().gap(15), SimpleStyles.newSheet(), layer);
        _root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).
                                            inset(5, 10)));
        _root.setSize(width(), height());

        _root.add(new Label("Triple Play Demos").addStyles(Style.FONT.is(DemoScreen.TITLE_FONT)));

        Group grid = new Group(new TableLayout(
                                   TableLayout.COL.alignRight(),
                                   TableLayout.COL.stretch(),
                                   TableLayout.COL.stretch(),
                                   TableLayout.COL.stretch()).gaps(10, 10));
        _root.add(grid);

        int shown = 0, toShow = (TripleDemo.mainArgs.length == 0) ? -1 :
            Integer.parseInt(TripleDemo.mainArgs[0]);

        for (int ii = 0; ii < _screens.length; ii++) {
            if (ii%3 == 0) grid.add(new Label(_rlabels[ii/3]));
            final DemoScreen screen = _screens[ii];
            if (screen == null) {
                grid.add(new Shim(1, 1));
            } else {
                grid.add(new Button(screen.name()).onClick(new UnitSlot() { public void onEmit () {
                    _stack.push(screen);
                    screen.back.clicked().connect(new UnitSlot() { public void onEmit () {
                        _stack.remove(screen);
                    }});
                }}));
                // push this screen immediately if it was specified on the command line
                if (shown++ == toShow) _stack.push(screen, ScreenStack.NOOP);
            }
        }
    }

    @Override public void wasHidden () {
        super.wasHidden();
        iface.destroyRoot(_root);
    }

    protected Button screen (String title, final ScreenFactory factory) {
        return new Button(title).onClick(new UnitSlot() { public void onEmit () {
            final DemoScreen screen = factory.apply();
            _stack.push(screen);
            screen.back.clicked().connect(new UnitSlot() { public void onEmit () {
                _stack.remove(screen);
            }});
        }});
    }

    protected interface ScreenFactory {
        DemoScreen apply ();
    }

    protected final String[] _rlabels;
    protected final DemoScreen[] _screens;

    protected final ScreenStack _stack;
    protected Root _root;
}
