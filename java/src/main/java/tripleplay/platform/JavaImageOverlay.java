//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2013, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.platform;

import java.awt.Graphics;

import javax.swing.JComponent;

import playn.core.Image;
import playn.java.JavaImage;

public class JavaImageOverlay extends JavaNativeOverlay
    implements ImageOverlay
{
    public JavaImageOverlay (Image image) {
        super(new ImageComponent((JavaImage)image));
    }

    public Image image () {
        return ((ImageComponent)component).image;
    }

    public void repaint () {
        component.repaint();
    }

    protected static class ImageComponent extends JComponent
    {
        public final JavaImage image;

        public ImageComponent (JavaImage image) {
            this.image = image;
        }

        @Override public void paint (Graphics g) {
            g.drawImage(image.bufferedImage(), 0, 0, null);
        }
    }
}
