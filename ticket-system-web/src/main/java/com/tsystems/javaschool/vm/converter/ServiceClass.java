package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.helper.UserHelper;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.File;
import java.util.Scanner;

public class ServiceClass {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

            String line = in.nextLine();
            System.out.println("$.ajax({");

            if (line.contains("$.post")) {
                System.out.println("type: \"post\",");
            }
            if (line.contains("$.get")) {
                System.out.println("type: \"get\",");
            }
            String url = line.substring(line.indexOf('\''), line.indexOf(','));
            System.out.println("url: " + url + ",");
            //String data = in.nextLine();
            System.out.println("data: ");
            read(in);
            System.out.print("success: ");
            read(in);


        }

    private static void read(Scanner in) {
        StringBuilder sb = new StringBuilder();
        String line;
        int br = 0;
        while (true) {
            line = in.nextLine();
            for (Character c : line.toCharArray()) {
                if (c.equals('{')) {
                    br++;
                } else if (c.equals('}')) {
                    br--;
                }
            }
            sb.append(line).append('\n');
            if (br == 0) {
                break;
            }
        }
        System.out.println(sb.toString());
    }

}
