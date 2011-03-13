package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.util.*;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class MicroCanvasGraphics extends DirectGraphics
    {
    MicroGameView view;


    public MicroCanvasGraphics()
        {
        }

    public MicroCanvasGraphics( final Image aImage )
        {
        myTargetGC = aImage.getGraphics();
        width = aImage.getWidth();
        height = aImage.getHeight();
        }

    // From DirectGraphics

    public int getColorRGB24()
        {
        return myTargetGC.getColor() & 0x00FFFFFF;
        }

    public int getColorARGB32()
        {
        return myTargetGC.getColor();
        }

    public final void setColorRGB24( final int aRGB24 )
        {
        myTargetGC.setColor( 0xFF000000 | aRGB24 );
        }

    public final void setColorARGB32( final int aARGB32 )
        {
        myTargetGC.setColor( aARGB32 );
        }

    public final void setFont( final FontResource aFont )
        {
        //#if DEBUG
        Assert.isTrue( "only MicroFontResource supported for now", aFont instanceof MicroFontResource );
        //#endif
        final MicroFontResource fontResource = (MicroFontResource) aFont;
        myTargetGC.setFont( fontResource.font );
        }

    public final void clearRGB24( final int aRGB24 )
        {
        setColorRGB24( aRGB24 );
        myTargetGC.fillRect( 0, 0, width, height );
        }

    public final void clearARGB32( final int aARGB32 )
        {
        setColorARGB32( aARGB32 );
        myTargetGC.fillRect( 0, 0, width, height );
        }

    public final void drawLine( final int aX1, final int aY1, final int aX2, final int aY2 )
        {
        myTargetGC.drawLine( aX1, aY1, aX2, aY2 );
        }

    public final void drawRect( final int aX, final int aY, final int aWidth, final int aHeight )
        {
        myTargetGC.drawRect( aX, aY, aWidth, aHeight );
        }

    public final void drawRGB( final int[] aARGB32, final int aOffsetX, final int aScanlineSize, final int aX, final int aY, final int aWidth, final int aHeight, final boolean aUseAlpha )
        {
        myTargetGC.drawRGB( aARGB32, aOffsetX, aScanlineSize, aX, aY, aWidth, aHeight, aUseAlpha );
        }

    public final void fillRect( final int aX, final int aY, final int aWidth, final int aHeight )
        {
        myTargetGC.fillRect( aX, aY, aWidth, aHeight );
        }

    public final void fillTriangle( final int aX1, final int aY1, final int aX2, final int aY2, final int aX3, final int aY3 )
        {
        myTargetGC.fillTriangle( aX1, aY1, aX2, aY2, aX3, aY3 );
        }

    public final void blendImage( final ImageResource aImage, final int aX, final int aY, final int aAlpha256 )
        {
        //#if DEBUG
        if ( aImage == NullImageResource.NULL ) throw new IllegalArgumentException();
        //#else
        //# if ( aImage == NullImageResource.NULL ) return;
        //#endif

        //#if DEBUG
        Assert.isTrue( "only MicroImageResource supported for now", aImage instanceof MicroImageResource );
        Assert.between( "alpha value 256", 0, 255, aAlpha256 );
        //#endif
        if ( aAlpha256 == 0 )
            {
            // Nothing to do..
            }
        else if ( aAlpha256 == FULLY_OPAQUE )
            {
            drawImage( aImage, aX, aY );
            }
        else
            {
            final MicroImageResource imageResource = (MicroImageResource) aImage;
            myImageBlender.blend( imageResource, aAlpha256 );
            myTargetGC.drawRGB( myImageBlender.buffer, 0, myImageBlender.width, aX, aY, myImageBlender.width, myImageBlender.height, true );
            }
        }

    public final void blendImage( final ImageResource aImage, final Rectangle aSourceRect, final int aX, final int aY, final int aAlpha256 )
        {
        //#if DEBUG
        if ( aImage == NullImageResource.NULL ) throw new IllegalArgumentException();
        //#else
        //# if ( aImage == NullImageResource.NULL ) return;
        //#endif

        //#if DEBUG
        Assert.isTrue( "only MicroImageResource supported for now", aImage instanceof MicroImageResource );
        Assert.between( "alpha value 256", 0, 255, aAlpha256 );
        //#endif
        if ( aAlpha256 == 0 )
            {
            // Nothing to do..
            }
        else if ( aAlpha256 == FULLY_OPAQUE )
            {
            drawImage( aImage, aSourceRect, aX, aY );
            }
        else
            {
            final MicroImageResource imageResource = (MicroImageResource) aImage;
            myImageBlender.blend( imageResource, aSourceRect, aAlpha256 );
            myTargetGC.drawRGB( myImageBlender.buffer, 0, myImageBlender.width, aX, aY, myImageBlender.width, myImageBlender.height, true );
            }
        }

    public final void drawImage( final ImageResource aImage, final int aX, final int aY )
        {
        //#if DEBUG
        if ( aImage == NullImageResource.NULL ) throw new IllegalArgumentException();
        //#else
        //# if ( aImage == NullImageResource.NULL ) return;
        //#endif

        //#if DEBUG
        Assert.isTrue( "only MicroImageResource supported for now", aImage instanceof MicroImageResource );
        //#endif
        final MicroImageResource imageResource = (MicroImageResource) aImage;
        myTargetGC.drawImage( imageResource.image, aX, aY, ALIGN_TOP_LEFT );
        }

    public final void drawImage( final ImageResource aImage, final int aX, final int aY, final int aAlignment )
        {
        //#if DEBUG
        if ( aImage == NullImageResource.NULL ) throw new IllegalArgumentException();
        //#else
        //# if ( aImage == NullImageResource.NULL ) return;
        //#endif

        //#if DEBUG
        Assert.isTrue( "only MicroImageResource supported for now", aImage instanceof MicroImageResource );
        //#endif
        final MicroImageResource imageResource = (MicroImageResource) aImage;
        final Position aligned = getAlignedPosition( aX, aY, aImage.getWidth(), aImage.getHeight(), aAlignment );
        myTargetGC.drawImage( imageResource.image, aligned.x, aligned.y, ALIGN_TOP_LEFT );
        }

    public final void drawImage( final ImageResource aImage, final Rectangle aSourceRect, final int aTargetX, final int aTargetY )
        {
        //#if DEBUG
        if ( aImage == NullImageResource.NULL ) throw new IllegalArgumentException();
        //#else
        //# if ( aImage == NullImageResource.NULL ) return;
        //#endif

        //#if DEBUG
        Assert.isTrue( "only MicroImageResource supported for now", aImage instanceof MicroImageResource );
        //#endif
        final MicroImageResource imageResource = (MicroImageResource) aImage;
        storeCurrentClipRect();
        myTargetGC.clipRect( aTargetX, aTargetY, aSourceRect.width, aSourceRect.height );
        myTargetGC.drawImage( imageResource.image, aTargetX - aSourceRect.x, aTargetY - aSourceRect.y, ALIGN_TOP_LEFT );
        restorePreviousClipRect();
        }

    public final void drawSubstring( final String aText, final int aStart, final int aEnd, final int aX, final int aY )
        {
        myTargetGC.drawSubstring( aText, aStart, aEnd - aStart, aX, aY, ALIGN_TOP_LEFT );
        }

    public final void drawChar( final char aCharCode, final int aX, final int aY )
        {
        myTargetGC.drawChar( aCharCode, aX, aY, ALIGN_TOP_LEFT );
        }

    public final void initialize() throws Exception
        {
        }

    public void beginFrame()
        {
        updateGraphicsSize();
        myTargetGC = myBufferGC = createNewGraphics();
        }

    private void updateGraphicsSize()
        {
        width = view.width();
        height = view.height();
        }

    private Graphics createNewGraphics()
        {
        final int realWidth = view.getWidth();
        final int realHeight = view.getHeight();
        final int width = view.width();
        final int height = view.height();

        myBufferGC = view.accessGraphics();
        clearGC( myBufferGC, realWidth, realHeight );

        final int xOffset = ( realWidth - width ) / 2;
        final int yOffset = ( realHeight - height ) / 2;
        resetGC( myBufferGC, xOffset, yOffset, width, height );

        return myBufferGC;
        }

    private static void clearGC( final Graphics aGC, final int aWidth, final int aHeight )
        {
        aGC.translate( -aGC.getTranslateX(), -aGC.getTranslateY() );
        aGC.setColor( 0 );
        aGC.fillRect( 0, 0, aWidth, aHeight );
        }

    private static void resetGC( final Graphics aGC, final int aOffsetX, final int aOffsetY, final int aWidth, final int aHeight )
        {
        aGC.translate( -aGC.getTranslateX(), -aGC.getTranslateY() );
        aGC.translate( aOffsetX, aOffsetY );
        aGC.setClip( 0, 0, aWidth, aHeight );
        }

    public void endFrame()
        {
        if ( view.isShown() ) view.flushGraphics();
        myTargetGC = myBufferGC = null;
        }

    public final void cleanup()
        {
        }

    // Implementation

    private void storeCurrentClipRect()
        {
        myStoredClip.x = myTargetGC.getClipX();
        myStoredClip.y = myTargetGC.getClipY();
        myStoredClip.width = myTargetGC.getClipWidth();
        myStoredClip.height = myTargetGC.getClipHeight();
        }

    private void restorePreviousClipRect()
        {
        myTargetGC.setClip( myStoredClip.x, myStoredClip.y, myStoredClip.width, myStoredClip.height );
        }


    private int width;

    private int height;

    private Graphics myTargetGC;

    private Graphics myBufferGC;

    private final Rectangle myStoredClip = new Rectangle();

    private final ImageBlender myImageBlender = new ImageBlender();

    private static final int ALIGN_TOP_LEFT = Graphics.TOP | Graphics.LEFT;
    }
