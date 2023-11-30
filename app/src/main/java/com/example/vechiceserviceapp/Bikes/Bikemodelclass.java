package com.example.vechiceserviceapp.Bikes;



import java.io.Serializable;

public class Bikemodelclass implements Serializable {
    String id;
    String servicecentername;
    String speciality;
    String image;
    String location;
    String ratings;
    String pickup;
    String recommended;
    String contactno;
    String description;
    String time;

    String cost;


    public Bikemodelclass(String id,
                          String servicecentername,
                          String speciality,
                          String image,
                          String location,
                          String ratings,
                          String pickup,
                          String recommended,
                          String contactno,
                          String description,
                          String time,
                          String cost
    ){
        this.id=id;
        this.servicecentername=servicecentername;
        this.image=image;
        this.speciality=speciality;
        this.time=time;
        this.location=location;
        this.ratings=ratings;
        this.pickup=pickup;
        this.recommended=recommended;
        this.contactno=contactno;
        this.description=description;
        this.cost=cost;
    }
    public  Bikemodelclass(){

    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getServicecentername(){
        return servicecentername;
    }
    public void setServicecentername(String servicecentername){
        this.servicecentername=servicecentername;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }
    public String getSpeciality(){
        return speciality;
    }
    public void setSpeciality(String speciality){
        this.speciality=speciality;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public String getRatings(){
        return ratings;
    }
    public void setRatings(String ratings){
        this.ratings=ratings;
    }
    public String getPickup(){
        return pickup;
    }
    public void setPickup(String pickup){
        this.pickup=pickup;
    }
    public String getRecommended(){
        return recommended;
    }
    public void setRecommended(String recommended){
        this.recommended=recommended;
    }
    public String getContactno(){
        return contactno;
    }
    public void setContactno(String contactno){
        this.contactno=contactno;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }

    public String getCost(){
        return cost;
    }
    public void setCost(String cost){
        this.cost=cost;
    }
}
