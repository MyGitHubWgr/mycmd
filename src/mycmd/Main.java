package mycmd;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by gongrui on 16-12-26.
 */
public class Main {

    public static Scanner scanner;

    public static void init() {

        scanner = new Scanner(System.in);

        if(!new File(CommandUtils.proFile).exists()){
            try {
                new File(CommandUtils.proFile).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        init();

        System.out.println("--------------------------------------------------");
        System.out.println("------------------Welcome MyCmd-------------------");
        System.out.println("--------------------------------------------------");

        while(true) {
            String str = scanner.nextLine();
            if(str.trim().length() > 0) {
                String[] strs = str.split("\\s+");

                List<CommandType> commandTypes = Arrays.asList(CommandType.values()).stream()
                        .filter(e -> e.getType().equalsIgnoreCase(strs[0]))
                        .collect(Collectors.toList());
                if(commandTypes.size() < 1) continue;
                switch (commandTypes.get(0)){
                    case ADD_COMMAND:
                        if(CommandUtils.addCommand(strs))
                            System.out.println("add " + strs[1] +" success");
                        else
                            System.out.println("update " + strs[1] +" success");
                        break;
                    case DEL_COMMAND:
                        if(CommandUtils.delCommand(strs))
                            System.out.println("del " + strs[1] +" success");
                        else
                            System.out.println(strs[1] +" don not exits");
                        break;
                    case RUN_COMMAND:{
                        Object o = new Object();
                        synchronized (o){

                        StringBuffer buffer = new StringBuffer();
                        CommandUtils.runCommand(strs,buffer,o);

                        try {
//                            o.wait();
                            o.wait(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        String result = buffer.toString();
                        if(result.equalsIgnoreCase("true"))
                            System.out.println("run " + strs[1] + " success");
                        else
                            System.out.println("run " + strs[1] + " failed -----> " +result);
                        }
                        break;
                    }
                    case EXIT_COMMAND:
                        System.exit(0);
                }
            }
        }

    }
}
