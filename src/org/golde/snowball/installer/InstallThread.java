package org.golde.snowball.installer;

import java.io.*;

import org.golde.snowball.installer.*;
import org.golde.snowball.installer.helpers.*;

import com.google.gson.*;

public class InstallThread extends Thread
{
    private final Installer curInstaller;
    
    public InstallThread(final Installer curInstaller) {
        this.curInstaller = curInstaller;
        final File curDir = new File(OSHelper.getOS().getMC());
        if (!curDir.exists()) {
            curDir.mkdir();
            curDir.mkdirs();
        }
    }
    
    @Override
    public void run() {
        final String mc = OSHelper.getOS().getMC();
        try {
            File curDir = new File(mc + "Snowball/");
            FileHelper.deleteDirectory(curDir);
            curDir = new File(mc + "versions/Snowball/");
            if (curDir.exists()) {
                FileHelper.deleteDirectory(curDir);
            }
            curDir.mkdirs();
            curDir.mkdir();
            final JsonObject curProfile = new JsonObject();
            curProfile.addProperty("name", "Snowball");
            curProfile.addProperty("type", "custom");
            curProfile.addProperty("created", "1970-01-01T00:00:00.002Z");
            curProfile.addProperty("lastUsed", "2021-01-01T00:00:00.002Z");
            curProfile.addProperty("icon", "Dirt_Snow");
            curProfile.addProperty("lastVersionId", "Snowball");
            
            //curProfile.addProperty("javaDir", OSHelper.getOS().getJava());
            final File curProfilesFile = new File(mc + "/launcher_profiles.json");
            JsonObject curProfiles = new JsonObject();
            if (curProfilesFile.exists()) {
                curProfiles = new JsonParser().parse(FileHelper.readFile(curProfilesFile)).getAsJsonObject();
            }
            else {
                curProfiles.add("profiles", new JsonObject());
            }
            curProfiles.get("profiles").getAsJsonObject().add("Snowball", curProfile);
            curProfiles.addProperty("selectedProfile", "Snowball");
            FileHelper.writeContent(new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(curProfiles), curProfilesFile);
            FileHelper.writeContent(ConstantGetters.getJson(), new File(curDir + "/Snowball.json"));
            this.curInstaller.moveFoward();
            FileHelper.writeContent(ConstantGetters.getJar(), new File(curDir + "/Snowball.jar"));
            this.curInstaller.moveFoward();
        }
        catch (Exception e) {
            e.printStackTrace();
            this.curInstaller.die();
            System.err.println("Could not download client.. Fatal error, shutting down!");
        }
    }
}
