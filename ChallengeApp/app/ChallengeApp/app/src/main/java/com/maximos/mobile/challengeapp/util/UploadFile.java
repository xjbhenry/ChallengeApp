package com.maximos.mobile.challengeapp.util;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Maximos on 11/17/2014.
 */
public class UploadFile {

    private static final String TAG_NAME = UploadFile.class.getName();
    private static final Logger logger = Logger.getLogger(TAG_NAME);
    private int serverResponseCode = 0;
    public String fileOnServer = null;

    public int uploadVideo (String selectedPath) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;
        String responseFromServer = "";

        String urlString = "http://meishicolumbus.com/ChallengeApp/challenge.php";
        try
        {
            //------------------ CLIENT REQUEST
            File file = new File(selectedPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            conn.setRequestProperty("uploaded_file",selectedPath);
            dos = new DataOutputStream( conn.getOutputStream() );
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + selectedPath + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            logger.log(Level.INFO, "Request Object :" + dos.toString());
            logger.log(Level.INFO, "selectedPath :" + selectedPath);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);

            if(serverResponseCode == 200){

                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                        +" F:/wamp/wamp/www/uploads";
                logger.log(Level.INFO,msg);
            }
            fileInputStream.close();
            dos.flush();
            dos.close();
        }
        catch (MalformedURLException ex)
        {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        catch (IOException ioe)
        {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }

        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream ( conn.getInputStream() );
            String str;

            while (( str = inStream.readLine()) != null)
            {
                Log.e("Debug","Server Response "+str);
                str = fetchUploadedFileName(str);
                Log.e("Debug", "fileName :" + str);
                fileOnServer = str;
            }
            inStream.close();
        }
        catch (IOException ioex){
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
        return serverResponseCode;
    }

    public String fetchUploadedFileName(String str) {
        logger.log(Level.INFO, TAG_NAME + " fetch uploaded fileName: " );
        /* substr to fetch value of file to upload */
        String substr = str.substring(str.indexOf("file_path= ")+"file_path= ".length(),str.indexOf("success"));
        logger.log(Level.INFO, TAG_NAME + ": " + substr.trim());
        return substr;
    }
   /* public static void main(String args[]) {
        logger.log(Level.INFO,TAG_NAME + " : inside main()");
        fetchUploadedFileName();
    }*/
}
