package org.pilsgeschwader.furryironman.model.eve;

import java.util.Date;

/**
 *
 * @author binary gamura
 */
public class EvESkillInTrainingInfo 
{
    private Date trainingEndTime;
    private Date trainingStartTime;
    private int trainingTypeID;
    private int trainingStartSP;
    
    private int trainingDestinationSP;
    private int trainingToLevel;
    
    private int skillInTraining;

    public Date getTrainingEndTime()
    {
        return trainingEndTime;
    }

    public void setTrainingEndTime(Date trainingEndTime)
    {
        this.trainingEndTime = trainingEndTime;
    }

    public Date getTrainingStartTime()
    {
        return trainingStartTime;
    }

    public void setTrainingStartTime(Date trainingStartTime)
    {
        this.trainingStartTime = trainingStartTime;
    }

    public int getTrainingTypeID()
    {
        return trainingTypeID;
    }

    public void setTrainingTypeID(int trainingTypeID)
    {
        this.trainingTypeID = trainingTypeID;
    }

    public int getTrainingStartSP()
    {
        return trainingStartSP;
    }

    public void setTrainingStartSP(int trainingStartSP)
    {
        this.trainingStartSP = trainingStartSP;
    }

    public int getTrainingDestinationSP()
    {
        return trainingDestinationSP;
    }

    public void setTrainingDestinationSP(int trainingDestinationSP)
    {
        this.trainingDestinationSP = trainingDestinationSP;
    }

    public int getSkillInTraining()
    {
        return skillInTraining;
    }

    public void setSkillInTraining(int skillInTraining)
    {
        this.skillInTraining = skillInTraining;
    }

    public int getTrainingToLevel()
    {
        return trainingToLevel;
    }

    public void setTrainingToLevel(int trainingToLevel)
    {
        this.trainingToLevel = trainingToLevel;
    }
    
    
    
    
}
