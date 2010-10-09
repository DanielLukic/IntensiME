package net.intensicode.me;

import net.intensicode.SystemContext;
import net.intensicode.core.DirectScreen;
import net.intensicode.util.*;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Graphics;


public final class MicroGameView extends GameCanvas implements DirectScreen
    {
    public MicroCanvasGraphics graphics;

    //#if TOUCH
    public MicroTouchHandler touch;
    //#endif

    public MicroGameSystem system;

    public MicroKeysHandler keys;

    public SystemContext context;


    public MicroGameView()
        {
        super( false );
        setFullScreenMode( true );
        }

    public final Graphics accessGraphics()
        {
        return getGraphics();
        }

    // From DirectScreen

    public final int width()
        {
        if ( myTargetSize.width == 0 ) return getWidth();
        return myTargetSize.width;
        }

    public final int height()
        {
        if ( myTargetSize.width == 0 ) return getHeight();
        return myTargetSize.height;
        }

    public final int getTargetWidth()
        {
        return myTargetSize.width;
        }

    public final int getTargetHeight()
        {
        return myTargetSize.height;
        }

    public void setTargetSize( final int aWidth, final int aHeight )
        {
        myTargetSize.setTo( aWidth, aHeight );
        }

    // Internal API

    public final int getNativeWidth()
        {
        return getWidth();
        }

    public final int getNativeHeight()
        {
        return getHeight();
        }

    public final void beginFrame()
        {
        system.graphics.beginFrame();
        }

    public final void endFrame()
        {
        system.graphics.endFrame();
        }

    public final void initialize()
        {
        Log.info( "Target screen size: {}x{}", width(), height() );
        Log.info( "Device screen size: {}x{}", getWidth(), getHeight() );
        graphics.view = this;
        }

    public final void cleanup()
        {
        }

    public final Position toTarget( final int aNativeX, final int aNativeY )
        {
        myTransformedPosition.x = aNativeX * width() / getWidth();
        myTransformedPosition.y = aNativeY * height() / getHeight();
        return myTransformedPosition;
        }

    // From Canvas

    protected final void hideNotify()
        {
        system.stop();
        super.hideNotify();
        }

    protected final void showNotify()
        {
        super.showNotify();
        system.start();
        }

    protected final void keyPressed( final int aCode )
        {
        final int gameAction = getGameAction( aCode );
        keys.keyPressed( aCode, gameAction );
        }

    //#if NO_KEY_REPEAT

    protected final void keyRepeated( final int i )
        {
        }

    //#endif

    protected final void keyReleased( final int aCode )
        {
        final int gameAction = getGameAction( aCode );
        keys.keyReleased( aCode, gameAction );
        }

    protected final void pointerPressed( final int aX, final int aY )
        {
        //#if TOUCH
        touch.pointerPressed( aX, aY );
        //#endif
        }

    protected final void pointerReleased( final int aX, final int aY )
        {
        //#if TOUCH
        touch.pointerReleased( aX, aY );
        //#endif
        }

    protected final void pointerDragged( final int aX, final int aY )
        {
        //#if TOUCH
        touch.pointerDragged( aX, aY );
        //#endif
        }


    private final Size myTargetSize = new Size();

    private final Position myTransformedPosition = new Position();
    }
