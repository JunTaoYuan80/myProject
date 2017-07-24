package com.yuan.lm.yiang.io;

import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;
import com.yuan.lm.yiang.io.CaseObject;

@BTrace public class TraceMethodArgsAndReturn {
	static int i = 100;
	@OnMethod(
			clazz="CaseObject",
			method="execute",
			location=@Location(Kind.RETURN)
	)
	public static void onExecute(@Self CaseObject instance, int sleepTime, @Return boolean result){
		//for(int i=0;i<10;i++){
		println("i = "+i);
			println("call CaseObject.execute");
			println(strcat("sleepTime is:",str(sleepTime)));
			println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
			println(strcat("return value is:",str(result)));
		//}
			
			println("call CaseObject.execute");
			println(strcat("sleepTime is:",str(sleepTime)));
			println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
			println(strcat("return value is:",str(result)));
			
			
			println("call CaseObject.execute");
			println(strcat("sleepTime is:",str(sleepTime)));
			println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
			println(strcat("return value is:",str(result)));
			
			
			println("call CaseObject.execute");
			println(strcat("sleepTime is:",str(sleepTime)));
			println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
			println(strcat("return value is:",str(result)));
			
			
			println("call CaseObject.execute");
			println(strcat("sleepTime is:",str(sleepTime)));
			println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
			println(strcat("return value is:",str(result)));
	}
}
