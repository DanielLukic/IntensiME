package net.intensicode.me;

import javax.microedition.lcdui.Image;

final class CachedBlendedImage
    {
    private static final int INVALID_ALPHA_VALUE = -1;

    int alpha256 = INVALID_ALPHA_VALUE;

    final Image sourceImage;

    final int width;

    final int height;

    final int numberOfPixels;

    final int[] buffer;

    Image blendedImage;

    CachedBlendedImage( final Image aSourceImage )
        {
        sourceImage = aSourceImage;
        width = aSourceImage.getWidth();
        height = aSourceImage.getHeight();
        numberOfPixels = width * height;
        buffer = new int[numberOfPixels];
        }

    void applyAlpha( final int aAlpha256 )
        {
        if ( blendedImage != null && alpha256 == aAlpha256 ) return;

        sourceImage.getRGB( buffer, 0, width, 0, 0, width, height );
        for ( int idx = 0; idx < numberOfPixels; idx++ )
            {
            final int argb = buffer[ idx ];
            final int alpha = ( argb >> 24 ) & 0xFF;
            final int blendedAlpha = alpha * aAlpha256 * 255;
            buffer[ idx ] = blendedAlpha << 24 | ( argb & 0x00FFFFFF );
            }
        blendedImage = Image.createRGBImage( buffer, width, height, true );
        alpha256 = aAlpha256;
        }
    }
