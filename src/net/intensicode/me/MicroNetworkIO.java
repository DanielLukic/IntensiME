package net.intensicode.me;

import net.intensicode.core.*;
import net.intensicode.util.Log;

import javax.microedition.io.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

public final class MicroNetworkIO implements NetworkIO
    {
    public final void process( final NetworkRequest aRequest, final NetworkCallback aNetworkCallback )
        {
        try
            {
            final String url = aRequest.url();
            final boolean isPost = HttpConnection.POST.equals( aRequest.method() );
            final Connection connection = Connector.open( url, isPost ? Connector.READ_WRITE : Connector.READ, true );
            final HttpConnection http = (HttpConnection) connection;
            http.setRequestMethod( aRequest.method() );

            final Enumeration headers = aRequest.headers();
            while ( headers.hasMoreElements() )
                {
                final String key = String.valueOf( headers.nextElement() );
                final String value = aRequest.headerValue( key );
                http.setRequestProperty( key, value );
                Log.info( "{}: {}", key, value );
                }

            if ( isPost )
                {
                final DataOutputStream outputStream = http.openDataOutputStream();
                outputStream.write( aRequest.body() );
                outputStream.close();
                }

            final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            final DataInputStream inputStream = http.openDataInputStream();
            final byte[] buffer = new byte[1024];
            while ( true )
                {
                final int read = inputStream.read( buffer );
                if ( read == -1 ) break;
                byteStream.write( buffer, 0, read );
                }

            final byte[] response = byteStream.toByteArray();
            Log.info( "RESPONSE:\n{}", new String( response ) );
            aNetworkCallback.onReceived( response );

            inputStream.close();
            byteStream.close();

            http.close();
            }
        catch ( final IOException e )
            {
            aNetworkCallback.onError( e );
            }
        }

    public final void sendAndReceive( final String aURL, final byte[] aBody, final NetworkCallback aCallback )
        {
        final NetworkRequest request = new NetworkRequest()
        {
        public final String url()
            {
            return aURL;
            }

        public final String method()
            {
            return aBody == null ? NetworkRequest.METHOD_GET : NetworkRequest.METHOD_POST;
            }

        public final Enumeration headers()
            {
            return NO_HEADERS.keys();
            }

        public final String headerValue( final String aHeaderKey )
            {
            return (String) NO_HEADERS.get( aHeaderKey );
            }

        public final byte[] body()
            {
            return aBody;
            }

        private final Hashtable NO_HEADERS = new Hashtable();
        };
        process( request, aCallback );
        }
    }
