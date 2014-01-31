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
        return paidUntil;
    }

    public void setPaidUntil(Date paidUntil)
    {
        this.paidUntil = paidUntil;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
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
