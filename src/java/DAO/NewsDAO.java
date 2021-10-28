/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Account;
import Model.GroupNews;
import Model.News;
import java.sql.Date;
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
public class NewsDAO extends AbstractNewsDAO {

    @Override
    public void insert(News news) {
        String sql = "INSERT INTO news (title,content,posted_date,constructor) VALUES(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, news.getTitle());
            prepare_stmt.setString(2, news.getContent());
            prepare_stmt.setDate(3, news.getPostedDate());
            prepare_stmt.setString(4, news.getAccount().getUsername());
            prepare_stmt.executeUpdate();
            sql = "SELECT @@IDENTITY as no";
            prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            int no = 0;
            if (rs.next()) {
                no = rs.getInt("no");
            }
            sql = "INSERT INTO groupnews VALUES(?,?)";
            for (GroupNews grnews : news.getGroupnews()) {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setInt(1, grnews.getGroup().getGid());
                prepare_stmt.setInt(2, no);
                prepare_stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getTotalNews(String query) {
        String sql = "SELECT COUNT(*) as totalsearchednews FROM news WHERE ";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        int totalSearchedNews = 0;
        try {
            Date postedDate = Date.valueOf(query);
            sql += "posted_date = ?";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setDate(1, postedDate);
                rs = prepare_stmt.executeQuery();
                if (rs.next()) {
                    totalSearchedNews = rs.getInt("totalsearchednews");
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            sql += "title LIKE ? or content LIKE ? or constructor LIKE ?";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                for (int i = 1; i <= 3; i++) {
                    prepare_stmt.setString(i, "%" + query + "%");
                }
                rs = prepare_stmt.executeQuery();
                if (rs.next()) {
                    totalSearchedNews = rs.getInt("totalsearchednews");
                }
            } catch (SQLException ex1) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return totalSearchedNews;
    }

    @Override
    public List<News> search(String query, int offset, int limit) {
        List<News> piecesOfNews = new ArrayList<>();
        String sql = "SELECT * FROM news WHERE ";
        PreparedStatement prepare_stmt;
        ResultSet rs;
        News news;
        Account account;
        try {
            Date postedDate = Date.valueOf(query);
            sql += "posted_date = ?  ORDER BY posted_date desc  OFFSET ? rows FETCH next ? rows only ";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                prepare_stmt.setDate(1, postedDate);
                prepare_stmt.setInt(2, offset);
                prepare_stmt.setInt(3, limit);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    news = new News();
                    news.setNo(rs.getInt("no"));
                    news.setTitle(rs.getString("title"));
                    news.setContent(rs.getString("content"));
                    news.setPostedDate(rs.getDate("posted_date"));
                    account = new Account();
                    account.setUsername(rs.getString("constructor"));
                    news.setAccount(account);
                    piecesOfNews.add(news);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IllegalArgumentException ex) {
            sql += "title LIKE ? or content LIKE ? or constructor LIKE ?  ORDER BY posted_date desc "
                    + "OFFSET ? rows FETCH next ? rows only";
            try {
                prepare_stmt = connection.prepareStatement(sql);
                for (int i = 1; i <= 3; i++) {
                    prepare_stmt.setString(i, "%" + query + "%");
                }
                prepare_stmt.setInt(4, offset);
                prepare_stmt.setInt(5, limit);
                rs = prepare_stmt.executeQuery();
                while (rs.next()) {
                    news = new News();
                    news.setNo(rs.getInt("no"));
                    news.setTitle(rs.getString("title"));
                    news.setContent(rs.getString("content"));
                    news.setPostedDate(rs.getDate("posted_date"));
                    account = new Account();
                    account.setUsername(rs.getString("constructor"));
                    news.setAccount(account);
                    piecesOfNews.add(news);
                }
            } catch (SQLException ex1) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return piecesOfNews;
    }

    @Override
    public News getById(int no) {
        String sql = "SELECT * FROM news WHERE no = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, no);
            ResultSet rs = prepare_stmt.executeQuery();
            if (rs.next()) {
                News news = new News();
                Account account = new Account();
                news.setNo(rs.getInt("no"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setPostedDate(rs.getDate("posted_date"));
                account.setUsername(rs.getString("constructor"));
                news.setAccount(account);
                return news;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(News news) {
        String sql = "UPDATE news SET title = ?,content = ? WHERE no = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, news.getTitle());
            prepare_stmt.setString(2, news.getContent());
            prepare_stmt.setInt(3, news.getNo());
            prepare_stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void delete(int no) {
        String sql = "DELETE FROM groupnews WHERE no = ?";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            prepare_stmt.setInt(1, no);
            prepare_stmt.executeUpdate();
            sql = "DELETE FROM news WHERE no = ?";
            prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setInt(1, no);
            prepare_stmt.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public News getById(int no, Account account) {
        String sql = "SELECT n.no,n.title,n.content,n.posted_date,n.constructor "
                + " FROM account as a inner join groupaccount as gc on a.username = gc.username "
                + " inner join groupnews as gn on gc.gid = gn.gid inner join news as n on gn.no = n.no "
                + " WHERE a.username = ? AND n.no = ?" ;
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            prepare_stmt.setString(1, account.getUsername());
            prepare_stmt.setInt(2, no);
            ResultSet rs = prepare_stmt.executeQuery();
            News news;
            if(rs.next()){
                news = new News();
                news.setAccount(account);
                news.setContent(rs.getString("content"));
                news.setNo(rs.getInt("no"));
                news.setTitle(rs.getString("title"));
                news.setPostedDate(rs.getDate("posted_date"));
                return news;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

}
