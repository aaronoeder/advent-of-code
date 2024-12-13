package me.oeder.aoc.days2016;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.oeder.aoc.AdventDay;
import me.oeder.aoc.common.Coord;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class Day14 extends AdventDay {

    @Override
    public Object getAnswer(List<String> lines, Part part) {
        String line = lines.get(0);

        int hashIterations = part == Part.ONE ? 1 : 2017;
        int key = 0;
        int index = 0;
        while (true) {
            String md5 = getHash(line + index, hashIterations);
            List<Character> repeats = getRepeats(md5, 3);
            if (!repeats.isEmpty()) {
                char firstRepeat = repeats.get(0);

                boolean isKey = false;
                for (int i = index + 1; i <= index + 1000; i++) {
                    String nextMd5 = getHash(line + i, 1);
                    if (getRepeats(nextMd5, 5).contains(firstRepeat)) {
                        isKey = true;
                        break;
                    }
                }

                if (isKey) {
                    key++;
                    if (key == 64) {
                        return index;
                    }
                }
            }
            index++;
        }
    }

    private List<Character> getRepeats(String str, int min) {
        List<Character> repeats = new ArrayList<>();

        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length - min + 1; i++) {
            char ch = chars[i];
            boolean isRepeat = true;
            for (int j = i + 1; j < i + min; j++) {
                if (chars[j] != chars[i]) {
                    isRepeat = false;
                }
            }
            if (isRepeat) {
                repeats.add(ch);
            }
        }
        return repeats;
    }

    private Map<CacheObject, String> cache = new HashMap<>();
    private String getHash(String str, int iterations) {
        CacheObject cacheObject = new CacheObject(str, iterations);
        if (cache.containsKey(cacheObject)) {
            return cache.get(cacheObject);
        }

        String hash = null;
        if (iterations == 0) {
            hash = str;
        } else {
            String md5 = DigestUtils.md5Hex(str);
            hash = getHash(md5, iterations - 1);
        }
        cache.put(cacheObject, hash);
        return hash;
    }

    @Data
    @AllArgsConstructor
    private static class CacheObject {
        private String str;
        private int iterations;
    }
}