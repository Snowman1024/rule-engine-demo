package com.xikang.ruleengine.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 列出多个集合中，所有可能的组合
 * @Author Snowman1024
 * @Date 2020/3/3 14:26
 * @Version 1.0
 **/
public class CollectionsUtil {

    public static void main(String[] args) {
        List<List<String>> strings = new ArrayList<>();
        strings.add(new ArrayList(Arrays.asList("1", "2", "3")));
        strings.add(new ArrayList(Arrays.asList("a", "b", "c")));
        strings.add(new ArrayList(Arrays.asList("x", "y", "z")));

        List<String> list = test(strings, strings.get(0), "", new ArrayList<>());
        System.out.println(list);
    }


    public static List<String> test(List<List<String>> list, List<String> arr, String str, List<String> result) {
        for (int i = 0; i < list.size(); i++) {
            //取得当前的集合
            if (i == list.indexOf(arr)) {
                //迭代集合
                for (String st : arr) {
                    st = str + st + "_";
                    if (i < list.size() - 1) {
                        test(list, list.get(i + 1), st, result);
                    } else if (i == list.size() - 1) {
                        result.add(st);
                    }
                }
            }
        }
        return result;
    }
}
