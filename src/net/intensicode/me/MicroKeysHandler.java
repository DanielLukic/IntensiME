package net.intensicode.me;

import net.intensicode.core.KeysHandler;
import net.intensicode.util.Log;

public final class MicroKeysHandler extends KeysHandler
    {
    public MicroKeysHandler( final MicroGameView aView )
        {
        super( new MicroKeysConfiguration( aView ) );
        }

    // Package Interface

    final void keyPressed( final int aKeyCode, final int aGameAction )
        {
        final int keyID = keyByCode( aKeyCode, aGameAction, true );
        if ( keyID != INVALID ) queueSetEvent( keyID );
        }

    final void keyReleased( final int aKeyCode, final int aGameAction )
        {
        final int keyID = keyByCode( aKeyCode, aGameAction, false );
        if ( keyID != INVALID ) queueClearEvent( keyID );
        }

    // Implementation

    private int keyByCode( final int aCode, final int aGameAction, final boolean aPressed )
        {
        lastCode = aCode;
        lastAction = aGameAction;

        if ( aGameAction == leftCode ) return LEFT;
        if ( aGameAction == rightCode ) return RIGHT;
        if ( aGameAction == upCode ) return UP;
        if ( aGameAction == downCode ) return DOWN;
        if ( aGameAction == fireCode ) return FIRE1;
        if ( aGameAction == fireCodeA ) return FIRE1;
        if ( aGameAction == fireCodeB ) return FIRE2;
        if ( aGameAction == fireCodeC ) return FIRE1;
        if ( aGameAction == fireCodeD ) return FIRE2;

        if ( aCode == leftCode ) return LEFT;
        if ( aCode == rightCode ) return RIGHT;
        if ( aCode == upCode ) return UP;
        if ( aCode == downCode ) return DOWN;
        if ( aCode == fireCode ) return FIRE1;
        if ( aCode == fireCodeA ) return FIRE1;
        if ( aCode == fireCodeB ) return FIRE2;
        if ( aCode == fireCodeC ) return FIRE1;
        if ( aCode == fireCodeD ) return FIRE2;
        if ( aCode == starCode ) return FIRE1;
        if ( aCode == poundCode ) return FIRE2;
        if ( aCode == softLeftCode ) return LEFT_SOFT;
        if ( aCode == softRightCode ) return RIGHT_SOFT;
        if ( aCode == softPauseCode ) return PAUSE_KEY;

        // Stupid fall backs for real devices..
        switch ( aCode )
            {
            case -6:
            case -1:
            case -21:
            case 21:
            case -10:
            case -202:
            case 57345:
                return LEFT_SOFT;
            case -7:
            case -4:
            case -22:
            case -20:
            case 22:
            case -11:
            case -203:
            case 57346:
                return RIGHT_SOFT;
            }

        //#if DEBUG
        //# net.intensicode.util.Log.debug( "Unhandled keycode: {}", aCode );
        //# lastInvalidCode = aCode;
        //#endif

        return INVALID;
        }
    }
