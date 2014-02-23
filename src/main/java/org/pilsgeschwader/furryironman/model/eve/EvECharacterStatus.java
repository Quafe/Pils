package org.pilsgeschwader.furryironman.model.eve;

import java.util.Date;

/**
 *
 * @author binarygamura
 */
public class EvECharacterStatus
{
    private Date paidUntil;
    
    private Date createDate;
    
    private int logonCount;
    
    private int loginMinutes;

    public Date getPaidUntil()
    {
        return paidUntil == null ? null : new Date(paidUntil.getTime());
    }

    public void setPaidUntil(Date paidUntil)
    {
        this.paidUntil = new Date(paidUntil == null ? 0l : paidUntil.getTime());
    }

    public Date getCreateDate()
    {
        return createDate == null ? null : new Date(createDate.getTime());
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = new Date(createDate == null ? 0l : createDate.getTime());
    }

    public int getLogonCount()
    {
        return logonCount;
    }

    public void setLogonCount(int logonCount)
    {
        this.logonCount = logonCount;
    }

    public int getLoginMinutes()
    {
        return loginMinutes;
    }

    public void setLoginMinutes(int loginMinutes)
    {
        this.loginMinutes = loginMinutes;
    }
    
    
}
