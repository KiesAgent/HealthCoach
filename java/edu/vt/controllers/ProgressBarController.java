//<!--
//  - Created by Xiao Guo and Chenyu Mao
//  - Created by Osman Balci on 2021.7.15
//  - Copyright © 2021 Osman Balci. All rights reserved.
//  -->
package edu.vt.controllers;
import edu.vt.EntityBeans.User;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Named("progressBarView")
@SessionScoped
public class ProgressBarController implements Serializable{

    private double protein;
    private double fat;
    private double carbs;
    private double calorie;


    public Double getProtein() {
//        System.out.println("num is "+protein);
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getCalorie() {
        return calorie;
    }

    public void setCalorie(Double calorie) {
        this.calorie = calorie;
    }

    public String update(){

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        protein += (protein/150 * 100);
        fat += (fat/50 * 100);
        carbs += (carbs/200 * 100);
        calorie += (calorie/2000 * 100);


        return "/userAccount/Profile?faces-redirect=true";
    }
}
