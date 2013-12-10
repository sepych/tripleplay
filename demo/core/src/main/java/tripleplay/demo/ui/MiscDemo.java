//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2013, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.demo.ui;

import playn.core.Image;
import playn.core.PlayN;

import pythagoras.f.MathUtil;

import react.Function;
import react.IntValue;
import react.UnitSlot;

import tripleplay.ui.*;
import tripleplay.ui.layout.*;
import tripleplay.util.Colors;

import tripleplay.demo.DemoScreen;

/**
 * Displays various UI stuff.
 */
public class MiscDemo extends DemoScreen
{
    @Override protected String name () {
        return "General";
    }
    @Override protected String title () {
        return "UI: General";
    }
    @Override protected Group createIface () {
        Icon smiley = Icons.image(PlayN.assets().getImage("images/smiley.png"));
        final Image squares = PlayN.assets().getImage("images/squares.png");
        final CapturedRoot capRoot = iface.addRoot(
            new CapturedRoot(iface, AxisLayout.horizontal(), stylesheet()));

        CheckBox toggle, toggle2;
        Label label2;
        Field editable, disabled;
        Button setField;

        Group iface = new Group(AxisLayout.horizontal().stretchByDefault()).add(
            // left column
            new Group(AxisLayout.vertical()).add(
                // labels, visibility and icon toggling
                new Label("Toggling visibility"),
                new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                    new Group(AxisLayout.vertical()).add(
                        new Group(AxisLayout.horizontal()).add(
                            toggle = new CheckBox(),
                            new Label("Toggle Viz")),
                        new Group(AxisLayout.horizontal()).add(
                            toggle2 = new CheckBox(),
                            new Label("Toggle Icon"))),
                    new Group(AxisLayout.vertical()).add(
                        new Label("Label 1").addStyles(REDBG),
                        label2 = new Label("Label 2"),
                        new Label("Label 3", smiley))),
                new Shim(5, 10),

                // labels with varying icon alignment
                new Label("Icon positioning"),
                new Group(AxisLayout.horizontal().gap(10), GREENBG).add(
                    new Label("Left", tileIcon(squares, 0)).setStyles(Style.ICON_POS.left),
                    new Label("Right", tileIcon(squares, 1)).setStyles(Style.ICON_POS.right),
                    new Label("Above", tileIcon(squares, 2)).setStyles(Style.ICON_POS.above,
                                                                       Style.HALIGN.center),
                    new Label("Below", tileIcon(squares, 3)).setStyles(Style.ICON_POS.below,
                                                                       Style.HALIGN.center)),
                new Shim(5, 10),

                // a captured root's widget
                new Label("Root capture"),
                new Group(AxisLayout.vertical()).addStyles(
                    Style.BACKGROUND.is(Background.solid(Colors.RED).inset(10))).add(
                        capRoot.createWidget())),

            // right column
            new Group(AxisLayout.vertical()).add(
                // buttons, toggle buttons, wirey uppey
                new Label("Buttons"),
                buttonsSection(squares),
                new Shim(5, 10),

                // an editable text field
                new Label("Text editing"),
                editable = new Field("Editable text").setConstraint(Constraints.fixedWidth(150)),
                setField = new Button("Set -> "),
                disabled = new Field("Disabled text").setEnabled(false)));

        capRoot.add(new Label("Captured Root!").addStyles(
            Style.BACKGROUND.is(Background.blank().inset(10)))).pack();

        // add a style animation to the captured root (clicking on cap roots NYI)
        this.iface.animator().repeat(_root.layer).delay(1000).then().action(new Runnable() {
            int cycle;
            @Override public void run () {
                capRoot.addStyles(Style.BACKGROUND.is(cycle++ % 2 == 0 ?
                    Background.solid(Colors.WHITE).alpha(.5f) : Background.blank()));
            }
        });

        toggle.checked.update(true);
        toggle.checked.connect(label2.visibleSlot());
        toggle2.checked.map(new Function<Boolean,Icon>() {
            public Icon apply (Boolean checked) {
                return checked ? tileIcon(squares, 0) : null;
            }
        }).connect(label2.icon.slot());

        final Field source = editable, target = disabled;
        setField.clicked().connect(new UnitSlot() {
            @Override public void onEmit () {
                PlayN.log().info("Setting text to " + source.text.get());
                target.text.update(source.text.get());
            }
        });
        return iface;
    }

    protected Group buttonsSection (Image squares) {
        ToggleButton toggle3 = new ToggleButton("Toggle Enabled");
        Button disabled = new Button("Disabled");
        toggle3.selected().connectNotify(disabled.enabledSlot());
        toggle3.selected().map(new Function<Boolean,String>() {
            public String apply (Boolean selected) { return selected ? "Enabled" : "Disabled"; }
        }).connectNotify(disabled.text.slot());

        class ThrobButton extends Button {
            public ThrobButton (String title) {
                super(title);
            }
            public void throb () {
                root().iface().animator().
                    tweenScale(layer).to(1.2f).in(300.0f).easeIn().then().
                    tweenScale(layer).to(1.0f).in(300.0f).easeOut();
            }
            @Override protected void layout () {
                super.layout();
                float ox = MathUtil.ifloor(_size.width/2), oy = MathUtil.ifloor(_size.height/2);
                layer.setOrigin(ox, oy);
                layer.transform().translate(ox, oy);
            }
        }
        final ThrobButton throbber = new ThrobButton("Throbber");

        final Label pressResult = new Label();
        final IntValue clickCount = new IntValue(0);
        final Box box = new Box();
        return new Group(AxisLayout.vertical().offEqualize()).add(
            new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                toggle3, AxisLayout.stretch(disabled)),
            new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                new LongPressButton("Long Pressable").onLongPress(new UnitSlot() {
                    @Override public void onEmit () { pressResult.text.update("Long pressed"); }
                }).onClick(new UnitSlot() {
                    @Override public void onEmit () { pressResult.text.update("Clicked"); }
                }), AxisLayout.stretch(pressResult)),
            new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                new Label("Image button"),
                new ImageButton(tile(squares, 0), tile(squares, 1)).onClick(new UnitSlot() {
                    @Override public void onEmit () { clickCount.increment(1); }
                }),
                new ValueLabel(clickCount)),
            new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                new Button("Fill Box").onClick(new UnitSlot() {
                    @Override public void onEmit () {
                        box.set(new Label(box.contents() == null ? "Filled" : "Refilled"));
                    }
                }),
                box),
            new Group(AxisLayout.horizontal().gap(15), GREENBG).add(
                throbber.onClick(new UnitSlot() {
                    @Override public void onEmit () {
                        throbber.throb();
                    }
                }))
            );
    }

    protected Image tile (Image image, int index) {
        final float iwidth = 16, iheight = 16;
        return image.subImage(index*iwidth, 0, iwidth, iheight);
    }
    protected Icon tileIcon (Image image, int index) {
        return Icons.image(tile(image, index));
    }

    protected static final Styles GREENBG = Styles.make(
        Style.BACKGROUND.is(Background.solid(0xFF99CC66).inset(5)));
    protected static final Styles REDBG = Styles.make(
        Style.BACKGROUND.is(Background.solid(0xFFCC6666).inset(5)));
}
