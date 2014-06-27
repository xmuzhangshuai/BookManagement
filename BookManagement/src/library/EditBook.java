package library;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Container;
import java.util.regex.Pattern;
import java.sql.*;

public class EditBook
        extends JFrame
        implements ActionListener{

    static JTextField book_name, book_num, price, author, press, time_in;  //定义文本框
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");
    JButton edit_booknum = new JButton("图书号修改");
    static boolean book_num_setEditable = false;

    public EditBook(){
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框

        JPanel pn_north = new JPanel();
        JLabel label = new JLabel("图书修改", SwingConstants.CENTER);
        label.setForeground(Color.blue);
        pn_north.add(label);

        JPanel pn_west = new JPanel();

        JPanel pn_center = new JPanel(new GridLayout(6, 1));
        book_num = new JTextField(MainWin.sbookNum);
        book_name = new JTextField(MainWin.sbookName);
        price = new JTextField(MainWin.sprice);
        author = new JTextField(MainWin.sauthor);
        press = new JTextField(MainWin.spress);
        time_in = new JTextField(MainWin.stimeIn);
        pn_west.setLayout(new GridLayout(6, 1));
        pn_west.add(new JLabel("图书名", SwingConstants.CENTER));
        pn_center.add(book_name);
        pn_west.add(new JLabel("图书号", SwingConstants.CENTER));
        pn_center.add(book_num);
        pn_west.add(new JLabel("单  价", SwingConstants.CENTER));
        pn_center.add(price);
        pn_west.add(new JLabel("作  者", SwingConstants.CENTER));
        pn_center.add(author);
        pn_west.add(new JLabel("出版社", SwingConstants.CENTER));
        pn_center.add(press);
        pn_west.add(new JLabel("入库时间", SwingConstants.CENTER));
        pn_center.add(time_in);

        JPanel pn_east = new JPanel();

        JPanel pn_south = new JPanel();
        pn_south.setLayout(new GridLayout(3, 1));
        JPanel pn_south1 = new JPanel();
        JPanel pn_south2 = new JPanel();
        JPanel pn_south3 = new JPanel();
        pn_south.add(pn_south1);
        pn_south.add(pn_south3);
        pn_south.add(pn_south2);

        pn_south3.add(edit_booknum);
        pn_south2.add(ok);
        pn_south2.add(cancle);

        cp.add(pn_north, "North");
        cp.add(pn_west, "West");
        cp.add(pn_center, "Center");
        cp.add(pn_south, "South");
        cp.add(pn_east, BorderLayout.EAST);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(350, 400);
        int xcenter = (x - 350) / 2;
        int ycenter = (y - 400) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("修改");
        setResizable(false);
        book_num.setEditable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);
        edit_booknum.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        Object source = e.getSource();
        //Pattern p=Pattern.compile("/^([1-9]\d*|\d+\.\d+)$/");
        //Pattern p=Pattern.compile("(\\d+)(.(\\d+))?");
        boolean b = Pattern.matches("(\\d+)(.(\\d+))?", price.getText());
        if(cmd.equals("图书号修改")){
            book_num.setEditable(true);
            book_name.setEditable(false);
            price.setEditable(false);
            author.setEditable(false);
            press.setEditable(false);
            time_in.setEditable(false);
            book_num_setEditable = true;
            edit_booknum.setText("其他信息修改");
        }
        if(cmd.equals("其他信息修改")){
            book_num.setEditable(false);
            book_name.setEditable(true);
            price.setEditable(true);
            author.setEditable(true);
            press.setEditable(true);
            time_in.setEditable(true);
            book_num_setEditable = false;
            edit_booknum.setText("图书号修改");
        }
        if(source == ok){
            if(book_name.getText().equals("") || book_num.getText().equals("")
                    || price.getText().equals("") || author.getText().equals("")
                    || press.getText().equals("") || time_in.getText().
                    equals("")){
                JOptionPane.showMessageDialog(this, "请填写所有图书信息！",
                        "提示", JOptionPane.OK_OPTION);
            }
            else if(b == false){
                JOptionPane.showMessageDialog(null, "价格错误", "提示",
                        JOptionPane.YES_NO_OPTION);
            }
            else{
                editBook(1);
            }
        }
        else if(source == cancle){
            this.dispose();
        }
    }

    public void editBook(int index){
        Database db = new Database();
        db.dbCon();
        /*String update = "update book set bookName='" + EditBook.book_name.
        getText() + "' and price='" + EditBook.price.getText()
        + "' and author='" + EditBook.author.getText() + "' and press='"
        + EditBook.press.getText()+ "' and timeIn='"
        + EditBook.time_in.getText()+ "'and borrower='' where bookNum='"
        + EditBook.book_num.getText() + "';";*/
        String updateBookNum = "update book set bookNum='" + book_num.getText()
                + "'where bookNum='" + MainWin.sbookNum + "'";
        String updateBookName = "update book set bookName='"
                + book_name.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        String updatePrice = "update book set price='" + price.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        String updateAuthor = "update book set author='" + author.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        String updatePress = "update book set press='" + press.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        String updateTimeIn = "update book set timeIn='" + time_in.getText()
                + "' where bookNum='" + book_num.getText() + "';";
        String query = "select * from book where bookNum='" + book_num.getText()
                + "'";

        int bookNameUpdated = 0, priceUpdated = 0, authorUpdated = 0, pressUpdated =
                0, timeInUpdated = 0, bookNumUpdated = 0;
        try{
            if(book_num_setEditable){
                ResultSet rs = db.stmt.executeQuery(query);//返回查询结果集
                boolean exist = rs.next();//判断结果集是否有数据
                if(exist){
                    JOptionPane.showMessageDialog(null, "该图书号已被使用！",
                            "信息", JOptionPane.YES_NO_OPTION);
                    return;
                }
                else{
                    bookNumUpdated = db.stmt.executeUpdate(updateBookNum);
                }
            }
            else{
                bookNameUpdated = db.stmt.executeUpdate(updateBookName);
                priceUpdated = db.stmt.executeUpdate(updatePrice);
                authorUpdated = db.stmt.executeUpdate(updateAuthor);
                pressUpdated = db.stmt.executeUpdate(updatePress);
                timeInUpdated = db.stmt.executeUpdate(updateTimeIn);
            }
            if(bookNumUpdated == 1 || (bookNameUpdated == 1 && priceUpdated
                    == 1 && authorUpdated
                    == 1 && pressUpdated == 1 && timeInUpdated == 1)){
                JOptionPane.showMessageDialog(null, "修改成功！",
                        "信息", JOptionPane.YES_NO_OPTION);
                this.hide();
            }
            else{
                JOptionPane.showMessageDialog(null, "信息无法写入数据库！",
                        "信息", JOptionPane.YES_NO_OPTION);
            }
            String str = "select * from book;";
            ShowBook showbk = new ShowBook();
            showbk.showFirst(str);
        }
        catch(SQLException g){
            System.out.println("E Code:" + g.getErrorCode());
            System.out.println("E M:" + g.getMessage());
            JOptionPane.showMessageDialog(null, "时间格式错误！",
                    "信息", JOptionPane.YES_NO_OPTION);
        }
        finally{
            db.dbClose();
        }
    }

    /*public static void main(String args[]){

        new EditBook().setVisible(true);
    }*/
}
