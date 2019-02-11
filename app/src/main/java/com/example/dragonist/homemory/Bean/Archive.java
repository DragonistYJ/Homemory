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
	private String savePath;
	private String visibleRange;
	private String fileName;
	private String fileType;
	private String user;
	private String uploadDate;
	private Boolean bigEvent;
	private String id;
	private String nickName;
	private Bitmap icon;
	private String Path;//如果是音乐，视频，文件等档案，需要将他写进本地。
	private Bitmap portrait;

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

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
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
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
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Boolean getBigEvent() {
		return bigEvent;
	}
	public void setBigEvent(Boolean bigEvent) {
		this.bigEvent = bigEvent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Bitmap getPortrait() {
		return portrait;
	}
	public void setPortrait(Bitmap portrait) {
		this.portrait = portrait;
	}

	public Archive(String relationship, String location, String keyword, String description, String label,
				   String classification, String savePath, String visibleRange, String fileName, String fileType, String user,
				   Boolean bigEvent, String nickName) {
		super();
		this.relationship = relationship;
		this.location = location;
		this.keyword = keyword;
		this.description = description;
		this.label = label;
		this.classification = classification;
		this.savePath = savePath;
		this.visibleRange = visibleRange;
		this.fileName = fileName;
		this.fileType = fileType;
		this.user = user;
		this.bigEvent = bigEvent;
		this.Path=null;
		this.nickName=nickName;
	}

	public Archive(String location, String description, String classification, String visibleRange, String uploadDate, String nickName,String id) {
		this.location = location;
		this.description = description;
		this.classification = classification;
		this.visibleRange = visibleRange;
		this.uploadDate = uploadDate;
		this.nickName = nickName;
		this.id=id;
	}

	public Archive(String relationship, String location, String keyword, String description, String label,
				   String classification, String savePath, String visibleRange, String fileName, String fileType, String user,
				   Timestamp uploadDate, Boolean bigEvent, Integer id, String nickName) {
		super();
		this.relationship = relationship;
		this.location = location;
		this.keyword = keyword;
		this.description = description;
		this.label = label;
		this.classification = classification;
		this.savePath = savePath;
		this.visibleRange = visibleRange;
		this.fileName = fileName;
		this.fileType = fileType;
		this.user = user;
		this.bigEvent = bigEvent;
		this.Path=null;
		this.nickName=nickName;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	@Override
	public String toString() {
		return "Archive [relationship=" + relationship + ", location=" + location + ", keyword=" + keyword
				+ ", description=" + description + ", label=" + label + ", classification=" + classification
				+ ", savePath=" + savePath + ", visibleRange=" + visibleRange + ", fileName=" + fileName + ", fileType="
				+ fileType + ", user=" + user + ", uploadDate=" + uploadDate + ", bigEvent=" + bigEvent + ", id=" + id
				+ "]";
	}
}
