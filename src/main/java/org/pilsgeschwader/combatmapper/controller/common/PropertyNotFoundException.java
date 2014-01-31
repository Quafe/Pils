
package org.pilsgeschwader.combatmapper.controller.common;

/**
 *
 * @author boreas
 */
public class PropertyNotFoundException extends Exception
{
    public PropertyNotFoundException(String key)
    {
        super("unable to find config value for \""+key+"\".");
    }
}
