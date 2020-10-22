
public class server 
{
        String privatekey, publickey, encrypted, decrypted;
    
    server(String encrypted)
    {
        this.privatekey = "";
        this.publickey = "";
        this.encrypted = encrypted;
        this.decrypted = "";
    }
    
    public String decrypt()//Kenny and Shawn should work
    {
        //Do decryption with "encrypted" and "privatekey"
        return decrypted;
    }
    
    public String print()//Kobe will work on this
    {
        
        return " ";
        
    }
    
    public String recieve(String encrypted)//Kobe will work on this
    {
        this.encrypted = encrypted;
        return " ";
    }
       public void send()//Kobe will work on this
   {
       
   }
    
}
