# Network-Security---Assignment-1
How to run the program:
For both part 1 and part 2 to work correctly, the user has to run the server first and then the client.

How the part 1 works:
Running the server first will create an avaliable connection on port 6789 for the client. The server will wait until the client connects to the server.
The client will connect to the server with a loop back address and the port number. Once that happens, the server creates a public key and sends it to the client.
After receving the key, the client generates a random string and encrypts it with the public key using the Bouncy Castle security provider. 
Lastly, the client sends the encrypted text to the server, where the server decrypts the text using its private key with Bouncy Castle.

How the part 2 works:
Running the server first will create an avaliable connection on port 6789 for the client. The server will wait until the client connects to the server.
The client will connect to the server with a loop back address and the port number. Once that happens, both the client and server creates a private key and public key.
The server then sends their public key to the client and vice versa. After receving the key, both server and client decide on a shared key agreement. 
Once both the server and client decide on a shared key. The client generates a random string and encrypts the string with the shared key. 
Lastly, the client sends the encrypted text back to the server, where the server decrypts the text with the shared key.
