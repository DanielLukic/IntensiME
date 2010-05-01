//#condition TOUCH

package net.intensicode.me;

import net.intensicode.touch.TouchEvent;

public class MicroTouchEvent extends TouchEvent
    {
    public static final int ACTION_PRESS = 0;

    public static final int ACTION_SWIPE = 1;

    public static final int ACTION_RELEASE = 2;

    public long timestamp;

    public int action;

    public int x;

    public int y;


    public final void reset( final int aAction, final int aX, final int aY )
        {
        timestamp = System.currentTimeMillis();
        action = aAction;
        x = aX;
        y = aY;
        }

    // From TouchEvent

    public final long timestamp()
        {
        return timestamp;
        }

    public final boolean isPress()
        {
        return action == ACTION_PRESS;
        }

    public final boolean isSwipe()
        {
        return action == ACTION_SWIPE;
        }

    public final boolean isRelease()
        {
        return action == ACTION_RELEASE;
        }

    public final int getX()
        {
        return x;
        }

    public final int getY()
        {
        return y;
        }
    }
