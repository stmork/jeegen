package de.itemis.jee6.util;

import java.io.UnsupportedEncodingException;

public class Base64 {
	private final static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private final static int[]  toInt   = new int[128];

    static
    {
        for(int i=0; i< ALPHABET.length; i++)
        {
            toInt[ALPHABET[i]]= i;
        }
    }

    public static String encode(final String input) throws UnsupportedEncodingException
    {
    	return encode(input.getBytes("UTF-8"));
    }

    /**
     * Translates the specified byte array into Base64 string.
     *
     * @param buf the byte array (not null)
     * @return the translated Base64 string (not null)
     */
    public static String encode(final byte[] buf)
    {
        final int size = buf.length;
        final int mask = 0x3F;
        final char[] ar = new char[((size + 2) / 3) * 4];

        int a = 0;
        int i = 0;
        while(i < size)
        {
            final byte b0 = buf[i++];
            final byte b1 = (i < size) ? buf[i++] : 0;
            final byte b2 = (i < size) ? buf[i++] : 0;

            ar[a++] = ALPHABET[(b0 >> 2) & mask];
            ar[a++] = ALPHABET[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
            ar[a++] = ALPHABET[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
            ar[a++] = ALPHABET[b2 & mask];
        }
        switch(size % 3)
        {
            case 1: ar[--a]  = '=';
            case 2: ar[--a]  = '=';
        }
        return new String(ar);
    }
    
    /**
     * Translates the specified Base64 string into a byte array.
     *
     * @param s the Base64 string (not null)
     * @return the byte array (not null)
     */
    public static byte[] decode(final String s)
    {
        final int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
        final byte[] buffer = new byte[s.length() * 3 / 4 - delta];
        final int mask = 0xFF;
        
        int index = 0;
        for(int i = 0; i < s.length(); i+=4)
        {
            final int c0 = toInt[s.charAt( i )];
            final int c1 = toInt[s.charAt( i + 1)];

            buffer[index++] = (byte)(((c0 << 2) | (c1 >> 4)) & mask);
            if(index >= buffer.length)
            {
                return buffer;
            }

            final int c2 = toInt[s.charAt( i + 2)];
            buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);
            if(index >= buffer.length)
            {
                return buffer;
            }

            final int c3 = toInt[s.charAt( i + 3 )];
            buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
        }
        return buffer;
    } 
}
