package org.pilsgeschwader.furryironman.model.eve;

/**
 *
 * @author binarygamura
 */
public class EvEAugumentor
{
    public enum Type {WILLPOWER, PERCEPTION, CHARISMA, MEMORY, INTELLIGENCE};
    
    private String implantName;
    
    private int implantValue;
    
    private Type type;

    public String getImplantName()
    {
        return implantName;
    }

    public void setImplantName(String implantName)
    {
        this.implantName = implantName;
    }

    public int getImplantValue()
    {
        return implantValue;
    }

    public void setImplantValue(int implantValue)
    {
        this.implantValue = implantValue;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "EvEAugumentor{" + "implantName=" + implantName + ", implantValue=" + implantValue + ", type=" + type + '}';
    }
    
}
