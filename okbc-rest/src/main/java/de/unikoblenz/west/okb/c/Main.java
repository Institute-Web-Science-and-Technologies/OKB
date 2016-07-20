package de.unikoblenz.west.okb.c;

import de.unikoblenz.west.okb.c.restapi.RequestRouter;

public class Main {

    public static void main(String[] args) {
        new RequestRouter();
        RequestRouter.enableCORS("*","*","*");
    }
}