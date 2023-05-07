/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author My Computer
 */
public class Account implements Serializable{
    private String username;
    private String password;
    private int roleNumber = 0;
    private List<Group> groups = new ArrayList<>();
    private List<Feature> features = new ArrayList<>();
    private List<News> group_of_news;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password , List<Group> groups  ) {
        this.username = username;
        this.password = password;
        this.groups = groups;
        this.features = features;
    }
    
    
    public Account() {
    }

    public List<News> getGroup_of_news() {
        return group_of_news;
    }

    public void setGroup_of_news(List<News> group_of_news) {
        this.group_of_news = group_of_news;
    }
    

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
    
    
    
    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        for(int i = 0 ; i < this.groups.size() ; i++){
            if(this.groups.get(i).getGid() != other.groups.get(i).getGid()) return false;
        }
        return true;
    }
    
    
}
