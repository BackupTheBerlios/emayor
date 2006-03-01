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
 * Name:            XincoCoreLanguageServer
 *
 * Description:     language
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

import java.sql.*;
import java.util.Vector;

import com.bluecubs.xinco.core.*;

public class XincoCoreLanguageServer extends XincoCoreLanguage {
    
    //create language object for data structures
    public XincoCoreLanguageServer(int attrID, XincoDBManager DBM) throws XincoException {
        
        try {
            
            Statement stmt = DBM.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM xinco_core_language WHERE id=" + attrID);

			//throw exception if no result found
			int RowCount = 0;
            while (rs.next()) {
				RowCount++;
                setId(rs.getInt("id"));
				setSign(rs.getString("sign"));
				setDesignation(rs.getString("designation"));
            }
			if (RowCount < 1) {
				throw new XincoException();
			}

            stmt.close();
            
        } catch (Exception e) {
			throw new XincoException();
        }
        
    }
    
    //create language object for data structures
    public XincoCoreLanguageServer(int attrID, String attrS, String attrD) throws XincoException {
        
        setId(attrID);
		setSign(attrS);
		setDesignation(attrD);
        
    }
    
    //write to db
    public int write2DB(XincoDBManager DBM) throws XincoException {

        try {

            Statement stmt;

            if (getId() > 0) {
				stmt = DBM.con.createStatement();
				stmt.executeUpdate("UPDATE xinco_core_language SET sign='" + getSign().replaceAll("'","\\\\'") + "', designation='" + getDesignation().replaceAll("'","\\\\'") + "' WHERE id=" + getId());
				stmt.close();
            } else {
				setId(DBM.getNewID("xinco_core_language"));
            
				stmt = DBM.con.createStatement();
				stmt.executeUpdate("INSERT INTO xinco_core_language VALUES (" + getId() + ", '" + getSign().replaceAll("'","\\\\'") + "', '" + getDesignation().replaceAll("'","\\\\'") + "')");
				stmt.close();
            }
            
            DBM.con.commit();
            
        } catch (Exception e) {
            try {
                DBM.con.rollback();
            } catch (Exception erollback) {
            }
            throw new XincoException();
        }
        
        return getId();
        
    }
    
    //create complete list of languages
    public static Vector getXincoCoreLanguages(XincoDBManager DBM) {
        
        Vector coreLanguages = new Vector();
        
        try {
            
            Statement stmt = DBM.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM xinco_core_language ORDER BY designation");

            while (rs.next()) {
                coreLanguages.addElement(new XincoCoreLanguageServer(rs.getInt("id"), rs.getString("sign"), rs.getString("designation")));
            }

            stmt.close();
            
        } catch (Exception e) {
            coreLanguages.removeAllElements();
        }

        return coreLanguages;
    }

}
