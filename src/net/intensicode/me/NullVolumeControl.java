package net.intensicode.me;

import javax.microedition.media.control.VolumeControl;

public final class NullVolumeControl implements VolumeControl
    {
    public final void setMute( final boolean aMuted )
        {
        myMutedFlag = aMuted;
        }

    public final boolean isMuted()
        {
        return myMutedFlag;
        }

    public final int setLevel( final int aVolumeInPercent )
        {
        myVolumeInPercent = aVolumeInPercent;
        return aVolumeInPercent;
        }

    public final int getLevel()
        {
        return myVolumeInPercent;
        }

    private boolean myMutedFlag;

    private int myVolumeInPercent;
    }
