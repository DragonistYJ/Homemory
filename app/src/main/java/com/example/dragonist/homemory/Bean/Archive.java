package com.example.dragonist.homemory.Bean;

import android.graphics.Bitmap;

import java.sql.Timestamp;

public class Archive {
	private String relationship;
	private String location;
	private String keyword;
	private String description;
	private String label;
	private String classification;
	private String visibleRange;
	private String fileName;
	private String fileType;
	private String user;
	private Timestamp uploadDate;
	private Boolean bigEvent;
	private Integer id;
	private String nickName;
	private Bitmap icon;
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getVisibleRange() {
		return visibleRange;
	}
	public void setVisibleRange(String visibleRange) {
		this.visibleRange = visibleRange;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Boolean getBigEvent() {
		return bigEvent;
	}
	public void setBigEvent(Boolean bigEvent) {
		this.bigEvent = bigEvent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public Archive(String location, String description, String classification, String visibleRange, Timestamp uploadDate, Integer id, String nickName, Bitmap icon) {
		this.location = location;
		this.description = description;
		this.classification = classification;
		this.visibleRange = visibleRange;
		this.uploadDate = uploadDate;
		this.id = id;
		this.nickName = nickName;
		this.icon = icon;
	}
}
