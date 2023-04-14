package com.XZY_SUNSHINE.crm.commons.utils;

import java.util.UUID;

public class uuid {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
