package org.pilsgeschwader.furryironman.model.eve;

/**
 *
 * @author boreas
 */
public class EvEItemDefinition
{
    private int typeID;
    
    private int groupID;
    
    private String typeName;
    
    private String description;
    
    private double mass;
    
    private double capacity;
    
    private double volume;
    
    private int portionSize;
    
    private int raceId;
    
    private float basePrice;
    
    private boolean published;
    
    private int marketGroupID;
    
    private double chanceOfDuplicating;

    public int getTypeID()
    {
        return typeID;
    }

    public void setTypeID(int typeID)
    {
        this.typeID = typeID;
    }

    public int getGroupID()
    {
        return groupID;
    }

    public void setGroupID(int groupID)
    {
        this.groupID = groupID;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description == null ? "" : description.replace("\n", " ").replace("\r", "").replace(";", ",").trim();
    }

    public double getMass()
    {
        return mass;
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public double getCapacity()
    {
        return capacity;
    }

    public void setCapacity(double capacity)
    {
        this.capacity = capacity;
    }

    public double getVolume()
    {
        return volume;
    }

    public void setVolume(double volume)
    {
        this.volume = volume;
    }

    public int getPortionSize()
    {
        return portionSize;
    }

    public void setPortionSize(int portionSize)
    {
        this.portionSize = portionSize;
    }

    public int getRaceId()
    {
        return raceId;
    }

    public void setRaceId(int raceId)
    {
        this.raceId = raceId;
    }

    public float getBasePrice()
    {
        return basePrice;
    }

    public void setBasePrice(float basePrice)
    {
        this.basePrice = basePrice;
    }

    public boolean isPublished()
    {
        return published;
    }

    public void setPublished(boolean published)
    {
        this.published = published;
    }

    public int getMarketGroupID()
    {
        return marketGroupID;
    }

    public void setMarketGroupID(int marketGroupID)
    {
        this.marketGroupID = marketGroupID;
    }

    public double getChanceOfDuplicating()
    {
        return chanceOfDuplicating;
    }

    public void setChanceOfDuplicating(double chanceOfDuplicating)
    {
        this.chanceOfDuplicating = chanceOfDuplicating;
    }

    @Override
    public String toString()
    {
        return "EvEItemDefinition{" + "typeID=" + typeID + ", groupID=" + groupID + ", typeName=" + typeName + ", description=" + description + ", mass=" + mass + ", capacity=" + capacity + ", volume=" + volume + ", portionSize=" + portionSize + ", raceId=" + raceId + ", basePrice=" + basePrice + ", published=" + published + ", marketGroupID=" + marketGroupID + ", chanceOfDuplicating=" + chanceOfDuplicating + '}';
    }
}
