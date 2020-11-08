package ServerFunctions;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import java.security.*;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class serverfunctions 
{
    
    private Socket          socket;
    private ServerSocket    server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private DataInputStream input;
    private DataOutputStream output;
    
        String encrypted, decrypted;
        
        PrivateKey privatekey;
        Object key;
        PublicKey otherpublickey, mypublickey;
        SecretKey sharedkey;
        byte[] sharedsecret;
        int port;

        KeyPairGenerator kpg;
        KeyPair kp;
        KeyAgreement ka;
        
    public serverfunctions() throws Exception
    { 
        //These are just some initializations. Not really needed, but I believe you should initialize because you might run into problems otherwise
        socket = null;
        server = null;

        this.privatekey = null;
        this.mypublickey = null;
        this.otherpublickey = null;
        this.sharedkey = null;
        this.key = null;
        this.encrypted = null;
        this.decrypted = null;
        this.sharedsecret = null;
        
        this.kpg = KeyPairGenerator.getInstance("EC");//Initializes kpg as an object of "KeyPairGenerator." "EC" stands for "Elliptic Curve."
        this.kpg.initialize(256);//256-bit keys    
        this.kp = kpg.generateKeyPair();//Generates the public and private key
        this.ka = null;
    }
    
    
    
    public void decrypt() throws Exception//Kenny and Shawn should work on this
    {
        //"Bouncy Castle" is a special library that you MUST use in order for encryption/decryption to work properly
        //The class called "Cipher" does NOT support "ECIES" by default. The "Bouncy Castle" .jar files are already included in the project
        //You shouldn't have to worry about downloading the library yourself. I've also imported everything needed from this .jar file.
        
        Cipher cipher = Cipher.getInstance("AES");
            
            //Stores the bytes of the encrypted string for decryption.
            //Note that the encrypted string's characters can "mess up" a lot of functions like getBytes()
            //This is why there's a loop here. You have to pick out the characters individually and convert them into bytes
            //one-by-one.
            byte[] bb = new byte[this.encrypted.length()];
            for (int i=0; i<this.encrypted.length(); i++) 
            {
                bb[i] = (byte) this.encrypted.charAt(i);
            }

            cipher.init(Cipher.DECRYPT_MODE, this.sharedkey);
            this.decrypted = new String(cipher.doFinal(bb));
    }
    
    
  
    //Calls instance of "KeyPair" called "kp" and its function called "getPrivate()," which gets the already generated private key
    public void setprivatekey() throws Exception
    {
        this.privatekey = kp.getPrivate();
    }
    
    
    //Calls instance of "KeyPair" called "kp" and its function called "getPublic()," which gets the already generated public key
    public void setmypublickey() throws Exception
    {
      this.mypublickey = kp.getPublic();  
    }
    
    
    
    
      //Sets public key "publickey" by getting input from server. Make sure that you are connected to the server first!
   public void getotherpublickey() throws Exception
   {
            //takes input from terminal 
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();//You have to use flush and initialize the ObjectInputStream right after initializing ObjectOutputStream or the object output and input streams won't work
            in = new ObjectInputStream(socket.getInputStream()); 
            
            this.key = in.readObject();//ObjectOutputStream will take "PublicKey" objects just fine, but ObjectInputStream won't initialize a "PublicKey" type this way. 
            //This is why I put it in Object "Key"

	this.otherpublickey = (PublicKey) this.key;//I then static cast "Key" to type "PublicKey" in order to ensure it will work with encryption functions properly.

   }
   
    
    
    
    
    //Sends "publickey" to the client
    public void sendkey() throws Exception//Kobe will work on this
   {
        //Objects of type "PublicKey" are considered objects to the ObjectOutputStream, so they can be sent through an object stream. 
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();//You have to use flush and initialize the ObjectInputStream right after initializing ObjectOutputStream or the object output and input streams won't work
        in = new ObjectInputStream(socket.getInputStream());
 
      out.writeObject(this.mypublickey);
   }
   
    
    
   public void keyagreement() throws Exception
   {
    ka = KeyAgreement.getInstance("ECDH");
    ka.init(this.privatekey);
    ka.doPhase(this.otherpublickey, true);
   }
   
   
   
   public void setsharedsecret() throws Exception
   {
    // Generate shared secret
    this.sharedsecret = ka.generateSecret();
   }
    
    
   public void setsharedkey() throws Exception
   {
    // Derive a key from the shared secret and both public keys
    MessageDigest hash = MessageDigest.getInstance("SHA-256");
    hash.update(this.sharedsecret);
    // Simple deterministic ordering
    List<ByteBuffer> keys = Arrays.asList(ByteBuffer.wrap(this.mypublickey.getEncoded()), ByteBuffer.wrap(this.otherpublickey.getEncoded()));
    Collections.sort(keys);
    hash.update(keys.get(0));
    hash.update(keys.get(1));

    byte[] derivedKey = hash.digest();

    this.sharedkey = new SecretKeySpec(derivedKey, "AES");
    
    
   }
   
       //Receives string from the client
    public void receivestring() throws Exception//Kobe will work on this
    {
        input = new DataInputStream(socket.getInputStream());//I couldn't get ObjectInputStream to work with receiving strings, so I had to use DataInputStream
        this.encrypted = input.readUTF();  
    }
   
   
    public void print()//Kobe will work on this
    {
        
      
      
    // Display our public key
    System.out.println("Our public key: \n" + tostring(this.mypublickey) + "\n");
    
    // Display their public key
    System.out.println("Their public key: \n" + tostring(this.otherpublickey) + "\n");
    
    System.out.println("Our private key: \n" + tostring(this.privatekey) + "\n");
        
    System.out.println("Shared secret: \n" + tostring(this.sharedsecret) + "\n");
      
    System.out.println("Shared key: \n" + tostring(this.sharedkey) + "\n");
    
    
        System.out.println("The received encrypted string was: \n" + this.encrypted + "\n");

        System.out.println("The decrypted string is: \n" + this.decrypted + "\n");
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
   
   
   //Function Overload
   
   //A little helper function for outputting byte arrays as strings
   public String tostring(byte[] array)
   {
      StringBuilder storage = new StringBuilder(array.length * 2);
       
      for(byte b: array)
      {
        storage.append(String.format("%02x", b));
      }
      
      return storage.toString();
   }
   
   //A little helper function for outputting keys as strings
   public String tostring(Key key)
   {
      StringBuilder storage = new StringBuilder(key.getEncoded().length * 2);
       
      for(byte b: key.getEncoded())
      {
        storage.append(String.format("%02x", b));
      }
      
      return storage.toString();
   }
  
}
