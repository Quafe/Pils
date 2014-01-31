
package org.pilsgeschwader.combatmapper.model.eve;

/**
 *
 * @author boreas
 */
public class SolarSystem
{
    private float x;
    
    private float y;
    
    private float z;
    
    private int regionId;
    
    private int constellationId;
    
    private int solarSystemId;
    
    private float security;
    
    private String name;
    
    public SolarSystem()
    {
        
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return z;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public int getRegionId()
    {
        return regionId;
    }

    public void setRegionId(int regionId)
    {
        this.regionId = regionId;
    }

    public int getConstellationId()
    {
        return constellationId;
    }

    public void setConstellationId(int constellationId)
    {
        this.constellationId = constellationId;
    }

    public int getSolarSystemId()
    {
        return solarSystemId;
    }

    public void setSolarSystemId(int solarSystemId)
    {
        this.solarSystemId = solarSystemId;
    }

    public float getSecurity()
    {
        return security;
    }

    public void setSecurity(float security)
    {
        this.security = security;
    }
    
    
    
}
