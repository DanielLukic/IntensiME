package net.intensicode.me;

import net.intensicode.core.FontResource;

import javax.microedition.lcdui.Font;

public final class MicroFontResource implements FontResource
    {
    final Font font;

    public MicroFontResource( final Font aFont )
        {
        font = aFont;
        }

    // From FontResource

    public final int substringWidth( final String aString, final int aOffset, final int aLength )
        {
        return font.substringWidth( aString, aOffset, aLength );
        }

    public final int charWidth( final char aCharCode )
        {
        return font.charWidth( aCharCode );
        }

    public final int getHeight()
        {
        return font.getHeight();
        }
    }
