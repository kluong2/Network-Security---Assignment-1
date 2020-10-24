package TestApps;

import ServerFunctions.serverfunctions;

public class servertest 
{
    
    public static void main(String args[]) throws Exception
    { 
        
        serverfunctions server = new serverfunctions(); 
        
        server.openport(6789);
        
        server.setpublickey();
        
        server.sendkey();
        
        
    } 
    
}
