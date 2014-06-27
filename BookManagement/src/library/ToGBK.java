/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author xia
 */
public class ToGBK{

    public static String toGBK(String s){
        String str = s;
        try{
            if(s == null){
                return null;
            }
            else{
                str = new String(s.getBytes("ISO-8859-1"), "GBK");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return str;
    }
}
