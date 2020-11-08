package App;

import ClientFunctions.clientfunctions;

public class client 
{ 
    
    public static void main(String args[]) throws Exception
    { 
        client clien = new client();

        clientfunctions client = new clientfunctions(); 
        
        client.connect("127.0.0.1", 6789);//Connects to server after servertest is run

        client.setpublickey();//Waits for input from server before setting the public key
        
        client.setstring();
        
        client.encrypt();
        
        client.print();
        
        client.sendstring();
        
    } 
    
} 


