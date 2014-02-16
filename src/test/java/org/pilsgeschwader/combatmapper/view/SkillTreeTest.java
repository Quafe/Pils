package org.pilsgeschwader.combatmapper.view;

import java.io.FileInputStream;
import org.junit.Test;
import org.pilsgeschwader.furryironman.controller.skills.SkillTreeImporter;
import org.pilsgeschwader.furryironman.model.eve.EvESkillTree;

import static org.junit.Assert.*;

/**
 *
 * @author binary gamura
 */
public class SkillTreeTest 
{
    @Test
    public void testImport() throws Exception
    {
        long start = System.currentTimeMillis();
        SkillTreeImporter importer = new SkillTreeImporter();
        EvESkillTree tree = importer.loadSkilltree(new FileInputStream("./skills_2014_02_10.xml"));
        assertNotNull(tree);
        System.out.println("done after "+(System.currentTimeMillis() - start)+"ms.");
    }
}
