package com.yuan.datastructure;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;
import java.util.Vector;

/**
 * 如何判定括号合法性
 *
 * @Author yuanjt
 * @Date 2020-12-21 10:08
 **/
public class BracketValid {

    public static void main(String[] args) {
        String s = "([{([])}()][]{})";
        System.out.println(isValid(s));
    }


    /**
     * @param s
     * @return
     */
    static boolean isValid(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        Vector<Character> vector = new Vector<>();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            if ('(' == c || '[' == c || '{' == c) {
                vector.add(c);
            }
            if (')' == c || ']' == c || '}' == c) {
                char ch = vector.remove(vector.size() - 1);
                switch (c) {
                    case ')':
                        if (ch != '(') {
                            return false;
                        }
                        break;
                    case ']':
                        if (ch != '[') {
                            return false;
                        }
                        break;
                    case '}':
                        if (ch != '{') {
                            return false;
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return vector.size() == 0;
    }

    /**
     * @param s
     * @return
     */
    static boolean isValid2(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            if ('(' == c || '[' == c || '{' == c) {
                stack.push(c);
            }
            if (')' == c || ']' == c || '}' == c) {
                char ch = stack.pop();
                switch (c) {
                    case ']':
                        if (ch != '[') {
                            return false;
                        }
                        break;
                    case ')':
                        if (ch != '(') {
                            return false;
                        }
                        break;
                    case '}':
                        if (ch != '{') {
                            return false;
                        }
                        break;
                }
            }
        }

        return stack.empty();
    }

}
