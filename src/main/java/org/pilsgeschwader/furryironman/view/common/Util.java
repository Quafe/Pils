package org.pilsgeschwader.furryironman.view.common;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.IllegalFormatException;
import java.util.Locale;
import org.pilsgeschwader.furryironman.model.eve.EvESkillBonus;

/**
 *
 * @author binary gamura
 */
public class Util 
{
    
    public static NumberFormat getIskNumberFormatter()
    {
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("ISK");
        format.setDecimalFormatSymbols(symbols);
        return format;
    }
    
    public static String getEveSkillBonusName(EvESkillBonus bonus)
    {
        String text = "unknown bonus %f";
        switch(bonus.getType())
        {
            case UNKNOWN:
                break;
            case MAXJUMPCLONESBONUS:
                text = "max jumpclones +%f";
                break;
            case CONSUMPTIONQUANTITYBONUSPERCENT:
                text = "consumption bonus -%f%%";
                break;
            case CONSUMPTIONQUANTITYBONUS:
                text = "consumption bonus -%f%%";
                break;
            case DURATIONBONUS:
                text = "duration +%f%%";
                break;
            case SHIELDRECHARGERATEBONUS:
                text = "shield recharge rate +%f%%";
                break;
            case SHIPPOWERBONUS:
                text = "power grid bonus +%f%%";
                break;
            case TURRETSPEEBONUS:
                text = "turret tracking +%f%%";
                break;
            case DAMAGEMULTIPLIERBONUS:
                text = "damage +%f%%";
                break;
            case CANNOTBETRAINEDONTRIAL:
                text = "cannot be trained on trails";
                break;
            case ROFBONUS:
                text = "rate of fire +%f%%";
                break;
            case RANGESKILLBONUS:
                text = "consumption bonus -%f%%";
                break;
            case TRACKINGSPEEDBONUS:
                text = "tracking speed +%f%%";
                break;
            case CAPNEEDBONUS:
                text = "cap needed -%f%%";
                break;
            case FALLOFFBONUS:
                text = "falloff +%f%%";
                break;
            case CPUNEEDBONUS:
                text = "cpu needs -%f%%";
                break;
            case MISSILEVELOCITYBONUS:
                text = "missile velocity -%f%%";
                break;
            case MAXATTACKTARGETS:
                text = "max targets -%f";
                break;
            case AGILITYBONUS:
                text = "ship agility -%f%%";
                break;
            case REQUIREDSKILL4:
                text = "consumption bonus -%f%%";
                break;
            case REQUIREDSKILL4LEVEL:
                text = "required lvl 4 skill";
                    break;
            case SCANRESOLUTIONBONUS:
                text = "scan resolution +%f%%";
                    break;
            case SHIELDCAPACITYBONUS:
                text = "shield capacity +%f%%";
                    break;
            case SQUADRONCOMMANDBONUS:
                text = "squardon members +%f";
                    break;
            case SOCIALMUTATOR:
                text = "standing +%f%%";
                    break;
            case NEGOTIATIONBONUS:
                    break;
            case DIPLOMACYMUTATOR:
                    break;
            case FASTTALKMUTATOR:
                    break;
            case CONNECTIONBONUSMUTATOR:
                    break;
            case CRIMINALCONNECTIONSMUTATOR:
                    break;
            case BOUNTYSKILLBONUS:
                text = "bounty payments +%f%%";
                    break;
            case CORPORATIONMEMBERBONUS:
                    break;
            case SKILLALLYCOSTMODIFIERBONUS:
                    break;
            case BASEDEFENDERALLYCOST:
                    break;
            case DURATIONSKILLBONUS:
                    break;
            case RESEARCHGANGSIZEBONUS:
                    break;
            case POSSTRUCTURECONTROLAMOUNT:
                    break;
            case MANUFACTURINGTIMEBONUS:
                    break;
            case AMARRTECHMUTATOR:
                    break;
            case CALDARITECHMUTATOR:
                    break;
            case GALLENTETECHMUTATOR:
                    break;
            case REFININGYIELDMUTATOR:
                text = "refining yield +%f%%";
                    break;
            case MININGAMOUNTBONUS:
                text = "mining amount +%f%%";
                    break;
            case MANUFACTURINGSLOTBONUS:
                text = "manufactoring slots +%f";
                    break;
            case MANUFACTURECOSTBONUSSHOWINFO:
                    break;
            case MANUFACTURECOSTBONUS:
                text = "manufactoring cost -%f%%";
                    break;
            case HULLHPBONUS:
                text = "hull hitpoints +%f%%";
                    break;
            case ARMORHPBONUS:
                text = "armor hitpoints +%f%%";
                    break;
            case COPYSPEEDBONUS:
                text = "blueprint copy time -%f%%";
                    break;
            case BLUEPRINTMANUFACTURETIMEBONUS:
                    break;
            case LABORATORYSLOTSBONUS:
                text = "laboratory slots +%f";
                    break;
            case INVENTIONBONUS:
                    break;
            case MINERALNEEDRESEARCHBONUS:
                    break;
            case MAXSCANDEVIATIONMODIFIER:
                    break;
            case SCANSTRENGTHBONUS:
                text = "scan strenth -%f%%";
                    break;
            case POWERENGINEERINGOUTPUTBONUS:
                    break;
            case RECHARGERATEBONUS:
                text = "recharge rate +%f%%";
                    break;
            case CAPRECHARGEBONUS:
                text = "capacitor recharge rate +%f%%";
                    break;
            case CAPACITORCAPACITYBONUS:
                    break;
            case UNIFORMITYBONUS:
                    break;
            case POWERNEEDBONUS:
                text = "power need -%f%%";
                    break;
            case CPUOUTPUTBONUS2:
                text = "cpu output +%f%%";
                    break;
            case MAXTARGETRANGEBONUS:
                text = "max. targeting range +%f%%";
                    break;
            case MAXTARGETBONUS:
                text = "max targets +%f";
                    break;
            case MAXACTIVEDRONEBONUS:
                text = "drones in space +%f";
                    break;
            case DRONERANGEBONUS:
                text = "drone range +%f%%";
                    break;
            case DAMAGEHP:
                    break;
            case ACCESSDIFFICULTYBONUS:
                    break;
            case TRADEPREMIUMBONUS:
                    break;
            case CONTRABANDDETECTIONCHANCEBONUS:
                    break;
            case SMUGGLINGCHANCEBONUS:
                    break;
            case VELOCITYBONUS:
                text = "velocity +%f%%";
                    break;
            case SPEEDFBONUS:
                    break;
            case CAPACITORNEEDMULTIPLIER:
                    break;
            case WARPCAPACITORNEEDBONUS:
                    break;
            case JUMPDRIVECAPACITORNEEDBONUS:
                    break;
            case SCANSPEEDBONUS:
                text = "scan probe time -%f%%";
                break;
            case DAMAGECLOUDCHANCEREDUCTION:
                break;
            case HARDENINGBONUS:
                break;
            case RESISTANCEBONUS:
                text = "resistance +%f%%";
                break;
            case CLOAKINGTARGETINGDELAYBONUS:
                text = "targeting delay after decloak -%f%%";
                break;
            case DRONEMAXVELOCITYBONUS:
                text = "drone velocity +%f%%";
                break;
            case CLOAKVELOCITYBONUS:
                text = "cloaked ship velocity  +%f%%";
                break;
            case MAXFLIGHTTIMEBONUS:
                break;
            case SPEEDFACTOR:
                text = "speed -%f%%";
                break;
            case CARGOCAPACITYBONUS:
              text = "ship cargohold +%f%%";
                break;
            case ACCESSDIFFICULTYBONUSABSOLUTEPERCENT:
                break;
            case VIRUSCOHERENCEBONUS:
                text = "virus coherence +%f";
                break;
            case ICEHARVESTCYCLEBONUS:
                text = "ice harvester duration -%f%%";
                break;
            case SCANSKILLEWSTRENGTHBONUS:
                text = "scan probe strenth +%f%%";
                break;
            case SCANSKILLTARGETPAINTSTRENGTHBONUS:
                text = "target painter strength +%f%%";
                break;
            case AOECLOUDSIZEBONUS:
                text = "explosion radius +%f%%";
                break;
            case AOEVELOCITYBONUS:
                text = "explosion velocity +%f%%";
                break;
            case SHIELDBOOSTCAPACITORBONUS:
                break;
            case CONSUMPTIONQUANTITYBONUSPERCENTAGE:
                break;
            case JUMPDRIVERANGEBONUS:
                text = "jump drive activation cost -%f%%";
                break;
            case SHIELDCAPACITYMULTIPLIER:
                text = "shield capacity -%f%%";
                break;
            case MININGUPGRADECPUREDUCTIONBONUS:
                break;
            case PROPULSIONSKILLPROPULSIONSTRENGTHBONUS:
                break;
            case REQUIREDSKILL5LEVEL:
                text = "required skill 5 level";
                break;
            case REQUIREDSKILL6LEVEL:
                text = "required skill 6 level";
                break;
            case REQUIREDSKILL5:
                text = "required skill 5";
                break;
            case REQUIREDSKILL6:
                text = "required skill 6";
                break;
            case MAXJUMPCLONES:
                text = "max jumpclones +%f";
                break;
            case BOOSTERCHANCEBONUS:                
                break;
            case RIGDRAWBACKBONUS:
                text = "rig drawbacks -%f%%";
                break;
            case PROJECMDURATIONBONUS:
                text = "projected ecm duration -%f%%";
                break;
            case THERMODYNAMICSHEATDAMAGE:
                text = "module heat damage -%f%%";
                break;
            case SHIPBROKENREPAIRCOSTMULTIPLIERBONUS:
                text = "ship repair cost -%f%%";
                break;
            case MODULEREPAIRRATEBONUS:
                text = "module repair rate +%f%%";
                break;
            case MASSPENALTYREDUCTION:
                text = "mass penalty -%f%%";
                break;
            case SUBSYSTEMSLOT:
                text = "subsystemslots  +%f";
                break;
            case PITAXREDUCTIONMODIFER:
                text = "pi tax reduced by %f%%";
                break;
            case BOOSTERATTRIBUTEMODIFIER:
                text = "booster effectivnes +%f%%";
                break;
            case SENSORSTRENGTHBONUS:
                text = "sensor strength +%f%%";
                break;
        }
        try
        {
            return String.format(text, bonus.getValue());
        }
        catch(IllegalFormatException ex)
        {
            ex.printStackTrace(System.err);
            return bonus.getType().name();
        }
    }
    
    public static void main(String[] args)
    {
        for(EvESkillBonus.Type type : EvESkillBonus.Type.values())
        {
            System.out.println("\tcase "+type.name()+":\n\t\tbreak;");
        }
    }
}
