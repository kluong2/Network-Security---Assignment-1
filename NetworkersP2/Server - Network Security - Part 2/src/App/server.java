package App;

import ServerFunctions.serverfunctions;

public class server 
{
    
    public static void main(String args[]) throws Exception
    { 
        
        serverfunctions server = new serverfunctions(); 
        
        server.openport(6789);//Server will wait for client to connect before continuing past this point
        
        server.setprivatekey();
        
        server.setmypublickey();//Calculates the public key
        
        server.sendkey();//Sends public key to the client
        
        server.getotherpublickey();
        
        server.keyagreement();
        
        server.setsharedsecret();
        
        server.setsharedkey();
        
        server.receivestring();
        
        server.decrypt();
        
        server.print();
        
    } 
    
}
