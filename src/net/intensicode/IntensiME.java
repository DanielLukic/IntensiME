package net.intensicode;

import net.intensicode.core.*;
import net.intensicode.me.*;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public abstract class IntensiME extends MIDlet implements SystemContext
    {
    protected IntensiME()
        {
        }

    protected void initGameSystem()
        {
        if ( isGameSystemCreated() ) return;

        createGameViewAndGameSystem();

        IntensiGameHelper.initGameSystemFromConfigurationFile( myGameSystem );
        }

    // From SystemContext

    public final GameSystem system()
        {
        return myGameSystem;
        }

    public boolean useOpenglIfPossible()
        {
        return false;
        }

    public void onApplicationShouldPause( final GameSystem aGameSystem )
        {
        // Default implementation does nothing..
        }

    public void onFramesDropped( final GameSystem aGameSystem )
        {
        // Default implementation does nothing..
        }

    public void onDebugTriggered()
        {
        // Default implementation does nothing..
        }

    public void onCheatTriggered()
        {
        // Default implementation does nothing..
        }

    public void terminateApplication()
        {
        destroyApp( true );

        //#if RUNME
        System.exit( 0 );
        //#endif
        }

    // From MIDlet

    public final void startApp() throws MIDletStateChangeException
        {
        try
            {
            initGameSystem();
            setDisplay( myGameView );
            }
        catch ( final Exception e )
            {
            //#if DEBUG
            e.printStackTrace();
            //#endif

            throw new MIDletStateChangeException( e.toString() );
            }
        }

    public final void pauseApp()
        {
        setDisplay( null );
        notifyPaused();
        }

    public final void destroyApp( final boolean unconditional )
        {
        setDisplay( null );
        notifyDestroyed();
        }

    // Implementation

    private boolean isGameSystemCreated()
        {
        return myGameSystem != null;
        }

    private synchronized void createGameViewAndGameSystem()
        {
        final MicroGameSystem system = new MicroGameSystem( this );
        final MicroGameEngine engine = new MicroGameEngine( system );
        final MicroGameView view = new MicroGameView();
        final MicroCanvasGraphics graphics = new MicroCanvasGraphics();
        final MicroResourcesManager resources = new MicroResourcesManager( this.getClass() );
        //#ifdef TOUCH
        final MicroTouchHandler touch = new MicroTouchHandler( system );
        //#endif
        final MicroKeysHandler keys = new MicroKeysHandler( view );
        final MicroAudioManager audio = new MicroAudioManager( resources );
        final MicroStorageManager storage = new MicroStorageManager();

        view.keys = keys;
        //#if TOUCH
        view.touch = touch;
        //#endif
        view.context = this;
        view.system = system;
        view.graphics = graphics;

        system.resources = resources;
        system.graphics = graphics;
        system.storage = storage;
        system.engine = engine;
        //#ifdef TOUCH
        system.touch = touch;
        //#endif
        system.screen = view;
        system.audio = audio;
        system.keys = keys;

        myGameView = view;
        myGameSystem = system;
        }

    private void setDisplay( final MicroGameView aDisplay )
        {
        Display.getDisplay( this ).setCurrent( aDisplay );
        }

    private MicroGameView myGameView;

    private MicroGameSystem myGameSystem;
    }
