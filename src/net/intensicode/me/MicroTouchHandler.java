//#condition TOUCH

package net.intensicode.me;

import net.intensicode.core.GameSystem;
import net.intensicode.touch.TouchHandler;

public final class MicroTouchHandler extends TouchHandler
    {
    public MicroTouchHandler( final GameSystem aGameSystem )
        {
        super( aGameSystem );
        }

    // Package Interface

    final void pointerPressed( final int aX, final int aY )
        {
        myTouchEvent.reset( MicroTouchEvent.ACTION_PRESS, aX, aY );
        processTouchEvent( myTouchEvent );
        }

    final void pointerReleased( final int aX, final int aY )
        {
        myTouchEvent.reset( MicroTouchEvent.ACTION_RELEASE, aX, aY );
        processTouchEvent( myTouchEvent );
        }

    final void pointerDragged( final int aX, final int aY )
        {
        myTouchEvent.reset( MicroTouchEvent.ACTION_SWIPE, aX, aY );
        processTouchEvent( myTouchEvent );
        }

    private final MicroTouchEvent myTouchEvent = new MicroTouchEvent();
    }
