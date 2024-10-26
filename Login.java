package ProjectOrder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//自訂義JPanel背景
class Custom1Panel extends JPanel {
	 private ImageIcon backgroundImage;

 public Custom1Panel() {
 	backgroundImage = new ImageIcon("images/paper.jpg");
     setLayout(null); // 使用絕對佈局
     setOpaque(false); // 使面板透明
 }

 @Override
 protected void paintComponent(Graphics g) {
     super.paintComponent(g);
     g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
 }
}

public class Login extends JFrame implements ActionListener {
	
	private static JFrame f;
	private static Custom1Panel p;
	private static JRadioButton rb1,rb2;
	private static JLabel l1,l2,l3;
	private static JTextField t1;
	private static JButton b;
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/foodorder?serverTimezone=Asia/Taipei";
	static final String USER = "root";
	static final String PASSWORD = "hungter7";
	
	
	 Login () {
		 
		 try {
	            Class.forName(JDBC_DRIVER);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	
		f=new JFrame("點餐系統");
		p=new Custom1Panel();
		p.setBounds(0,0,300,260);
		p.setLayout(null);
		p.setBackground(Color.cyan);
		
		rb1 = new JRadioButton("內用");
		rb1.setBounds(45,60,100,50);  
//		rb1.setBackground(Color.cyan);
		rb1.setFont(new Font("標楷體", Font.CENTER_BASELINE, 23));
		rb1.addActionListener(this);
		rb1.setOpaque(false);
		p.add(rb1);
		
		rb2 = new JRadioButton("外帶");
		rb2.setBounds(145,60,100,50);  
//		rb2.setBackground(Color.cyan);
		rb2.setFont(new Font("標楷體", Font.CENTER_BASELINE, 23));
		rb2.addActionListener(this);
	    rb2.setOpaque(false);
		p.add(rb2);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(rb1);bg.add(rb2); 
		
		ImageIcon icon = new ImageIcon("images/coffee.png");
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setBounds(175, 7, icon.getIconWidth(), icon.getIconHeight());
		p.add(imageLabel);

		
		l1 = new JLabel("請輸入桌號:");
		l1.setBounds(40,102,90,50);  
//		l1.setBackground(Color.cyan);
		l1.setFont(new Font("標楷體", Font.CENTER_BASELINE, 15));
		p.add(l1);
		
		l2 = new JLabel("桌");
		l2.setBounds(190,102,90,50);  
//		l2.setBackground(Color.cyan);
		l2.setFont(new Font("標楷體", Font.CENTER_BASELINE, 15));
		p.add(l2);
		
		l3 = new JLabel("歡迎來到早秋咖啡");
		l3.setBounds(10,5,200,30);  
//		l3.setBackground(Color.cyan);
		l3.setFont(new Font("新宋体", Font.CENTER_BASELINE, 20));
		p.add(l3);
		
		t1 = new JTextField();
		t1.setBounds(135,115,50,23);  
		t1.setFont(new Font("標楷體", Font.CENTER_BASELINE, 17));
		t1.setHorizontalAlignment(JTextField.CENTER);
		p.add(t1);
		
		b = new JButton("確認");
		b.setBounds(100,160,80,40);  
		b.setFont(new Font("微軟正黑體", Font.CENTER_BASELINE, 15));
		b.addActionListener(this);
		p.add(b);
		
		l1.setVisible(false);
		l2.setVisible(false);
		t1.setVisible(false);
		
		f.add(p);
		f.setSize(300,260);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);	
	
	}
	 
	 public void actionPerformed(ActionEvent e) {  
		 if (e.getSource() == rb1) { // 內用被選中
		        l1.setVisible(true); // 顯示桌號標籤
		        l2.setVisible(true); // 顯示"桌"標籤
		        t1.setVisible(true); // 顯示文本框
		    } else if (e.getSource() == rb2) { // 外帶被選中
		        l1.setVisible(false); // 隱藏桌號標籤
		        l2.setVisible(false); // 隱藏"桌"標籤
		        t1.setVisible(false); // 隱藏文本框
		    }
		 if (e.getSource() == b) { // 確認按鈕被點擊
			 String tableNumber = t1.getText();
			 boolean isDiningIn = rb1.isSelected();
			 Connection conn = null;
	         PreparedStatement pstmt = null;
			 try {
				 Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
				 
		            // 連接到資料
		            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		            String sql = "INSERT INTO insidetakeout (date, dining_type, table_number) VALUES (?, ?, ?)";
		            pstmt = conn.prepareStatement(sql);
		            
		            pstmt.setTimestamp(1, currentTimestamp); // order_time
		            pstmt.setString(2, isDiningIn ? "內用" : "外帶");
		            pstmt.setString(3, isDiningIn ? tableNumber : null); // 外帶時設為 NULL
		            
		            
		            pstmt.executeUpdate();
		           
		        } catch (SQLException ex) {
		            ex.printStackTrace(); // 輸出錯誤訊息
		            JOptionPane.showMessageDialog(this, "資料庫錯誤: " + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
		        }finally {
	                try {
	                    if (pstmt != null) pstmt.close();
	                    if (conn != null) conn.close();
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                }
	            }

		        
		        if (isDiningIn) {
		            System.out.println("內用，桌號: " + tableNumber + " 桌");
		        } else {
		            System.out.println("外帶");
		        }
		        
		        int totalPrice = 0;
		        new GUI(tableNumber, isDiningIn, totalPrice); // 創建並顯示 Menu 視窗
		        f.dispose(); // 關閉當前 Login 視窗
		 }
	 }

	public static void main(String[] args) {		
		new Login();	
	}	

}
