package org.pilsgeschwader.combatmapper.model.eve;

/**
 *
 * @author boreas
 */
public enum XMLElementNames
{
    TEST("test");
    
    
    
    public final String lowercase;
    
    public final String uppercase;
    
    private final String elementName;
    
    private XMLElementNames(String elementName)
    {
        elementName = elementName.trim();
        this.elementName = elementName;
        lowercase = elementName.toLowerCase();
        uppercase = elementName.toUpperCase();        
    }
}
