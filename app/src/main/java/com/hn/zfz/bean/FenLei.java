package com.hn.zfz.bean;
/**
 * 课程分类实体类
 * @author Administrator
 *
 */
public class FenLei {
	private String id;
	private String name;
	private int yiZuo;
	private int tiMuZongShu;
	private float zhengQueLv;
	private int shiyong;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYiZuo() {
		return yiZuo;
	}

	public void setYiZuo(int yiZuo) {
		this.yiZuo = yiZuo;
	}

	public int getTiMuZongShu() {
		return tiMuZongShu;
	}

	public void setTiMuZongShu(int tiMuZongShu) {
		this.tiMuZongShu = tiMuZongShu;
	}

	public float getZhengQueLv() {
		return zhengQueLv;
	}

	public void setZhengQueLv(float zhengQueLv) {
		this.zhengQueLv = zhengQueLv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public FenLei(){
		
	}
	public FenLei(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getShiyong() {
		return shiyong;
	}

	public void setShiyong(int shiyong) {
		this.shiyong = shiyong;
	}
	
}
