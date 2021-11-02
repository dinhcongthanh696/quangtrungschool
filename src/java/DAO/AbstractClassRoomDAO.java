/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ClassRoom;
import java.util.List;

/**
 *
 * @author My Computer
 */
public abstract class AbstractClassRoomDAO extends BaseDAO{
    public abstract List<ClassRoom> getALL();
    public abstract void insert(ClassRoom classroom);
}
