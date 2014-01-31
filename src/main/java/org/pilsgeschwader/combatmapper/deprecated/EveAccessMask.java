//package org.pilsgeschwader.combatmapper.model.eve;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author boreas
// */
//public enum EveAccessMask
//{
//    ACCOUNT_STATUS(33554432, "account status"),
//    
//    API_KEY_INFO(0, "api key info"),
//    
//    LIST_OF_CHARACTERS(0, "list of all characters"),
//    
//    ACCOUNT_BALANCE(1, "account balance"),
//    
//    ASSET_LIST(2, "asset list"),
//    
//    KILL_LOG (256, "all kill mails");
//
//    private final int mask;
//    
//    private final String name;
//    
//    private EveAccessMask(int mask, String name)
//    {
//        this.mask = mask;
//        this.name = name;
//    }
//
//    public int getMask()
//    {
//        return mask;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//    
//    public boolean matches(int accessMask)
//    {
//        return (accessMask & mask) == mask;
//    }
//    
//    public static EveApiKey getMatchingKeyForMask(List<EveApiKey> keys, EveAccessMask mask)
//    {
//        EveApiKey match = null;
//        for(EveApiKey key : keys)
//        {
//            if(mask.matches(key.getAccessMask()))
//            {
//                match = key;
//                break;
//            }
//        }
//        return match;
//    }
//    
//    public static List<EveAccessMask> getMatching(EveApiKey key)
//    {
//        List<EveAccessMask> result = new ArrayList<>();
//        int mask = key.getAccessMask();
//        for(EveAccessMask temp : values())
//        {
//            if(temp.matches(mask))
//            {
//                result.add(temp);
//            }
//        }
//        return result;
//    }
//}
