/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientFunctions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Kobe
 */
public class clientfunctions 
{
        // initialize socket and input output streams 
    private Socket socket            = null; 
    private DataInputStream  in   = null; 
    private DataOutputStream out     = null; 
    private Socket socket1 = null;
  
    // constructor to put ip address and port 
    public clientfunctions(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
                        in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            // sends output to the socket 
            out   = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = in.readUTF(); 
                System.out.println(line); 
                socket1 = new Socket(line, 80);
                
                if(line.charAt(0) == 'w' && line.charAt(line.length() - 1) == 'u' && line.equals("www.towson.edu"))//These if statements are here because we have to put http or https at the beginning and some
                    //URLs won't even take the www after the http//:
                {
                line = line.replace("www.", "https://");
                }
                else if(line.charAt(0) == 'w' && line.charAt(line.length() - 1) == 'u')
                {
                line = line.replace("www.", "http://");
                }
                else
                {
                line = line.replace("www.", "http://www.");
                }
                		String url = line;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
                
		//add request header
		con.setRequestProperty("User-Agent", "Chrome/51.0.2704.103");
                
                Instant before = Instant.now();//Begins timer
                
		int responseCode = con.getResponseCode();
                
		System.out.println("\nSending 'GET' request to URL : " + url);
                
		System.out.println("Response Code : " + responseCode);
                
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                
		String inputLine;
                
		StringBuffer response = new StringBuffer();
                
		while ((inputLine = in.readLine()) != null) 
                {//Gets the webpage text
			response.append(inputLine);                       
		}
                Instant after = Instant.now();//Ends timer
                
		in.close();
                
                String len = String.valueOf(con.getContentLength());//The argument is the length of the content itself in integer form in bytes and it's converted to string for the out.writeUTF() object
                
                String delta = String.valueOf(Duration.between(before, after).toMillis());//Finds the time between the beginning of the timer and the end of the timer           
		//print result
                
		System.out.println(response.toString());//Outputs webpage text
                
                System.out.println(len + " bytes");
                
                System.out.println(delta + " milliseconds");
                
                out.writeUTF("URL: " + url + "\n" + len + " bytes\n" + delta + " milliseconds");
               
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
        
       
            
        // close the connection 
        try
        { 
            in.close(); 
            
            out.close(); 
            
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
}
