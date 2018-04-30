package com.yuan.my_project.mytest.protobuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author yuanjuntao
 * @date 2017-07-07
 */
public class JavaSerializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				PersonVO vo = new PersonVO();
				vo.setId(1234);
				vo.setName("Jonh");
				vo.setEmail("jdoe@example.com");
				vo.setPhoneNum("555-4321");

				//streamTrans(i, vo);

				fileTrans(i, vo);

			}
			System.out.println("end, cost time:" + (System.currentTimeMillis() - start));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void fileTrans(int i, PersonVO vo)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		//序列化
		FileOutputStream fileOut = new FileOutputStream("temp.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(vo);
		out.close();
		fileOut.close();
		
		//反序列化
		FileInputStream fileIn = new FileInputStream("temp.ser");
		 ObjectInputStream in = new ObjectInputStream(fileIn);
		 com.yuan.my_project.mytest.protobuf.deserialize.PersonVO p = (com.yuan.my_project.mytest.protobuf.deserialize.PersonVO) in.readObject();
		 if (i % 1000 == 0) {
			System.out.println(p.getId());
		}
		 in.close();
		 fileIn.close();
	}

	private static void streamTrans(int i, PersonVO vo) throws IOException, ClassNotFoundException {
		// 序列化，生成序列化字节码临时文件
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(vo);
		byte[] data = bos.toByteArray();
		bos.close();
		oos.close();

		// 反序列化，从序列化文件读取字节码，解析成对应对象
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		com.yuan.my_project.mytest.protobuf.deserialize.PersonVO p = (com.yuan.my_project.mytest.protobuf.deserialize.PersonVO) ois
				.readObject();
		if (i % 1000 == 0) {
			System.out.println(p.getId());
		}
		bis.close();
		ois.close();
	}

}
