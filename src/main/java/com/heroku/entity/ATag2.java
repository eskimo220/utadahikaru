package com.heroku.entity;

import java.io.Serializable;

import javax.validation.Valid;

public class ATag2 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] chkbox;
	 @Valid  
	private ATag[] atag;

	public String[] getChkbox() {
		return chkbox;
	}

	public void setChkbox(String[] chkbox) {
		this.chkbox = chkbox;
	}

	public ATag[] getAtag() {
		return atag;
	}

	public void setAtag(ATag[] atag) {
		this.atag = atag;
	}
	
	

}