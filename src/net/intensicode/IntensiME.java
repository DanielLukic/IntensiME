package net.intensicode;

import net.intensicode.core.GameSystem;
import net.intensicode.me.*;
import net.intensicode.util.Log;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public abstract class IntensiME extends MIDlet implements PlatformContext, SystemContext
    {
    protected IntensiME()
        {
        }

    protected void initGameSystem()
        {
        if ( isGameSystemCreated() ) return;

        createGameViewAndGameSystem();

        myHelper = new IntensiGameHelper( myGameSystem );
        myHelper.initGameSystemFromConfigurationFile();
        }

    // From PlatformContext

    public final long compatibleTimeInMillis()
        {
        return System.currentTimeMillis();
        }

    public final void openWebBrowser( final String aURL )
        {
        try
            {
            platformRequest( aURL );
            pauseApp();
            }
        catch ( final ConnectionNotFoundException e )
            {
            Log.error( aURL, e );
            }
        }

    public final void sendEmail( final EmailData aEmailData )
        {
        // TODO: How to do this?
        }

    public final String getPlatformSpecString()
        {
        // TODO: Extend this with system properties?
        return "J2ME";
        }

    public final String getGraphicsSpecString()
        {
        return "J2ME";
        }

    public final String getExtendedExceptionData( final Throwable aException )
        {
        return aException.toString();
        }

    // From SystemContext

    public final GameSystem system()
        {
        return myGameSystem;
        }

    public final void fillEmailData( final EmailData aEmailData )
        {
        }

    public final ConfigurationElementsTree getPlatformValues()
        {
        return ConfigurationElementsTree.EMPTY;
        }

    public final ConfigurationElementsTree getSystemValues()
        {
        return myGameSystem.getSystemValues();
        }

    public ConfigurationElementsTree getApplicationValues()
        {
        return ConfigurationElementsTree.EMPTY;
        }

    public final void loadConfigurableValues()
        {
        myHelper.loadConfiguration( getPlatformValues() );
        myHelper.loadConfiguration( getSystemValues() );
        myHelper.loadConfiguration( getApplicationValues() );
        }

    public final void saveConfigurableValues()
        {
        myHelper.saveConfiguration( getPlatformValues() );
        myHelper.saveConfiguration( getSystemValues() );
        myHelper.saveConfiguration( getApplicationValues() );
        }

    public void onFramesDropped()
        {
        // Default implementation does nothing..
        }

    public void onInfoTriggered()
        {
        // Default implementation does nothing..
        }

    public void onDebugTriggered()
        {
        myHelper.toggleDebugScreen();
        }

    public void onCheatTriggered()
        {
        myHelper.toggleCheatScreen();
        }

    public void onPauseApplication()
        {
        // Default implementation does nothing..
        }

    public void onDestroyApplication()
        {
        // Default implementation does nothing..
        }

    public final void triggerConfigurationMenu()
        {
        myHelper.triggerConfigurationMenu();
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
        myGameSystem.destroy();

        setDisplay( null );
        notifyDestroyed();

        //#if RUNME
        System.exit( 0 );
        //#endif
        }

    // Implementation

    private boolean isGameSystemCreated()
        {
        return myGameSystem != null;
        }

    private synchronized void createGameViewAndGameSystem()
        {
        final MicroGameSystem system = new MicroGameSystem( this, this );
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

        //#if SENSORS
        final MicroSensorsManager sensors = new MicroSensorsManager();
        //#endif

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
        //#if TRACKBALL
        system.trackball = new MicroTrackballHandler();
        //#endif
        //#if SENSORS
        system.sensors = sensors;
        //#endif
        system.engine = engine;
        system.screen = view;
        //#ifdef TOUCH
        system.touch = touch;
        //#endif
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

    private IntensiGameHelper myHelper;
    }
