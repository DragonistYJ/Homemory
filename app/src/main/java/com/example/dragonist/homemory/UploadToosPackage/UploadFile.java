package com.example.dragonist.homemory.UploadToosPackage;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Handler;

public class
UploadFile implements Serializable{
    private static final long serialVersionUID=1L;
    private Handler handler;//暂时没有用
    private File file;//上传的数据本身信息。
    private UploadDescription description;//对档案的描述就是那个著录
    private String species;//标签，家庭大事那几个。
    private String range;//可见范围
    private String fileType;//文件类型
    private String fileName;
    private String account;
    private boolean isBigEvent;

    public UploadFile(File file , UploadDescription description, String species, String range, String fileType){
        this.file=file;
        this.description=description;
        this.species=species;
        this.range=range;
        this.fileType=fileType;
    }
    public UploadFile(){
        UploadDescription uploadDescription=new UploadDescription("","","","","");
    }
    public UploadDescription getDescription(){
        return description;
    }
    public String getFileType(){
        return fileType;
    }
    public String getLabel(){
        return species;
    }
    public String getRange(){
        return range;
    }
   public String getFileName(){
        return this.fileName;
   }
    public File getFile() {
        return file;
    }
  public String setFileName(String fileName){
        this.fileName=fileName;
        return  this.fileName;
  }
    public UploadDescription setDescription(UploadDescription description){
        this.description=description;
        return this.description;
    }
    public String setLabel(String species){
        this.species=species;
        return  this.species;
    }
    public String setRange(String range){
        this.range=range;
        return this.range;
    }
    public File setFile(File file){
        this.file=file;
        return this.file;
    }
    public String setFileType(String FileType){
        this.fileType=FileType;
        return this.fileType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isBigEvent() {
        return isBigEvent;
    }

    public void setBigEvent(boolean bigEvent) {
        isBigEvent = bigEvent;
    }
}
