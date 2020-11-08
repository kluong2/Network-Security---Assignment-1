package ClientFunctions;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.security.*;
import java.util.*;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
    Object key;
    PublicKey publickey;
    byte[] plaintext, ciphertext;
        
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
        this.publickey = null;
        this.plaintext = null;
        this.ciphertext = null; 
    }
    
    
   public void encrypt() throws Exception//Moshe should work on this function
    {
        //"Bouncy Castle" is a special library that aids in encryption and decryption with various types of keys
        //The class called "Cipher" does NOT support "ECIES" by default. The "Bouncy Castle" .jar files are already included in the project
        //You shouldn't have to worry about downloading the library yourself. I've also imported everything needed from this .jar file.
Security.addProvider(new BouncyCastleProvider());
Cipher cipher = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
cipher.init(Cipher.ENCRYPT_MODE, this.publickey);

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
   
   
   public void print()//Kobe will work on this
   {
       System.out.println("The string is: \n" + this.string + "\n");

       System.out.println("The encrypted string is literally: \n" + this.encrypted + "\n");
       
       System.out.println("The public key is: \n" + tostring(this.publickey) + "\n");
   }
   
   
   //Sends encrypted string called "encrypted" to the server. Make sure that you run the connection function first!
   public void sendstring() throws Exception//Kobe will work on this
   {
            output = new DataOutputStream(socket.getOutputStream());
           
            output.writeUTF(this.encrypted);
   }
   
   
   
   //Sets public key "publickey" by getting input from server. Make sure that you are connected to the server first!
   public void setpublickey() throws Exception
   {
            //takes input from terminal 
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();//You have to use flush and initialize the ObjectInputStream right after initializing ObjectOutputStream or the object output and input streams won't work
            in = new ObjectInputStream(socket.getInputStream()); 
            
            this.key = in.readObject();//ObjectOutputStream will take "PublicKey" objects just fine, but ObjectInputStream won't initialize a "PublicKey" type this way. 
            //This is why I put it in Object "Key"

	this.publickey = (PublicKey) this.key;//I then static cast "Key" to type "PublicKey" in order to ensure it will work with encryption functions properly.
   }
   

   
   
   //Sets up the connection to the server. Make sure that the server has an open port and is actually running first!
   public void connect(String address, int port) throws Exception
   {
       this.port = port;
       this.address = address;

            socket = new Socket(this.address, this.port); 
            System.out.println("Connected"); 
   }
   
   public void setstring()//Credit to Moshe for this
   {
       String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
       String randomString = "";
              
       Random rand = new Random();
       
       //Random Length of String
       int length;
       
       length = rand.nextInt(98 + 1);
       
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
