package net.intensicode;

import net.intensicode.core.*;

public class AsyncNetworkIO implements NetworkIO
    {
    public AsyncNetworkIO( final NetworkIO aNetworkIO )
        {
        myNetworkIO = aNetworkIO;
        }

    public boolean isOnline()
        {
        return myNetworkIO.isOnline();
        }

    public void sendAndReceive( final String aURL, final byte[] aBody, final NetworkCallback aCallback )
        {
        new Thread()
        {
        public void run()
            {
            myNetworkIO.sendAndReceive( aURL, aBody, aCallback );
            }
        }.start();
        }

    public void process( final NetworkRequest aRequest, final NetworkCallback aNetworkCallback )
        {
        new Thread()
        {
        public void run()
            {
            myNetworkIO.process( aRequest, aNetworkCallback );
            }
        }.start();
        }

    private final NetworkIO myNetworkIO;
    }
