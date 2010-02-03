package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.graphics.*;
import net.intensicode.util.Log;

import javax.microedition.lcdui.*;
import java.io.*;

public final class MicroResourcesManager extends ResourcesManager
    {
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

    public final ImageResource createImageResource( final int aWidth, final int aHeight )
        {
        final Image image = Image.createImage( aWidth, aHeight );
        return new MicroImageResource( image );
        }

    public final ImageResource loadImageResource( final String aResourcePath ) throws IOException
        {
        //#if DEBUG
        Log.debug( "Loading image {}", aResourcePath );
        //#endif
        final Image image = Image.createImage( openStream( aResourcePath ) );
        return new MicroImageResource( aResourcePath, image );
        }

    public final InputStream openStream( final String aResourcePath )
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
