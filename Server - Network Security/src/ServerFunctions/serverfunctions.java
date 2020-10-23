package ServerFunctions;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class serverfunctions 
{
    
    private Socket          socket;
    private ServerSocket    server;
    private DataInputStream in;
    private DataOutputStream out;
    
        String privatekey, encrypted, decrypted;
        long publickey;
        int port;
    
    public serverfunctions()
    {
        socket = null;
        server = null;
        in = null;
        out = null;
        
        this.privatekey = "";
        this.publickey = 0;
        this.encrypted = "";
        this.decrypted = "";
 
       
        
        
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
      out.writeLong(publickey);
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
