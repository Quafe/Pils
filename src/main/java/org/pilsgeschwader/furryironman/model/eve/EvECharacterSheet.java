package org.pilsgeschwader.furryironman.model.eve;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author binarygamura
 */
public class EvECharacterSheet
{
    private String name;
    
    private int characterID;
    
    private String race;
    
    private String bloodline;
    
    private String gender;
    
    private String cloneName;
    
    private int cloneSkillPoints;
    
    private float balance;

    private final List<EvESkill> skills;
    
    private Date dateOfBirth;
    
    private final List<String> corpTitles;
    
    private final List<String> corpRoles;
    
    private final List<String> corpHqRoles;
    
    private int corporationID;
    
    private String corporationName;
    
    private int willpower;
    
    private int intelligence;
    
    private int perception;
    
    private int charisma;
    
    private int memory;
    
    private final List<EvEAugumentor> implants;
    
    private String ancestry;
    
    public EvECharacterSheet()
    {
        skills = new ArrayList<>();
        corpTitles = new ArrayList<>();
        corpHqRoles = new ArrayList<>();
        corpRoles = new ArrayList<>();
        implants = new ArrayList<>();
    }

    public String getAncestry()
    {
        return ancestry;
    }

    public void setAncestry(String ancestry)
    {
        this.ancestry = ancestry;
    }
    
    public void addAugumentor(EvEAugumentor augumentor)
    {
        implants.add(augumentor);
    }

    public List<EvEAugumentor> getImplants()
    {
        return implants;
    }
    
    public int getWillpower()
    {
        return willpower;
    }

    public void setWillpower(int willpower)
    {
        this.willpower = willpower;
    }

    public int getIntelligence()
    {
        return intelligence;
    }

    public void setIntelligence(int intelligence)
    {
        this.intelligence = intelligence;
    }

    public int getPerception()
    {
        return perception;
    }

    public void setPerception(int perception)
    {
        this.perception = perception;
    }

    public int getCharisma()
    {
        return charisma;
    }

    public void setCharisma(int charisma)
    {
        this.charisma = charisma;
    }

    public int getMemory()
    {
        return memory;
    }

    public void setMemory(int memory)
    {
        this.memory = memory;
    }
    
    public int getCorporationID()
    {
        return corporationID;
    }

    public void setCorporationID(int corporationID)
    {
        this.corporationID = corporationID;
    }

    public String getCorporationName()
    {
        return corporationName;
    }

    public void setCorporationName(String corporationName)
    {
        this.corporationName = corporationName;
    }

    
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    
    
    public int getCharacterID()
    {
        return characterID;
    }

    public void setCharacterID(int characterID)
    {
        this.characterID = characterID;
    }

    
    
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }
    
    public List<String> getCorpRoles()
    {
        return corpRoles;
    }

    public List<String> getCorpHqRoles()
    {
        return corpHqRoles;
    }
    
    public List<EvESkill> getSkills()
    {
        return skills;
    }

    public List<String> getCorpTitles()
    {
        return corpTitles;
    }
    
    public void addCorpTitle(String corpTitle)
    {
        corpTitles.add(corpTitle);
    }
    
    public void addCorpRole(String role)
    {
        corpRoles.add(role);
    }
    
    public void addCorpHqRole(String role)
    {
        corpHqRoles.add(role);
    }
    
    public String getRace()
    {
        return race;
        
    }
    
    public void addSkill(EvESkill skill)
    {
        if(!skills.contains(skill))
        {
            skills.add(skill);
        }
    }

    public void setRace(String race)
    {
        this.race = race;
    }

    public String getBloodline()
    {
        return bloodline;
    }

    public void setBloodline(String bloodline)
    {
        this.bloodline = bloodline;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCloneName()
    {
        return cloneName;
    }

    public void setCloneName(String cloneName)
    {
        this.cloneName = cloneName;
    }

    public int getCloneSkillPoints()
    {
        return cloneSkillPoints;
    }

    public void setCloneSkillPoints(int cloneSkillPoints)
    {
        this.cloneSkillPoints = cloneSkillPoints;
    }

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    @Override
    public String toString()
    {
        return "EvECharacterSheet{" + "name=" + name + ", characterID=" + characterID + ", race=" + race + ", bloodline=" + bloodline + ", gender=" + gender + ", cloneName=" + cloneName + ", cloneSkillPoints=" + cloneSkillPoints + ", balance=" + balance + ", skills=" + skills + ", dateOfBirth=" + dateOfBirth + ", corpTitles=" + corpTitles + ", corpRoles=" + corpRoles + ", corpHqRoles=" + corpHqRoles + ", corporationID=" + corporationID + ", corporationName=" + corporationName + ", willpower=" + willpower + ", intelligence=" + intelligence + ", perception=" + perception + ", charisma=" + charisma + ", memory=" + memory + ", implants=" + implants + '}';
    }

    
    
}
