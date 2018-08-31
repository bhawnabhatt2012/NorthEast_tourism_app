package in.project.android.indiatouristplaces;

/**
 * Created by android on 22-12-2016.
 */

import java.io.File;
import java.io.FilenameFilter;
import java.net.URLEncoder;
import java.sql.SQLOutput;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class FileCache {

    private File cacheDir;
   // private static final int EXTERNAL_STORAGE_REQUEST_CODE = 123;


    public FileCache(Context context){

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            //creates dirctory to the path android -> data -> Package Name
            cacheDir=new File(context.getExternalCacheDir(),"StatesCache");
        }
        else {
            cacheDir = new File(context.getCacheDir(),"StateImages");
        }
        if(!cacheDir.exists())
        {
            cacheDir.mkdirs();

        }

    }


    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        //String filename=String.valueOf(url.hashCode());
        String filename1 = url.substring(url.lastIndexOf('/') + 1);
        int pos = filename1.lastIndexOf(".");
        if (pos > 0) {
            filename1 = filename1.substring(0, pos);
        }
        //System.out.println("URL:     "+ FilenameFilter.);
        System.out.println("FILENAME:     "+ filename1);
        //Another possible solution (thanks to grantland)
        //String   = URLEncoder.encode(url);
        File f = new File(cacheDir, filename1);
        return f;

    }

    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}