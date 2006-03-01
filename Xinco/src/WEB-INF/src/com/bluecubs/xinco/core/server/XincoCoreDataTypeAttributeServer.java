/**
 *Copyright 2004 blueCubs.com
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 *************************************************************
 * This project supports the blueCubs vision of giving back
 * to the community in exchange for free software!
 * More information on: http://www.bluecubs.org
 *************************************************************
 *
 * Name:            XincoCoreDataTypeAttributeServer
 *
 * Description:     data type attribute
 *
 * Original Author: Alexander Manes
 * Date:            2004
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.core.server;

import java.util.Vector;
import java.sql.*;

import com.bluecubs.xinco.core.*;

public class XincoCoreDataTypeAttributeServer extends XincoCoreDataTypeAttribute {
    
    //create data type attribute object for data structures
    public XincoCoreDataTypeAttributeServer(int attrID1, int attrID2, XincoDBManager DBM) throws XincoException {
        
        try {
            Statement stmt = DBM.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM xinco_core_data_type_attribute WHERE xinco_core_data_type_id=" + attrID1 + " AND attribute_id=" + attrID2);

			//throw exception if no result found
			int RowCount = 0;
            while (rs.next()) {
				RowCount++;
				setXinco_core_data_type_id(rs.getInt("xinco_core_data_type_id"));
				setAttribute_id(rs.getInt("attribute_id"));
                setDesignation(rs.getString("designation"));
				setData_type(rs.getString("data_type"));
				setSize(rs.getInt("size"));
            }
			if (RowCount < 1) {
				throw new XincoException();
			}

            stmt.close();
        } catch (Exception e) {
        	throw new XincoException();
        }
        
    }

    //create data type attribute object for data structures
    public XincoCoreDataTypeAttributeServer(int attrID1, int attrID2, String attrD, String attrDT, int attrS) throws XincoException {
        
		setXinco_core_data_type_id(attrID1);
		setAttribute_id(attrID2);
        setDesignation(attrD);
		setData_type(attrDT);
		setSize(attrS);
        
    }
    
    //create complete list of data type attributes
    public static Vector getXincoCoreDataTypeAttributes(int attrID, XincoDBManager DBM) {
        
        Vector coreDataTypeAttributes = new Vector();
        
        try {
            Statement stmt = DBM.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM xinco_core_data_type_attribute WHERE xinco_core_data_type_id =" + attrID + " ORDER BY attribute_id");

            while (rs.next()) {
				coreDataTypeAttributes.addElement(new XincoCoreDataTypeAttributeServer(rs.getInt("xinco_core_data_type_id"), rs.getInt("attribute_id"), rs.getString("designation"), rs.getString("data_type"), rs.getInt("size")));
            }

            stmt.close();
        } catch (Exception e) {
			coreDataTypeAttributes.removeAllElements();
        }

        return coreDataTypeAttributes;
    }

}
