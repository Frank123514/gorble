package net.got.worldgen;

/* Stefan Gustavson's Simplex Noise — version 2012-03-09
 *
 * This code was placed in the public domain by its original author,
 * Stefan Gustavson. You may use it as you see fit, but
 * attribution is appreciated.
 *
 * Ported verbatim into the GoT mod package; only the package declaration
 * and class visibility have been changed.
 */
public final class SimplexNoise {

    private static final Grad[] grad3 = {
            new Grad( 1, 1, 0), new Grad(-1, 1, 0), new Grad( 1,-1, 0), new Grad(-1,-1, 0),
            new Grad( 1, 0, 1), new Grad(-1, 0, 1), new Grad( 1, 0,-1), new Grad(-1, 0,-1),
            new Grad( 0, 1, 1), new Grad( 0,-1, 1), new Grad( 0, 1,-1), new Grad( 0,-1,-1)
    };

    private static final short[] p = {
            151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,
            21,10,23,190,6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,
            237,149,56,87,174,20,125,136,171,168,68,175,74,165,71,134,139,48,27,166,77,146,158,231,
            83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,
            216,80,73,209,76,132,187,208,89,18,169,200,196,135,130,116,188,159,86,164,100,109,198,
            173,186,3,64,52,217,226,250,124,123,5,202,38,147,118,126,255,82,85,212,207,206,59,227,
            47,16,58,17,182,189,28,42,223,183,170,213,119,248,152,2,44,154,163,70,221,153,101,155,
            167,43,172,9,129,22,39,253,19,98,108,110,79,113,224,232,178,185,112,104,218,246,97,228,
            251,34,242,193,238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,49,192,214,
            31,181,199,106,157,184,84,204,176,115,121,50,45,127,4,150,254,138,236,205,93,222,114,67,
            29,24,72,243,141,128,195,78,66,215,61,156,180
    };

    private static final short[] perm     = new short[512];
    private static final short[] permMod12 = new short[512];

    static {
        for (int i = 0; i < 512; i++) {
            perm[i]      = p[i & 255];
            permMod12[i] = (short)(perm[i] % 12);
        }
    }

    private static final double F2 = 0.5 * (Math.sqrt(3.0) - 1.0);
    private static final double G2 = (3.0 - Math.sqrt(3.0)) / 6.0;
    private static final double F3 = 1.0 / 3.0;
    private static final double G3 = 1.0 / 6.0;

    private SimplexNoise() {}

    private static int fastfloor(double x) {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }

    private static double dot(Grad g, double x, double y)              { return g.x*x + g.y*y; }
    private static double dot(Grad g, double x, double y, double z)    { return g.x*x + g.y*y + g.z*z; }

    // ── 2D simplex noise ──────────────────────────────────────────────────

    /** Returns a value in [-1, 1]. */
    public static double noise2(double xin, double yin) {
        double n0, n1, n2;
        double s  = (xin + yin) * F2;
        int    i  = fastfloor(xin + s);
        int    j  = fastfloor(yin + s);
        double t  = (i + j) * G2;
        double x0 = xin - (i - t);
        double y0 = yin - (j - t);
        int i1, j1;
        if (x0 > y0) { i1 = 1; j1 = 0; } else { i1 = 0; j1 = 1; }
        double x1 = x0 - i1 + G2;
        double y1 = y0 - j1 + G2;
        double x2 = x0 - 1.0 + 2.0 * G2;
        double y2 = y0 - 1.0 + 2.0 * G2;
        int ii  = i & 255, jj = j & 255;
        int gi0 = permMod12[ii      + perm[jj     ]];
        int gi1 = permMod12[ii + i1 + perm[jj + j1]];
        int gi2 = permMod12[ii +  1 + perm[jj +  1]];
        double t0 = 0.5 - x0*x0 - y0*y0;
        n0 = t0 < 0 ? 0.0 : (t0*=t0) * t0 * dot(grad3[gi0], x0, y0);
        double t1 = 0.5 - x1*x1 - y1*y1;
        n1 = t1 < 0 ? 0.0 : (t1*=t1) * t1 * dot(grad3[gi1], x1, y1);
        double t2 = 0.5 - x2*x2 - y2*y2;
        n2 = t2 < 0 ? 0.0 : (t2*=t2) * t2 * dot(grad3[gi2], x2, y2);
        return 70.0 * (n0 + n1 + n2);
    }

    // ── 3D simplex noise ──────────────────────────────────────────────────

