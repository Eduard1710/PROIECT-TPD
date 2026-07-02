package com.example.dto;

public class ReceiverDTO {
	private int id;
	private String address;
	private byte deleted;
	private String firstname;
	private String lastname;

	public ReceiverDTO(String address, String firstname, String lastname) {
		super();
		this.address = address;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public ReceiverDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getDeleted() {
		return deleted;
	}

	public void setDeleted(byte deleted) {
		this.deleted = deleted;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
