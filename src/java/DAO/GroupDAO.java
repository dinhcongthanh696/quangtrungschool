/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Group;
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
public class GroupDAO extends AbstractGroupDAO{

    @Override
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM [group]";
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            ResultSet rs = prepare_stmt.executeQuery();
            Group group;
            while(rs.next()){
                group = new Group();
                group.setGid(rs.getInt("gid"));
                group.setGname(rs.getString("gname"));
                groups.add(group);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }
    
}
