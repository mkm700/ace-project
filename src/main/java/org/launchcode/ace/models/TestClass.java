package org.launchcode.ace.models;

import java.util.Date;

public class TestClass {
	private String name;
	private int num;
//	private Date today;
//	private float fee;
//	private boolean mon;
//	private boolean tues;
	
	
//	public TestClass(String name, int num, Date today, float fee, boolean mon, boolean tues) {
//		public TestClass(String name, int num) {	
//		this.name = name;
//		this.num = num;
////		this.today = today;
////		this.fee = fee;
////		this.mon = mon;
////		this.tues = tues;
//	}


	public TestClass(String name, int num) {
			// TODO Auto-generated constructor stub
		this.name = name;
		this.num = num;
		}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	@Override
	public String toString() {
		return "TestClass [name=" + name + ", num=" + num + "]";
	}
	
	


//	public Date getToday() {
//		return today;
//	}
//
//
//	public void setToday(Date today) {
//		this.today = today;
//	}
//
//
//	public float getFee() {
//		return fee;
//	}
//
//
//	public void setFee(float fee) {
//		this.fee = fee;
//	}
//
//
//	public boolean isMon() {
//		return mon;
//	}
//
//
//	public void setMon(boolean mon) {
//		this.mon = mon;
//	}
//
//
//	public boolean isTues() {
//		return tues;
//	}


//	public void setTues(boolean tues) {
//		this.tues = tues;
//	}
	
	

}
