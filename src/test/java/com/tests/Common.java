package com.tests;

import common.GodwokenClient;

public class Common {
    private GodwokenClient godwokenClient;



    public Common(){
        godwokenClient =  GodwokenClient.GetGodwokenInstance();
    }

    public GodwokenClient getGodwokenClient(){
        return godwokenClient;
    }
}
