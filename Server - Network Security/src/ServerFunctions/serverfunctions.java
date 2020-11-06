package ServerFunctions;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.security.*;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class serverfunctions 
{
    
    private Socket          socket;
    private ServerSocket    server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private DataInputStream input;
    private DataOutputStream output;
    
        String encrypted, decrypted;
        
        Object privatekey;
        PublicKey publickey;
        int port;

        KeyPairGenerator kpg;
        KeyPair kp;
        
    public serverfunctions() throws Exception
    { 
        //These are just some initializations. Not really needed, but I believe you should initialize because you might run into problems otherwise
        socket = null;
        server = null;

        this.privatekey = null;
        this.publickey = null;
        this.encrypted = null;
        this.decrypted = null;
        
        this.kpg = KeyPairGenerator.getInstance("EC");
        this.kpg.initialize(256);     
        this.kp = kpg.generateKeyPair();
    }
    
    public void decrypt() throws Exception//Kenny and Shawn should work on this
    {
        //"Bouncy Castle" is a special library that you MUST use in order for encryption/decryption to work properly
        //The class called "Cipher" does NOT support "ECIES" by default. The "Bouncy Castle" .jar files are already included in the project
        //You shouldn't have to worry about downloading the library yourself. I've also imported everything needed from this .jar file.
        Security.addProvider(new BouncyCastleProvider());
        
        Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
            
            //Stores the bytes of the encrypted string for decryption.
            //Note that the encrypted string's characters can "mess up" a lot of functions like getBytes()
            //This is why there's a loop here. You have to pick out the characters individually and convert them into bytes
            //one-by-one.
            byte[] bb = new byte[this.encrypted.length()];
            for (int i=0; i<this.encrypted.length(); i++) 
            {
                bb[i] = (byte) this.encrypted.charAt(i);
            }

            cipher.init(Cipher.DECRYPT_MODE, kp.getPrivate());
            this.decrypted = new String(cipher.doFinal(bb));
    }
    
    public void print()//Kobe will work on this
    {
        System.out.println("The received encrypted string was: \n" + this.encrypted + "\n");

        System.out.println("The decrypted string is: \n" + this.decrypted + "\n");

        System.out.println("The public key is: \n" + this.publickey + "\n");
    }
    
    //Receives string from the client
    public void receivestring() throws Exception//Kobe will work on this
    {
        input = new DataInputStream(socket.getInputStream());//I couldn't get ObjectInputStream to work with receiving strings, so I had to use DataInputStream
        this.encrypted = input.readUTF();  
    }
  
    //Calls instance of "KeyPair" called "kp" and its function called "getPublic()," which gets the already generated public key
    public void setpublickey() throws Exception
    {
           this.publickey = kp.getPublic();   
    }
    
    
    //Calls instance of "KeyPair" called "kp" and its function called "getPrivate()," which gets the already generated private key
    public void setprivatekey() throws Exception
    {
        this.privatekey = kp.getPrivate();
    }
    
    //Sends "publickey" to the client
    public void sendkey() throws Exception//Kobe will work on this
   {
        //Objects of type "PublicKey" are considered objects to the ObjectOutputStream, so they can be sent through an object stream. 
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();//You have to use flush and initialize the ObjectInputStream right after initializing ObjectOutputStream or the object output and input streams won't work
        in = new ObjectInputStream(socket.getInputStream());
 
      out.writeObject(this.publickey);
   }
   

   //Makes server available to connect to by opening a port
   public void openport(int port) throws Exception
   {
       this.port = port;

            server = new ServerSocket(this.port); 
            
            System.out.println("Server started\n"); 

            System.out.println("Waiting for a client ...\n"); 

            socket = server.accept(); 
            
            System.out.println("Client accepted\n");    
   }
    
}
