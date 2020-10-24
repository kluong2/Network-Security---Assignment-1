package TestApps;
import java.net.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.time.*;
import ClientFunctions.clientfunctions;
public class clienttest 
{ 
    
    public static void main(String args[]) 
    { 
        
        clientfunctions client = new clientfunctions(); 
        
        client.connect("127.0.0.1", 6789);

        client.setpublickey();
    } 
    
} 


