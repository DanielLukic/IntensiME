package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.util.Log;

import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;

public final class MediaPlayerAudioResource implements MusicResource, SoundResource, AudioResource
    {
    public MediaPlayerAudioResource( final Player aPlayer )
        {
        myPlayer = aPlayer;
        myVolumeControl = getVolumeControl();
        setVolume( 50 );
        }

    // From MusicResource

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

    public final void play()
        {
        try
            {
            stop();
            myPlayer.start();
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
            myPlayer.stop();
            myPlayer.setMediaTime( 0 );
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
            myPlayer.stop();
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
            myPlayer.start();
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


    private int myCurrentVolume;

    private final Player myPlayer;

    private final VolumeControl myVolumeControl;
    }
