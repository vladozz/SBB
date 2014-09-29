package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.helper.UserHelper;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ServiceClass {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 4, 5);
        System.out.println("Collections.disjoint(list1, list2) = " + Collections.disjoint(list1, list2));
    }

    private static void toAJAX() {
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
        System.out.println("});");
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
