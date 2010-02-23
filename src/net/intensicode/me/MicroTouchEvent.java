//#condition TOUCH

package net.intensicode.me;

import net.intensicode.core.TouchEvent;

public class MicroTouchEvent implements TouchEvent
    {
    public static final int ACTION_PRESS = 0;

    public static final int ACTION_SWIPE = 1;

    public static final int ACTION_RELEASE = 2;

    public int action;

    public int x;

    public int y;

    public final void reset( final int aAction, final int aX, final int aY )
        {
        action = aAction;
        x = aX;
        y = aY;
        }

    // From TouchEvent

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
