package library;

import java.awt.*;
import java.awt.event.*;
import java.awt.Container;
import java.sql.*;
import javax.swing.*;

public class BookBorrow
        extends JFrame
        implements ActionListener{

    private JButton ok, cancle;
    private JTextField book_num, user_name;
    JPanel pn, pn_center, pn_south, pn_east;

    public BookBorrow(){
        initComponents();
    }

    private void initComponents(){
        Container cp = getContentPane();
        pn_center = new JPanel(new GridLayout(8, 2));
        pn_south = new JPanel();
        pn_east = new JPanel();
        book_num = new JTextField(18);
        user_name = new JTextField(18);
        ok = new JButton("确定");
        cancle = new JButton("取消");

        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, "East");
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel("       图书号"));
        pn_center.add(book_num);
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel("       用户名"));
        pn_center.add(user_name);
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_center.add(new JLabel(""));
        pn_south.add(ok);
        pn_south.add(cancle);

        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("借书");
        setSize(230, 250);
        Toolkit tkt = Toolkit.getDefaultToolkit();
        Dimension screen = tkt.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        int xcenter = (x - 300) / 2;
        int ycenter = (y - 200) / 2;
        setLocation(xcenter, ycenter);
        setResizable(false);

        ok.addActionListener(this);
        cancle.addActionListener(this);
    }

    /*public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
    new BookBorrow().setVisible(true);
    }
    });
    }*/
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == ok){
            if(book_num.getText().equals("") || user_name.getText().equals("")){
                JOptionPane.showMessageDialog(null, "请输入图书号和用户ID！");
                return;
            }
            else{
                bookBorrow();
            }
        }
        if(source == cancle){
            //frame.setVisible(false);
            dispose();
        }

    }

    private void bookBorrow(){
        String sql1 = "select * from reader where readerID='" + user_name.
                getText() + "';";
        String sql2 = "select * from book where bookNum='" + book_num.getText()
                + "';";
        String sql3 = "update book set borrower='" + user_name.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        try{
            Database db = new Database();
            db.dbCon();
            ResultSet rs1 = db.stmt.executeQuery(sql1);
            boolean reader = rs1.next();
            ResultSet rs2 = db.stmt.executeQuery(sql2);
            boolean book = rs2.next();
            if(reader == false){
                JOptionPane.showMessageDialog(null, "无此读者！");
                return;
            }
            if(book == false){
                JOptionPane.showMessageDialog(null, "无此图书！");
                return;
            }
            int done = db.stmt.executeUpdate(sql3);
            if(done == 1){
                JOptionPane.showMessageDialog(null, "操作成功！");
                book_num.setText("");
                user_name.setText("");
                String str = "select * from book;";
                ShowBook showbk = new ShowBook();
                showbk.showFirst(str);
            }
            else{
                JOptionPane.showMessageDialog(null, "操作失败！");
            }

        }
        catch(SQLException g){
            System.out.println("Error Code: " + g.getErrorCode());
            System.out.println("Error Message: " + g.getMessage());
            JOptionPane.showMessageDialog(null, "操作失败！");
        }
    }
}
