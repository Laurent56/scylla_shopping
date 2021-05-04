/* Assumption all Item are quantity 1.
 * All code by Laurent Weichberger: ompoint@gmail.com
 */

package com.scylladb.shop;

public class Item {

	private String m_itemNum = null;
	
	private String m_description = null;
	
	private float m_cost = 0.0F;
	
	public Item(String num, String desc, String cost) {
		
		this(num, desc, Float.parseFloat(cost));
	}
	
	public Item(String num, String desc, float cost) {
		
		m_itemNum = num;
		m_description = desc;
		m_cost = cost;
		
	}

	
	public String getItemNum() {
		return m_itemNum;
	}

	public void setItemNum(String m_itemNum) {
		this.m_itemNum = m_itemNum;
	}

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String m_description) {
		this.m_description = m_description;
	}

	public float getCost() {
		return m_cost;
	}

	public void setCost(float m_cost) {
		this.m_cost = m_cost;
	}

	@Override
	public String toString() {
		return "Item [m_itemNum=" + m_itemNum + ", m_description=" + m_description + ", m_cost=" + m_cost + "]";
	}
	public String toTuple() {
		
		return "('" + m_itemNum + "', '" + m_description + "', " + m_cost + ")";
	}
}
