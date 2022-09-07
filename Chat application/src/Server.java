import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;  // to create an applet
public class Server {
    
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public Server() throws IOException  //constructer 
    {    try {
    	server= new ServerSocket(7775);
    	System.out.println("server is ready to accept");
    	System.out.println("waiting...");
    	socket=server.accept();
    	br= new BufferedReader(new InputStreamReader
    			(socket.getInputStream()));
        out= new PrintWriter(socket.getOutputStream());
        startReading();
        startWriting();
        
    }
    catch (Exception e) {
    	e.printStackTrace();
		// TODO: handle exception
	}
    }
    public void startReading() {
    	Runnable r1=()->{
    		System.out.println("reader started");
    		try {
    		while(true&&!socket.isClosed())
    		{
    			
					String msg=br.readLine();
					if(msg.equals("exit"))
					{
						System.out.println("endedd chat");
						socket.close();
						break;
					}
					System.out.println("Client: "+msg);
			
    		}
    		}
    		catch(Exception e)
    		{
    			//e.printStackTrace();
    			System.out.println("Conection closed");
    		}
    	
    	};
    	new Thread(r1).start();
    }
    public void startWriting() {
    	Runnable r2=()->{
    		System.out.println("writer started...");
    		try {
    		while(true&& !socket.isClosed()) {
    			
					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
				String content=br1.readLine();
				out.println(content);
				out.flush();//flush the content of the buffer to the output stream.
    		   	     if(content.equals("exit"))
    		   	     {
    		   	    	 socket.close();
    		   	    	 break;
    		   	     }
    	         	} 
    		}
    		catch (Exception e) {
              //e.printStackTrace();
    		System.out.println("connection closed");
    		}
    		
    	};
    	new Thread(r2).start();

    }
    
	public static void main(String[] args) throws IOException {
		System.out.println("server started my friend");
		 new Server(); //object

	}

}
