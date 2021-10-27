/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author My Computer
 */
public class News {
    private int no;
    private String title;
    private String content;
    private Date postedDate;
    private Account account;
    private List<GroupNews> groupnews;

    public News(String title, String content, Date postedDate, Account account) {
        this.title = title;
        this.content = content;
        this.postedDate = postedDate;
        this.account = account;
    }

    public News() {
    }
    
    

    public List<GroupNews> getGroupnews() {
        return groupnews;
    }

    public void setGroupnews(List<GroupNews> groupnews) {
        this.groupnews = groupnews;
    }
    
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
}
