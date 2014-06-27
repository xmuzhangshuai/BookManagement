package library;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;
import java.sql.*;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class BookIn
        extends JFrame
        implements ActionListener{

    JTextField book_name, book_num, price, author, press;    //定义文本框
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");

    public BookIn(){
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框

        JPanel pn_north = new JPanel();
        JLabel label = new JLabel("图书入库", SwingConstants.CENTER);
        label.setForeground(Color.blue);
        pn_north.add(label);

        JPanel pn_west = new JPanel();
        JPanel pn_center = new JPanel(new GridLayout(6, 1));
        book_name = new JTextField(20);
        book_num = new JTextField(20);
        price = new JTextField(20);
        author = new JTextField(20);
        press = new JTextField(20);
        pn_west.setLayout(new GridLayout(6, 1));
        pn_west.add(new JLabel("图书号", SwingConstants.CENTER));
        pn_center.add(book_num);
        pn_west.add(new JLabel("图书名", SwingConstants.CENTER));
        pn_center.add(book_name);
        pn_west.add(new JLabel("单  价", SwingConstants.CENTER));
        pn_center.add(price);
        pn_west.add(new JLabel("作  者", SwingConstants.CENTER));
        pn_center.add(author);
        pn_west.add(new JLabel("出版社", SwingConstants.CENTER));
        pn_center.add(press);

        JPanel pn_east = new JPanel();

        JPanel pn_south = new JPanel();
        pn_south.setLayout(new GridLayout(2, 1));
        JPanel pn_south1 = new JPanel();
        JPanel pn_south2 = new JPanel();
        pn_south.add(pn_south1);
        pn_south.add(pn_south2);

        pn_south2.add(ok);
        pn_south2.add(cancle);

        cp.add(pn_north, "North");
        cp.add(pn_west, "West");
        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, "East");


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(350, 400);
        int xcenter = (x - 350) / 2;
        int ycenter = (y - 400) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("图书入库");
        setResizable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e){
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        boolean b = Pattern.matches("(\\d+)(.(\\d+))?", price.getText());
        if(source == ok){
            if(book_name.getText().equals("") || book_num.getText().equals("")
                    || price.getText().equals("") || author.getText().equals("")
                    || press.getText().equals("")){
                JOptionPane.showMessageDialog(this,
                        "请填写所有图书信息！", "提示",
                        JOptionPane.OK_OPTION);
            }
            else if(b == false){
                JOptionPane.showMessageDialog(null, "价格错误", "提示",
                        JOptionPane.YES_NO_OPTION);
            }
            else{
                insertRecord();
            }
        }
        else if(source == cancle){
            this.hide();
        }

    }
    /* public static void main(String []arg){

    BookIn a=new BookIn();
    }*/

    public void insertRecord(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = df.format(date);
        Database db = new Database();
        db.dbCon();
        try{
            String s = "insert into book values('" + book_num.getText() + "','"
                    + book_name.getText() + "','" + price.getText() + "','" + author.
                    getText()
                    + "','" + press.getText() + "','" + time + "',null)";
            //查询输入的图书号是否在数据库中存在
            String query = "select * from book where bookNum='" + book_num.
                    getText() + "'";
            ResultSet rs = db.stmt.executeQuery(query);//返回查询结果集
            boolean moreRecords = rs.next();//判断结果集是否有数据
            if(moreRecords){
                JOptionPane.showMessageDialog(this, "图书号已经被"
                        + "使用，请重新输入");
                db.dbClose();
                book_num.setText("");
                return;
            }
            else{
                int insert = db.stmt.executeUpdate(s);
                if(insert == 1){
                    JOptionPane.showMessageDialog(null, "图书信息录入成功！");
                    book_num.setText("");
                    book_name.setText("");
                    price.setText("");
                    author.setText("");
                    press.setText("");
                    String str = "select * from book;";
                    ShowBook showbk = new ShowBook();
                    showbk.showFirst(str);
                    MainWin.label1.setText("书库现在共有图书 " + ShowBook.count + " 本");
                }
            }
        }
        catch(SQLException g){
            System.out.println("E Code" + g.getErrorCode());
            System.out.println("E M" + g.getMessage());
            JOptionPane.showMessageDialog(null, "时间错误", "提示",
                    JOptionPane.YES_NO_OPTION);
        }
    }
}
