package net.intensicode;

import net.intensicode.core.GameSystem;
import net.intensicode.graphics.AsyncRenderQueue;
import net.intensicode.me.*;
import net.intensicode.util.Log;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import java.util.Hashtable;

public abstract class IntensiME extends MIDlet implements PlatformContext, PlatformHooks, SystemContext, CommandListener
    {
    protected IntensiME()
        {
        }

    protected void initGameSystem() throws Exception
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

    public String screenOrientationId()
        {
        // TODO: What to do here? Is a displayable available here?
        return SCREEN_ORIENTATION_PORTRAIT;
        }

    public final void showError( final String aMessage, final Throwable aOptionalThrowable )
        {
        final Alert alert = createAlert( aMessage, aOptionalThrowable );
        alert.setType( AlertType.WARNING );
        alert.addCommand( COMMAND_CONTINUE );
        setAlert( alert );
        }

    public final void showCriticalError( final String aMessage, final Throwable aOptionalThrowable )
        {
        final Alert alert = createAlert( aMessage, aOptionalThrowable );
        alert.setType( AlertType.ERROR );
        setAlert( alert );
        }

    private Alert createAlert( final String aMessage, final Throwable aOptionalThrowable )
        {
        final Alert alert = new Alert( "IntensiGame Error" );
        alert.setCommandListener( this );
        alert.setString( makeFullErrorMessage( aMessage, aOptionalThrowable ) );
        alert.setTimeout( Alert.FOREVER );
        alert.addCommand( COMMAND_EXIT );
        return alert;
        }

    private String makeFullErrorMessage( final String aMessage, final Throwable aOptionalThrowable )
        {
        if ( aOptionalThrowable == null ) return aMessage;
        final StringBuffer buffer = new StringBuffer( aMessage );
        buffer.append( "\n\n\n" );
        buffer.append( getExtendedExceptionData( aOptionalThrowable ) );
        return buffer.toString();
        }

    private static final Command COMMAND_CONTINUE = new Command( "CONTINUE", Command.BACK, 0 );

    private static final Command COMMAND_EXIT = new Command( "EXIT", Command.EXIT, 0 );

    public final void storePreferences( final String aPreferencesId, final String aPropertyKey, final boolean aValue )
        {
        throw new RuntimeException( "nyi" );
        }

    public final void register( final String aComponentName, final String aClassName )
        {
        myRegisteredComponents.put( aComponentName, aClassName );
        }

    private final Hashtable myRegisteredComponents = new Hashtable();

    public final Object component( final String aComponentName )
        {
        throw new RuntimeException( "nyi" );
        }

    // From CommandListener

    public void commandAction( final Command aCommand, final Displayable aDisplayable )
        {
        if ( aCommand == COMMAND_CONTINUE ) setDisplay( myGameView );
        if ( aCommand == COMMAND_EXIT ) terminateApplication();
        }

    // From PlatformHooks

    public void checkForUpdate( final String aUpdateUrl, final int aVersionNumber, final UpdateCallback aCallback )
        {
        //#if UPDATE
        new Updater( myGameSystem ).check(aUpdateUrl, aVersionNumber, aCallback);
        //#endif
        }

    public final void trackPageView( final String aPageId )
        {
        }

    public void trackState( final String aCategory, final String aAction, final String aLabel )
        {
        }

    public void trackException( final String aErrorId, final String aMessage, final Throwable aOptionalThrowable )
        {
        }

    public int getBannerAdHeight()
        {
        return 50;
        }

    public boolean hasBannerAds()
        {
        return false;
        }

    public boolean hasFullscreenAds()
        {
        return false;
        }

    public void hideBannerAd()
        {

        }

    public void positionAdBanner( final int aVerticalPosition )
        {

        }

    public void preloadFullscreenAd()
        {

        }

    public void showBannerAd()
        {

        }

    public final void triggerNewBannerAd()
        {
        }

    public final void triggerNewFullscreenAd()
        {
        }

    // From SystemContext

    public String determineResourcesFolder( final int aWidth, final int aHeight, final String aScreenOrientationId )
        {
        return myHelper.determineResourcesFolder( aWidth, aHeight, aScreenOrientationId );
        }

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

    //#if ORIENTATION_DYNAMIC

    public void onOrientationChanged()
        {
        // Default implementation does nothing..
        }

    //#endif

    public final void triggerConfigurationMenu()
        {
        myHelper.triggerConfigurationMenu();
        }

    public /*final*/ void terminateApplication()
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

    private synchronized void createGameViewAndGameSystem() throws Exception
        {
        final MicroGameSystem system = new MicroGameSystem();
        system.context = this;
        system.hooks = this;
        system.platform = this;

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

        //#if RENDER_ASYNC
        final AsyncRenderQueue renderQueue = new AsyncRenderQueue( 2 );
        system.renderThread = new net.intensicode.graphics.AsyncRenderThread( renderQueue, graphics, system.platform );
        system.graphics = new net.intensicode.graphics.AsyncDirectGraphics( renderQueue );
        //#else
        //# system.graphics = graphics;
        //#endif

        system.resources = resources;
        system.storage = storage;
        system.network = new AsyncNetworkIO(new MicroNetworkIO());
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

        //#if ONLINE
        system.online = new VoidOnlineSystem();
        //#endif

        myGameView = view;
        myGameSystem = system;
        }

    private void setAlert( final Alert aAlert )
        {
        Display.getDisplay( this ).setCurrent( aAlert, myGameView );
        }

    private void setDisplay( final Displayable aDisplayable )
        {
        Display.getDisplay( this ).setCurrent( aDisplayable );
        }


    private MicroGameView myGameView;

    private MicroGameSystem myGameSystem;

    private IntensiGameHelper myHelper;
    }
