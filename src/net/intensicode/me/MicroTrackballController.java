//#condition TRACKBALL

package net.intensicode.me;

import net.intensicode.core.TrackballControllerBase;
import net.intensicode.util.*;

public final class MicroTrackballController extends TrackballControllerBase
    {
    // Protected API

    protected long systemSpecificNowInMillis()
        {
        return System.currentTimeMillis();
        }

    // Package Interface

    final void pointerPressed( final int aX, final int aY )
        {
        updateLastRawPosition( aX, aY );
        }

    final void pointerReleased( final int aX, final int aY )
        {
        updateLastRawPosition( aX, aY );
        }

    final void pointerDragged( final int aX, final int aY )
        {
        if ( myMoreThanOneEventFlag )
            {
            processRawPositionUpdate( aX, aY );
            }
        else
            {
            updateLastRawPosition( aX, aY );
            }
        }

    // Implementation

    private void processRawPositionUpdate( final int aX, final int aY )
        {
        final int deltaX = aX - myLastRawPosition.x;
        final int deltaY = aY - myLastRawPosition.y;
        if ( deltaX != 0 || deltaY != 0 )
            {
            onSystemUpdateEvent( deltaX, deltaY );
            updateLastRawPosition( aX, aY );
            }
        }

    private void updateLastRawPosition( final int aX, final int aY )
        {
        myLastRawPosition.x = aX;
        myLastRawPosition.y = aY;
        myMoreThanOneEventFlag = true;
        }


    private boolean myMoreThanOneEventFlag;

    private final Position myLastRawPosition = new Position();
    }
