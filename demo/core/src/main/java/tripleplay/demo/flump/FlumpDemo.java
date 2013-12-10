//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2013, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.demo.flump;

import playn.core.util.Callback;
import playn.core.util.Clock;
import static playn.core.PlayN.*;

import tripleplay.flump.*;
import tripleplay.ui.Group;
import tripleplay.ui.layout.AbsoluteLayout;

import tripleplay.demo.DemoScreen;

public class FlumpDemo extends DemoScreen
{
    @Override protected String name () {
        return "Flump";
    }

    @Override protected String title () {
        return "Flump animation";
    }

    @Override protected Group createIface () {
        final Group root = new Group(new AbsoluteLayout());

        JsonLoader.loadLibrary("flump", new Callback<Library>() {
            public void onSuccess (Library lib) {
                _movie = lib.createMovie("walk");
                _movie.layer().setTranslation(graphics().width()/2, 300);
                root.layer.add(_movie.layer());
            }
            public void onFailure (Throwable cause) { throw new IllegalStateException(cause); }
        });

        return root;
    }

    @Override public void paint (Clock clock) {
        super.paint(clock);
        if (_movie != null) {
            _movie.paint(clock);
        }
    }

    protected Movie _movie;
}
