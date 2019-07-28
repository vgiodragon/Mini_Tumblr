package com.giotec.mini_tumblr.Utils;

public class Connections {
    private static String basePhotoPost = "https://programminghumour.tumblr.com/image/";

    public static String getURLPhoto(String id){
        return basePhotoPost+id;
    }
}
