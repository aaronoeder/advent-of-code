package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        int length = 272;
        String res = lines.get(0);
        while (res.length() < length) {
            String temp = "0";
            for (int i = res.length() - 1; i >= 0; i--) {
                temp += res.charAt(i) == '0' ? '1' : '0';
            }
            res += temp;
        }
        res = res.substring(0, length);

        String checksum = res;
        while (true) {
            checksum = getChecksum(checksum);
            if (checksum.length() % 2 != 0) {
                return checksum;
            }
        }
    }

    private String getChecksum(String str) {
        String checksum = "";
        for (int i = 0; i < str.length() - 1; i += 2) {
            char first = str.charAt(i);
            char second = str.charAt(i + 1);
            checksum += (first == second ? '1' : '0');
        }
        return checksum;
    }
}