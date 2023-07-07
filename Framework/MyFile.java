package etu2050.framework;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import jakarta.servlet.http.Part;


public class MyFile {
    String Name ;
    byte [] Bytes;
    
    //getters 
    public String getName(){return this.Name;}
    public byte [] getBytes(){return this.Bytes;}

    //setters
    public void setName(String nom){this.Name = nom;}
    public void setBytes(byte [] bits){this.Bytes = bits;}

    //Constructor
    public MyFile(Part theFile) throws Exception {

        setName(theFile.getSubmittedFileName());
        InputStream stream = theFile.getInputStream();
        // int i = stream.available();
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead = 0;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        setBytes(baos.toByteArray()); 
    }

    // public byte[] searchForContent(Part part,String fileName){
    //     if(part.getFileName()!=null){
    //       if(part.getFileName().equals(fileName)){
    //         InputStream stream=part.getInputStream();
      
    //         byte[] buffer = new byte[512];
    //         ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
    //         int bytesRead;
    //         while ((bytesRead = stream.read(buffer) != -1)
    //         {
    //           baos.write(buffer, 0, bytesRead);
    //         }
    //         return baos.toByteArray();
    //       }
    //     }
    //     return null;
    //   }
}
