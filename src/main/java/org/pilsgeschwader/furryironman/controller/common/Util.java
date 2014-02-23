/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.controller.common;

import java.util.Map;

/**
 *
 * @author binary gamura
 */
public class Util 
{
    public static void buildDefaultParameters(String vCode, int keyId, Map<String, String> map)
    {
        map.put("vCode", vCode);
        map.put("keyId", String.valueOf(keyId));
    }
}
