/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.model.eve;

import java.util.Date;

/**
 *
 * @author binary gamura
 */
public class EveWalletJournalEntry 
{
    private Date date;
    
    private long refID;
    
    private int refTypeID;
    
    private int ownerId1;
    
    private int ownerId2;
    
    private String ownerName1;
    
    private String ownerName2;
    
    private String argName1;
    
    private int argId1;
    
    private float amount;
    
    private float balance;
    
    private String reason;
    
    private String taxRecieverId;
    
    private String taxAmount;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public long getRefID()
    {
        return refID;
    }

    public void setRefID(long refID)
    {
        this.refID = refID;
    }

    public int getRefTypeID()
    {
        return refTypeID;
    }

    public void setRefTypeID(int refTypeID)
    {
        this.refTypeID = refTypeID;
    }

    public int getOwnerId1()
    {
        return ownerId1;
    }

    public void setOwnerId1(int ownerId1)
    {
        this.ownerId1 = ownerId1;
    }

    public int getOwnerId2()
    {
        return ownerId2;
    }

    public void setOwnerId2(int ownerId2)
    {
        this.ownerId2 = ownerId2;
    }

    public String getOwnerName1()
    {
        return ownerName1;
    }

    public void setOwnerName1(String ownerName1)
    {
        this.ownerName1 = ownerName1;
    }

    public String getOwnerName2()
    {
        return ownerName2;
    }

    public void setOwnerName2(String ownerName2)
    {
        this.ownerName2 = ownerName2;
    }

    public String getArgName1()
    {
        return argName1;
    }

    public void setArgName1(String argName1)
    {
        this.argName1 = argName1;
    }

    public int getArgId1()
    {
        return argId1;
    }

    public void setArgId1(int argId1)
    {
        this.argId1 = argId1;
    }

    public float getAmount()
    {
        return amount;
    }

    public void setAmount(float amount)
    {
        this.amount = amount;
    }

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getTaxRecieverId()
    {
        return taxRecieverId;
    }

    public void setTaxRecieverId(String taxRecieverId)
    {
        this.taxRecieverId = taxRecieverId;
    }

    public String getTaxAmount()
    {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount)
    {
        this.taxAmount = taxAmount;
    }

    @Override
    public String toString()
    {
        return "EveWalletJournalEntry{" + "date=" + date + ", refID=" + refID + ", refTypeID=" + refTypeID + ", ownerId1=" + ownerId1 + ", ownerId2=" + ownerId2 + ", ownerName1=" + ownerName1 + ", ownerName2=" + ownerName2 + ", argName1=" + argName1 + ", argId1=" + argId1 + ", amount=" + amount + ", balance=" + balance + ", reason=" + reason + ", taxRecieverId=" + taxRecieverId + ", taxAmount=" + taxAmount + '}';
    }
    
}
