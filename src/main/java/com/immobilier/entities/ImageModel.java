package com.immobilier.entities;

import javax.persistence.*;

@Entity
@Table(name = "image_table")
public class ImageModel {

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Integer getId_annonce() {
//		return id_annonce;
//	}

//	public void setId_annonce(Integer id_annonce) {
//		this.id_annonce = id_annonce;
//	}

	public ImageModel() {
		super();
	}

	public ImageModel( String name, String type, byte[] picByte /*, Integer id_annonce*/) {
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	//	this.id_annonce = id_annonce ;
	}

	@Id
//	@Column(name = "id_image")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//private Integer id_annonce;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
	@Column(name = "picByte", length = 15048576)
	private byte[] picByte;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
}