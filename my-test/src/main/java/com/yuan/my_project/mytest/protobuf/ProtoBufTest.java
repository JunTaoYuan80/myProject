package com.yuan.my_project.mytest.protobuf;

import java.io.IOException;
import java.io.OutputStream;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yuan.my_project.mytest.protobuf.AddressBookProtos.Person;

public class ProtoBufTest {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//			long start = System.currentTimeMillis();
//			for(int i=0;i<10;i++){
//				Person john = Person.newBuilder().setId(1234).setName("John Doe").setEmail("jdoe@example.com")
//						.addPhones(Person.PhoneNumber.newBuilder().setNumber("555-4321").setType(Person.PhoneType.HOME))
//						.build();
//				//对象序列化，发送对方
//				byte[] data = john.toByteArray();
//
//				//反序列化，接受到对方的字节流 生成对应对象，
//				//即使反序列化对象的字段比序列化对象的字段多，只要不是required类型的字段，就不会报错
//				com.yuan.my_project.mytest.protobuf.deserialize.AddressBookProtos.Person p = com.yuan.my_project.mytest.protobuf.deserialize.AddressBookProtos.Person.parseFrom(data);
//				System.out.println(p.getId());
//			}
//			System.out.println("end cost time:"+(System.currentTimeMillis() - start));
//		} catch (InvalidProtocolBufferException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
