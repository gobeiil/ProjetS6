/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.test.client;

import static org.junit.Assert.assertTrue;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
public class SandboxJukitoTest {
    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
        }
    }

    @Test
    public void testSandbox() {
        assertTrue(true);
    }
}