/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.place;

public class NameTokens {
    public static final String userGroups = "/userGroups";
    public static final String editUser   = "/editUser/{userId}";

    public static String getUserGroups() {
        return userGroups;
    }

    public static String getEditUser() {
        return editUser;
    }
}