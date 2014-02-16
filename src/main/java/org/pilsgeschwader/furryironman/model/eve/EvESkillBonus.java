package org.pilsgeschwader.furryironman.model.eve;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author binary gamura
 */
public class EvESkillBonus 
{    
    private Type type;
    
    private float value;

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public void setValue(float value)
    {
        this.value = value;
    }
    
    public float getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "EvESkillBonus{" + "type=" + type + ", value=" + value + '}';
    }
    
    public enum Type 
    {
        UNKNOWN,
        MAXJUMPCLONESBONUS,
        CONSUMPTIONQUANTITYBONUSPERCENT,
        CONSUMPTIONQUANTITYBONUS,
        DURATIONBONUS,
        SHIELDRECHARGERATEBONUS,
        SHIPPOWERBONUS,
        TURRETSPEEBONUS,
        DAMAGEMULTIPLIERBONUS,
        CANNOTBETRAINEDONTRIAL,        
        ROFBONUS,
        RANGESKILLBONUS,
        TRACKINGSPEEDBONUS,
        CAPNEEDBONUS,
        FALLOFFBONUS,
        CPUNEEDBONUS,
        MISSILEVELOCITYBONUS,
        MAXATTACKTARGETS,
        AGILITYBONUS,
        REQUIREDSKILL4,
        REQUIREDSKILL4LEVEL,
        SCANRESOLUTIONBONUS,
        SHIELDCAPACITYBONUS,
        SQUADRONCOMMANDBONUS,
        SOCIALMUTATOR,
        NEGOTIATIONBONUS,
        DIPLOMACYMUTATOR,
        FASTTALKMUTATOR,
        CONNECTIONBONUSMUTATOR,
        CRIMINALCONNECTIONSMUTATOR,
        BOUNTYSKILLBONUS,
        CORPORATIONMEMBERBONUS,
        SKILLALLYCOSTMODIFIERBONUS,
        BASEDEFENDERALLYCOST,
        DURATIONSKILLBONUS,
        RESEARCHGANGSIZEBONUS,
        POSSTRUCTURECONTROLAMOUNT,
        MANUFACTURINGTIMEBONUS,
        AMARRTECHMUTATOR,
        CALDARITECHMUTATOR,
        GALLENTETECHMUTATOR,
        REFININGYIELDMUTATOR,
        MININGAMOUNTBONUS,
        MANUFACTURINGSLOTBONUS,
        MANUFACTURECOSTBONUSSHOWINFO,
        MANUFACTURECOSTBONUS,
        HULLHPBONUS,
        ARMORHPBONUS,
        COPYSPEEDBONUS,
        BLUEPRINTMANUFACTURETIMEBONUS,
        LABORATORYSLOTSBONUS,
        INVENTIONBONUS,
        MINERALNEEDRESEARCHBONUS,
        MAXSCANDEVIATIONMODIFIER,
        SCANSTRENGTHBONUS,
        POWERENGINEERINGOUTPUTBONUS,
        RECHARGERATEBONUS,
        CAPRECHARGEBONUS,
        CAPACITORCAPACITYBONUS,
        UNIFORMITYBONUS,
        POWERNEEDBONUS,
        CPUOUTPUTBONUS2,
        MAXTARGETRANGEBONUS,
        MAXTARGETBONUS,
        MAXACTIVEDRONEBONUS,
        DRONERANGEBONUS,
        DAMAGEHP,
        ACCESSDIFFICULTYBONUS,
        TRADEPREMIUMBONUS,
        CONTRABANDDETECTIONCHANCEBONUS,
        SMUGGLINGCHANCEBONUS,
        VELOCITYBONUS,
        SPEEDFBONUS,
        CAPACITORNEEDMULTIPLIER,
        WARPCAPACITORNEEDBONUS,
        JUMPDRIVECAPACITORNEEDBONUS,
        SCANSPEEDBONUS,
        DAMAGECLOUDCHANCEREDUCTION,
        HARDENINGBONUS,
        RESISTANCEBONUS,
        CLOAKINGTARGETINGDELAYBONUS,
        DRONEMAXVELOCITYBONUS,
        CLOAKVELOCITYBONUS,
        MAXFLIGHTTIMEBONUS,
        SPEEDFACTOR,
        CARGOCAPACITYBONUS,
        ACCESSDIFFICULTYBONUSABSOLUTEPERCENT,
        VIRUSCOHERENCEBONUS,
        ICEHARVESTCYCLEBONUS,
        SCANSKILLEWSTRENGTHBONUS,
        SCANSKILLTARGETPAINTSTRENGTHBONUS,
        AOECLOUDSIZEBONUS,
        AOEVELOCITYBONUS,
        SHIELDBOOSTCAPACITORBONUS,
        CONSUMPTIONQUANTITYBONUSPERCENTAGE,
        JUMPDRIVERANGEBONUS,
        SHIELDCAPACITYMULTIPLIER,
        MININGUPGRADECPUREDUCTIONBONUS,
        PROPULSIONSKILLPROPULSIONSTRENGTHBONUS,
        REQUIREDSKILL5LEVEL,
        REQUIREDSKILL6LEVEL,
        REQUIREDSKILL5,
        REQUIREDSKILL6,
        MAXJUMPCLONES,
        BOOSTERCHANCEBONUS,
        RIGDRAWBACKBONUS,
        PROJECMDURATIONBONUS,
        THERMODYNAMICSHEATDAMAGE,
        SHIPBROKENREPAIRCOSTMULTIPLIERBONUS,
        MODULEREPAIRRATEBONUS,
        MASSPENALTYREDUCTION,
        SUBSYSTEMSLOT,
        PITAXREDUCTIONMODIFER,
        BOOSTERATTRIBUTEMODIFIER,
        SENSORSTRENGTHBONUS;

        private static final Logger logger = Logger.getLogger(EvESkillBonus.Type.class.getName());
        
        @Override
        public String toString()
        {
            return "Type{" + '}';
        }
        
        public static Type fromString(String input)
        {
            try
            {
                return input == null ? null : valueOf(input.toUpperCase());
            }
            catch(IllegalArgumentException ex)
            {
                logger.log(Level.WARNING, "unable to find bonus for \"{0}\".", input);
                return UNKNOWN;
            }
        }
    }
}
