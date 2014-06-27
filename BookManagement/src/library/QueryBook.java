package library;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;

class QueryBook
        extends JFrame
        implements ActionListener{

    JTextField book_name = new JTextField(18);
    JButton ok = new JButton("确定");
    JButton cancle = new JButton("取消");

    public QueryBook(){
        Container cp = getContentPane(); // 初始化面板、按钮、标签、文本框

        //布局,添加控件
        JLabel label1 = new JLabel("请输入要查询的图书名：", SwingConstants.CENTER);
        label1.setForeground(Color.blue);
        JPanel pn_north = new JPanel();
        pn_north.add(label1);
        JPanel pn_west = new JPanel();

        JPanel pn_center = new JPanel();
        pn_center.add(book_name);

        JPanel pn_east = new JPanel();

        JPanel pn_south = new JPanel();
        pn_south.add(ok);
        pn_south.add(cancle);

        cp.add(pn_north, "North");
        cp.add(pn_center, "Center");
        cp.add(pn_west, "West");
        cp.add(pn_east, "East");
        cp.add(pn_south, "South");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        int x = screen.width;
        int y = screen.height;
        setSize(400, 200);
        int xcenter = (x - 350) / 2;
        int ycenter = (y - 330) / 2;
        setLocation(xcenter, ycenter);/*显示在窗口中央*/
        setTitle("图书查询");
        setResizable(false);

        ok.addActionListener(this);//注册监听器
        cancle.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        String ql = "";
        //String cmd=e.getActionCommand();
        Object source = e.getSource();
        if(source == ok){
            ql = book_name.getText().trim();
            String sql = "select * from book  where bookName"
                    + " like '%" + ql + "%'";
            ShowBook showbk = new ShowBook();
            showbk.showFirst(sql);
            MainWin.label1.setText("搜索结果： " + ShowBook.count + " 本");
            if(ShowBook.count == 0){
                JOptionPane.showMessageDialog(this, "无此图书！");
                return;
            }
            else{
                this.hide();
            }
        }
        if(source == cancle){
            this.dispose();
        }
    }
    /*public static void main(String []arg)
    {
    QueryBook a=new QueryBook();
    }*/
}
