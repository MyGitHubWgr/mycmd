package mycmd;

import java.io.*;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by gongrui on 16-12-26.
 */
public class CommandUtils {

    public static final String proFile = "/usr/mycmd/mycmd.properties";

    public static boolean addCommand(String[] strs) {
        Properties pro = getCmdPro();
        String command = pro.getProperty(strs[1]);
        StringBuilder builder = new StringBuilder();
        for(int i = 2 ; i < strs.length;i++){
            if(i == strs.length - 1)
                builder.append(strs[i]);
            else
                builder.append(strs[i]).append(" ");
        }
        pro.setProperty(strs[1],builder.toString());
        storeCmdPro(pro);
        if(command == null){
            return true;
        }
        return false;
    }

    public static boolean delCommand(String[] strs) {

        Properties pro = getCmdPro();
        String command = pro.getProperty(strs[1],null);
        if (command == null) {
            pro.remove(strs[1]);
            storeCmdPro(pro);
            return true;
        }
        return false;
    }

    public static void runCommand(String[] strs,StringBuffer buffer, Object o) {
 //       synchronized (o){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String run = getCmdPro().getProperty(strs[1],null);
                    if(run == null) {buffer.append(strs[1] +" don not exists");return;}
                    try {
                        Runtime.getRuntime().exec(run);
                        buffer.append("true");
                    }catch (Exception e){
                        buffer.append(e.getMessage());
                    }
                }
            }).start();

     //   }

    }

    public static Properties getCmdPro() {
        Properties pro = new Properties();
        try {
            pro.load(new FileInputStream(new File(proFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pro;
    }

     public static void storeCmdPro(Properties pro) {
         try {
             pro.store(new FileOutputStream(new File(proFile)),new Date().toString());
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}