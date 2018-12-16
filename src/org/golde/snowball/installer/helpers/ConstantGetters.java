package org.golde.snowball.installer.helpers;

import java.io.*;
import java.net.*;

public class ConstantGetters
{
	
	public static final String CLIENT_VERSION = "1.0.0";
	private static final String DL_LOC = "https://raw.githubusercontent.com/SnowballClient/files/master/downloads/" + CLIENT_VERSION + "/";
	
    public static InputStream getIcon() {
        return ClassLoader.getSystemResourceAsStream("assets/icon.png");
    }
    
    public static InputStream getJar() throws Exception {
        return FileHelper.getStreamFromWeb(DL_LOC + "Snowball.jar");
    }
    
    public static InputStream getJson() throws Exception {
        return FileHelper.getStreamFromWeb(DL_LOC + "Snowball.json");
    }
    
    public static URL getAudio() {
        return ClassLoader.getSystemResource("assets/audio.mp3");
    }
}
