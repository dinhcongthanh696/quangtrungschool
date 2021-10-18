/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
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
public abstract class AbstractClassRoomDAO extends BaseDAO{
    public abstract List<ClassRoom> getALL();
    
    protected List<ClassRoom> select(String sql, Object... parameters) {
        List<ClassRoom> classRooms = new ArrayList<>();
        try {
            PreparedStatement prepare_stmt = connection.prepareStatement(sql);
            Object param;
            for (int i = 0; i < parameters.length; i++) {
                param = parameters[i];
                if (param instanceof String) {
                    prepare_stmt.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    prepare_stmt.setInt(i + 1, (Integer) param);
                }
            }
            ResultSet rs = prepare_stmt.executeQuery();
            ClassRoom classRoom;
            while (rs.next()) {
                classRoom = new ClassRoom();
                classRoom.setClassCode(rs.getString("class_code"));
                // set more attributes for class
                classRooms.add(classRoom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractStudentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classRooms;
    }
}
