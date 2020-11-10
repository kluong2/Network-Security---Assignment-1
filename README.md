# Network-Security---Assignment-1
How to run the program:
For this program to be executed correctly. The user has to run the server first. Then run the client after the server.  


How the program works:
Running the server first will create an avaliable connection on port 6789 for the client. The server will wait until the client connects to the server.
The client will connect to the server with a loop back address and the port port number. Once that happens both the client and server creates a private key and public key.
Then the server sends their public key to the client and vice versa. After receving the key both server and client decide on a shared key agreement. 
Once both the server and client decide on a shared key. The client would generate a random string and encrypt the string with the shared key using bouncy castle. 
Lastly, the client sends the encrypted text back to the server, where the server decrypts the text with the shared key and using bouncy castle. 
