/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.view;

import java.awt.Image;
import java.io.File;
import java.net.URI;
import org.pilsgeschwader.furryironman.controller.common.AbstractImageController;

/**
 *
 * @author binary gamura
 */
public class CorpImageGetter 
{
    public static void main(String[] args)
    {
        try
        {
            AbstractImageController controller = new AbstractImageController(new File("./images/corp"), new URI("http://image.eveonline.com/Corporation/"), AbstractImageController.IMAGE_TYPE_PNG);
            controller.fetchImageFor(772108740, 128);
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }
}
