package com.yuan.my_project.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "http://www.baidu.com/s?rurl=java%20aqs%20%E5%BA%94%E7%94%A8%E5%9C%BA%E6%99%AF&rsv_spt=1&rsv_iqid=0x8a1bc41300071e43&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=23&rsv_sug1=4&rsv_sug7=100&rsv_sug2=0&inputT=12288&rsv_sug4=13037";
		
		String pattern = "([^&])&";
		
		Pattern p = Pattern.compile(pattern);
		
		StringBuffer sb = new StringBuffer();
		Matcher m = p.matcher(str);
		System.out.println(m.find());
		if(m.find()){
			m.appendReplacement(sb, "");
			System.out.println(sb.toString());
		}
		
	}

}
