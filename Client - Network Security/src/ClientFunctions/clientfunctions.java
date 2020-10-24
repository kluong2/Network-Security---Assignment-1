package ClientFunctions;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import java.security.*;
import java.security.spec.ECParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.ECPublicKey;

import javax.crypto.KeyAgreement;

import java.util.*;
import java.nio.ByteBuffer;
import java.io.Console;

import static javax.xml.bind.DatatypeConverter.printHexBinary;
import static javax.xml.bind.DatatypeConverter.parseHexBinary;

public class clientfunctions 
{
    
    private Socket socket; 
    private DataInputStream  in;
    private DataOutputStream out; 
    private Socket socket1;
    
    private String address;
    private int port;
    String encrypted, string;
    byte[] publickey;
    
    public clientfunctions()
    {
        socket = null;
        in = null;
        out = null;
        socket1 = null;
        
        this.address = address;
        this.port = port;
        this.encrypted = "";
        this.string = "";
        this.publickey = null;
        

        

        
        
    }
    
    
   public void encrypt()//Moshe should work on this funciton
    {
       
        //Do encryption on "string" with "publickey."
        
    }
   
   
   public void print()//Kobe will work on this
   {
       
   }
   
   
   //Sends encrypted string called "encrypted" to the server. Make sure that you run the connection function first!
   public void sendstring()//Kobe will work on this
   {
                      try
        { 
            // sends output to the socket 
            out   = new DataOutputStream(socket.getOutputStream()); 
            out.writeUTF(encrypted);
  
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
   }
   
   
   
   //Sets public key "publickey" by getting input from server. Make sure that you are connected to the server first!
   public void setpublickey()
   {
       
        try
        { 
            
            //takes input from terminal 
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
            this.publickey = parseHexBinary(in.readUTF());
            //System.out.println(printHexBinary(this.publickey));
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
       
   }
   
   
   
   //Sets up the connection to the server. Make sure that the server has an open port and is actually running first!
   public void connect(String address, int port)
   {
       this.port = port;
       this.address = address;
        try
        { 
            socket = new Socket(this.address, this.port); 
            System.out.println("Connected"); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
   }
   
   
   
   
}
