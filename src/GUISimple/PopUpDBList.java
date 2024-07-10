package GUISimple;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PopUpDBList extends JFrame implements MouseListener, ActionListener {
   JMenuBar menuBar = new JMenuBar();
   JLabel dbLocation;
   JButton createBtn;

   JTable table = new JTable();
   JScrollPane scroll = new JScrollPane();
   JPanel listViewPanel = new JPanel();
   ArrayList<JLabel> listOfDB = new ArrayList<JLabel>();
   JTextField databasePathText;

   String[] insertCode = new String[100];
   String dropName;
   JFrame showCode = new JFrame();
   
   Statement stmt1 = null;
   PreparedStatement pstmt1 = null;
   Connection conn = null;
   Statement stmt = null;
   ResultSet rs = null;
   String type = null;

   public PopUpDBList(String type) {
      this.type = type;
      setSize(700, 500);
      menuSetting();

      setJMenuBar(menuBar);

      try {
         stmt = DBConnect.makeConnection().createStatement();
         stmt.executeUpdate("use sml");
         rs = stmt.executeQuery("show tables");
         while (rs.next()) {
            listOfDB.add(new JLabel(rs.getString(1)));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      setUpView();

      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();
      setLocation(screenSize.width / 3, screenSize.height / 3);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setVisible(true);
   }

   public void menuSetting() {
      if (type.equals("DB 불러오기")) {
         dbLocation = new JLabel();
         menuBar.add(dbLocation);
      } else {
         dbLocation = new JLabel();
         createBtn = new JButton();

         createBtn.setText("CreateTable");
         menuBar.add(dbLocation);
         menuBar.add(createBtn);
         createBtn.addActionListener(this);
      }
      dbLocation.setText("DB Location : sml");
   }

   public void setUpView() {
      listViewPanel.setLayout(new BoxLayout(listViewPanel, BoxLayout.PAGE_AXIS));
      for (JLabel jLabel : listOfDB) {
         jLabel.addMouseListener(this);
         listViewPanel.add(jLabel);
      }
      scroll.add(listViewPanel);
      scroll.setViewportView(listViewPanel);
      add(scroll);
   }

   // 테이블 선택
   public void mouseClicked(MouseEvent e) {
      
      showCode.setSize(500, 500);
      dropName = "";
      JMenuBar selectMenu = new JMenuBar();
      JButton selectBtn = new JButton("selectBtn");
      JButton deleteBtn = new JButton("DropTable");
      selectMenu.add(selectBtn);
      selectMenu.add(deleteBtn);

      showCode.setJMenuBar(selectMenu);

      Toolkit kit = Toolkit.getDefaultToolkit();
      Dimension screenSize = kit.getScreenSize();
      showCode.setLocation(screenSize.width / 3, screenSize.height / 3);

      JLabel eJL = (JLabel) e.getComponent();
      String text = eJL.getText();
      Object MBR[][] = new Object[100][2];
      String column[] = { "InstructionCounter", "Register" };
      if (dbLocation.getText().equals("DB Location : sml")) {
         try {
            rs = stmt.executeQuery("select * from " + text);
            int i = 0;
            while (rs.next()) {
               MBR[i][0] = (String) (rs.getString("InstructionCounter"));
               MBR[i][1] = (rs.getString("Register"));
               i++;
            }
            DefaultTableModel model = new DefaultTableModel(MBR, column);
            table = new JTable(model);
            table.setLocation(110, 210);
            table.setSize(110, 210);

            JScrollPane scrollpane = new JScrollPane(table);
            showCode.add(scrollpane);
            showCode.setVisible(true);
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
         setUpView();

      }
      dropName = text;
      deleteBtn.addActionListener(this);
      selectBtn.addActionListener(e4 -> {
         String[] strstr = new String[100];

         for (int i = 0; i < strstr.length; i++) {
            PageEvent.str[i] = (String) table.getValueAt(i, 1);
         }
         PageEvent.rePa();
      });
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("CreateTable")) {
         insertCode = PageEvent.str;
         System.out.println("copy success" + insertCode);
         try {
            String url = "jdbc:mysql://localhost/sml";
            String user = "root";
            String passwd = "root";

            conn = DriverManager.getConnection(url, user, passwd);
            String programName = GUISimple.databasePathText.getText();
            System.out.println(programName);
            String s = " create table " + programName
                  + " ( InstructionCounter varchar(2) PRIMARY KEY, Register varchar(5))";
            stmt1 = DBConnect.makeConnection().createStatement();
            stmt1.execute("use sml");
            System.out.print("stmt1 is " + stmt1);
            stmt1.execute(s); // 결과값 노 반환 상태값은 반환함

            String code = "insert into " + programName + " values(?, ?)";
            pstmt1 = conn.prepareStatement(code);
            for (int i = 0; i < insertCode.length; i++) {
               if (i < 10) {
                  pstmt1.setString(1, "0" + i);
               } else {
                  pstmt1.setString(1, "" + i);
               }
               System.out.print(pstmt1);

               pstmt1.setString(2, insertCode[i]);
               pstmt1.executeUpdate();
            }
         } catch (SQLException e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(this, e2.getMessage(), "경고", JOptionPane.INFORMATION_MESSAGE);
         } finally {

         }
      }
      if (e.getActionCommand().equals("DropTable")) {
         try {
            String s = "drop table " + dropName;
            stmt1 = DBConnect.makeConnection().createStatement();
            stmt1.execute("use sml");
            stmt1.execute(s);
         } catch (SQLException e2) {
            e2.printStackTrace();
         } finally {

         }
      }

   }

}