package org.pilsgeschwader.furryironman.controller.common;

import java.nio.charset.Charset;
import java.util.Map;

/**
 *
 * @author binary gamura
 */
public class Util 
{
    
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
