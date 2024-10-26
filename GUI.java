package ProjectOrder;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class NewButton extends JButton {
	private ImageIcon buttonImage;
	private String s;
	private int sizeX;
	private int sizeY;
	
	public NewButton(String s, int sizeX, int sizeY) {
		super(s);
		this.sizeX = sizeX;
		this.sizeX = sizeY;
		setContentAreaFilled(false);
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(31,171,137));
		g.fillRoundRect(0,0,getSize().width-1,getSize().height-1,15,15);
		buttonImage = new ImageIcon("images/button.jpg");
		g.drawImage(buttonImage.getImage(), 0, 0, getWidth(), getHeight(), null);
		super.paintComponent(g);		
	}
	
	protected void paintBorder(Graphics g) {
		g.drawRoundRect(0,0,getSize().width-1,getSize().height-1,15,15);	
	}
}	

// 自訂義JPanel背景
class CustomPanel extends JPanel {
	 private ImageIcon backgroundImage;

    public CustomPanel() {
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

public class GUI implements ActionListener {
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/foodorder?serverTimezone=Asia/Taipei";
	static final String USER = "root";
	static final String PASSWORD = "hungter7";
	
    private int cursonCount = 0;
    private int frenchbreadCount = 0;
    private int pestoCount = 0;
    private int hamcheeseCount = 0;
    private int doublecheeseCount = 0;
    private int frenchtoastCount = 0;
    private int chocolatetoastCount = 0;
    private int cheesetoastCount = 0;
    private int hamCount = 0; 
    private int peanutCount = 0;
    private int lavacakeCount = 0;
    private int chocolatecakeCount = 0;
    private int tilamisucakeCount = 0;
    private int bostoncakeCount = 0;
    private int lemoncakeCount = 0;
    private int expressoCount = 0;
    private int blackcoffeeCount = 0;
    private int cabuchinoCount = 0;
    private int latteCount = 0;
    private int lemoncoffeeCount = 0;
    private int hotchocolateCount = 0;
    private int milkteaCount = 0;
    private int blackteaCount = 0;
    private int honeyteaCount = 0;
    private int jadeteaCount = 0;
    
    private final int curson = 125;
    private final int frenchbread = 90;
    private final int pesto = 200;
    private final int hamcheese = 180;
    private final int doublecheese = 150;
    private final int frenchtoast = 50;
    private final int chocolatetoast = 60;
    private final int cheesetoast = 60;
    private final int hamtoast = 70;
    private final int peanut = 50;
    private final int lavacake = 60;
    private final int chocolatecake = 65;
    private final int tilamisucake = 65;
    private final int bostoncake = 70;
    private final int lemoncake = 70;
    private final int expresso = 70;
    private final int blackcoffee = 80;
    private final int cabuchino = 100;
    private final int latte = 120;
    private final int lemoncoffee = 120;
    private final int hotchocolate = 100;
    private final int milktea = 110;
    private final int blacktea = 100;
    private final int honeytea = 120;
    private final int jadetea = 120;
    
    private String tableNumber;
    private boolean isDiningIn;
    private int totalPrice;
    
    private String currentItem = ""; // 用來追蹤當前選擇的餐點
    
    private static JFrame f;
    private static CustomPanel backgroundPanel; // 背景面板
    private static JPanel buttonPanel; // 控制按鈕的面板
    private static JPanel orderPanel; // 控制訂單的面板
    private static JPanel changemenuPanel;
    private static CustomPanel drinkPanel;
    private static CardLayout cardLayout;
    private static JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,
                           b16,b17,b18,b19,b20,b21,b22,b23,b24,b25;
    private static NewButton food,drink;
    private static JButton  deleteButton,buyButton;
    private static JLabel tabletitle,total,count,title,eattitle,drinktitle,menutitle,
                          coffeetitle;
    private static JSpinner spinner; // 新增的 JSpinner
    private static JTable cartTable; // 使用 JTable 來呈現購物車
    private static DefaultTableModel tableModel; // 用於管理表格數據
    	 
    

   public GUI(String tableNumber, boolean isDiningIn, int totalPrice) {
	   this.tableNumber = tableNumber;
       this.isDiningIn = isDiningIn;
       this.totalPrice = totalPrice;
       
       try {
           Class.forName(JDBC_DRIVER);
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       
        // 初始化視窗和背景面板
        f = new JFrame("點餐系統");
        cardLayout = new CardLayout();
        backgroundPanel = new CustomPanel();
        backgroundPanel.setLayout(cardLayout);

        // 按鈕面板
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false); // 使按鈕面板透明小
        
        //飲料面板
        drinkPanel = new CustomPanel();
        drinkPanel.setLayout(null);
        drinkPanel.setOpaque(false); // 使按鈕面板透明小
        
        // 訂單面板
        orderPanel = new JPanel();
        orderPanel.setLayout(null);
        orderPanel.setOpaque(false); // 使訂單面板透明
        orderPanel.setBounds(810, 10, 370, 745); // 設置訂單面板大小
        orderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 設置黑色邊框，無標題
 
        //更換菜單面板
        changemenuPanel = new JPanel();
        changemenuPanel.setLayout(null);
        changemenuPanel.setOpaque(false); // 使訂單面板透明
        changemenuPanel.setBounds(0, 10, 200, 745); // 設置訂單面板大小
        changemenuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 設置黑色邊框，無標題
                
        food = new NewButton("輕食/甜點",180,50);
        food.setBounds(10,100,180,50);
        food.setFont(new Font("新宋体", Font.BOLD, 16));
        drink = new NewButton("飲料",180,50);
        drink.setBounds(10,150,180,50);
        drink.setFont(new Font("新宋体", Font.BOLD, 16));
        
        // 設置按鈕的點擊事件
        food.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切換到 buttonPanel
                cardLayout.show(backgroundPanel, "buttonPanel");
            }
        });
        
