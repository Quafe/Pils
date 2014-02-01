/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.controller.common;

import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author binary gamura
 */
public class InvalidApiKeyException extends ControllerException
{
    private final ApiKey key;
    
    public InvalidApiKeyException(ApiKey key)
    {
        super("an api key is invalid and will be removed.");
        this.key = key;
    }

    public ApiKey getKey()
    {
        return key;
    }
}
