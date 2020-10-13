
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
    //initialize socket and input stream 
    private Socket          socket   = null; 
    
    private ServerSocket    server   = null; 
    
    private DataInputStream in       =  null; 
    
    private DataOutputStream out = null;
    
    public serverfunctions (int port) 
    { 
        
        try
        { 
            Scanner input = new Scanner(System.in);
            
            server = new ServerSocket(port); 
            
            System.out.println("Server started"); 
  
            System.out.println("Waiting for a client ..."); 
  
            socket = server.accept(); 
            
            System.out.println("Client accepted"); 
            
            System.out.println("Enter in the name of a website or server starting with www for the client to connect to: ");
            
            String domain = input.nextLine();
            
            out = new DataOutputStream(socket.getOutputStream()); 
            
            
            // takes input from the client socket 
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream())); 
            
            out.writeUTF(domain);//Outputs the name typed in to the client server
            
            String line = "";
            
  
            
            while (!line.equals("Over")) //This reads whatever is taken from the client
            { 
                try
                { 
                    line = in.readUTF(); 
                    
                    System.out.println(line); 
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            }
            
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close(); 
            in.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }

    } 

}
