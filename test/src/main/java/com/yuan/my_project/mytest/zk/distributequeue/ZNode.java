package com.yuan.my_project.mytest.zk.distributequeue;

public class ZNode implements Comparable<ZNode> {
	private final String name;
	private String prefix;
	private long sequeue = -1;
	
	public ZNode(String name){
		if(name == null){
			throw new NullPointerException("node id is null");
		}
		
		this.name = name;
		this.prefix = name;
		
		int idx = name.lastIndexOf("-");
		if(idx>=0){
			this.prefix = name.substring(0,idx);
			this.sequeue = Long.parseLong(name.substring(idx+1));
		}
		
	}

	public int compareTo(ZNode o) {
		// TODO Auto-generated method stub
		long c1 = this.sequeue;
		long c2 = o.sequeue;
		if(c1 == -1 && c2 == -1){
			System.out.println(Thread.currentThread().getName()+" name.compareto.name");
			return this.name.compareTo(o.name);
		}
		if(c1 == -1){
			return -1;
		}else if(c2 == -1){
			return 1;
		}else{
			return (int)(c1-c2);
		}
	}
	
	@Override
	public String toString(){
		return name.toString();
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getSequeue() {
		return sequeue;
	}

	public void setSequeue(long sequeue) {
		this.sequeue = sequeue;
	}

	public String getName() {
		return name;
	}

	
}
