package net.hb.pro;

import  java.net.*;
import  java.io.*;
import  java.util.*; 

public class MultiServerTest {
	private  ArrayList<Socket>  clients = new  ArrayList<Socket>( ) ; 
	
	public  class WorkerThread  extends  Thread {
		private  Socket sk= null ;  
		public WorkerThread( Socket  socket){ this.sk=socket; }
		public void run( ){
			String ip=null, msg=null ;
				try{
					clients.add(sk) ;  
					InputStreamReader  isr = new  InputStreamReader( sk.getInputStream() ) ; 
					BufferedReader br = new  BufferedReader( isr) ;
					ip=sk.getInetAddress().getHostAddress() ;
					
					msg = ip +"주소 접속" ;
					//다른클라이언트에게 입장 사실 알림
					for( Socket s: clients) {
						PrintWriter  pw = new PrintWriter( s.getOutputStream(), true) ;
						pw.println( msg) ; 
					} 	System.out.println(msg) ;
					
					//다른클라이언트에게 메세지 전달 				
					while( (msg=br.readLine()) != null) {
						for( Socket s: clients) {
							PrintWriter  pw = new PrintWriter( s.getOutputStream(), true) ;
							pw.println( msg) ; 
						}System.out.println("while for(Sockets: clients) " + msg) ;
					}
				}catch(Exception ex1) {
					msg = ip+"주소 퇴장!!!" ;
						try{
						//다른클라이언트에게 퇴장 사실 알림 	
							for( Socket s: clients) {
								PrintWriter  pw = new PrintWriter( s.getOutputStream(), true) ;
								pw.println( msg) ; 
							} System.out.println(msg) ;
						}catch(Exception ex2) {  }
					System.out.println(msg) ;
					clients.remove(sk) ;
				} //ex1 end
		} //end
	} //내부클래스 end

	public void serverStart( ) {
		 try{
			 ServerSocket  ss = new  ServerSocket(8000) ;
			 System.out.println("ServerSerivceStart~~~~~11:30") ;
			 while(true){
				 Socket sk=ss.accept() ;
				 WorkerThread  wt = new WorkerThread(sk) ; 
				 wt.start() ;
			 }
		 }catch(Exception ex) { }
	} //end
	
	public static void main(String[] args) {
		MultiServerTest  mst = new MultiServerTest( ) ;
		mst.serverStart() ; 
	} //end

} // class End
