/**
 *Copyright 2005 blueCubs.com
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
 * Name:            XincoArchiveThread
 *
 * Description:     handle document indexing in thread 
 *
 * Original Author: Alexander Manes
 * Date:            2005/01/16
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.archive;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.bluecubs.xinco.core.server.XincoDBManager;

/**
 * This class runs document archiving in a separate thread
 * (only one archiving thread is allowed)
 */
public class XincoArchiveThread extends Thread {
	
	public static XincoArchiveThread instance = null;
	
	public Calendar firstRun = null;
	public Calendar lastRun = null;
	
	public void run() {
		long archive_period = 14400000;
		firstRun = new GregorianCalendar();
		while (true) {
			try {
				XincoDBManager dbm = null;
				dbm = new XincoDBManager();
				archive_period = dbm.config.FileArchivePeriod;
				//exit archiver if period = 0
				if (archive_period == 0) {
					break;
				}
				XincoArchiver.archiveData(dbm);
				lastRun = new GregorianCalendar();
				dbm.con.close();
				dbm = null;
			} catch (Exception e){
				//continue, wait and try again...
				archive_period = 14400000;
			}
			try {
				Thread.sleep(archive_period);
			} catch (Exception se) {
				break;
			}
		}
	}
	
	public static XincoArchiveThread getInstance() {
		if (instance == null) {
			instance = new XincoArchiveThread();
		}
		return instance;
	}
	
	private XincoArchiveThread() {
	}
	
}
