package net.intensicode;

public final class MathEx
    {
    // TODO: How to remove duplication between IntensiME and IntensiDroid?

    public static float toDegrees( final float aRadians )
        {
        return aRadians * rad2degF;
        }

    public static float toRadians( final float aDegrees )
        {
        return aDegrees * deg2radF;
        }

    public static float length( final float aX, final float aY )
        {
        return (float) Math.sqrt( aX * aX + aY * aY );
        }

    public static float acos( final float arg )
        {
        if ( arg > 1 || arg < -1 ) return Float.NaN;
        return (float) ( PIO2 - asin( arg ) );
        }

    public static float asin( float arg )
        {
        double temp;
        int sign;

        sign = 0;
        if ( arg < 0 )
            {
            arg = -arg;
            sign++;
            }

        if ( arg > 1 ) return Float.NaN;

        temp = Math.sqrt( 1 - arg * arg );
        if ( arg > 0.7 )
            temp = PIO2 - atan( temp / arg );
        else
            temp = atan( arg / temp );
        if ( sign > 0 )
            temp = -temp;
        return (float) temp;
        }

    public static float cos( final float aRadians )
        {
        return (float) Math.cos( aRadians );
        }

    public static float sin( final float aRadians )
        {
        return (float) Math.sin( aRadians );
        }

    public static float sqrt( final float aValue )
        {
        return (float) Math.sqrt( aValue );
        }

    // Implementation

    private static double atan( final double arg )
        {
        if ( arg > 0 ) return msatan( arg );
        return -msatan( -arg );
        }

//    private static double atan2( double arg1, double arg2 )
//        {
//        if ( arg1 + arg2 == arg1 )
//            {
//            if ( arg1 >= 0 ) return PIO2;
//            return -PIO2;
//            }
//        arg1 = atan( arg1 / arg2 );
//        if ( arg2 < 0 )
//            {
//            if ( arg1 <= 0 ) return arg1 + Math.PI;
//            return arg1 - Math.PI;
//            }
//        return arg1;
//        }

    private static double msatan( final double arg )
        {
        if ( arg < sq2m1 ) return mxatan( arg );
        if ( arg > sq2p1 ) return PIO2 - mxatan( 1 / arg );
        return PIO2 / 2 + mxatan( ( arg - 1 ) / ( arg + 1 ) );
        }

    private static double mxatan( final double arg )
        {
        final double argsq = arg * arg;
        final double value1 = ( ( ( ( p4 * argsq + p3 ) * argsq + p2 ) * argsq + p1 ) * argsq + p0 );
        final double value2 = value1 / ( ( ( ( ( argsq + q4 ) * argsq + q3 ) * argsq + q2 ) * argsq + q1 ) * argsq + q0 );
        return value2 * arg;
        }


    private static double deg2rad = 0.01745329251994329576923690768488;

    private static float deg2radF = (float) deg2rad;

    private static double rad2deg = 57.2957795130823208767981548141052;

    private static float rad2degF = (float) rad2deg;

    private static final double PIO2 = 1.5707963267948966135E0;

    private static final double sq2p1 = 2.414213562373095048802e0;

    private static final double sq2m1 = .414213562373095048802e0;

    private static final double p4 = .161536412982230228262e2;

    private static final double p3 = .26842548195503973794141e3;

    private static final double p2 = .11530293515404850115428136e4;

    private static final double p1 = .178040631643319697105464587e4;

    private static final double p0 = .89678597403663861959987488e3;

    private static final double q4 = .5895697050844462222791e2;

    private static final double q3 = .536265374031215315104235e3;

    private static final double q2 = .16667838148816337184521798e4;

    private static final double q1 = .207933497444540981287275926e4;

    private static final double q0 = .89678597403663861962481162e3;
    }
