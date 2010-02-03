package net.intensicode.me;

import net.intensicode.core.*;

public final class MicroGameSystem extends GameSystem
    {
    public MicroGameSystem( final SystemContext aSystemContext )
        {
        super( aSystemContext );
        }

    // From GameSystem

    protected void throwWrappedExceptionToTellCallingSystemAboutBrokenGameSystem( final Exception aException )
        {
        throw new ChainedException( "failed showing error screen", aException );
        }
    }
