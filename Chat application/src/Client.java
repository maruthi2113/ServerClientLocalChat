import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
Socket socket;
BufferedReader br;
PrintWriter out;

    
  public Client() {
		try {
			System.out.println("sending request to server");
			socket= new Socket("127.0.0.1", 7775);
			br= new BufferedReader(new InputStreamReader
	    			(socket.getInputStream()));
	        out= new PrintWriter(socket.getOutputStream());
	       
			
			startReading();
	        startWriting();
		} catch (Exception e) {
			//e.getStackTrace();
		}
	}
	
	 public void startReading() {
	    	Runnable r1=()->{
	    		System.out.println("reader started");
	    		try {
	    		while(true)
	    		{
	    			
						String msg=br.readLine();
						if(msg.equals("exit"))
						{
							System.out.println("server ended chat");
							socket.close();
							break;
						}
						System.out.println("Server: "+msg);
					}
	    		}
	    		 catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
	    			 System.out.println("connection closed");
					}
	    	};
	    	new Thread(r1).start();
	    }
	    public void startWriting() {
	    	Runnable r2=()->{
	    		System.out.println("writer started...");
	    		try {
	    		while(!socket.isClosed()) {
	    			
						BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();
					
					out.println(content);
					out.flush();//flush the content of the buffer to the output stream.
					if(content.equals("exit")) {
						socket.close();
						break;
					}
	    		}
	    		}
	    		catch(Exception e)
	    		{
	    			//e.printStackTrace();
	        		System.out.println("connection closed");

	    		}
	    		

	    	};
	    	new Thread(r2).start();

	    }

	public static void main(String[] args) {
		System.out.println("this is client side");
    new Client();
	}

}
