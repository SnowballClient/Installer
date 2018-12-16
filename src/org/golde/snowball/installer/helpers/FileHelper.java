package org.golde.snowball.installer.helpers;

import java.net.*;
import java.io.*;

public class FileHelper
{
    public static void writeContent(final InputStream curStream, final File curFile) throws Exception {
        if (curFile.exists()) {
            curFile.delete();
        }
        final FileOutputStream curOutputStream = new FileOutputStream(curFile);
        final byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = curStream.read(buffer)) != -1) {
            curOutputStream.write(buffer, 0, bytesRead);
        }
        curOutputStream.close();
    }
    
    public static void writeContent(final String newContent, final File curFile) throws Exception {
        if (curFile.exists()) {
            curFile.delete();
        }
        final PrintWriter curWriter = new PrintWriter(new BufferedWriter(new FileWriter(curFile, true)));
        curWriter.println(newContent);
        curWriter.close();
    }
    
    public static void deleteDirectory(final File curDir) throws NullPointerException {
        if (curDir.exists()) {
            for (final File curFile : curDir.listFiles()) {
                if (curFile.isDirectory()) {
                    deleteDirectory(curFile);
                }
                else {
                    curFile.delete();
                }
            }
        }
        curDir.delete();
    }
    
    public static String readFile(final File curFile) throws Exception {
        final BufferedReader curReader = new BufferedReader(new FileReader(curFile));
        final StringBuilder curBuilder = new StringBuilder();
        String curLine;
        while ((curLine = curReader.readLine()) != null && !curLine.startsWith("#")) {
            curBuilder.append(" " + curLine);
        }
        curReader.close();
        return curBuilder.toString();
    }
    
    public static InputStream getStreamFromWeb(final String curURL) throws IOException {
        final URLConnection curConnection = new URL(curURL).openConnection();
        curConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        curConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        curConnection.setDoOutput(true);
        return curConnection.getInputStream();
    }
}
