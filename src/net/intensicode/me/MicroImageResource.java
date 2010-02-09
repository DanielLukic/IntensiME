package net.intensicode.me;

import net.intensicode.core.DirectGraphics;
import net.intensicode.core.ImageResource;

import javax.microedition.lcdui.Image;

public final class MicroImageResource implements ImageResource
    {
    public static final String NO_RESOURCE_PATH = "";

    public final String resourcePath;

    public final Image image;

    public MicroImageResource( final Image aImage )
        {
        this( NO_RESOURCE_PATH, aImage );
        }

    public MicroImageResource( final String aResourcePath, final Image aImage )
        {
        resourcePath = aResourcePath;
        image = aImage;
        }

    // From ImageResource

    public final int getWidth()
        {
        return image.getWidth();
        }

    public final int getHeight()
        {
        return image.getHeight();
        }

    public final DirectGraphics getGraphics()
        {
        if ( myMicroCanvasGraphics == null ) myMicroCanvasGraphics = new MicroCanvasGraphics( image );
        return myMicroCanvasGraphics;
        }

    public final void getRGB( final int[] aBuffer, final int aOffsetX, final int aScanlineSize, final int aX, final int aY, final int aWidth, final int aHeight )
        {
        image.getRGB( aBuffer, aOffsetX, aScanlineSize, aX, aY, aWidth, aHeight );
        }

    public final void purge()
        {
        // We cannot do anything here.. :/
        }

    private MicroCanvasGraphics myMicroCanvasGraphics;
    }
