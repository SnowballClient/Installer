package org.golde.snowball.installer.helpers;

import java.io.*;
import java.util.*;

public enum OSHelper
{
    MAC("Library" + File.separator + "Application Support" + File.separator + "minecraft", "bin" + File.separator + "java"), 
    LINUX(".minecraft", "bin" + File.separator + "java"), 
    WIN("AppData" + File.separator + "Roaming" + File.separator + ".minecraft", "bin" + File.separator + "javaw.exe");
    
    private final String curMC;
    private final String curExe;
    
    private OSHelper(final String curMC, final String curExe) {
        this.curMC = File.separator + curMC + File.separator;
        this.curExe = File.separator + curExe;
    }
    
    public String getMC() {
        return System.getProperty("user.home") + this.curMC;
    }
    
    public static final OSHelper getOS() {
        final String curOS = System.getProperty("os.name").toLowerCase();
        return curOS.startsWith("windows") ? OSHelper.WIN : (curOS.startsWith("mac") ? OSHelper.MAC : OSHelper.LINUX);
    }
    
    public String getJava() {
        return System.getProperty("java.home") + this.curExe;
    }
    
    public List<File> getPossibleLauncherLocations() {
        final List<File> curList = Arrays.asList(new File(this.getMC() + "launcher.jar"));
        if (this.equals(OSHelper.WIN)) {
            curList.add(new File("C:" + File.separator + "Program Files (x86)" + File.separator + "Minecraft" + File.separator + "MinecraftLauncher.exe"));
            curList.add(new File("C:" + File.separator + "Program Files" + File.separator + "Minecraft" + File.separator + "MinecraftLauncher.exe"));
        }
        return curList;
    }
}
