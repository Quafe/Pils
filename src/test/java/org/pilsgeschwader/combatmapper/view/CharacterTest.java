/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.combatmapper.view;

import java.io.File;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.pilsgeschwader.furryironman.controller.character.CharacterController;
import org.pilsgeschwader.furryironman.model.eve.ApiKey;

/**
 *
 * @author binarygamura
 */
public class CharacterTest
{
    private static CharacterController controller;
    
    @BeforeClass
    public static void init()
    {
        controller = new CharacterController(new File("./xml_cache"));
    }
    
    @Test
    @Ignore
    public void testCharacterSheet()
    {
        ApiKey key = new ApiKey(2656713,"j8webTIcKK8WD6gJOqw3EIah57HNBUEVCg5HbcEvzdKwW8uc4JaUz8bUHhsrFibR");
        
        
    }
}
