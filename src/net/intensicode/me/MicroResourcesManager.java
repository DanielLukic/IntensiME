package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.graphics.*;
import net.intensicode.util.Log;

import javax.microedition.lcdui.*;
import java.io.*;

public final class MicroResourcesManager extends ResourcesManager
    {
    public static final int MAX_IMAGE_RESOURCE_SIZE = 512;

    public MicroResourcesManager( final Class aReferenceClass )
        {
        myReferenceClass = aReferenceClass;
        }

    // From ResourcesManager

    public final FontGenerator getSmallDefaultFont()
        {
        final Font font = Font.getFont( Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL );
        final MicroFontResource resource = new MicroFontResource( font );
        return new SystemFontGenerator( resource );
        }

    public final int maxImageResourceSize()
        {
        return MAX_IMAGE_RESOURCE_SIZE;
        }

    public final ImageResource createImageResource( final int aWidth, final int aHeight )
        {
        final Image image = Image.createImage( aWidth, aHeight );
        return new MicroImageResource( image );
        }

    // Protected API

    protected final ImageResource loadImageResourceDo( final String aResourceId, final InputStream aStream ) throws IOException
        {
        try
            {
            //#if DEBUG
            Log.debug( "Loading image {}", aResourceId );
            //#endif
            final Image image = Image.createImage( aStream );
            return new MicroImageResource( aResourceId, image );
            }
        catch ( final IOException e )
            {
            throw new ChainedIOException( "failed loading image " + aResourceId, e );
            }
        }

    protected final InputStream openStreamDo( final String aResourcePath )
        {
        final String absolutePath = makeAbsolutePath( aResourcePath );
        return myReferenceClass.getResourceAsStream( absolutePath );
        }

    // Implementation

    private String makeAbsolutePath( final String aResourcePath )
        {
        if ( aResourcePath.startsWith( "/" ) ) return aResourcePath;
        return "/" + aResourcePath;
        }

    private final Class myReferenceClass;
    }
