package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.io.StorageIO;
import net.intensicode.util.Log;

import javax.microedition.rms.*;
import java.io.*;

public class MicroStorageManager implements StorageManager
    {
    public final boolean hasData( final StorageIO aStorageIO )
        {
        final String name = aStorageIO.getName();

        RecordStore recordStore = null;
        try
            {
            recordStore = RecordStore.openRecordStore( name, false );
            if ( recordStore == null ) return false;

            final byte[] data = recordStore.getRecord( RECORDSTORE_ID );
            if ( data != null && data.length > 0 ) return true;
            }
        catch ( final Throwable t )
            {
            //#if DEBUG
            Log.debug( "Failed checking record store: {}", name );
            //#endif
            }
        finally
            {
            closeRecordStore( recordStore, name );
            }
        return false;
        }

    public void erase( final StorageIO aStorageIO )
        {
        final String name = aStorageIO.getName();

        //#if DEBUG
        Log.debug( "Erasing {}", name );
        //#endif

        try
            {
            RecordStore.deleteRecordStore( name );
            }
        catch ( final Throwable t )
            {
            //#if DEBUG
            Log.debug( "Failed erasing record store: {}", name );
            //#endif
            }
        }

    public void load( final StorageIO aStorageIO ) throws IOException
        {
        final String name = aStorageIO.getName();

        //#if DEBUG
        Log.debug( "Loading {}", name );
        //#endif

        RecordStore recordStore = null;
        try
            {
            recordStore = RecordStore.openRecordStore( name, false );
            if ( recordStore == null ) return;

            final byte[] data = recordStore.getRecord( RECORDSTORE_ID );
            if ( data != null && data.length > 0 )
                {
                final ByteArrayInputStream bytes = new ByteArrayInputStream( data );
                aStorageIO.loadFrom( new DataInputStream( bytes ) );
                }
            }
        catch ( final InvalidRecordIDException e )
            {
            // Alright.. no data yet..
            }
        catch ( final RecordStoreException e )
            {
            throw new ChainedIOException( e );
            }
        finally
            {
            closeRecordStore( recordStore, name );
            }
        }

    public void save( final StorageIO aStorageIO ) throws IOException
        {
        final String name = aStorageIO.getName();

        //#if DEBUG
        Log.debug( "Saving {}", name );
        //#endif

        RecordStore recordStore = null;
        try
            {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            aStorageIO.saveTo( new DataOutputStream( output ) );
            output.close();

            final byte[] data = output.toByteArray();
            recordStore = RecordStore.openRecordStore( name, true );
            try
                {
                recordStore.setRecord( RECORDSTORE_ID, data, 0, data.length );
                }
            catch ( final RecordStoreException e )
                {
                recordStore.addRecord( data, 0, data.length );
                }
            }
        catch ( final RecordStoreException e )
            {
            throw new ChainedIOException( e );
            }
        finally
            {
            closeRecordStore( recordStore, name );
            }
        }

    // Implementation

    private void closeRecordStore( final RecordStore aRecordStore, final String aName )
        {
        if ( aRecordStore != null ) try
            {
            aRecordStore.closeRecordStore();
            }
        catch ( final Throwable t )
            {
            //#if DEBUG
            Log.debug( "Failed closing record store: {}", aName );
            //#endif
            throw new RuntimeException( "nyi" );
            //GameSystem.showError( "Closing failed for " + myName, t );
            }
        }

    private static final int RECORDSTORE_ID = 1;
    }
