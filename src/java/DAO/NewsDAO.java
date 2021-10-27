/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.GroupNews;
import Model.News;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class NewsDAO extends AbstractNewsDAO{

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
            if(rs.next()){
                no = rs.getInt("no");
            }
            sql = "INSERT INTO groupnews VALUES(?,?)";
            System.out.println(news.getGroupnews() == null);
            for(GroupNews grnews : news.getGroupnews()){
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
        } finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
