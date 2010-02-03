package net.intensicode;

import net.intensicode.core.*;
import net.intensicode.me.*;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public abstract class IntensiGameME extends MIDlet implements SystemContext
    {
    protected IntensiGameME()
        {
        }

    protected void initGameSystem()
        {
        if ( isGameSystemCreated() ) return;

        createGameViewAndGameSystem();

        IntensiGameHelper.initGameSystemFromConfigurationFile( myGameSystem );
        }

    // From SystemContext

    public final GameSystem getGameSystem()
        {
        return myGameSystem;
        }

    public void onApplicationShouldPause( final GameSystem aGameSystem )
        {
        // Default implementation does nothing..
        }

    public void onFramesDropped( final GameSystem aGameSystem )
        {
        myGameSystem.showError( "cannot keep minimum frame rate - system overloaded?", null );
        }

    public final void terminateApplication()
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
        //#ifdef TOUCH_SUPPORTED
        final MicroTouchHandler touch = new MicroTouchHandler( system );
        //#endif
        final MicroKeysHandler keys = new MicroKeysHandler( view );
        final MicroAudioManager audio = new MicroAudioManager( resources );
        final MicroStorageManager storage = new MicroStorageManager();

        view.keys = keys;
        //#if TOUCH_SUPPORTED
        view.touch = touch;
        //#endif
        view.context = this;
        view.engine = engine;
        view.system = system;
        view.graphics = graphics;

        system.resources = resources;
        system.graphics = graphics;
        system.storage = storage;
        system.engine = engine;
        //#ifdef TOUCH_SUPPORTED
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
