package ClientFunctions;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

import java.security.*;
import java.util.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;





public class clientfunctions 
{
    private Socket socket; 
    private ObjectOutputStream out; 
    private ObjectInputStream  in;
    private DataOutputStream output; 
    private DataInputStream  input;
 

    private String address;
    private int port;
    String string, encrypted;
    PrivateKey privatekey;
    Object key;
    PublicKey otherpublickey, mypublickey;
    SecretKey sharedkey;
    byte[] plaintext, ciphertext, sharedsecret;
    
        KeyPairGenerator kpg;
        KeyPair kp;
        KeyAgreement ka;
        
        
        
    public clientfunctions() throws Exception
    {
        //I don't think these initializations are really needed, but I do it just in case there could be problems
        this.socket = null;
        this.out = null;
        this.in = null;
        this.address = null;
        this.port = 0;
        
        this.encrypted = null;
        this.string = null;
        this.privatekey = null;
        this.mypublickey = null;
        this.otherpublickey = null;
        this.sharedkey = null;
        this.plaintext = null;
        this.ciphertext = null; 
        this.sharedsecret = null;
        
        this.kpg = KeyPairGenerator.getInstance("EC");//Initializes kpg as an object of "KeyPairGenerator." "EC" stands for "Elliptic Curve."
        this.kpg.initialize(256);//256-bit keys
        this.kp = kpg.generateKeyPair();//Generate the public and private key
        this.ka = null;
    }
    
    
    
    
    
   public void encrypt() throws Exception//Moshe should work on this function
    {
        //"Bouncy Castle" is a special library that you MUST use in order for encryption/decryption to work properly
        //The class called "Cipher" does NOT support "ECIES" by default. The "Bouncy Castle" .jar files are already included in the project
        //You shouldn't have to worry about downloading the library yourself. I've also imported everything needed from this .jar file.
 
        
        Cipher cipher = Cipher.getInstance("AES");

cipher.init(Cipher.ENCRYPT_MODE, this.sharedkey);

    this.plaintext  = this.string.getBytes("UTF-8");

    this.ciphertext = cipher.doFinal(this.plaintext);

            //The below loop sets up a "StringBuilder" called "sb" so that the encrypted string
            //can be accurately put into a string variable. Many pre-defined functions that try to help with this
            //will "mess up," I'm guessing because of the type of characters that the encrypted bytes can contain
            //Mainly "ciphertext" can contain characters like a line-feed, space, or carriage return which can mess 
            //up the pre-defined "helper" functions when they parse through it. 
            StringBuilder sb = new StringBuilder();
            for (byte b: this.ciphertext) 
            {
                sb.append((char)b);
            }    
            
this.encrypted = sb.toString();
    }
   
   
   

   
   //Sends encrypted string called "encrypted" to the server. Make sure that you run the connection function first!
   public void sendstring() throws Exception//Kobe will work on this
   {
            output = new DataOutputStream(socket.getOutputStream());
           
            output.writeUTF(this.encrypted);
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
   
   
   
    
   //Sets up the connection to the server. Make sure that the server has an open port and is actually running first!
   public void connect(String address, int port) throws Exception
   {
       this.port = port;
       this.address = address;

            socket = new Socket(this.address, this.port); 
            System.out.println("Connected\n"); 
   }
   
   
   
   public void setstring()//Credit to Moshe for this
   {
       String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
       String randomString = "";
              
       Random rand = new Random();
       
       //Random Length of String
       int length;
       
       length = rand.nextInt(98) + 1;
       
       char[] text = new char[length];
       
       for(int i = 0; i < length; i++)
       {
           
       text[i] = characters.charAt(rand.nextInt(characters.length()));
           
       }
       
       for(int i = 0; i < text.length; i ++)
       {
           
           randomString += text[i]; 
           
       }
       System.out.println();

       this.string = randomString;    
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
    
    System.out.println("The random string is: \n" + this.string + "\n");
    
    System.out.println("The encrypted string is: \n" + this.encrypted + "\n");

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
