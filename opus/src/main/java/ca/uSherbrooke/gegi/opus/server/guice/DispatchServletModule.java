/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.server.guice;


import ca.uSherbrooke.gegi.persist.dao.OpusDaoModule;
import com.google.inject.servlet.ServletModule;

public class DispatchServletModule extends ServletModule {
    @Override
    public void configureServlets() {
    	install(new OpusDaoModule(""));
    }
}