        drink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 切換到 drinkPanel
                cardLayout.show(backgroundPanel, "drinkPanel");
            }
        });

        
        // 添加按鈕到 changemenuPanel
        changemenuPanel.add(food);
        changemenuPanel.add(drink);
        
        // 按鈕：牛肉可頌
        b1 = new JButton("<html>牛肉可頌<br><div style='text-align: center; color: blue;'>$125</div></html>");
        b1.setBounds(210, 180, 180, 80);
        b1.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b1.addActionListener(this);
        buttonPanel.add(b1);

        // 按鈕：法國麵包
        b2 = new JButton("<html>法國麵包<br><div style='text-align: center; color: blue;'>$90</div></html>");
        b2.setBounds(210, 260, 180, 80);
        b2.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b2.addActionListener(this);
        buttonPanel.add(b2);
        
        b3 = new JButton("<html>青醬雞肉帕里尼<br><div style='text-align: center; color: blue;'>$200</div></html>");
        b3.setBounds(210, 340, 180, 80);
        b3.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b3.addActionListener(this);
        buttonPanel.add(b3);
        
        b4 = new JButton("<html>火腿起司帕里尼<br><div style='text-align: center; color: blue;'>$180</div></html>");
        b4.setBounds(210, 420, 180, 80);
        b4.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b4.addActionListener(this);
        buttonPanel.add(b4); 
        
        b5 = new JButton("<html>雙起司帕里尼<br><div style='text-align: center; color: blue;'>$150</div></html>");
        b5.setBounds(210, 500, 180, 80);
        b5.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b5.addActionListener(this);
        buttonPanel.add(b5); 
        
        b6 = new JButton("<html>鮮奶法國吐司<br><div style='text-align: center; color: blue;'>$50</div></html>");
        b6.setBounds(400, 180, 180, 80);
        b6.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b6.addActionListener(this);
        buttonPanel.add(b6); 
        
        b7 = new JButton("<html>榛果巧克力<br><div style='text-align: center; color: blue;'>$60</div></html>");
        b7.setBounds(400, 260, 180, 80);
        b7.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b7.addActionListener(this);
        buttonPanel.add(b7); 
        
        b8 = new JButton("<html>雙層起司<br><div style='text-align: center; color: blue;'>$60</div></html>");
        b8.setBounds(400, 340, 180, 80);
        b8.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b8.addActionListener(this);
        buttonPanel.add(b8);
        
        b9 = new JButton("<html>火腿起司<br><div style='text-align: center; color: blue;'>$70</div></html>");
        b9.setBounds(400, 420, 180, 80);
        b9.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b9.addActionListener(this);
        buttonPanel.add(b9);
        
        b10 = new JButton("<html>花生<br><div style='text-align: center; color: blue;'>$50</div></html>");
        b10.setBounds(400, 500, 180, 80);
        b10.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b10.addActionListener(this);
        buttonPanel.add(b10);
        
        b11 = new JButton("<html>熔岩起司蛋糕<br><div style='text-align: center; color: blue;'>$60</div></html>");
        b11.setBounds(590, 180, 180, 80);
        b11.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b11.addActionListener(this);
        buttonPanel.add(b11);
        
        b12 = new JButton("<html>生乳蒙布朗<br><div style='text-align: center; color: blue;'>$65</div></html>");
        b12.setBounds(590, 260, 180, 80);
        b12.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b12.addActionListener(this);
        buttonPanel.add(b12);
        
        b13 = new JButton("<html>提拉米蘇<br><div style='text-align: center; color: blue;'>$65</div></html>");
        b13.setBounds(590, 340, 180, 80);
        b13.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b13.addActionListener(this);
        buttonPanel.add(b13);
        
        b14 = new JButton("<html>波士頓派<br><div style='text-align: center; color: blue;'>$70</div></html>");
        b14.setBounds(590, 420, 180, 80);
        b14.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b14.addActionListener(this);
        buttonPanel.add(b14);
        
        b15 = new JButton("<html>檸檬塔<br><div style='text-align: center; color: blue;'>$70</div></html>");
        b15.setBounds(590, 500, 180, 80);
        b15.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b15.addActionListener(this);
        buttonPanel.add(b15);
        
     // 按鈕：咖啡類
        b16 = new JButton("<html>義式濃縮<br><div style='text-align: center; color: blue;'>$70</div></html>");
        b16.setBounds(210, 180, 180, 80);
        b16.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b16.addActionListener(this);
        drinkPanel.add(b16);
        
        b17 = new JButton("<html>美式咖啡<br><div style='text-align: center; color: blue;'>$80</div></html>");
        b17.setBounds(210, 260, 180, 80);
        b17.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b17.addActionListener(this);
        drinkPanel.add(b17);
        
        b18 = new JButton("<html>卡布奇諾<br><div style='text-align: center; color: blue;'>$100</div></html>");
        b18.setBounds(210, 340, 180, 80);
        b18.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b18.addActionListener(this);
        drinkPanel.add(b18);
        
        b19 = new JButton("<html>拿鐵<br><div style='text-align: center; color: blue;'>$120</div></html>");
        b19.setBounds(210, 420, 180, 80);
        b19.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b19.addActionListener(this);
        drinkPanel.add(b19);
        
        b20 = new JButton("<html>檸檬西西里<br><div style='text-align: center; color: blue;'>$120</div></html>");
        b20.setBounds(210, 500, 180, 80);
        b20.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b20.addActionListener(this);
        drinkPanel.add(b20);
        
        b21 = new JButton("<html>熱可可<br><div style='text-align: center; color: blue;'>$100</div></html>");
        b21.setBounds(400, 180, 180, 80);
        b21.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b21.addActionListener(this);
        drinkPanel.add(b21);
        
        b22 = new JButton("<html>皇家奶茶<br><div style='text-align: center; color: blue;'>$110</div></html>");
        b22.setBounds(400, 340, 180, 80);
        b22.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b22.addActionListener(this);
        drinkPanel.add(b22);
        
        b23 = new JButton("<html>熟韻紅茶<br><div style='text-align: center; color: blue;'>$100</div></html>");
        b23.setBounds(400, 260, 180, 80);
        b23.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b23.addActionListener(this);
        drinkPanel.add(b23);
        
        b24 = new JButton("<html>蜂蜜柚子綠茶<br><div style='text-align: center; color: blue;'>$120</div></html>");
        b24.setBounds(400,420, 180, 80);
        b24.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b24.addActionListener(this);
        drinkPanel.add(b24);
        
        b25 = new JButton("<html>翡翠檸檬<br><div style='text-align: center; color: blue;'>$120</div></html>");
        b25.setBounds(400,500, 180, 80);
        b25.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        b25.addActionListener(this);
        drinkPanel.add(b25);
           
        buyButton = new JButton("結帳");
        buyButton.setBounds(10, 670, 350, 70);
        buyButton.setFont(new Font("微軟正黑體", Font.BOLD,35));
        buyButton.setForeground(Color.black);
        buyButton.addActionListener(this);

        // 刪除訂單按鈕
        deleteButton = new JButton("刪除訂單");
        deleteButton.setBounds(10, 620, 350, 50);
        deleteButton.setFont(new Font("微軟正黑體", Font.BOLD, 25));
        deleteButton.setBackground(Color.yellow);
        deleteButton.setForeground(Color.red);
        deleteButton.addActionListener(this);

        // 設置購物清單表格
        String[] columnNames = {"品項", "數量", "價格"}; // 欄位標題
        tableModel = new DefaultTableModel(columnNames, 0);
        cartTable = new JTable(tableModel);
        cartTable.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        cartTable.setRowHeight(18);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        cartTable.setDefaultRenderer(Object.class, centerRenderer);
        cartTable.setBackground(Color.cyan);
        cartTable.setShowGrid(false);
        cartTable.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = cartTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("微軟正黑體", Font.BOLD,18));

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBounds(10, 70, 350, 500); // 調整寬度以適應面板

        // 新增 JSpinner，預設值為 0，最小值 0，最大值 100，步長為 1
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setBounds(300, 575, 60, 35);
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        editor.getTextField().setFont(new Font("微軟正黑體", Font.PLAIN, 18)); // 設定字體大小為20pt
        spinner.addChangeListener(e -> updateSpinnerValue());
        
        if (isDiningIn) {
        	tabletitle = new JLabel("桌號: "+tableNumber+"桌");
            tabletitle.setBounds(220, 14, 200, 50);
            tabletitle.setForeground(Color.red);
            tabletitle.setFont(new Font("微軟正黑體", Font.CENTER_BASELINE, 30));
            orderPanel.add(tabletitle);
        }else {
        	tabletitle = new JLabel("外帶");
            tabletitle.setBounds(250, 12, 200, 50);
            tabletitle.setForeground(Color.red);
            tabletitle.setFont(new Font("微軟正黑體", Font.CENTER_BASELINE, 40));
            orderPanel.add(tabletitle);
        }
        
        
        
        menutitle=new JLabel("Menu");
        menutitle.setBounds(413,5,200,100);
        menutitle.setForeground(Color.black);
        menutitle.setFont(new Font("Segoe Script", Font.CENTER_BASELINE, 50));
        buttonPanel.add(menutitle);
        
        eattitle=new JLabel("·輕食｜吐司｜蛋糕");
        eattitle.setBounds(210,90,500,90);
        eattitle.setForeground(Color.black);
        eattitle.setFont(new Font("新宋体", Font.CENTER_BASELINE, 30));
        buttonPanel.add(eattitle);
        
        drinktitle=new JLabel("Menu");
        drinktitle.setBounds(413,5,200,100);
        drinktitle.setForeground(Color.black);
        drinktitle.setFont(new Font("Segoe Script", Font.CENTER_BASELINE, 50));
        drinkPanel.add(drinktitle);
        
        coffeetitle=new JLabel("·咖啡｜飲料");
        coffeetitle.setBounds(210,90,250,90);
        coffeetitle.setForeground(Color.black);
        coffeetitle.setFont(new Font("新宋体", Font.CENTER_BASELINE, 30));
        drinkPanel.add(coffeetitle);
        

        
        title=new JLabel("訂單明細");
        title.setBounds(10, 10, 200, 50);
        title.setForeground(Color.black);
        title.setFont(new Font("微軟正黑體", Font.CENTER_BASELINE, 42));
        

        // 顯示總金額
        total = new JLabel("總金額: $0");
        total.setBounds(10, 565, 200, 50);
        total.setForeground(Color.black);
        total.setFont(new Font("微軟正黑體", Font.BOLD, 25));
        
        count=new JLabel("數量:");
        count.setBounds(240, 565, 200, 50);
        count.setForeground(Color.black);
        count.setFont(new Font("微軟正黑體", Font.BOLD, 23));
        
        
        orderPanel.add(title);
        orderPanel.add(spinner);
        orderPanel.add(total);
        orderPanel.add(buyButton);
        orderPanel.add(count);
        orderPanel.add(scrollPane);
        orderPanel.add(deleteButton);

        // 將面板添加到背景面板（並設置唯一標識符)
        backgroundPanel.add(buttonPanel, "buttonPanel");
        backgroundPanel.add(drinkPanel, "drinkPanel");
        

        // 設置視窗
        
        f.add(changemenuPanel); // 左側切換菜單面板
        f.add(orderPanel);
        f.add(backgroundPanel);
        f.setSize(1200, 800);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout.show(backgroundPanel, "buttonPanel"); // 預設顯示 buttonPanel
        
   }
    // 事件處理：根據按鈕點擊來選擇當前餐點
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            cursonCount++;
            currentItem = "牛肉可頌";
            spinner.setValue(cursonCount);
        } else if (e.getSource() == b2) {
            frenchbreadCount++;
            currentItem = "法國麵包";
            spinner.setValue(frenchbreadCount);
        } else if (e.getSource() == b3) {
        	pestoCount++;
            currentItem = "青醬雞肉帕里尼";
            spinner.setValue(pestoCount);
        }else if (e.getSource() == b4) {
        	hamcheeseCount++;
            currentItem = "火腿起司帕里尼";
            spinner.setValue(hamcheeseCount);
        }else if (e.getSource() == b5) {
        	doublecheeseCount++;
            currentItem = "雙起司帕里尼";
            spinner.setValue(doublecheeseCount);
        }else if (e.getSource() == b6) {
        	frenchtoastCount++;
            currentItem = "鮮奶法國吐司";
            spinner.setValue(frenchtoastCount);
        }else if (e.getSource() == b7) {
        	chocolatetoastCount++;
            currentItem = "榛果巧克力吐司";
            spinner.setValue(chocolatetoastCount);
        }else if (e.getSource() == b8) {
        	cheesetoastCount++;
            currentItem = "雙層起司吐司";
            spinner.setValue(cheesetoastCount);
        }else if (e.getSource() == b9) {
        	hamCount++;
            currentItem = "火腿起司吐司";
            spinner.setValue(hamCount);
        }else if (e.getSource() == b10) {
        	peanutCount++;
            currentItem = "花生吐司";
            spinner.setValue(peanutCount);
        }else if (e.getSource() == b11) {
        	lavacakeCount++;
            currentItem = "熔岩起司蛋糕";
            spinner.setValue(lavacakeCount);
        }else if (e.getSource() == b12) {
        	chocolatecakeCount++;
            currentItem = "生乳蒙布朗";
            spinner.setValue(chocolatecakeCount);
        }else if (e.getSource() == b13) {
        	tilamisucakeCount++;
            currentItem = "提拉米蘇";
            spinner.setValue(tilamisucakeCount);
        }else if (e.getSource() == b14) {
        	bostoncakeCount++;
            currentItem = "波士頓派";
            spinner.setValue(bostoncakeCount);
        }else if (e.getSource() == b15) {
        	lemoncakeCount++;
            currentItem = "檸檬塔";
            spinner.setValue(lemoncakeCount);
        }else if (e.getSource() == b16) {
        	expressoCount++;
            currentItem = "義式濃縮";
            spinner.setValue(expressoCount);
        }else if (e.getSource() == b17) {
        	blackcoffeeCount++;
            currentItem = "美式咖啡";
            spinner.setValue(blackcoffeeCount);
        }else if (e.getSource() == b18) {
        	cabuchinoCount++;
            currentItem = "卡布奇諾";
            spinner.setValue(cabuchinoCount);
        }else if (e.getSource() == b19) {
        	latteCount++;
            currentItem = "拿鐵";
            spinner.setValue(latteCount);
        }else if (e.getSource() == b20) {
        	lemoncoffeeCount++;
            currentItem = "檸檬西西里";
            spinner.setValue(lemoncoffeeCount);
        }else if (e.getSource() == b21) {
        	hotchocolateCount++;
            currentItem = "熱可可";
            spinner.setValue(hotchocolateCount);
        }else if (e.getSource() == b22) {
        	milkteaCount++;
            currentItem = "皇家奶茶";
            spinner.setValue(milkteaCount);
        }else if (e.getSource() == b23) {
        	blackteaCount++;
            currentItem = "熟韻紅茶";
            spinner.setValue(blackteaCount);
        }else if (e.getSource() == b24) {
        	honeyteaCount++;
            currentItem = "蜂蜜柚子綠茶";
            spinner.setValue(honeyteaCount);
        }else if (e.getSource() == b25) {
        	jadeteaCount++;
            currentItem = "翡翠檸檬";
            spinner.setValue(jadeteaCount);
        }
        
        else if (e.getSource() == buyButton) {
        	Connection conn = null;
	        PreparedStatement pstmt = null;
	        
	        try {
				 Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
				 
		            // 連接到資料
		            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		            String sql = "INSERT INTO orders (date, dishname, quantity) VALUES (?, ?, ?)";
		            pstmt = conn.prepareStatement(sql);
		            conn.setAutoCommit(false); // 關閉自動提交
		            // 將每個品項的資訊插入到資料庫
		            if (cursonCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); // 設置時間
		                pstmt.setString(2, "牛肉可頌"); // 設置菜名
		                pstmt.setInt(3, cursonCount); // 設置數量
		                pstmt.addBatch(); // 添加到批處理
		            }
		            if (frenchbreadCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "法國麵包"); 
		                pstmt.setInt(3, frenchbreadCount); 
		                pstmt.addBatch();
		            }if (pestoCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "青醬雞肉帕里尼"); 
		                pstmt.setInt(3, pestoCount); 
		                pstmt.addBatch();
		            }if (hamcheeseCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "火腿起司帕里尼"); 
		                pstmt.setInt(3, hamcheeseCount); 
		                pstmt.addBatch();
		            }if (doublecheeseCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "雙起司帕里尼"); 
		                pstmt.setInt(3, doublecheeseCount); 
		                pstmt.addBatch();
		            }if (frenchtoastCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "鮮奶法國吐司"); 
		                pstmt.setInt(3, frenchtoastCount); 
		                pstmt.addBatch();
		            }if (chocolatetoastCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "榛果巧克力吐司"); 
		                pstmt.setInt(3, chocolatetoastCount); 
		                pstmt.addBatch();
		            }if (cheesetoastCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "雙層起司吐司"); 
		                pstmt.setInt(3, cheesetoastCount); 
		                pstmt.addBatch();
		            }if (hamCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "火腿起司吐司"); 
		                pstmt.setInt(3, hamCount); 
		                pstmt.addBatch();
		            }if (peanutCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "花生吐司"); 
		                pstmt.setInt(3, peanutCount); 
		                pstmt.addBatch();
		            }if (lavacakeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "熔岩起司蛋糕"); 
		                pstmt.setInt(3, lavacakeCount); 
		                pstmt.addBatch();
		            }if (chocolatecakeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "生乳蒙布朗"); 
		                pstmt.setInt(3, chocolatecakeCount); 
		                pstmt.addBatch();
		            }if (tilamisucakeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "提拉米蘇"); 
		                pstmt.setInt(3, tilamisucakeCount); 
		                pstmt.addBatch();
		            }if (bostoncakeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "波士頓派"); 
		                pstmt.setInt(3, bostoncakeCount); 
		                pstmt.addBatch();
		            }if (lemoncakeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "檸檬塔"); 
		                pstmt.setInt(3, lemoncakeCount); 
		                pstmt.addBatch();
		            }if (expressoCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "義式濃縮"); 
		                pstmt.setInt(3, expressoCount); 
		                pstmt.addBatch();
		            }if (blackcoffeeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "美式咖啡"); 
		                pstmt.setInt(3, blackcoffeeCount); 
		                pstmt.addBatch();
		            }if (cabuchinoCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "卡布奇諾"); 
		                pstmt.setInt(3, cabuchinoCount); 
		                pstmt.addBatch();
		            }if (latteCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "拿鐵"); 
		                pstmt.setInt(3, latteCount); 
		                pstmt.addBatch();
		            }if (lemoncoffeeCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "檸檬西西里"); 
		                pstmt.setInt(3, lemoncoffeeCount); 
		                pstmt.addBatch();
		            }if (hotchocolateCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "熱可可"); 
		                pstmt.setInt(3, hotchocolateCount); 
		                pstmt.addBatch();
		            }if (milkteaCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "皇家奶茶"); 
		                pstmt.setInt(3, milkteaCount); 
		                pstmt.addBatch();
		            }if (blackteaCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "熟韻紅茶"); 
		                pstmt.setInt(3, blackteaCount); 
		                pstmt.addBatch();
		            }if (honeyteaCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "蜂蜜柚子綠茶"); 
		                pstmt.setInt(3, honeyteaCount); 
		                pstmt.addBatch();
		            }if (jadeteaCount > 0) {
		                pstmt.setTimestamp(1, currentTimestamp); 
		                pstmt.setString(2, "翡翠檸檬"); 
		                pstmt.setInt(3, jadeteaCount); 
		                pstmt.addBatch();
		            }
		            pstmt.executeBatch();
		            conn.commit(); // 提交更改
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace(); // 輸出錯誤訊息
		            System.err.println("SQLState: " + ex.getSQLState());
		            System.err.println("Error Code: " + ex.getErrorCode());
		            System.err.println("Message: " + ex.getMessage());
		        } finally {
		            try {
		                if (pstmt != null) pstmt.close();
		                if (conn != null) conn.close();
		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
        	showCheckoutScreen();
        	clearOrderDetails(); // 清空訂單明細
        	
        	
        }
        
        else if (e.getSource() == deleteButton) {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow >= 0) {
                String item = (String) tableModel.getValueAt(selectedRow, 0);
                
                if (item.equals("牛肉可頌")) {
                    cursonCount = 0;
                } else if (item.equals("法國麵包")) {
                    frenchbreadCount = 0;
                }else if (item.equals("青醬雞肉帕里尼")) {
                	pestoCount = 0;
                }else if (item.equals("火腿起司帕里尼")) {
                	hamcheeseCount = 0;
                }else if (item.equals("雙起司帕里尼")) {
                	doublecheeseCount = 0;
                }else if (item.equals("鮮奶法國吐司")) {
                	frenchtoastCount = 0;
                }else if (item.equals("榛果巧克力吐司")) {
                	chocolatetoastCount = 0;
                }else if (item.equals("雙層起司吐司")) {
                	cheesetoastCount = 0;
                }else if (item.equals("火腿起司吐司")) {
                	hamCount = 0;
                }else if (item.equals("花生吐司")) {
                	peanutCount = 0;
                }else if (item.equals("熔岩起司蛋糕")) {
                	lavacakeCount = 0;
                }else if (item.equals("生乳蒙布朗")) {
                	chocolatecakeCount = 0;
                }else if (item.equals("提拉米蘇")) {
                	tilamisucakeCount = 0;
                }else if (item.equals("波士頓派")) {
                	bostoncakeCount = 0;
                }else if (item.equals("檸檬塔")) {
                	lemoncakeCount = 0;
                }else if (item.equals("義式濃縮")) {
                	expressoCount = 0;
                }else if (item.equals("美式咖啡")) {
                	blackcoffeeCount = 0;
                }else if (item.equals("卡布奇諾")) {
                	cabuchinoCount = 0;
                }else if (item.equals("拿鐵")) {
                	latteCount = 0;
                }else if (item.equals("檸檬西西里")) {
                	lemoncoffeeCount = 0;
                }else if (item.equals("熱可可")) {
                	hotchocolateCount = 0;
                }else if (item.equals("皇家奶茶")) {
                	milkteaCount = 0;
                }else if (item.equals("熟韻紅茶")) {
                	blackteaCount = 0;
                }else if (item.equals("蜂蜜柚子綠茶")) {
                	honeyteaCount = 0;
                }else if (item.equals("翡翠檸檬")) {
                	jadeteaCount = 0;
                }
                tableModel.removeRow(selectedRow);               
            }
        }
        updateTotal();
        updateCart();
    }
    
    
    private void clearOrderDetails() {
        cursonCount = 0; // 重置牛肉可頌計數
        frenchbreadCount = 0; // 重置法國麵包計數
        pestoCount = 0;
        hamcheeseCount = 0;
        doublecheeseCount = 0;
        frenchtoastCount = 0;
        chocolatetoastCount = 0;
        cheesetoastCount = 0;
        hamCount = 0;
        peanutCount = 0;
        lavacakeCount = 0;
        chocolatecakeCount = 0;
        tilamisucakeCount = 0;
        bostoncakeCount = 0;
        lemoncakeCount = 0;
        expressoCount = 0;
        blackcoffeeCount = 0;
        cabuchinoCount = 0;
        latteCount = 0;
        lemoncoffeeCount = 0;
        hotchocolateCount = 0;
        milkteaCount = 0;
        blackteaCount = 0;
        honeyteaCount = 0;
        jadeteaCount = 0;
        
        spinner.setValue(0);  // 將 JSpinner 的值重置為 0
        tableModel.setRowCount(0); // 清空購物車表格
        updateTotal(); // 更新總金額顯示
    }
    
    private void showCheckoutScreen() {
        // 設置購物清單表格
        String[] columnNames = {"品項", "數量", "價格"}; // 欄位標題
        DefaultTableModel checkoutTableModel = new DefaultTableModel(columnNames, 0);

        // 添加訂單到表格中
        if (cursonCount > 0) {
            checkoutTableModel.addRow(new Object[]{"牛肉可頌", cursonCount, curson});
        }
        if (frenchbreadCount > 0) {
            checkoutTableModel.addRow(new Object[]{"法國麵包", frenchbreadCount, frenchbread});
        }
        if (pestoCount > 0) {
            checkoutTableModel.addRow(new Object[]{"青醬雞肉帕里尼", pestoCount, pesto});
        }
        if (hamcheeseCount > 0) {
            checkoutTableModel.addRow(new Object[]{"火腿起司帕里尼", hamcheeseCount, hamcheese});
        }
        if (doublecheeseCount > 0) {
            checkoutTableModel.addRow(new Object[]{"雙起司帕里尼", doublecheeseCount ,doublecheese});
        }
        if (frenchtoastCount > 0) {
            checkoutTableModel.addRow(new Object[]{"鮮奶法國吐司", frenchtoastCount ,frenchtoast});
        }
        if (chocolatetoastCount > 0) {
            checkoutTableModel.addRow(new Object[]{"榛果巧克力吐司", chocolatetoastCount ,chocolatetoast});
        }
        if (cheesetoastCount > 0) {
            checkoutTableModel.addRow(new Object[]{"雙層起司吐司", cheesetoastCount ,cheesetoast});
        }
        if (hamCount > 0) {
            checkoutTableModel.addRow(new Object[]{"火腿起司吐司", hamCount ,hamtoast});
        }
        if (peanutCount > 0) {
            checkoutTableModel.addRow(new Object[]{"花生吐司", peanutCount ,peanut});
        }
        if (lavacakeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"熔岩起司蛋糕", lavacakeCount ,lavacake});
        }
        if (chocolatecakeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"生乳蒙布朗", chocolatecakeCount ,chocolatecake});
        }
        if (tilamisucakeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"提拉米蘇", tilamisucakeCount ,tilamisucake});
        }
        if (bostoncakeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"波士頓派", bostoncakeCount ,bostoncake});
        }
        if (lemoncakeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"檸檬塔", lemoncakeCount ,lemoncake});
        }
        if (expressoCount > 0) {
            checkoutTableModel.addRow(new Object[]{"義式濃縮", expressoCount ,expresso});
        }
        if (blackcoffeeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"美式咖啡", blackcoffeeCount ,blackcoffee});
        }
        if (cabuchinoCount > 0) {
            checkoutTableModel.addRow(new Object[]{"卡布奇諾", cabuchinoCount ,cabuchino});
        }
        if (latteCount > 0) {
            checkoutTableModel.addRow(new Object[]{"拿鐵", latteCount ,latte});
        }
        if (lemoncoffeeCount > 0) {
            checkoutTableModel.addRow(new Object[]{"檸檬西西里", lemoncoffeeCount ,lemoncoffee});
        }
        if (hotchocolateCount > 0) {
            checkoutTableModel.addRow(new Object[]{"熱可可", hotchocolateCount ,hotchocolate});
        }
        if (milkteaCount > 0) {
            checkoutTableModel.addRow(new Object[]{"皇家奶茶", milkteaCount ,milktea});
        }
        if (blackteaCount > 0) {
            checkoutTableModel.addRow(new Object[]{"熟韻紅茶", blackteaCount ,blacktea});
        }
        if (honeyteaCount > 0) {
            checkoutTableModel.addRow(new Object[]{"蜂蜜柚子綠茶", honeyteaCount ,honeytea});
        }
        if (jadeteaCount > 0) {
            checkoutTableModel.addRow(new Object[]{"翡翠檸檬", jadeteaCount ,jadetea});
        }

        // 計算總金額
        int totalPrice = (cursonCount * curson) + (frenchbreadCount * frenchbread)
        		         +(pestoCount * pesto) +(hamcheeseCount * hamcheese)
        		         +(doublecheeseCount * doublecheese)+(frenchtoastCount * frenchtoast)
        		         +(chocolatetoastCount * chocolatetoast)+(cheesetoastCount * cheesetoast)
        		         +(hamCount * hamtoast) +(peanutCount * peanut)
        		         +(lavacakeCount * lavacake)+(chocolatecakeCount * chocolatecake)
        		         +(tilamisucakeCount * tilamisucake)+(bostoncakeCount * bostoncake)
        		         +(lemoncakeCount * lemoncake)+(expressoCount * expresso)
        		         +(blackcoffeeCount * blackcoffee)+(cabuchinoCount * cabuchino)
        		         +(latteCount * latte)+(lemoncoffeeCount * lemoncoffee)
        		         +(hotchocolateCount * hotchocolate)+(milkteaCount * milktea)
        		         +(blackteaCount * blacktea)+(honeyteaCount * honeytea)
        		         +(jadeteaCount * jadetea);

        // 創建結帳表格
        JTable checkoutTable = new JTable(checkoutTableModel);
        checkoutTable.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        checkoutTable.setRowHeight(30);
        JTableHeader header = checkoutTable.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("微軟正黑體", Font.BOLD,18));
        
        checkoutTable.setShowGrid(false); // 不顯示格子線
        checkoutTable.setIntercellSpacing(new Dimension(0, 0)); // 設置單元格間距為0
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        checkoutTable.setDefaultRenderer(Object.class, centerRenderer);

        // 將表格包裝在滾動面板中
        JScrollPane scrollPane = new JScrollPane(checkoutTable);

        // 創建結帳視窗
        JFrame checkoutFrame = new JFrame("結帳");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setLayout(new BorderLayout()); // 使用邊界佈局

        // 添加表格滾動面板
        checkoutFrame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new GridBagLayout()); // 左对齐
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
     // 显示桌号或外带标签的 JLabel
        String labelText = isDiningIn ? "桌號: " + tableNumber +"桌" : "外帶";
        JLabel statusLabel = new JLabel(labelText);
        statusLabel.setFont(new Font("微軟正黑體", Font.BOLD, 24));
        statusLabel.setForeground(Color.red);
        gbc.gridx = 0; // 第 0 列
        gbc.gridy = 0; // 第 0 行
        gbc.weightx = 1.0; // 占据可用空间
        gbc.anchor = GridBagConstraints.WEST; // 左对齐
        bottomPanel.add(statusLabel, gbc);    
        
     // 添加一个占位组件
        gbc.gridx = 1; // 第 1 列
        gbc.weightx = 0.0; // 不占空间
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(new JPanel(), gbc); // 添加空的 JPanel 作为占位

        // 显示总金额的 JLabel
        JLabel totalLabel = new JLabel("總金額: $" + totalPrice + " 元");
        totalLabel.setFont(new Font("微軟正黑體", Font.BOLD, 30));
        gbc.gridx = 2; // 第 1 列
        gbc.anchor = GridBagConstraints.EAST; // 右对齐
        bottomPanel.add(totalLabel, gbc);

        // 将底部面板添加到结账窗口的南部
        checkoutFrame.add(bottomPanel, BorderLayout.SOUTH);

        checkoutFrame.setSize(400, 500);
        checkoutFrame.setLocationRelativeTo(null);
        checkoutFrame.setVisible(true);
    }
    
    private void updateSpinnerValue() {
        int newValue = (int) spinner.getValue();
        if (currentItem.equals("牛肉可頌")) cursonCount = newValue;
        else if (currentItem.equals("法國麵包")) frenchbreadCount = newValue;
        else if (currentItem.equals("青醬雞肉帕里尼")) pestoCount = newValue;
        else if (currentItem.equals("火腿起司帕里尼")) hamcheeseCount = newValue;
        else if (currentItem.equals("雙起司帕里尼")) doublecheeseCount = newValue;
        else if (currentItem.equals("鮮奶法國吐司")) frenchtoastCount = newValue;
        else if (currentItem.equals("榛果巧克力吐司")) chocolatetoastCount = newValue;
        else if (currentItem.equals("雙層起司吐司")) cheesetoastCount = newValue;
        else if (currentItem.equals("火腿起司吐司")) hamCount = newValue;
        else if (currentItem.equals("花生吐司")) peanutCount = newValue;
        else if (currentItem.equals("熔岩起司蛋糕")) lavacakeCount = newValue;
        else if (currentItem.equals("生乳蒙布朗")) chocolatecakeCount = newValue;
        else if (currentItem.equals("提拉米蘇")) tilamisucakeCount = newValue;
        else if (currentItem.equals("波士頓派")) bostoncakeCount = newValue;
        else if (currentItem.equals("檸檬塔")) lemoncakeCount = newValue;
        else if (currentItem.equals("義式濃縮")) expressoCount = newValue;
        else if (currentItem.equals("美式咖啡")) blackcoffeeCount = newValue;
        else if (currentItem.equals("卡布奇諾")) cabuchinoCount = newValue;
        else if (currentItem.equals("拿鐵")) latteCount = newValue;
        else if (currentItem.equals("檸檬西西里")) lemoncoffeeCount = newValue;
        else if (currentItem.equals("熱可可")) hotchocolateCount = newValue;
        else if (currentItem.equals("皇家奶茶")) milkteaCount = newValue;
        else if (currentItem.equals("熟韻紅茶")) blackteaCount = newValue;
        else if (currentItem.equals("蜂蜜柚子綠茶")) honeyteaCount = newValue;
        else if (currentItem.equals("翡翠檸檬")) jadeteaCount = newValue;
        updateCart();
        updateTotal();
    }

    private void updateTotal() {
        int totalPrice = (cursonCount * curson) + (frenchbreadCount * frenchbread)
		         		 +(pestoCount * pesto) +(hamcheeseCount * hamcheese)
		         		 +(doublecheeseCount * doublecheese)+(frenchtoastCount * frenchtoast)
		         		 +(chocolatetoastCount * chocolatetoast)+(cheesetoastCount * cheesetoast)
		         		+(hamCount * hamtoast)+(peanutCount * peanut)+(lavacakeCount * lavacake)
		         		+(chocolatecakeCount * chocolatecake)+(tilamisucakeCount * tilamisucake)
		         		+(bostoncakeCount * bostoncake)+(lemoncakeCount * lemoncake)
		         		+(expressoCount * expresso)+(blackcoffeeCount * blackcoffee)
		         		+(cabuchinoCount * cabuchino)+(latteCount * latte)
		         		+(lemoncoffeeCount * lemoncoffee)+(hotchocolateCount * hotchocolate)
		         		+(milkteaCount * milktea)+(blackteaCount * blacktea)
		         		+(honeyteaCount * honeytea)+(jadeteaCount * jadetea);
        total.setText("總金額: $" + totalPrice);
    }

    private void updateCart() {
        tableModel.setRowCount(0);
        if (cursonCount > 0) {
            tableModel.addRow(new Object[]{"牛肉可頌", cursonCount, curson});
        }
        if (frenchbreadCount > 0) {
            tableModel.addRow(new Object[]{"法國麵包", frenchbreadCount, frenchbread});
        }
        if (pestoCount > 0) {
            tableModel.addRow(new Object[]{"青醬雞肉帕里尼", pestoCount, pesto});
        }
        if (hamcheeseCount > 0) {
            tableModel.addRow(new Object[]{"火腿起司帕里尼", hamcheeseCount, hamcheese});
        }
        if (doublecheeseCount > 0) {
            tableModel.addRow(new Object[]{"雙起司帕里尼", doublecheeseCount, doublecheese});
        }
        if (frenchtoastCount > 0) {
            tableModel.addRow(new Object[]{"鮮奶法國吐司", frenchtoastCount, frenchtoast});
        }
        if (chocolatetoastCount > 0) {
            tableModel.addRow(new Object[]{"榛果巧克力吐司", chocolatetoastCount, chocolatetoast});
        }
        if (cheesetoastCount > 0) {
            tableModel.addRow(new Object[]{"雙層起司吐司", cheesetoastCount, cheesetoast});
        }
        if (hamCount > 0) {
            tableModel.addRow(new Object[]{"火腿起司吐司", hamCount, hamtoast});
        }
        if (peanutCount > 0) {
            tableModel.addRow(new Object[]{"花生吐司", peanutCount, peanut});
        }
        if (lavacakeCount > 0) {
            tableModel.addRow(new Object[]{"熔岩起司蛋糕", lavacakeCount, lavacake});
        }
        if (chocolatecakeCount > 0) {
            tableModel.addRow(new Object[]{"生乳蒙布朗", chocolatecakeCount, chocolatecake});
        }
        if (tilamisucakeCount > 0) {
            tableModel.addRow(new Object[]{"提拉米蘇", tilamisucakeCount, tilamisucake});
        }
        if (bostoncakeCount > 0) {
            tableModel.addRow(new Object[]{"波士頓派", bostoncakeCount, bostoncake});
        }
        if (lemoncakeCount > 0) {
            tableModel.addRow(new Object[]{"檸檬塔", lemoncakeCount, lemoncake});
        }
        if (expressoCount > 0) {
            tableModel.addRow(new Object[]{"義式濃縮", expressoCount, expresso});
        }
        if (blackcoffeeCount > 0) {
            tableModel.addRow(new Object[]{"美式咖啡", blackcoffeeCount, blackcoffee});
        }
        if (cabuchinoCount > 0) {
            tableModel.addRow(new Object[]{"卡布奇諾", cabuchinoCount, cabuchino});
        }
        if (latteCount > 0) {
            tableModel.addRow(new Object[]{"拿鐵", latteCount, latte});
        }
        if (lemoncoffeeCount > 0) {
            tableModel.addRow(new Object[]{"檸檬西西里", lemoncoffeeCount, lemoncoffee});
        }
        if (hotchocolateCount > 0) {
            tableModel.addRow(new Object[]{"熱可可", hotchocolateCount, hotchocolate});
        }
        if (milkteaCount > 0) {
            tableModel.addRow(new Object[]{"皇家奶茶", milkteaCount, milktea});
        }
        if (blackteaCount > 0) {
            tableModel.addRow(new Object[]{"熟韻紅茶",blackteaCount, blacktea});
        }
        if (honeyteaCount > 0) {
            tableModel.addRow(new Object[]{"蜂蜜柚子綠茶",honeyteaCount, honeytea});
        }
        if (jadeteaCount > 0) {
            tableModel.addRow(new Object[]{"翡翠檸檬",jadeteaCount, jadetea});
        }
    }

    public static void main(String[] args) {
    	 new GUI("5",false,0);
    }
}
