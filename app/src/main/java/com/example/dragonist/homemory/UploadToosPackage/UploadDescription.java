package com.example.dragonist.homemory.UploadToosPackage;

import java.io.Serializable;

public class UploadDescription implements Serializable{
          private String label;
          private String aboutPeople;
          private String location;
          private String keyWord;
          private String description;
          public UploadDescription(String label, String aboutPeople, String location, String keyWord, String description){
              this.label=label;
              this.aboutPeople=aboutPeople;
              this.location=location;
              this.keyWord=keyWord;
              this.description=description;
          }
          public UploadDescription(){}
          public String getLabel(){
              return label;
          }
          public String getAboutPeople(){
              return aboutPeople;
          }
          public String getLocation(){
              return location;
          }
          public String getKeyWord(){
              return keyWord;
          }
          public String getDescription(){
              return description;
          }
          public void setLabel(String label){
              this.label=label;
          }
          public void setLocation(String location){
              this.location=location;
          }
          public void setAboutPeople(String aboutPeople){
              this.aboutPeople=aboutPeople;
          }
          public void setKeyWord(String keyWord){
              this.keyWord=keyWord;
          }
          public void setDescription(String description){
              this.description=description;
          }
}
