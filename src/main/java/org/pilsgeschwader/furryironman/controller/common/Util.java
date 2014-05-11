package org.pilsgeschwader.furryironman.controller.common;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author binary gamura
 */
public class Util 
{
    
    public static DateFormat createDefaultEVEFormat()
    {
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format;
    }
    
    public static Charset createDefaultFileCharset()
    {
        return Charset.forName("UTF-8");
    }
    
    public static void buildDefaultParameters(String vCode, int keyId, Map<String, String> map)
    {
        map.put("vCode", vCode);
        map.put("keyId", String.valueOf(keyId));
    }
}
