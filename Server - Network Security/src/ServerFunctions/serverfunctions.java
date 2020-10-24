package ServerFunctions;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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

public class serverfunctions 
{
    
    private Socket          socket;
    private ServerSocket    server;
    private DataInputStream in;
    private DataOutputStream out;
    
        String privatekey, encrypted, decrypted;
        byte[] publickey;
        int port;
        
        KeyPairGenerator kpg;
        KeyPair kp;
            
    public serverfunctions() throws Exception
    {
        socket = null;
        server = null;
        in = null;
        out = null;
        
        this.privatekey = "";
        this.publickey = null;
        this.encrypted = "";
        this.decrypted = "";
 
        this.kpg = KeyPairGenerator.getInstance("EC");
        this.kpg.initialize(256);     
        this.kp = kpg.generateKeyPair();

        
    }
    
    public void decrypt()//Kenny and Shawn should work on this
    {
        //Do decryption with "encrypted" and "privatekey"
        
    }
    
    public void print()//Kobe will work on this
    {
        
       
        
    }
    
    //Sets "encrypted" variable in this class to the encrypted string from the client
    public void receivestring()//Kobe will work on this
    {
        
        
        try
        { 
        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        this.encrypted = in.readUTF();
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
        
        
    }
  
    public void setpublickey()
    {
           this.publickey = kp.getPublic().getEncoded();
    }
    
    public void setprivatekey()
    {
        
    }
    
    //Sends "publickey" to the client
    public void sendkey()//Kobe will work on this
   {
        try
        { 
      out = new DataOutputStream(socket.getOutputStream());
      //System.out.println(printHexBinary(this.publickey));
      out.writeUTF(printHexBinary(this.publickey));
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
 
   }
   

   //Makes server available to connect to
   public void openport(int port)
   {
       this.port = port;
        try
        { 
            
            server = new ServerSocket(this.port); 
            
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            
            System.out.println("Client accepted"); 
      
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
   }
    
}
