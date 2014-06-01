package org.gcm.android.transport;
import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class GcmObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5619344545517862582L;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("text")
	public String text;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
