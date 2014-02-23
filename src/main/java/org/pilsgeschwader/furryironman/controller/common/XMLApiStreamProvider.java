package org.pilsgeschwader.furryironman.controller.common;

import java.util.List;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author binary gamura
 */
public interface XMLApiStreamProvider 
{
    public void makeApiXMLRequest(XMLApiRequest request, List<ApiKey> keys) throws Exception;
}
