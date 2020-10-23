package TestApps;

import ServerFunctions.serverfunctions;

public class servertest 
{
    
    public static void main(String args[]) 
    { 
        
        serverfunctions server = new serverfunctions(); 
        
        server.openport(6789);
        
       
        
        
    } 
    
}
