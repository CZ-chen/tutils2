package tech.nodex.tutils2.lang;

import java.io.File;

/**
 * Created by cz on 2016-10-18.
 */
public class PathUtils {
    public static File useDir(String filePath){
        File file = new File(filePath);
        if(file.isDirectory()){
            return file;
        }
        if(file.exists()){
            throw new IllegalArgumentException("Not a directory");
        }
        if(!file.mkdirs()){
            throw new IllegalArgumentException("Make dirs fail!");
        }
        return file;
    }

    public static File useDir(File parent,String filePath){
        if(!filePath.startsWith("/") || filePath.startsWith("\\")){
            filePath = "/" + filePath;
        }
        return useDir(parent.toString()+filePath);
    }
}
