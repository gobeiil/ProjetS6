/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.place;

public class NameTokens {
    public static final String home = "/home";
    public static final String editUser = "/editUser/{userId}";

    public static String getHome() {
        return home;
    }

    public static String getEditUser() {
        return editUser;
    }
}