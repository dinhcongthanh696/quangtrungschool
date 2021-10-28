/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.Feature;
import Model.Group;
import Model.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class AccountDAO extends AbstractAccountDAO {

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            Account account;
            while (rs.next()) {
                account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                accounts.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    @Override
    public Account getById(String username) {
        Account account = null;
        String sql = "select ac.username,ac.password,f.url,g.gid from account as ac left join groupaccount as gc on ac.username = gc.username\n"
                + "left join [group] as g on gc.gid = g.gid left join groupfeature as gf on g.gid = gf.gid\n"
                + "left join feature as f on gf.fid = f.fid WHERE ac.username = ? "
                + "ORDER BY g.gid";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, username);
            ResultSet rs = prepare_stmt.executeQuery();
            Feature feature;
            Group group = new Group();
            group.setGid(0);
            while (rs.next()) {
                if (account == null) {
                    account = new Account();
                    account.setUsername(username);
                    account.setPassword(rs.getString("password"));
                }
                if (rs.getInt("gid") != group.getGid()) {
                    group = new Group();
                    group.setGid(rs.getInt("gid"));
                    account.getGroups().add(group);
                }
                if (rs.getString("url") != null) {
                    feature = new Feature();
                    feature.setUrl(rs.getString("url"));
                    account.getFeatures().add(feature);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return account;
    }

    @Override
    public void getGroupOfNews(Account account) {
        String sql = "SELECT DISTINCT TOP 5 n.no,n.title,n.content,n.posted_date,n.constructor "
                + " FROM account as a inner join groupaccount as gc on a.username = gc.username "
                + " inner join groupnews as gn on gc.gid = gn.gid inner join news as n on gn.no = n.no "
                + " WHERE a.username = ?"
                + " order by posted_date desc";
        List<News> group_of_news = new ArrayList<>();
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, account.getUsername());
            ResultSet rs = prepare_stmt.executeQuery();
            News news;
            while(rs.next()){
                news = new News();
                news.setAccount(account);
                news.setContent(rs.getString("content"));
                news.setNo(rs.getInt("no"));
                news.setTitle(rs.getString("title"));
                news.setPostedDate(rs.getDate("posted_date"));
                group_of_news.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        account.setGroup_of_news(group_of_news);
    }

}
