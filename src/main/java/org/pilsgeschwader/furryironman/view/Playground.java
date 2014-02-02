/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pilsgeschwader.furryironman.view;

import java.util.Arrays;

/**
 *
 * @author binarygamura
 */
public class Playground
{
    public static void main(String[] args)
    {
        
        String pythonString = "v\\xb6\\x02\\xc0\\xa4\\xb5$y\\xe6\\xd0:t\\xff\\xdbI\\xf9\\xed";
        String[] splitted = pythonString.split("\\\\x");
        
        int val;
        StringBuilder result = new StringBuilder();
        String value = Arrays.deepToString(splitted);
        for (String element : splitted)
        {
            if(element.length() ==  2)
            {
                result.append((char) Integer.parseInt(element, 16));
            }
        }
        System.out.println(result);
    }
}
