package org.hhz.nbareasoner.util.extern;

import java.net.URI;

/**
 * Created by mbehr on 06.03.2015.
 */
public class UriHelper {

    public static String getFileNameOfFileUri(URI uri, boolean cutFileExtension) throws UriHelperException
    {
        if(uri.getScheme().equals("file"))
        {

          String fileName = uri.getPath().substring(uri.getPath().lastIndexOf("/")+1,uri.getPath().length());

            if(!fileName.contains("."))
            {
                throw new UriHelperException("No file extension for URI " + uri.toString());
            }

            if(cutFileExtension)
            {
                fileName =  fileName.substring(0,fileName.indexOf("."));
            }

            return fileName;
        }
        else
        {
            throw new UriHelperException("URI '"+uri.toString()+"' is not a file uri. URI for a file should start with the file scheme (file:///) and end with the filename + file extension (filename.txt). ");
        }
    }

    public static String getPathWithoutFileNameOfFileUri(URI uri) throws UriHelperException
    {
        if(uri.getScheme().equals("file"))
        {

            return uri.getPath().substring(0,uri.getPath().lastIndexOf("/"));


        }
        else
        {
            throw new UriHelperException("URI '"+uri.toString()+"' is not a file uri. URI for a file should start with the file scheme (file:///) and end with the filename + file extension (filename.txt). ");
        }
    }

    private static class UriHelperException extends RuntimeException
    {
        public UriHelperException() {
            super();
        }

        public UriHelperException(String message) {
            super(message);
        }

        public UriHelperException(String message, Throwable cause) {
            super(message, cause);
        }

        public UriHelperException(Throwable cause) {
            super(cause);
        }

        protected UriHelperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }


}
