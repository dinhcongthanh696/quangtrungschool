/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author My Computer
 */
public class Group {
    private int gid;
    private String gname;

    public Group(int gid) {
        this.gid = gid;
    }

    public Group() {
    }
    
    

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
    
    
    
}
