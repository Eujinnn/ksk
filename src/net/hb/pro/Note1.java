package net.hb.pro;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Note1 extends Frame implements Runnable , ActionListener, WindowListener   {
		private Socket sc =null;
		Choice ch2=new Choice();
		private  int port = 8000 ;
		private String user ="";
		private String host ="";  
		private  JButton hostcl = new JButton("����");
		private  JButton jname = new JButton("����");
		private  JButton jname2 = new JButton("����");
		private  JButton Jbtnsend = new JButton(" Send ");
		private  JButton Jbtnexit  = new JButton(" ������ ");
		private  JButton del  = new JButton("�����");
		private  JTextField tf  = new JTextField(20);
		private  JTextField tf2 = new JTextField(10);
		private  JTextField tf3 = new JTextField(11);
		private  TextArea  ta = new TextArea(20,70);
		
		
		
		private  JPanel  p1 = new JPanel( ); 
		private  JPanel  p1_n = new JPanel( ); 
		private  JPanel  p1_c = new JPanel( ); 
		private  JPanel  p1_s = new JPanel( );
		
		private  JPanel  p2 = new JPanel( ); 
		private  JPanel  p2_n = new JPanel( );
		private  JPanel  p2_c = new JPanel( ); 
		private  JPanel  p2_s = new JPanel( );
		
		private JPanel  p3 = new JPanel( );
		private JPanel  p4 = new JPanel( );
		
		private  Label  info = new Label("message:");
		private  Label  nickname = new Label("�г���:");
		private  Label  hostadd = new Label("Server:");
		private  Font  font = new Font("�ü�ü", Font.BOLD, 18);
		private String name = "";
		
		private List list1 = new List(20,true);
		//�޴��߰�
		private  Menu mfile = new Menu("����");
		private  Menu mhelp = new Menu("����");
		
		private  MenuItem  mf_new = new MenuItem("���θ����");
		private  MenuItem  mf_save = new MenuItem("����");
		private  MenuItem  mf_open = new MenuItem("����");
		private  MenuItem  mf_exit = new MenuItem("����");		
		
		OutputStream out;
		
	public Note1( ){
			//�޴����̱�
			mfile.add(mf_new);
			mfile.addSeparator();
			mfile.add(mf_save);
			mfile.add(mf_open);
			mfile.addSeparator();
			mfile.add(mf_exit);
		
			
			MenuBar  mb = new MenuBar( );
			mb.add(mfile);   mb.add(mhelp);
			setMenuBar(mb);
			
			//1�ܰ�  �ǳڿ� ��ư, �ؽ�Ʈâ ���̱�
			tf3.add(ch2);  
			p1_n.add(hostadd);p1_n.add(tf3);
			p1_n.add(nickname);p1_n.add(tf2);p1_n.add(jname);p1_n.add(jname2);
			p1_c.add(ta);
			p1_s.add(info); p1_s.add(tf); p1_s.add(Jbtnsend);
			
			p2.setLayout(new BorderLayout());
			p2.add(list1,BorderLayout.CENTER);
			p2.add("South",p3);
			p2.setBounds(500, 25, 178, 347);
			p3.add(Jbtnexit,BorderLayout.SOUTH);
			p3.add(del);
		  
			//p2.add(Jbtnexit,BorderLayout.SOUTH);	
			ch2.addItem("203.236.209.116");
			ch2.addItem("203.236.209.115");
			ch2.addItem("203.236.209.114");
			//2�ܰ�  ta�� �߾ӿ� ���̱�
			add("Center",p1); 
			add("East",p2);
			
			p1.add("North",p1_n);
			p1.add("Center",p1_c);
			p1.add("South",p1_s);
		
			//3�ܰ�  add( )�޼ҵ�� ȭ�鸸���
			this.setTitle("ä��â�����");
			this.setBackground(new Color(180,180,180));
			this.setBounds(200, 200, 800, 500);
			this.setVisible(true);
			
			//4�ܰ� �̺�Ʈ�׼�ó���ϵ��� ��ü.add~~~Listener( )
			this.addWindowListener( this );
			Jbtnsend.addActionListener(this);
			tf.addActionListener(this);
			Jbtnexit.addActionListener(this);
			
			jname.addActionListener( this);
			jname2.addActionListener( this);
			
			mf_new.addActionListener(this);
			mf_open.addActionListener(this);
			mf_save.addActionListener(this);
			mf_exit.addActionListener(this);
			del.addActionListener(this);

		}//end
		
		public static void main(String[] args) {
			Note1 pad = new Note1( );
			pad.connect();
			
		}//main end

		public void actionPerformed(ActionEvent ae){
			if(ae.getSource()==jname) {
				if(tf2.getText().equals("")){ta.append(">>>�г����� �Էµ��� �ʾҽ��ϴ�.\n");}
			
				else {name(); hostadd();connect();}
				}
			
			else if(ae.getSource()==Jbtnexit){ exit( ); }
			else if(ae.getSource()==tf){ message( );}
			else if(ae.getSource()==Jbtnsend){ message( ); }
			else if(ae.getSource()==this.mf_exit){ exit( ); }
			else if(ae.getSource()==this.mf_new){	this.clear();}
			else if(ae.getSource()==mf_open){ file_open( ); }
			else if(ae.getSource()==mf_save){ file_save( ); }
			else if(ae.getSource()==del){ this.clear(); }
			else if(ae.getSource()==jname2){ 
				if(tf2.getText().equals("")){ta.append(">>>�г����� �Էµ��� �ʾҽ��ϴ�.\n");}
				else if(tf2.getText().equals(user)){ta.append(">>>�г����� �����մϴ�. �ٽ��Է����ּ���.\n");}
				else {name();ta.append(">>>�г����� "+user+" �� ����Ǿ����ϴ�.\n");}
				}
			
		}//actionPerformed(ae) end
		
		public void clear( ){
			ta.setText("");
			tf.setText("");
			tf.requestFocus();
		}//end
		
		public void file_open( ){ //���Ͽ���ó��
			clear( );
			
			FileDialog fd = new FileDialog( this, "����", FileDialog.LOAD);
			fd.setVisible(true);
			String path = fd.getDirectory();
			String file = fd.getFile();
			if(file == null || path==null)return;
			try{
			FileReader fr = new FileReader(path+file);
			BufferedReader br =new BufferedReader(fr);
			while(true){
				String data="";
				data=br.readLine();
				if(data==null)break;
				//ta_out.append(data+"\n");
				out.write((tf.getText()+data+"\n").getBytes());
			}
			
		}catch(Exception ex){}
			
		}//end
		
		public void file_save( ){ //��������ó��
			FileDialog fd = new FileDialog( this, "����", FileDialog.SAVE);
			fd.setVisible(true);
			String path = fd.getDirectory();
			String file = fd.getFile();
			if(file == null || path==null)return;
			File f = new File(path+file);
			try{
				PrintWriter pw = new PrintWriter(f);
				pw.print(ta.getText());
				pw.close();
				this.setTitle(path+file);
				System.out.println("���忡 �����Ͽ����ϴ�");
			}catch(Exception ex){}
		}//end
		
		public void exit( ){
			System.out.println("���α׷������մϴ�");
			System.exit(1);
		}//end
		
		public void name(){
			this.user = tf2.getText();	 
		 }
		
		public void hostadd(){
			
			this.host = ch2.getItem(ch2.getSelectedIndex());
		}
		
		public void message( ){
	try{
			 String str = tf.getText() ; 
			 OutputStream os = sc.getOutputStream() ;
			 PrintWriter  pw = new PrintWriter( os, true) ; 
			 pw.println( user+ "] " + str ) ;
			
			 tf.setText("") ;
			 tf.requestFocus() ; 
	}catch(Exception ex){}
		}//end

		public void windowActivated(WindowEvent arg0) {  }
		public void windowClosed(WindowEvent arg0) {  }
		public void windowClosing(WindowEvent arg0) { exit( );}
		public void windowDeactivated(WindowEvent arg0) {  }
		public void windowDeiconified(WindowEvent arg0) {  }
		public void windowIconified(WindowEvent arg0) {  }
		public void windowOpened(WindowEvent arg0) {  }

		public void connect(){
			try{
				sc=new Socket(host,port);
				ta.setText("�������� ����...");
				Thread tr = new Thread(this);
				tr.start();
			}catch(Exception ex){sc=null;}
		}//end
		
		public void run() {
			String b_host=this.host;
			String str;
			try{
				jname.setEnabled(false); 
				InputStream is = sc.getInputStream();
				BufferedReader br = new BufferedReader( new  InputStreamReader(is) );
				while((str=br.readLine())!=null){
		
					ta.append("\n"+str+"\n");
				}
			}catch(Exception ex){ta.append("�����̻�");}
		}
	
}//class END
