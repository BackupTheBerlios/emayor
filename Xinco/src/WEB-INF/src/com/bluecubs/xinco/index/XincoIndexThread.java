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
 * Name:            XincoIndexThread
 *
 * Description:     handle document indexing in thread 
 *
 * Original Author: Alexander Manes
 * Date:            2004/12/18
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.index;

import com.bluecubs.xinco.core.XincoCoreData;
import com.bluecubs.xinco.core.server.XincoDBManager;

/**
 * This class starts document indexing in a separate thread
 */
public class XincoIndexThread extends Thread {
	
	private XincoCoreData d = null;
	private boolean index_content = false;
	private XincoDBManager dbm = null;

	public void run() {
		XincoIndexer.indexXincoCoreData(d, index_content, dbm);
		try {
			dbm.con.close();
		} catch (Exception e)
		{
			//do nothing
		}
	}
	
	public XincoIndexThread(XincoCoreData d, boolean index_content, XincoDBManager dbm) {
		this.d = d;
		this.index_content = index_content;
		this.dbm = dbm;
	}
	
}
