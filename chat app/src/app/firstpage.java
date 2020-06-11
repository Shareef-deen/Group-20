package app;

import client.Client;

import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.sql.*;



public class firstpage extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private Panel panel_1;
	private JPanel panel;
	private JPasswordField logpass;
	private JTextField logtel;
	private JTextField signname;
	private JTextField signtel;
	private JTextField email;
	private JPasswordField signpass;
	private JPasswordField confpass;
	private JButton register = new JButton("Sign up");
	private JButton login = new JButton("LOGIN");
	private Socket connection;
	private String serverIP = "127.0.0.1";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		 try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(firstpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(firstpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(firstpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(firstpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					firstpage frame = new firstpage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public firstpage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 533);
		contentPane = new JPanel(); /**{public void paintComponent(Graphics g) {
			Image img = Toolkit.getDefaultToolkit().getImage(
					first.class.getResource("/images/pho.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}};*/
		contentPane.setBackground(Color.WHITE);
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		panel = new JPanel();
		panel.setBounds(0, 0, 984, 62);
		panel.setBackground(new Color(0, 0, 139));
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		login.setBounds(875, 21, 99, 30);
		login.addActionListener(this);
		panel.add(login);
		
		logpass = new JPasswordField();
		logpass.setBounds(714, 24, 138, 25);
		logpass.setToolTipText("Password");
		panel.add(logpass);
		
		logtel = new JTextField();
		logtel.setBounds(426, 21, 144, 25);
		logtel.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(logtel.getText().equals("contact"))
					logtel.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if(logtel.getText().equals(""))
					logtel.setText("contact");
			}
			
		});
		panel.add(logtel);
		logtel.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("password");
		lblNewLabel.setForeground(new Color(240, 255, 255));
		lblNewLabel.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-password-16 (1).png")));
		lblNewLabel.setBounds(626, 21, 78, 30);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tel num");
		lblNewLabel_1.setForeground(new Color(248, 248, 255));
		lblNewLabel_1.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-number-pad-16.png")));
		lblNewLabel_1.setBounds(351, 25, 68, 22);
		panel.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 63, 499, 431);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(firstpage.class.getResource("/images/photo_2.jpg")));
		lblNewLabel_2.setBounds(6, 17, 546, 378);
		panel_2.add(lblNewLabel_2);
		
		signname = new JTextField();
		signname.setBounds(749, 172, 187, 28);
		contentPane.add(signname);
		signname.setColumns(10);
		
		signtel = new JTextField();
		signtel.setBounds(749, 212, 187, 28);
		contentPane.add(signtel);
		signtel.setColumns(10);
		
		email = new JTextField();
		email.setBounds(749, 252, 187, 28);
		contentPane.add(email);
		email.setColumns(10);
		
		signpass = new JPasswordField();
		signpass.setBounds(749, 292, 187, 28);
		contentPane.add(signpass);
		
		confpass = new JPasswordField();
		confpass.setBounds(749, 334, 187, 28);
		contentPane.add(confpass);
		
		JLabel lblName = new JLabel("Name");
		lblName.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-username-32.png")));
		lblName.setBounds(637, 172, 81, 22);
		contentPane.add(lblName);
		
		JLabel lblNewLabel_3 = new JLabel("Tel num");
		lblNewLabel_3.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-telephone-16.png")));
		lblNewLabel_3.setBounds(637, 212, 81, 22);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Email");
		lblNewLabel_4.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-email-16.png")));
		lblNewLabel_4.setBounds(637, 252, 81, 22);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Password");
		lblNewLabel_5.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-password-16.png")));
		lblNewLabel_5.setBounds(637, 292, 81, 22);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Confirm pass");
		lblNewLabel_6.setIcon(new ImageIcon(firstpage.class.getResource("/images/icons8-password-16.png")));
		lblNewLabel_6.setBounds(637, 334, 100, 22);
		contentPane.add(lblNewLabel_6);
		
		
		register.setBounds(789, 394, 124, 28);
		register.addActionListener(this);
		contentPane.add(register);
		
		
}
	
	 public void startRunning(){
	        try{
	            connectToServer();
	            
	        }catch (IOException ioException){
	            ioException.printStackTrace();
	        }
	    }

	private void connectToServer() throws IOException{
        //showMessage("\n Attempting to connect... \n ");
        connection = new Socket(InetAddress.getByName(serverIP),1234);
        //showMessage("\n Connected to "+ connection.getInetAddress().getHostName());
        //showMessage("\n Connected to server");

    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == register) {
			int x =0;
			String name = signname.getText();
			String cont = signtel.getText();
			String mail = email.getText();
			char[] p1 = signpass.getPassword();
			char[] p2 = confpass.getPassword();
			String pass = new String(p1);
			String cpass = new String(p2);
			if(pass.equals(cpass)) {
				  try {
	                    Class.forName("org.sqlite.JDBC");
	                    Connection con = DriverManager.getConnection("jdbc:sqlite:chatapp.db");
	                    PreparedStatement ps = con.prepareStatement("insert into user values(?,?,?,?)");
	                    ps.setString(1, name);
	                    ps.setString(2, cont);
	                    ps.setString(3, mail);
	                    ps.setString(4, pass);
	                    
	                    x++;
	                    if (x > 0) {
	                    	ps.executeUpdate();
	                        JOptionPane.showMessageDialog(register, "Sign up successful");
	                        signname.setText("");
	                        signtel.setText("");
	                        email.setText("");
	                        signpass.setText("");
	                        confpass.setText("");
							Client clientObject = new Client("127.0.0.1");
							clientObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							clientObject.startRunning();
	                        this.dispose();
	                    }
	                } catch (Exception ex) {
	                    System.out.println(ex);
	                }
			} else {
                JOptionPane.showMessageDialog(register, "Password Does Not Match");
            }
			
		}
		else if(e.getSource()== login) {
			String cont = logtel.getText();
			char [] p1 = logpass.getPassword();
			String pass = new String(p1);
			
			try{
	            Class.forName("org.sqlite.JDBC");
	            Connection con = DriverManager.getConnection("jdbc:sqlite:chatapp.db");

	            Statement stmt = con.createStatement();
	                ResultSet ps = stmt.executeQuery("select password from user where contact='"+cont+"'");
				String pas =ps.getString("password");
	            if(pass.equals(pas)){

	                JOptionPane.showMessageDialog(login, "Log in successful");

	            }else {
	                System.out.println(pas+"\n"+pass);
	                JOptionPane.showMessageDialog(login, "wrong password ");
	            }

	            }  catch (Exception ex) {
	                System.out.println(ex);
	            }
			
		}
		
	}
}
