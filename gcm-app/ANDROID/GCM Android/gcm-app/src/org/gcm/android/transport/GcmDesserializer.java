package org.gcm.android.transport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GcmDesserializer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9191204116709505600L;
	
	public List<GcmObject> list;

	public GcmDesserializer(){
		list = new ArrayList<GcmObject>();
	}
	
	public List<GcmObject> getList() {
		return list;
	}

	public void setList(List<GcmObject> list) {
		this.list = list;
	}
	
	public void addItem(GcmObject object){
		list.add(object);
	}
	
	public int sizeOfList(){
		return list.size() - 1;
	}
	
}