    /** Returns a value in [-1, 1]. */
    public static double noise3(double xin, double yin, double zin) {
        double n0, n1, n2, n3;
        double s  = (xin + yin + zin) * F3;
        int    i  = fastfloor(xin + s);
        int    j  = fastfloor(yin + s);
        int    k  = fastfloor(zin + s);
        double t  = (i + j + k) * G3;
        double x0 = xin - (i - t);
        double y0 = yin - (j - t);
        double z0 = zin - (k - t);
        int i1, j1, k1, i2, j2, k2;
        if (x0 >= y0) {
            if      (y0 >= z0) { i1=1;j1=0;k1=0; i2=1;j2=1;k2=0; }
            else if (x0 >= z0) { i1=1;j1=0;k1=0; i2=1;j2=0;k2=1; }
            else               { i1=0;j1=0;k1=1; i2=1;j2=0;k2=1; }
        } else {
            if      (y0 < z0)  { i1=0;j1=0;k1=1; i2=0;j2=1;k2=1; }
            else if (x0 < z0)  { i1=0;j1=1;k1=0; i2=0;j2=1;k2=1; }
            else               { i1=0;j1=1;k1=0; i2=1;j2=1;k2=0; }
        }
        double x1 = x0 - i1 + G3,      y1 = y0 - j1 + G3,      z1 = z0 - k1 + G3;
        double x2 = x0 - i2 + 2*G3,    y2 = y0 - j2 + 2*G3,    z2 = z0 - k2 + 2*G3;
        double x3 = x0 - 1.0 + 3*G3,   y3 = y0 - 1.0 + 3*G3,   z3 = z0 - 1.0 + 3*G3;
        int ii = i & 255, jj = j & 255, kk = k & 255;
        int gi0 = permMod12[ii      + perm[jj      + perm[kk     ]]];
        int gi1 = permMod12[ii + i1 + perm[jj + j1 + perm[kk + k1]]];
        int gi2 = permMod12[ii + i2 + perm[jj + j2 + perm[kk + k2]]];
        int gi3 = permMod12[ii +  1 + perm[jj +  1 + perm[kk +  1]]];
        double t0 = 0.6 - x0*x0 - y0*y0 - z0*z0;
        n0 = t0 < 0 ? 0.0 : (t0*=t0) * t0 * dot(grad3[gi0], x0, y0, z0);
        double t1 = 0.6 - x1*x1 - y1*y1 - z1*z1;
        n1 = t1 < 0 ? 0.0 : (t1*=t1) * t1 * dot(grad3[gi1], x1, y1, z1);
        double t2 = 0.6 - x2*x2 - y2*y2 - z2*z2;
        n2 = t2 < 0 ? 0.0 : (t2*=t2) * t2 * dot(grad3[gi2], x2, y2, z2);
        double t3 = 0.6 - x3*x3 - y3*y3 - z3*z3;
        n3 = t3 < 0 ? 0.0 : (t3*=t3) * t3 * dot(grad3[gi3], x3, y3, z3);
        return 32.0 * (n0 + n1 + n2 + n3);
    }

    // ── Fractional Brownian Motion helpers ────────────────────────────────

    /**
     * 5-octave fBm using 2D simplex noise.
     * Output is normalised to [-1, 1] range.
     */
    public static double fbm2(double x, double z,
                              double baseFreqX, double baseFreqZ,
                              double lacunarity) {
        double bx = baseFreqX, bz = baseFreqZ;
        double n  = 1.0000 * noise2(x / bx, z / bz); bx *= lacunarity; bz *= lacunarity;
        n        += 0.5000 * noise2(x / bx, z / bz); bx *= lacunarity; bz *= lacunarity;
        n        += 0.2500 * noise2(x / bx, z / bz); bx *= lacunarity; bz *= lacunarity;
        n        += 0.1250 * noise2(x / bx, z / bz); bx *= lacunarity; bz *= lacunarity;
        n        += 0.0625 * noise2(x / bx, z / bz);
        return n / (1.0 + 0.5 + 0.25 + 0.125 + 0.0625);
    }

    /**
     * 2-octave fBm using 3D simplex noise (used for domain-warp and caves).
     * Output is normalised to [-1, 1] range.
     */
    public static double fbm3(double x, double y, double z,
                              double hScale, double vScale) {
        double n  = noise3(x / hScale, y / vScale, z / hScale);
        n        += 0.5 * noise3(x / (hScale * 0.5), y / (vScale * 0.5), z / (hScale * 0.5));
        return n / 1.5;
    }

    // ── Gradient class ────────────────────────────────────────────────────

    private static final class Grad {
        final double x, y, z;
        Grad(double x, double y, double z) { this.x = x; this.y = y; this.z = z; }
    }
}