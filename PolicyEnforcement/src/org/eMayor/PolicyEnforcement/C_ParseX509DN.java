/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_ParseX509DN {
	String m_S_Email; // Email Address
	String m_S_CN; //Contact Name
	String m_S_OU; // Organisation Unit
	String m_S_O; // Organisation
	String m_S_ST; // State
	String m_S_C; // Country
	
	public C_ParseX509DN(String myDN){
		try {
			int I_indexEmail = myDN.indexOf("EMAILADDRESS="); //
			if (I_indexEmail>-1) {
				I_indexEmail=I_indexEmail+13;
				int IindexEmailEnd = myDN.indexOf(",",I_indexEmail);
				if (IindexEmailEnd>-1) 
					m_S_Email = myDN.substring(I_indexEmail,IindexEmailEnd);
				else
					m_S_Email = myDN.substring(I_indexEmail);
			} else m_S_Email="Not Found";
			
		} catch (Exception e) {
			m_S_Email="Parse Error";
		}
		
		try {
			int I_indexCN = myDN.indexOf("CN=");
			if (I_indexCN>-1){
				I_indexCN=I_indexCN+3;
				int I_indexCNEnd=myDN.indexOf(",",I_indexCN);
				if (I_indexCNEnd>-1) 
					m_S_CN = myDN.substring(I_indexCN,I_indexCNEnd);
				else
					m_S_CN = myDN.substring(I_indexCN);
			} else m_S_CN="Not Found";
			
		} catch (Exception e) {
			m_S_CN="Parse Error";
		}
		try {
			int I_indexOU = myDN.indexOf("OU=");
			if (I_indexOU>-1 )
				{	I_indexOU=I_indexOU+3;
				    int I_indexOUEnd = myDN.indexOf(",",I_indexOU);
				    if (I_indexOUEnd>-1) 
				    	m_S_OU = myDN.substring(I_indexOU,I_indexOUEnd);
				    else 
				    	m_S_OU = myDN.substring(I_indexOU);
				    
				} else m_S_OU="Not Found";
		} catch (Exception e) {
			m_S_OU="Parse Error";
		}
		
		
		try {	
			int I_indexO = myDN.indexOf("O=");
			if (I_indexO>-1){
				I_indexO=I_indexO+2;
				int I_indexOEnd = myDN.indexOf(",",I_indexO);
				if (I_indexOEnd>-1) 
					m_S_O = myDN.substring(I_indexO,I_indexOEnd);
				else 
					m_S_O = myDN.substring(I_indexO);
				
			} else m_S_O="Not Found";
		} catch (Exception e) {
			m_S_O="Parse Error";
		}
		
		
		try {
			int I_indexST = myDN.indexOf("ST=");
			if (I_indexST>-1){
				I_indexST=I_indexST+3;
				int I_indexSTEnd = myDN.indexOf(",",I_indexST);
				if (I_indexSTEnd>-1)
					m_S_ST = myDN.substring(I_indexST,I_indexSTEnd);
				else 
					m_S_ST = myDN.substring(I_indexST);
			} else m_S_ST="Not Found";
		} catch (Exception e) {
			m_S_ST="Parse Error";
		}
		
		try {
			int I_indexC = myDN.indexOf("C=");
			if (I_indexC>-1) {
				I_indexC=I_indexC+2;			
				int I_indexCEnd = myDN.indexOf(",",I_indexC);
				if (I_indexCEnd>-1)
					m_S_C = myDN.substring(I_indexC, I_indexCEnd);
				else 
					m_S_C = myDN.substring(I_indexC);
			} else m_S_C="Not Found";
		} catch (Exception e) {
			m_S_C="Parse Error";
		}
		
	}

}
