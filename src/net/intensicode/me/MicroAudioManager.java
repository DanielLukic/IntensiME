package net.intensicode.me;

import net.intensicode.ReleaseProperties;
import net.intensicode.core.*;
import net.intensicode.util.*;

import javax.microedition.media.*;
import java.io.*;

public final class MicroAudioManager implements AudioManager
    {
    public MicroAudioManager( final ResourcesManager aResourcesManager )
        {
        myResourcesManager = aResourcesManager;
        }

    // From AudioManager

    public final boolean supportsMusicPlusSound()
        {
        return true;
        }

    public final boolean supportsMultiSound()
        {
        return false;
        }

    public final int numberOfSoundChannels()
        {
        return 1;
        }

    public final void enableMusicAndSound()
        {
        myMusicEnabled = true;
        mySoundEnabled = true;
        }

    public final void enableMusicOnly()
        {
        myMusicEnabled = true;
        mySoundEnabled = false;
        }

    public final void enableSoundOnly()
        {
        myMusicEnabled = false;
        mySoundEnabled = true;
        }

    public final void disable()
        {
        myMusicEnabled = false;
        mySoundEnabled = false;
        }

    public final void setMasterMute( final boolean aMutedFlag )
        {
        myMutedFlag = aMutedFlag;
        }

    public final void setMasterVolume( final int aVolumeInPercent )
        {
        myMasterVolume = aVolumeInPercent;
        }

    public final void setMasterMusicMute( final boolean aMutedFlag )
        {
        myMasterMusicMutedFlag = aMutedFlag;
        }

    public final void setMasterSoundMute( final boolean aMutedFlag )
        {
        myMasterSoundMutedFlag = aMutedFlag;
        }

    public final void setMasterMusicVolume( final int aVolumeInPercent )
        {
        myMasterMusicVolume = aVolumeInPercent;
        }

    public final void setMasterSoundVolume( final int aVolumeInPercent )
        {
        myMasterSoundVolume = aVolumeInPercent;
        }

    public final MusicResource loadMusic( final String aMusicName ) throws IOException
        {
        try
            {
            final String resourceFilePath = makeResourceFilePath( ReleaseProperties.MUSIC_FOLDER, aMusicName, ReleaseProperties.MUSIC_FORMAT_SUFFIX );
            final Player player = createAndPrepareMediaPlayer( resourceFilePath, ReleaseProperties.MUSIC_FORMAT_MIME_TYPE );
            final MediaPlayerAudioResource resource = new MediaPlayerAudioResource( player );
            registerMusicResource( resource );
            return resource;
            }
        catch ( final MediaException e )
            {
            Log.error( e );
            return new SilentAudioResource();
            }
        }

    public final SoundResource loadSound( final String aSoundName ) throws IOException
        {
        try
            {
            final String resourceFilePath = makeResourceFilePath( ReleaseProperties.SOUND_FOLDER, aSoundName, ReleaseProperties.SOUND_FORMAT_SUFFIX );
            final Player player = createAndPrepareMediaPlayer( resourceFilePath, ReleaseProperties.SOUND_FORMAT_MIME_TYPE );
            final MediaPlayerAudioResource resource = new MediaPlayerAudioResource( player );
            registerSoundResource( resource );
            return resource;
            }
        catch ( final MediaException e )
            {
            if ( !isExpectedException( e ) ) Log.error( e );
            return new SilentAudioResource();
            }
        }

    // Implementation

    private boolean isExpectedException( final MediaException aException )
        {
        final String message = aException.getMessage();
        return message.equalsIgnoreCase( ERROR_NO_SOUND_DEVICE_AVAILABLE );
        }

    private String makeResourceFilePath( final String aResourceFolder, final String aMusicName, final String aFileNameSuffix )
        {
        final StringBuffer builder = new StringBuffer();
        builder.append( aResourceFolder );
        builder.append( "/" );
        builder.append( aMusicName );
        builder.append( aFileNameSuffix );
        return builder.toString();
        }

    private Player createAndPrepareMediaPlayer( final String aMusicResourceFilePath, final String aMimeType ) throws IOException, MediaException
        {
        final InputStream stream = myResourcesManager.openStreamChecked( aMusicResourceFilePath );
        final Player player = Manager.createPlayer( stream, aMimeType );
        player.realize();
        player.prefetch();
        return player;
        }

    private void registerMusicResource( final MusicResource aMusicResource )
        {
        myMusicResources.add( aMusicResource );
        }

    private void registerSoundResource( final MusicResource aMusicResource )
        {
        mySoundResources.add( aMusicResource );
        }


    private boolean myMusicEnabled;

    private boolean mySoundEnabled;

    private boolean myMutedFlag;

    private int myMasterVolume;

    private boolean myMasterMusicMutedFlag;

    private boolean myMasterSoundMutedFlag;

    private int myMasterMusicVolume;

    private int myMasterSoundVolume;

    private final ResourcesManager myResourcesManager;

    private final DynamicArray myMusicResources = new DynamicArray();

    private final DynamicArray mySoundResources = new DynamicArray();
    }
