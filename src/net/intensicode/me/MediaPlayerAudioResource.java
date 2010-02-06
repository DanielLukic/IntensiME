package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.util.Log;

import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;

public final class MediaPlayerAudioResource implements AudioResource, AudioResourceEx
    {
    public MediaPlayerAudioResource( final Player aPlayer )
        {
        myPlayer = aPlayer;
        myEnabledFlag = true;
        myVolumeControl = getVolumeControl();
        setVolume( 50 );
        }

    public final void setLoopForever()
        {
        myPlayer.setLoopCount( -1 );
        }

    // From AudioResourceEx

    public final void enable()
        {
        myEnabledFlag = true;
        if ( myPlayingFlag ) resume();
        }

    public final void disable()
        {
        pause();
        myEnabledFlag = false;
        }

    // From AudioResource

    public final void setVolume( final int aVolumeInPercent )
        {
        myVolumeControl.setLevel( aVolumeInPercent );
        }

    public final void mute()
        {
        myVolumeControl.setMute( true );
        }

    public final void unmute()
        {
        myVolumeControl.setMute( false );
        }

    public final void start()
        {
        try
            {
            stop();
            if ( myEnabledFlag ) myPlayer.start();
            myPlayingFlag = true;
            }
        catch ( final MediaException e )
            {
            Log.error( e );
            }
        }

    public final void stop()
        {
        try
            {
            if ( myPlayingFlag ) myPlayer.stop();
            myPlayer.setMediaTime( 0 );
            myPlayingFlag = false;
            }
        catch ( final MediaException e )
            {
            Log.error( e );
            }
        }

    public final void pause()
        {
        try
            {
            if ( myPlayingFlag ) myPlayer.stop();
            myPlayingFlag = true;
            }
        catch ( final MediaException e )
            {
            Log.error( e );
            }
        }

    public final void resume()
        {
        try
            {
            if ( myEnabledFlag ) myPlayer.start();
            myPlayingFlag = false;
            }
        catch ( final MediaException e )
            {
            Log.error( e );
            }
        }

    // Implementation

    private VolumeControl getVolumeControl()
        {
        final Control[] controls = myPlayer.getControls();
        for ( int idx = 0; idx < controls.length; idx++ )
            {
            final Control control = controls[ idx ];
            if ( control instanceof VolumeControl ) return (VolumeControl) control;
            }
        return new NullVolumeControl();
        }


    private boolean myEnabledFlag;

    private boolean myPlayingFlag;

    private final Player myPlayer;

    private final VolumeControl myVolumeControl;
    }
