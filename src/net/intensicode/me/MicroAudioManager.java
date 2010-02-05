package net.intensicode.me;

import net.intensicode.ReleaseProperties;
import net.intensicode.core.*;

import javax.microedition.media.*;
import java.io.*;

public final class MicroAudioManager extends AudioManager
    {
    public MicroAudioManager( final ResourcesManager aResourcesManager )
        {
        myResourcesManager = aResourcesManager;
        }

    // From AudioManager

    protected AudioResourceEx loadMusicUnsafe( final String aMusicName ) throws Exception
        {
        final String resourceFilePath = makeResourceFilePath( ReleaseProperties.MUSIC_FOLDER, aMusicName, ReleaseProperties.MUSIC_FORMAT_SUFFIX );
        final Player player = createAndPrepareMediaPlayer( resourceFilePath, ReleaseProperties.MUSIC_FORMAT_MIME_TYPE );
        return new MediaPlayerAudioResource( player );
        }

    protected AudioResourceEx loadSoundUnsafe( final String aSoundName ) throws Exception
        {
        final String resourceFilePath = makeResourceFilePath( ReleaseProperties.SOUND_FOLDER, aSoundName, ReleaseProperties.SOUND_FORMAT_SUFFIX );
        final Player player = createAndPrepareMediaPlayer( resourceFilePath, ReleaseProperties.SOUND_FORMAT_MIME_TYPE );
        return new MediaPlayerAudioResource( player );
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


    private final ResourcesManager myResourcesManager;
    }
