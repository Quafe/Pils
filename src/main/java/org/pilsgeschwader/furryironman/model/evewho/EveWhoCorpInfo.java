/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.model.evewho;

/**
 *
 * @author binary gamura
 */
public class EveWhoCorpInfo {

    private int memberCount;
    
    private String name;
    
    private int corporation_id;

    public int getMemberCount()
    {
        return memberCount;
    }

    public void setMemberCount(int memberCount)
    {
        this.memberCount = memberCount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCorporation_id()
    {
        return corporation_id;
    }

    public void setCorporation_id(int corporation_id)
    {
        this.corporation_id = corporation_id;
    }
    
    
    
}
