package org.pilsgeschwader.furryironman.model.eve;

import java.util.Date;

/**
 *
 * @author binarygamura
 */
public class ApiKey
{
    private int keyId;
    
    private String verificationString;
    
    private int accessMask;
    
    private Date validTo;
    
    public ApiKey()
    {
        
    }
    
    public ApiKey(int keyId, String verificationString )
    {
        this.keyId = keyId;
        this.verificationString = verificationString;
    }

    public int getAccessMask()
    {
        return accessMask;
    }

    public void setAccessMask(int accessMask)
    {
        this.accessMask = accessMask;
    }

    public Date getValidTo()
    {
        return validTo;
    }

    public void setValidTo(Date validTo)
    {
        this.validTo = validTo;
    }
    
    public int getKeyId()
    {
        return keyId;
    }

    public void setKeyId(int keyId)
    {
        this.keyId = keyId;
    }

    public String getVerificationString()
    {
        return verificationString;
    }

    public void setVerificationString(String verificationString)
    {
        this.verificationString = verificationString;
    }

    @Override
    public String toString()
    {
        return "EveApiKey{" + "keyId=" + keyId + ", verificationString=" + verificationString + ", accessMask=" + accessMask + ", validTo=" + validTo + '}';
    }
    
}
