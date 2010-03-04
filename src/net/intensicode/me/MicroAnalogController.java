package net.intensicode.me;

import net.intensicode.core.AnalogController;
import net.intensicode.util.*;

public final class MicroAnalogController extends AnalogController
    {
    protected final void mapOrientationToMovement()
        {
        }

    protected final void mapAccelerationToMovement()
        {
        }

    protected final boolean hasNewData()
        {
        return myNewDataFlag;
        }

    protected final synchronized void updateDeltaValues()
        {
        xDeltaFixed += FixedMath.mul( xSensitivityFixed, FixedMath.toFixed( myAccumulatedDelta.x ) );
        yDeltaFixed += FixedMath.mul( ySensitivityFixed, FixedMath.toFixed( myAccumulatedDelta.y ) );
        myAccumulatedDelta.x = myAccumulatedDelta.y = 0;
        myNewDataFlag = false;
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
        final int xDelta = aX - myLastRawPosition.x;
        final int yDelta = aY - myLastRawPosition.y;
        accumulateDelta( xDelta, yDelta );
        updateLastRawPosition( aX, aY );
        }

    private synchronized void accumulateDelta( final int aDeltaX, final int aDeltaY )
        {
        myAccumulatedDelta.x += aDeltaX;
        myAccumulatedDelta.y += aDeltaY;
        myNewDataFlag = true;
        }

    private void updateLastRawPosition( final int aX, final int aY )
        {
        myLastRawPosition.x = aX;
        myLastRawPosition.y = aY;
        myMoreThanOneEventFlag = true;
        }


    private boolean myNewDataFlag;

    private boolean myMoreThanOneEventFlag;

    private final Position myLastRawPosition = new Position();

    private final Position myAccumulatedDelta = new Position();
    }
