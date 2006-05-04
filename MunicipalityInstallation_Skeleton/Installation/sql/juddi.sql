-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	4.1.12a-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Table structure for table `juddi`.`address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `ADDRESS_ID` int(11) NOT NULL default '0',
  `USE_TYPE` varchar(255) default NULL,
  `SORT_CODE` varchar(10) default NULL,
  `TMODEL_KEY` varchar(41) default NULL,
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`,`ADDRESS_ID`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`, `CONTACT_ID`) REFERENCES `contact` (`BUSINESS_KEY`, `CONTACT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`address`
--

/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`address_line`
--

DROP TABLE IF EXISTS `address_line`;
CREATE TABLE `address_line` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `ADDRESS_ID` int(11) NOT NULL default '0',
  `ADDRESS_LINE_ID` int(11) NOT NULL default '0',
  `LINE` varchar(80) NOT NULL default '',
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) default NULL,
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`,`ADDRESS_ID`,`ADDRESS_LINE_ID`),
  CONSTRAINT `address_line_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`, `CONTACT_ID`, `ADDRESS_ID`) REFERENCES `address` (`BUSINESS_KEY`, `CONTACT_ID`, `ADDRESS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`address_line`
--

/*!40000 ALTER TABLE `address_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `address_line` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`auth_token`
--

DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token` (
  `AUTH_TOKEN` varchar(51) NOT NULL default '',
  `PUBLISHER_ID` varchar(20) NOT NULL default '',
  `PUBLISHER_NAME` varchar(255) NOT NULL default '',
  `CREATED` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_USED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `NUMBER_OF_USES` int(11) NOT NULL default '0',
  `TOKEN_STATE` int(11) NOT NULL default '0',
  PRIMARY KEY  (`AUTH_TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`auth_token`
--

/*!40000 ALTER TABLE `auth_token` DISABLE KEYS */;
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:010CB420-6708-11DA-B420-D2FE9E5F1770','juddi','juddi','2005-12-07 10:58:23','2005-12-07 10:58:23',1,1),
 ('authToken:09A54EC0-2C08-11DA-8EC0-D6E07C1AEB6F','juddi','juddi','2005-09-23 09:59:59','2005-09-23 09:59:59',1,1),
 ('authToken:10952B70-058D-11DA-AF48-F90EAFDC0CB9','juddi','juddi','2005-08-05 10:43:58','2005-08-05 10:43:58',1,1),
 ('authToken:16C6F8B0-2C08-11DA-B8B0-9E79CF06BCA3','juddi','juddi','2005-09-23 10:00:21','2005-09-23 10:00:21',1,1),
 ('authToken:19CB1C40-058D-11DA-AF48-BF337C79C5D0','juddi','juddi','2005-08-05 10:44:14','2005-08-05 10:44:14',1,1),
 ('authToken:2008D060-2C08-11DA-9060-D89A197BE8FC','juddi','juddi','2005-09-23 10:00:37','2005-09-23 10:00:37',1,1),
 ('authToken:225C0FF0-058C-11DA-AF48-E980B72684AD','juddi','juddi','2005-08-05 10:37:19','2005-08-05 10:37:19',0,1),
 ('authToken:237631C0-2C08-11DA-B1C0-DF3B26BE4F72','juddi','juddi','2005-09-23 10:00:42','2005-09-23 10:00:42',1,1),
 ('authToken:26182D90-058C-11DA-AF48-D92CEA5AA0B3','juddi','juddi','2005-08-05 10:37:25','2005-08-05 10:37:25',0,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:26D4F870-058D-11DA-AF48-CD0943DFE5C6','juddi','juddi','2005-08-05 10:44:36','2005-08-05 10:44:36',1,1),
 ('authToken:26E80B40-058D-11DA-AF48-E40545526193','juddi','juddi','2005-08-05 10:44:36','2005-08-05 10:44:36',1,1),
 ('authToken:298A7800-6B05-11DA-B800-CD22A0924C78','juddi','juddi','2005-12-12 12:48:07','2005-12-12 12:48:07',1,1),
 ('authToken:32E66050-058C-11DA-AF48-E536B5201D84','juddi','juddi','2005-08-05 10:37:46','2005-08-05 10:37:46',1,1),
 ('authToken:3302E900-058C-11DA-AF48-FF3F3CC48DC4','juddi','juddi','2005-08-05 10:37:47','2005-08-05 10:37:47',1,1),
 ('authToken:349918A0-2C08-11DA-98A0-C1F37DC5466A','juddi','juddi','2005-09-23 10:01:11','2005-09-23 10:01:11',1,1),
 ('authToken:3A784140-0590-11DA-AF48-D8AF2E391C8B','juddi','juddi','2005-08-05 11:06:37','2005-08-05 11:06:37',1,1),
 ('authToken:3AEE57B0-2C08-11DA-97B0-F7600973E8B6','juddi','juddi','2005-09-23 10:01:22','2005-09-23 10:01:22',1,1),
 ('authToken:3EF3BB20-6B05-11DA-BB20-DB5CF375956D','juddi','juddi','2005-12-12 12:48:43','2005-12-12 12:48:43',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:3EF7D5F0-0590-11DA-AF48-E614B4A7C525','juddi','juddi','2005-08-05 11:06:45','2005-08-05 11:06:45',1,1),
 ('authToken:3FEC4420-2C08-11DA-8420-C51A983D2EA9','juddi','juddi','2005-09-23 10:01:30','2005-09-23 10:01:30',1,1),
 ('authToken:40A112C0-0588-11DA-AF48-A961B8E07EB7','juddi','juddi','2005-08-05 10:09:31','2005-08-05 10:09:31',1,1),
 ('authToken:40AF6AA0-0588-11DA-AF48-D1589B7AC1C2','juddi','juddi','2005-08-05 10:09:32','2005-08-05 10:09:32',1,1),
 ('authToken:41019000-1DF7-11DA-B4E8-BC9CC2D4C743','juddi','juddi','2005-09-05 12:24:34','2005-09-05 12:24:34',1,1),
 ('authToken:46891380-2C08-11DA-9380-A305A43C1910','juddi','juddi','2005-09-23 10:01:41','2005-09-23 10:01:41',1,1),
 ('authToken:46F3C400-6B05-11DA-8400-D4C9754D10AD','juddi','juddi','2005-12-12 12:48:57','2005-12-12 12:48:57',1,1),
 ('authToken:4836CED0-1DF7-11DA-B4E8-B6D1035DDCA4','juddi','juddi','2005-09-05 12:24:46','2005-09-05 12:24:46',1,1),
 ('authToken:495C2A10-058F-11DA-AF48-FC828C56EDAF','juddi','juddi','2005-08-05 10:59:53','2005-08-05 10:59:53',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:4973F7D0-058F-11DA-AF48-D4002DDB2ADB','juddi','juddi','2005-08-05 10:59:53','2005-08-05 10:59:53',1,1),
 ('authToken:5271D010-6B05-11DA-9010-F6463E44D0A9','juddi','juddi','2005-12-12 12:49:16','2005-12-12 12:49:16',1,1),
 ('authToken:52E32110-058F-11DA-AF48-9392E0560170','juddi','juddi','2005-08-05 11:00:09','2005-08-05 11:00:09',1,1),
 ('authToken:52F3E9F0-058F-11DA-AF48-8A3E33D28F47','juddi','juddi','2005-08-05 11:00:09','2005-08-05 11:00:09',1,1),
 ('authToken:55458D00-1DF7-11DA-B4E8-81885995A993','juddi','juddi','2005-09-05 12:25:08','2005-09-05 12:25:08',1,1),
 ('authToken:56D4B500-6B05-11DA-B500-F5E2EE177205','juddi','juddi','2005-12-12 12:49:23','2005-12-12 12:49:23',1,1),
 ('authToken:5AAA7140-0590-11DA-AF48-8DF2791725BB','juddi','juddi','2005-08-05 11:07:31','2005-08-05 11:07:31',1,1),
 ('authToken:60CBD2C0-FD03-11D9-92C0-E6D1DC474845','juddi','juddi','2005-07-25 13:58:13','2005-07-25 13:58:13',1,1),
 ('authToken:6142FEC0-6B05-11DA-BEC0-BA84A440122C','juddi','juddi','2005-12-12 12:49:41','2005-12-12 12:49:41',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:644EB820-6B05-11DA-B820-CD1418D70C8C','juddi','juddi','2005-12-12 12:49:46','2005-12-12 12:49:46',1,1),
 ('authToken:6614FCB0-1DF7-11DA-B4E8-D690001960C5','juddi','juddi','2005-09-05 12:25:36','2005-09-05 12:25:36',1,1),
 ('authToken:6C2AE520-FD03-11D9-A520-8344ADA0C996','juddi','juddi','2005-07-25 13:58:32','2005-07-25 13:58:32',1,1),
 ('authToken:6FADCA80-6B05-11DA-8A80-FBFD4F193673','juddi','juddi','2005-12-12 12:50:05','2005-12-12 12:50:05',1,1),
 ('authToken:70BB6D70-058D-11DA-AF48-9D5CF4B3F3F5','juddi','juddi','2005-08-05 10:46:40','2005-08-05 10:46:40',1,1),
 ('authToken:70C9C550-058D-11DA-AF48-B10A7D2931D7','juddi','juddi','2005-08-05 10:46:40','2005-08-05 10:46:40',1,1),
 ('authToken:8B337820-5C0C-11DA-AECA-9C2B7A56D804','juddi','juddi','2005-11-23 11:33:10','2005-11-23 11:33:10',1,1),
 ('authToken:8D2BF800-0581-11DA-AF48-B128C3EB6485','juddi','juddi','2005-08-05 09:21:34','2005-08-05 09:21:34',1,1),
 ('authToken:91DCF490-058F-11DA-AF48-E9AD7C05F860','juddi','juddi','2005-08-05 11:01:54','2005-08-05 11:01:54',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:9441FE30-001F-11DA-B29D-95BCBCD9F79D','juddi','juddi','2005-07-29 12:57:39','2005-07-29 12:57:39',1,1),
 ('authToken:96B18430-5C0C-11DA-AECA-B7A5836177A5','juddi','juddi','2005-11-23 11:33:29','2005-11-23 11:33:29',1,1),
 ('authToken:98C3C540-058F-11DA-AF48-95DD6BF23615','juddi','juddi','2005-08-05 11:02:06','2005-08-05 11:02:06',1,1),
 ('authToken:9A36DA60-5C0C-11DA-AECA-90E245C043EC','juddi','juddi','2005-11-23 11:33:35','2005-11-23 11:33:35',1,1),
 ('authToken:9A973D40-001F-11DA-B29D-9D31DDED0F5D','juddi','juddi','2005-07-29 12:57:49','2005-07-29 12:57:49',1,1),
 ('authToken:9C7C04E0-5C0B-11DA-AECA-8D8BAD59DEAA','juddi','juddi','2005-11-23 11:26:30','2005-11-23 11:26:30',1,1),
 ('authToken:9D210200-5C0C-11DA-AECA-80DA695A57B3','juddi','juddi','2005-11-23 11:33:40','2005-11-23 11:33:40',1,1),
 ('authToken:A0A0F670-5C0B-11DA-AECA-B5F3FE72CD67','juddi','juddi','2005-11-23 11:26:37','2005-11-23 11:26:37',1,1),
 ('authToken:A1684B80-058F-11DA-AF48-E0879B7B1E78','juddi','juddi','2005-08-05 11:02:20','2005-08-05 11:02:20',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:A50739A0-058E-11DA-AF48-A7A663669301','juddi','juddi','2005-08-05 10:55:17','2005-08-05 10:55:17',1,1),
 ('authToken:A51A4C70-058E-11DA-AF48-EBBF706BA65D','juddi','juddi','2005-08-05 10:55:17','2005-08-05 10:55:17',1,1),
 ('authToken:A5BB92A0-5C0B-11DA-AECA-A285F022C3F3','juddi','juddi','2005-11-23 11:26:45','2005-11-23 11:26:45',1,1),
 ('authToken:A6FECE70-058F-11DA-AF48-91A0F67F6F1E','juddi','juddi','2005-08-05 11:02:30','2005-08-05 11:02:30',1,1),
 ('authToken:A70F7040-058F-11DA-AF48-F90DBA698DD7','juddi','juddi','2005-08-05 11:02:30','2005-08-05 11:02:30',1,1),
 ('authToken:A9B5A3A0-5C0B-11DA-AECA-C5047BF87647','juddi','juddi','2005-11-23 11:26:52','2005-11-23 11:26:52',1,1),
 ('authToken:AEF57900-001F-11DA-B29D-A531308F8696','juddi','juddi','2005-07-29 12:58:24','2005-07-29 12:58:24',1,1),
 ('authToken:AF281290-0581-11DA-AF48-D66C58021124','juddi','juddi','2005-08-05 09:22:30','2005-08-05 09:22:30',1,1),
 ('authToken:AF425150-0581-11DA-AF48-EAE7E91BB381','juddi','juddi','2005-08-05 09:22:31','2005-08-05 09:22:31',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:CBB52700-058F-11DA-AF48-81AE6C40B8A7','juddi','juddi','2005-08-05 11:03:31','2005-08-05 11:03:31',0,1),
 ('authToken:D23445F0-204E-11DA-85F0-CE3863BFFD6A','juddi','juddi','2005-09-08 11:56:26','2005-09-08 11:56:26',1,1),
 ('authToken:D62BE5F0-204E-11DA-A5F0-A83F21A3A743','juddi','juddi','2005-09-08 11:56:33','2005-09-08 11:56:33',1,1),
 ('authToken:D988DC50-0A44-11DA-9C50-9525FFD0C45C','juddi','juddi','2005-08-11 10:49:38','2005-08-11 10:49:38',1,1),
 ('authToken:DF327210-0A44-11DA-B210-CA13C792BF4F','juddi','juddi','2005-08-11 10:49:47','2005-08-11 10:49:47',1,1),
 ('authToken:DF6F1EB0-5C0E-11DA-AECA-DA4523363C93','juddi','juddi','2005-11-23 11:49:50','2005-11-23 11:49:50',1,1),
 ('authToken:E1188770-204E-11DA-8770-A99A987781C5','juddi','juddi','2005-09-08 11:56:51','2005-09-08 11:56:51',1,1),
 ('authToken:E348C040-2C07-11DA-8040-89AF0689EFD3','juddi','juddi','2005-09-23 09:58:55','2005-09-23 09:58:55',1,1),
 ('authToken:EAED2AC0-5C0E-11DA-AECA-FB74E6AD7A47','juddi','juddi','2005-11-23 11:50:10','2005-11-23 11:50:10',1,1);
INSERT INTO `auth_token` (`AUTH_TOKEN`,`PUBLISHER_ID`,`PUBLISHER_NAME`,`CREATED`,`LAST_USED`,`NUMBER_OF_USES`,`TOKEN_STATE`) VALUES 
 ('authToken:EEA588B0-2C07-11DA-88B0-9C6D427F1D78','juddi','juddi','2005-09-23 09:59:14','2005-09-23 09:59:14',1,1),
 ('authToken:EF6B8570-204E-11DA-8570-B3640DE9BAC4','juddi','juddi','2005-09-08 11:57:15','2005-09-08 11:57:15',1,1),
 ('authToken:F88E9A40-0A44-11DA-9A40-834CD422D99B','juddi','juddi','2005-08-11 10:50:30','2005-08-11 10:50:30',1,1),
 ('authToken:F9C6A7B0-2C07-11DA-A7B0-9EA992416304','juddi','juddi','2005-09-23 09:59:32','2005-09-23 09:59:32',1,1),
 ('authToken:FAA76520-0583-11DA-AF48-C6792FEA6359','juddi','juddi','2005-08-05 09:38:56','2005-08-05 09:38:56',1,1),
 ('authToken:FAC414E0-0583-11DA-AF48-F4FAFCA98EA8','juddi','juddi','2005-08-05 09:38:56','2005-08-05 09:38:56',1,1);
/*!40000 ALTER TABLE `auth_token` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`binding_category`
--

DROP TABLE IF EXISTS `binding_category`;
CREATE TABLE `binding_category` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `CATEGORY_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(41) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BINDING_KEY`,`CATEGORY_ID`),
  CONSTRAINT `binding_category_ibfk_1` FOREIGN KEY (`BINDING_KEY`) REFERENCES `binding_template` (`BINDING_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`binding_category`
--

/*!40000 ALTER TABLE `binding_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `binding_category` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`binding_descr`
--

DROP TABLE IF EXISTS `binding_descr`;
CREATE TABLE `binding_descr` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `BINDING_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BINDING_KEY`,`BINDING_DESCR_ID`),
  CONSTRAINT `binding_descr_ibfk_1` FOREIGN KEY (`BINDING_KEY`) REFERENCES `binding_template` (`BINDING_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`binding_descr`
--

/*!40000 ALTER TABLE `binding_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `binding_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`binding_template`
--

DROP TABLE IF EXISTS `binding_template`;
CREATE TABLE `binding_template` (
  `SERVICE_KEY` varchar(41) NOT NULL default '',
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `ACCESS_POINT_TYPE` varchar(20) default NULL,
  `ACCESS_POINT_URL` varchar(255) default NULL,
  `HOSTING_REDIRECTOR` varchar(255) default NULL,
  `LAST_UPDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`BINDING_KEY`),
  KEY `SERVICE_KEY` (`SERVICE_KEY`),
  CONSTRAINT `binding_template_ibfk_1` FOREIGN KEY (`SERVICE_KEY`) REFERENCES `business_service` (`SERVICE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`binding_template`
--

/*!40000 ALTER TABLE `binding_template` DISABLE KEYS */;
INSERT INTO `binding_template` (`SERVICE_KEY`,`BINDING_KEY`,`ACCESS_POINT_TYPE`,`ACCESS_POINT_URL`,`HOSTING_REDIRECTOR`,`LAST_UPDATE`) VALUES 
 ('F9CDD3A0-2C07-11DA-93A0-CE5781EB1A04','09AEC4A0-2C08-11DA-84A0-DA63E63F7B40','https://localhost:84','https://localhost:8443/eMayorEJB/ForwardManager',NULL,'2005-09-23 09:59:59'),
 ('3EFCB7F0-0590-11DA-AF48-8A2A4612A854','3F021300-6B05-11DA-9300-A5EA1D1CFEC9','https','https://EMAYOR_AACHEN_HOST:EMAYOR_AACHEN_PORT/eMayorEJB/ForwardManager',NULL,'2005-12-12 12:48:43'),
 ('3FF5E110-2C08-11DA-A110-B2C92EEB3FBF','46976B60-2C08-11DA-AB60-CE67564FBDF0','https://localhost:84','https://localhost:8443/eMayorEJB/ForwardManager',NULL,'2005-09-23 10:01:41'),
 ('DF372D00-0A44-11DA-AD00-B32AEF7C7C49','5278FC00-6B05-11DA-BC00-BDC01374F3B7','https','https://EMAYOR_BOZEN_HOST:EMAYOR_BOZEN_PORT/eMayorEJB/ForwardManager',NULL,'2005-12-12 12:49:16'),
 ('A5C9EA80-5C0B-11DA-AECA-D3A4595B4B24','614EE5A0-6B05-11DA-A5A0-B90530AE9B1D','https','https://EMAYOR_SIENA_HOST:EMAYOR_SIENA_PORT/eMayorEJB/ForwardManager',NULL,'2005-12-12 12:49:41'),
 ('A9BF1980-5C0B-11DA-AECA-C3FE8C056DA2','6FB74060-6B05-11DA-8060-934C9525590A','https','https://EMAYOR_SEVILLE_HOST:EMAYOR_SEVILLE_PORT/eMayorEJB/ForwardManager',NULL,'2005-12-12 12:50:05');
/*!40000 ALTER TABLE `binding_template` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_category`
--

DROP TABLE IF EXISTS `business_category`;
CREATE TABLE `business_category` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CATEGORY_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(41) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`CATEGORY_ID`),
  CONSTRAINT `business_category_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_category`
--

/*!40000 ALTER TABLE `business_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_category` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_descr`
--

DROP TABLE IF EXISTS `business_descr`;
CREATE TABLE `business_descr` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `BUSINESS_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`BUSINESS_DESCR_ID`),
  CONSTRAINT `business_descr_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_descr`
--

/*!40000 ALTER TABLE `business_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_entity`
--

DROP TABLE IF EXISTS `business_entity`;
CREATE TABLE `business_entity` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `AUTHORIZED_NAME` varchar(255) NOT NULL default '',
  `PUBLISHER_ID` varchar(20) default NULL,
  `OPERATOR` varchar(255) NOT NULL default '',
  `LAST_UPDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_entity`
--

/*!40000 ALTER TABLE `business_entity` DISABLE KEYS */;
INSERT INTO `business_entity` (`BUSINESS_KEY`,`AUTHORIZED_NAME`,`PUBLISHER_ID`,`OPERATOR`,`LAST_UPDATE`) VALUES 
 ('3307CB00-058C-11DA-AF48-F29D5ADEFDBE','juddi','juddi','jUDDI.org','2005-08-05 10:37:47'),
 ('3A7D2340-0590-11DA-AF48-D7A6F8CC5A48','juddi','juddi','jUDDI.org','2005-08-05 11:06:37'),
 ('3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82','juddi','juddi','jUDDI.org','2005-09-23 10:01:22'),
 ('530BB7B0-058F-11DA-AF48-868C01738359','juddi','juddi','jUDDI.org','2005-08-05 11:00:09'),
 ('9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C','juddi','juddi','jUDDI.org','2005-11-23 11:26:30'),
 ('A0A82260-5C0B-11DA-AECA-FFA5982E2C8C','juddi','juddi','jUDDI.org','2005-11-23 11:26:37'),
 ('A711E140-058F-11DA-AF48-BFE51A48FC88','juddi','juddi','jUDDI.org','2005-08-05 11:02:30'),
 ('D9AC90F0-0A44-11DA-90F0-A986892F16BD','juddi','juddi','jUDDI.org','2005-08-11 10:49:38'),
 ('EEAA43A0-2C07-11DA-83A0-E7504C5C4864','juddi','juddi','jUDDI.org','2005-09-23 09:59:14');
/*!40000 ALTER TABLE `business_entity` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_identifier`
--

DROP TABLE IF EXISTS `business_identifier`;
CREATE TABLE `business_identifier` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `IDENTIFIER_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(41) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`IDENTIFIER_ID`),
  CONSTRAINT `business_identifier_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_identifier`
--

/*!40000 ALTER TABLE `business_identifier` DISABLE KEYS */;
/*!40000 ALTER TABLE `business_identifier` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_name`
--

DROP TABLE IF EXISTS `business_name`;
CREATE TABLE `business_name` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `BUSINESS_NAME_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `NAME` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`BUSINESS_NAME_ID`),
  CONSTRAINT `business_name_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_name`
--

/*!40000 ALTER TABLE `business_name` DISABLE KEYS */;
INSERT INTO `business_name` (`BUSINESS_KEY`,`BUSINESS_NAME_ID`,`LANG_CODE`,`NAME`) VALUES 
 ('3307CB00-058C-11DA-AF48-F29D5ADEFDBE',0,NULL,'Aachen'),
 ('3A7D2340-0590-11DA-AF48-D7A6F8CC5A48',0,NULL,'Aachen'),
 ('3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82',0,NULL,'Siena'),
 ('530BB7B0-058F-11DA-AF48-868C01738359',0,NULL,'Aachen'),
 ('9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C',0,NULL,'Siena'),
 ('A0A82260-5C0B-11DA-AECA-FFA5982E2C8C',0,NULL,'Seville'),
 ('A711E140-058F-11DA-AF48-BFE51A48FC88',0,NULL,'Aachen'),
 ('D9AC90F0-0A44-11DA-90F0-A986892F16BD',0,NULL,'Bolzano-Bozen'),
 ('EEAA43A0-2C07-11DA-83A0-E7504C5C4864',0,NULL,'Seville');
/*!40000 ALTER TABLE `business_name` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`business_service`
--

DROP TABLE IF EXISTS `business_service`;
CREATE TABLE `business_service` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `SERVICE_KEY` varchar(41) NOT NULL default '',
  `LAST_UPDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`SERVICE_KEY`),
  KEY `BUSINESS_KEY` (`BUSINESS_KEY`),
  CONSTRAINT `business_service_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`business_service`
--

/*!40000 ALTER TABLE `business_service` DISABLE KEYS */;
INSERT INTO `business_service` (`BUSINESS_KEY`,`SERVICE_KEY`,`LAST_UPDATE`) VALUES 
 ('3307CB00-058C-11DA-AF48-F29D5ADEFDBE','330A14F0-058C-11DA-AF48-C24B7ABC066B','2005-08-05 10:37:47'),
 ('3A7D2340-0590-11DA-AF48-D7A6F8CC5A48','3EFCB7F0-0590-11DA-AF48-8A2A4612A854','2005-12-12 12:48:43'),
 ('3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82','3FF5E110-2C08-11DA-A110-B2C92EEB3FBF','2005-09-23 10:01:41'),
 ('530BB7B0-058F-11DA-AF48-868C01738359','530E01A0-058F-11DA-AF48-A4B842586FAA','2005-08-05 11:00:09'),
 ('9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C','A5C9EA80-5C0B-11DA-AECA-D3A4595B4B24','2005-12-12 12:49:41'),
 ('A711E140-058F-11DA-AF48-BFE51A48FC88','A7169C30-058F-11DA-AF48-FC0819B0E410','2005-08-05 11:02:30'),
 ('A0A82260-5C0B-11DA-AECA-FFA5982E2C8C','A9BF1980-5C0B-11DA-AECA-C3FE8C056DA2','2005-12-12 12:50:05'),
 ('D9AC90F0-0A44-11DA-90F0-A986892F16BD','DF372D00-0A44-11DA-AD00-B32AEF7C7C49','2005-12-12 12:49:16'),
 ('EEAA43A0-2C07-11DA-83A0-E7504C5C4864','F9CDD3A0-2C07-11DA-93A0-CE5781EB1A04','2005-09-23 09:59:59');
/*!40000 ALTER TABLE `business_service` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`contact`
--

DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `USE_TYPE` varchar(255) default NULL,
  `PERSON_NAME` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`),
  CONSTRAINT `contact_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`contact`
--

/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` (`BUSINESS_KEY`,`CONTACT_ID`,`USE_TYPE`,`PERSON_NAME`) VALUES 
 ('3307CB00-058C-11DA-AF48-F29D5ADEFDBE',0,'publisher','juddi'),
 ('3A7D2340-0590-11DA-AF48-D7A6F8CC5A48',0,'publisher','juddi'),
 ('3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82',0,'publisher','juddi'),
 ('530BB7B0-058F-11DA-AF48-868C01738359',0,'publisher','juddi'),
 ('9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C',0,'publisher','juddi'),
 ('A0A82260-5C0B-11DA-AECA-FFA5982E2C8C',0,'publisher','juddi'),
 ('A711E140-058F-11DA-AF48-BFE51A48FC88',0,'publisher','juddi'),
 ('D9AC90F0-0A44-11DA-90F0-A986892F16BD',0,'publisher','juddi'),
 ('EEAA43A0-2C07-11DA-83A0-E7504C5C4864',0,'publisher','juddi');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`contact_descr`
--

DROP TABLE IF EXISTS `contact_descr`;
CREATE TABLE `contact_descr` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `CONTACT_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`,`CONTACT_DESCR_ID`),
  CONSTRAINT `contact_descr_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`, `CONTACT_ID`) REFERENCES `contact` (`BUSINESS_KEY`, `CONTACT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`contact_descr`
--

/*!40000 ALTER TABLE `contact_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`discovery_url`
--

DROP TABLE IF EXISTS `discovery_url`;
CREATE TABLE `discovery_url` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `DISCOVERY_URL_ID` int(11) NOT NULL default '0',
  `USE_TYPE` varchar(255) NOT NULL default '',
  `URL` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`DISCOVERY_URL_ID`),
  CONSTRAINT `discovery_url_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`discovery_url`
--

/*!40000 ALTER TABLE `discovery_url` DISABLE KEYS */;
INSERT INTO `discovery_url` (`BUSINESS_KEY`,`DISCOVERY_URL_ID`,`USE_TYPE`,`URL`) VALUES 
 ('3307CB00-058C-11DA-AF48-F29D5ADEFDBE',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=3307CB00-058C-11DA-AF48-F29D5ADEFDBE'),
 ('3A7D2340-0590-11DA-AF48-D7A6F8CC5A48',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=3A7D2340-0590-11DA-AF48-D7A6F8CC5A48'),
 ('3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=3AF583A0-2C08-11DA-83A0-A4B0ECA7CB82'),
 ('530BB7B0-058F-11DA-AF48-868C01738359',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=530BB7B0-058F-11DA-AF48-868C01738359'),
 ('9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=9C8CCDC0-5C0B-11DA-AECA-8B730A89A60C'),
 ('A0A82260-5C0B-11DA-AECA-FFA5982E2C8C',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=A0A82260-5C0B-11DA-AECA-FFA5982E2C8C'),
 ('A711E140-058F-11DA-AF48-BFE51A48FC88',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=A711E140-058F-11DA-AF48-BFE51A48FC88');
INSERT INTO `discovery_url` (`BUSINESS_KEY`,`DISCOVERY_URL_ID`,`USE_TYPE`,`URL`) VALUES 
 ('D9AC90F0-0A44-11DA-90F0-A986892F16BD',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=D9AC90F0-0A44-11DA-90F0-A986892F16BD'),
 ('EEAA43A0-2C07-11DA-83A0-E7504C5C4864',0,'businessEntity','http://localhost:8080/juddi//uddiget.jsp?businesskey=EEAA43A0-2C07-11DA-83A0-E7504C5C4864');
/*!40000 ALTER TABLE `discovery_url` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`email`
--

DROP TABLE IF EXISTS `email`;
CREATE TABLE `email` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `EMAIL_ID` int(11) NOT NULL default '0',
  `USE_TYPE` varchar(255) default NULL,
  `EMAIL_ADDRESS` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`,`EMAIL_ID`),
  CONSTRAINT `email_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`, `CONTACT_ID`) REFERENCES `contact` (`BUSINESS_KEY`, `CONTACT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`email`
--

/*!40000 ALTER TABLE `email` DISABLE KEYS */;
/*!40000 ALTER TABLE `email` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`instance_details_descr`
--

DROP TABLE IF EXISTS `instance_details_descr`;
CREATE TABLE `instance_details_descr` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `TMODEL_INSTANCE_INFO_ID` int(11) NOT NULL default '0',
  `INSTANCE_DETAILS_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BINDING_KEY`,`TMODEL_INSTANCE_INFO_ID`,`INSTANCE_DETAILS_DESCR_ID`),
  CONSTRAINT `instance_details_descr_ibfk_1` FOREIGN KEY (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`) REFERENCES `tmodel_instance_info` (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`instance_details_descr`
--

/*!40000 ALTER TABLE `instance_details_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `instance_details_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`instance_details_doc_descr`
--

DROP TABLE IF EXISTS `instance_details_doc_descr`;
CREATE TABLE `instance_details_doc_descr` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `TMODEL_INSTANCE_INFO_ID` int(11) NOT NULL default '0',
  `INSTANCE_DETAILS_DOC_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BINDING_KEY`,`TMODEL_INSTANCE_INFO_ID`,`INSTANCE_DETAILS_DOC_DESCR_ID`),
  CONSTRAINT `instance_details_doc_descr_ibfk_1` FOREIGN KEY (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`) REFERENCES `tmodel_instance_info` (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`instance_details_doc_descr`
--

/*!40000 ALTER TABLE `instance_details_doc_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `instance_details_doc_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`monitor`
--

DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `REMOTE_HOST` varchar(51) NOT NULL default '',
  `REQUEST_URI` varchar(255) NOT NULL default '',
  `CALLED_FUNCTION` varchar(51) NOT NULL default '',
  `UDDI_VERSION` varchar(51) NOT NULL default '',
  `LOG_TIME` datetime NOT NULL default '0000-00-00 00:00:00',
  `AUTH_TOKEN` varchar(51) default NULL,
  `FAULT` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`monitor`
--

/*!40000 ALTER TABLE `monitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `monitor` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`phone`
--

DROP TABLE IF EXISTS `phone`;
CREATE TABLE `phone` (
  `BUSINESS_KEY` varchar(41) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `PHONE_ID` int(11) NOT NULL default '0',
  `USE_TYPE` varchar(255) default NULL,
  `PHONE_NUMBER` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`BUSINESS_KEY`,`CONTACT_ID`,`PHONE_ID`),
  CONSTRAINT `phone_ibfk_1` FOREIGN KEY (`BUSINESS_KEY`, `CONTACT_ID`) REFERENCES `contact` (`BUSINESS_KEY`, `CONTACT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`phone`
--

/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`publisher`
--

DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `PUBLISHER_ID` varchar(20) NOT NULL default '',
  `PUBLISHER_NAME` varchar(255) NOT NULL default '',
  `LAST_NAME` varchar(150) default NULL,
  `FIRST_NAME` varchar(100) default NULL,
  `MIDDLE_INIT` varchar(5) default NULL,
  `WORK_PHONE` varchar(50) default NULL,
  `MOBILE_PHONE` varchar(50) default NULL,
  `PAGER` varchar(50) default NULL,
  `EMAIL_ADDRESS` varchar(255) default NULL,
  `ADMIN` varchar(5) default NULL,
  `ENABLED` varchar(5) default NULL,
  PRIMARY KEY  (`PUBLISHER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`publisher`
--

/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` (`PUBLISHER_ID`,`PUBLISHER_NAME`,`LAST_NAME`,`FIRST_NAME`,`MIDDLE_INIT`,`WORK_PHONE`,`MOBILE_PHONE`,`PAGER`,`EMAIL_ADDRESS`,`ADMIN`,`ENABLED`) VALUES 
 ('juddi','juddi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'false','true');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`publisher_assertion`
--

DROP TABLE IF EXISTS `publisher_assertion`;
CREATE TABLE `publisher_assertion` (
  `FROM_KEY` varchar(41) NOT NULL default '',
  `TO_KEY` varchar(41) NOT NULL default '',
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `KEY_NAME` varchar(255) NOT NULL default '',
  `KEY_VALUE` varchar(255) NOT NULL default '',
  `FROM_CHECK` varchar(5) NOT NULL default '',
  `TO_CHECK` varchar(5) NOT NULL default '',
  KEY `FROM_KEY` (`FROM_KEY`),
  KEY `TO_KEY` (`TO_KEY`),
  CONSTRAINT `publisher_assertion_ibfk_1` FOREIGN KEY (`FROM_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`),
  CONSTRAINT `publisher_assertion_ibfk_2` FOREIGN KEY (`TO_KEY`) REFERENCES `business_entity` (`BUSINESS_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`publisher_assertion`
--

/*!40000 ALTER TABLE `publisher_assertion` DISABLE KEYS */;
/*!40000 ALTER TABLE `publisher_assertion` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`service_category`
--

DROP TABLE IF EXISTS `service_category`;
CREATE TABLE `service_category` (
  `SERVICE_KEY` varchar(41) NOT NULL default '',
  `CATEGORY_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(41) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`SERVICE_KEY`,`CATEGORY_ID`),
  CONSTRAINT `service_category_ibfk_1` FOREIGN KEY (`SERVICE_KEY`) REFERENCES `business_service` (`SERVICE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`service_category`
--

/*!40000 ALTER TABLE `service_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_category` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`service_descr`
--

DROP TABLE IF EXISTS `service_descr`;
CREATE TABLE `service_descr` (
  `SERVICE_KEY` varchar(41) NOT NULL default '',
  `SERVICE_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`SERVICE_KEY`,`SERVICE_DESCR_ID`),
  CONSTRAINT `service_descr_ibfk_1` FOREIGN KEY (`SERVICE_KEY`) REFERENCES `business_service` (`SERVICE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`service_descr`
--

/*!40000 ALTER TABLE `service_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`service_name`
--

DROP TABLE IF EXISTS `service_name`;
CREATE TABLE `service_name` (
  `SERVICE_KEY` varchar(41) NOT NULL default '',
  `SERVICE_NAME_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `NAME` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`SERVICE_KEY`,`SERVICE_NAME_ID`),
  CONSTRAINT `service_name_ibfk_1` FOREIGN KEY (`SERVICE_KEY`) REFERENCES `business_service` (`SERVICE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`service_name`
--

/*!40000 ALTER TABLE `service_name` DISABLE KEYS */;
INSERT INTO `service_name` (`SERVICE_KEY`,`SERVICE_NAME_ID`,`LANG_CODE`,`NAME`) VALUES 
 ('330A14F0-058C-11DA-AF48-C24B7ABC066B',0,NULL,'forward'),
 ('3EFCB7F0-0590-11DA-AF48-8A2A4612A854',0,NULL,'forward'),
 ('3FF5E110-2C08-11DA-A110-B2C92EEB3FBF',0,NULL,'forward'),
 ('530E01A0-058F-11DA-AF48-A4B842586FAA',0,NULL,'forward'),
 ('A5C9EA80-5C0B-11DA-AECA-D3A4595B4B24',0,NULL,'forward'),
 ('A7169C30-058F-11DA-AF48-FC0819B0E410',0,NULL,'forward'),
 ('A9BF1980-5C0B-11DA-AECA-C3FE8C056DA2',0,NULL,'forward'),
 ('DF372D00-0A44-11DA-AD00-B32AEF7C7C49',0,NULL,'forward'),
 ('F9CDD3A0-2C07-11DA-93A0-CE5781EB1A04',0,NULL,'forward');
/*!40000 ALTER TABLE `service_name` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel`
--

DROP TABLE IF EXISTS `tmodel`;
CREATE TABLE `tmodel` (
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `AUTHORIZED_NAME` varchar(255) NOT NULL default '',
  `PUBLISHER_ID` varchar(20) default NULL,
  `OPERATOR` varchar(255) NOT NULL default '',
  `NAME` varchar(255) NOT NULL default '',
  `OVERVIEW_URL` varchar(255) default NULL,
  `DELETED` varchar(5) default NULL,
  `LAST_UPDATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`TMODEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel`
--

/*!40000 ALTER TABLE `tmodel` DISABLE KEYS */;
INSERT INTO `tmodel` (`TMODEL_KEY`,`AUTHORIZED_NAME`,`PUBLISHER_ID`,`OPERATOR`,`NAME`,`OVERVIEW_URL`,`DELETED`,`LAST_UPDATE`) VALUES 
 ('uuid:327A56F0-3299-4461-BC23-5CD513E95C55','Administrator',NULL,'jUDDI.org','uddi-org:operators','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#Operators',NULL,'2005-07-11 10:11:49'),
 ('uuid:4064C064-6D14-4F35-8953-9652106476A9','Administrator',NULL,'jUDDI.org','uddi-org:owningBusiness','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#owningBusiness',NULL,'2005-07-11 10:11:49'),
 ('uuid:4E49A8D6-D5A2-4FC2-93A0-0411D8D19E88','Administrator',NULL,'jUDDI.org','uddi-org:iso-ch:3166-1999','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#ISO3166',NULL,'2005-07-11 10:11:49'),
 ('uuid:807A2C6A-EE22-470D-ADC7-E0424A337C03','Administrator',NULL,'jUDDI.org','uddi-org:relationships','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#Relationships',NULL,'2005-07-11 10:11:49'),
 ('uuid:8609C81E-EE1F-4D5A-B202-3EB13AD01823','Administrator',NULL,'jUDDI.org','dnb-com:D-U-N-S','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#D-U-N-S',NULL,'2005-07-11 10:11:49'),
 ('uuid:A035A07C-F362-44DD-8F95-E2B134BF43B4','Administrator',NULL,'jUDDI.org','uddi-org:general_keywords','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#GenKW',NULL,'2005-07-11 10:11:49');
INSERT INTO `tmodel` (`TMODEL_KEY`,`AUTHORIZED_NAME`,`PUBLISHER_ID`,`OPERATOR`,`NAME`,`OVERVIEW_URL`,`DELETED`,`LAST_UPDATE`) VALUES 
 ('uuid:B1B1BAF5-2329-43E6-AE13-BA8E97195039','Administrator',NULL,'jUDDI.org','thomasregister-com:supplierID','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#Thomas',NULL,'2005-07-11 10:11:49'),
 ('uuid:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2','Administrator',NULL,'jUDDI.org','ntis-gov:naics:1997','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#NAICS',NULL,'2005-07-11 10:11:49'),
 ('uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','Administrator',NULL,'jUDDI.org','uddi-org:types','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#UDDItypes',NULL,'2005-07-11 10:11:49'),
 ('uuid:CD153257-086A-4237-B336-6BDCBDCC6634','Administrator',NULL,'jUDDI.org','unspsc-org:unspsc','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#UNSPSC',NULL,'2005-07-11 10:11:49'),
 ('uuid:DB77450D-9FA8-45D4-A7BC-04411D14E384','Administrator',NULL,'jUDDI.org','unspsc-org:unspsc:3-1','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#UNSPSC31',NULL,'2005-07-11 10:11:49'),
 ('uuid:E59AE320-77A5-11D5-B898-0004AC49CC1E','Administrator',NULL,'jUDDI.org','uddi-org:isReplacedBy','http://www.uddi.org/taxonomies/UDDI_Taxonomy_tModels.htm#IsReplacedBy',NULL,'2005-07-11 10:11:49');
/*!40000 ALTER TABLE `tmodel` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_category`
--

DROP TABLE IF EXISTS `tmodel_category`;
CREATE TABLE `tmodel_category` (
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `CATEGORY_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(255) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`TMODEL_KEY`,`CATEGORY_ID`),
  CONSTRAINT `tmodel_category_ibfk_1` FOREIGN KEY (`TMODEL_KEY`) REFERENCES `tmodel` (`TMODEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_category`
--

/*!40000 ALTER TABLE `tmodel_category` DISABLE KEYS */;
INSERT INTO `tmodel_category` (`TMODEL_KEY`,`CATEGORY_ID`,`TMODEL_KEY_REF`,`KEY_NAME`,`KEY_VALUE`) VALUES 
 ('uuid:327A56F0-3299-4461-BC23-5CD513E95C55',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:327A56F0-3299-4461-BC23-5CD513E95C55',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked'),
 ('uuid:4064C064-6D14-4F35-8953-9652106476A9',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:4064C064-6D14-4F35-8953-9652106476A9',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked'),
 ('uuid:4E49A8D6-D5A2-4FC2-93A0-0411D8D19E88',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:4E49A8D6-D5A2-4FC2-93A0-0411D8D19E88',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked'),
 ('uuid:807A2C6A-EE22-470D-ADC7-E0424A337C03',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','relationship'),
 ('uuid:8609C81E-EE1F-4D5A-B202-3EB13AD01823',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','identifier'),
 ('uuid:A035A07C-F362-44DD-8F95-E2B134BF43B4',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization');
INSERT INTO `tmodel_category` (`TMODEL_KEY`,`CATEGORY_ID`,`TMODEL_KEY_REF`,`KEY_NAME`,`KEY_VALUE`) VALUES 
 ('uuid:B1B1BAF5-2329-43E6-AE13-BA8E97195039',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','identifier'),
 ('uuid:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked'),
 ('uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked'),
 ('uuid:CD153257-086A-4237-B336-6BDCBDCC6634',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:CD153257-086A-4237-B336-6BDCBDCC6634',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','Checked'),
 ('uuid:DB77450D-9FA8-45D4-A7BC-04411D14E384',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','categorization'),
 ('uuid:E59AE320-77A5-11D5-B898-0004AC49CC1E',0,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','identifier');
INSERT INTO `tmodel_category` (`TMODEL_KEY`,`CATEGORY_ID`,`TMODEL_KEY_REF`,`KEY_NAME`,`KEY_VALUE`) VALUES 
 ('uuid:E59AE320-77A5-11D5-B898-0004AC49CC1E',1,'uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4','types','checked');
/*!40000 ALTER TABLE `tmodel_category` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_descr`
--

DROP TABLE IF EXISTS `tmodel_descr`;
CREATE TABLE `tmodel_descr` (
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `TMODEL_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`TMODEL_KEY`,`TMODEL_DESCR_ID`),
  CONSTRAINT `tmodel_descr_ibfk_1` FOREIGN KEY (`TMODEL_KEY`) REFERENCES `tmodel` (`TMODEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_descr`
--

/*!40000 ALTER TABLE `tmodel_descr` DISABLE KEYS */;
INSERT INTO `tmodel_descr` (`TMODEL_KEY`,`TMODEL_DESCR_ID`,`LANG_CODE`,`DESCR`) VALUES 
 ('uuid:327A56F0-3299-4461-BC23-5CD513E95C55',0,'en','Taxonomy for categorizing the businessEntity of an operator of a registry.'),
 ('uuid:4064C064-6D14-4F35-8953-9652106476A9',0,'en','A pointer to a businessEntity that owns the tagged data.'),
 ('uuid:4E49A8D6-D5A2-4FC2-93A0-0411D8D19E88',0,'en','ISO 3166-1:1997 and 3166-2:1998. Codes for names of countries and their subdivisions. Part 1: Country codes. Part 2:Country subdivision codes.'),
 ('uuid:807A2C6A-EE22-470D-ADC7-E0424A337C03',0,'en','Starter set classifications of businessEntity relationships'),
 ('uuid:8609C81E-EE1F-4D5A-B202-3EB13AD01823',0,'en','Dun&Bradstreet D-U-N-S® Number'),
 ('uuid:A035A07C-F362-44DD-8F95-E2B134BF43B4',0,'en','Special taxonomy consisting of namespace identifiers and the keywords associated with the namespaces'),
 ('uuid:B1B1BAF5-2329-43E6-AE13-BA8E97195039',0,'en','Thomas Registry Suppliers'),
 ('uuid:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2',0,'en','Business Taxonomy: NAICS(1997 Release)'),
 ('uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4',0,'en','UDDI Type Taxonomy');
INSERT INTO `tmodel_descr` (`TMODEL_KEY`,`TMODEL_DESCR_ID`,`LANG_CODE`,`DESCR`) VALUES 
 ('uuid:CD153257-086A-4237-B336-6BDCBDCC6634',0,'en','Product Taxonomy: UNSPSC (Version 7.3)'),
 ('uuid:DB77450D-9FA8-45D4-A7BC-04411D14E384',0,'en','Product Taxonomy: UNSPSC (Version 3.1)'),
 ('uuid:E59AE320-77A5-11D5-B898-0004AC49CC1E',0,'en','An identifier system used to point (using UDDI keys) to the tModel (or businessEntity) that is the logical replacement for the one in which isReplacedBy is used');
/*!40000 ALTER TABLE `tmodel_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_doc_descr`
--

DROP TABLE IF EXISTS `tmodel_doc_descr`;
CREATE TABLE `tmodel_doc_descr` (
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `TMODEL_DOC_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`TMODEL_KEY`,`TMODEL_DOC_DESCR_ID`),
  CONSTRAINT `tmodel_doc_descr_ibfk_1` FOREIGN KEY (`TMODEL_KEY`) REFERENCES `tmodel` (`TMODEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_doc_descr`
--

/*!40000 ALTER TABLE `tmodel_doc_descr` DISABLE KEYS */;
INSERT INTO `tmodel_doc_descr` (`TMODEL_KEY`,`TMODEL_DOC_DESCR_ID`,`LANG_CODE`,`DESCR`) VALUES 
 ('uuid:327A56F0-3299-4461-BC23-5CD513E95C55',0,'en','This checked value set is used to identify UDDI operators.'),
 ('uuid:4064C064-6D14-4F35-8953-9652106476A9',0,'en','This tModel indicates the businessEntity that published or owns the tagged tModel. Used with tModels to establish an \"owned\" relationship with a registered businessEntity.'),
 ('uuid:4E49A8D6-D5A2-4FC2-93A0-0411D8D19E88',0,'en','Taxonomy used to categorize entries by geographic location.'),
 ('uuid:807A2C6A-EE22-470D-ADC7-E0424A337C03',0,'en','This tModel is used to describe business relationships. Used in the publisher assertion messages.'),
 ('uuid:8609C81E-EE1F-4D5A-B202-3EB13AD01823',0,'en','This tModel is used for the Dun&Bradstreet D-U-N-S® Number identifier.'),
 ('uuid:A035A07C-F362-44DD-8F95-E2B134BF43B4',0,'en','This tModel defines an unidentified taxonomy.'),
 ('uuid:B1B1BAF5-2329-43E6-AE13-BA8E97195039',0,'en','This tModel is used for the Thomas Register supplier identifier codes.'),
 ('uuid:C0B9FE13-179F-413D-8A5B-5004DB8E5BB2',0,'en','This tModel defines the NAICS industry taxonomy.');
INSERT INTO `tmodel_doc_descr` (`TMODEL_KEY`,`TMODEL_DOC_DESCR_ID`,`LANG_CODE`,`DESCR`) VALUES 
 ('uuid:C1ACF26D-9672-4404-9D70-39B756E62AB4',0,'en','Taxonomy used to categorize Service Descriptions.'),
 ('uuid:CD153257-086A-4237-B336-6BDCBDCC6634',0,'en','This tModel defines Version 7.3 of the UNSPSC product taxonomy.'),
 ('uuid:DB77450D-9FA8-45D4-A7BC-04411D14E384',0,'en','This tModel defines the UNSPSC product taxonomy.'),
 ('uuid:E59AE320-77A5-11D5-B898-0004AC49CC1E',0,'en','This is a checked value set.');
/*!40000 ALTER TABLE `tmodel_doc_descr` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_identifier`
--

DROP TABLE IF EXISTS `tmodel_identifier`;
CREATE TABLE `tmodel_identifier` (
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `IDENTIFIER_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY_REF` varchar(255) default NULL,
  `KEY_NAME` varchar(255) default NULL,
  `KEY_VALUE` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`TMODEL_KEY`,`IDENTIFIER_ID`),
  CONSTRAINT `tmodel_identifier_ibfk_1` FOREIGN KEY (`TMODEL_KEY`) REFERENCES `tmodel` (`TMODEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_identifier`
--

/*!40000 ALTER TABLE `tmodel_identifier` DISABLE KEYS */;
/*!40000 ALTER TABLE `tmodel_identifier` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_instance_info`
--

DROP TABLE IF EXISTS `tmodel_instance_info`;
CREATE TABLE `tmodel_instance_info` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `TMODEL_INSTANCE_INFO_ID` int(11) NOT NULL default '0',
  `TMODEL_KEY` varchar(41) NOT NULL default '',
  `OVERVIEW_URL` varchar(255) default NULL,
  `INSTANCE_PARMS` varchar(255) default NULL,
  PRIMARY KEY  (`BINDING_KEY`,`TMODEL_INSTANCE_INFO_ID`),
  CONSTRAINT `tmodel_instance_info_ibfk_1` FOREIGN KEY (`BINDING_KEY`) REFERENCES `binding_template` (`BINDING_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_instance_info`
--

/*!40000 ALTER TABLE `tmodel_instance_info` DISABLE KEYS */;
INSERT INTO `tmodel_instance_info` (`BINDING_KEY`,`TMODEL_INSTANCE_INFO_ID`,`TMODEL_KEY`,`OVERVIEW_URL`,`INSTANCE_PARMS`) VALUES 
 ('09AEC4A0-2C08-11DA-84A0-DA63E63F7B40',0,'https://localhost:8443/eMayorEJB/ForwardM',NULL,NULL),
 ('3F021300-6B05-11DA-9300-A5EA1D1CFEC9',0,'0',NULL,NULL),
 ('46976B60-2C08-11DA-AB60-CE67564FBDF0',0,'https://localhost:8443/eMayorEJB/ForwardM',NULL,NULL),
 ('5278FC00-6B05-11DA-BC00-BDC01374F3B7',0,'0',NULL,NULL),
 ('614EE5A0-6B05-11DA-A5A0-B90530AE9B1D',0,'0',NULL,NULL),
 ('6FB74060-6B05-11DA-8060-934C9525590A',0,'0',NULL,NULL);
/*!40000 ALTER TABLE `tmodel_instance_info` ENABLE KEYS */;


--
-- Table structure for table `juddi`.`tmodel_instance_info_descr`
--

DROP TABLE IF EXISTS `tmodel_instance_info_descr`;
CREATE TABLE `tmodel_instance_info_descr` (
  `BINDING_KEY` varchar(41) NOT NULL default '',
  `TMODEL_INSTANCE_INFO_ID` int(11) NOT NULL default '0',
  `TMODEL_INSTANCE_INFO_DESCR_ID` int(11) NOT NULL default '0',
  `LANG_CODE` varchar(2) default NULL,
  `DESCR` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`BINDING_KEY`,`TMODEL_INSTANCE_INFO_ID`,`TMODEL_INSTANCE_INFO_DESCR_ID`),
  CONSTRAINT `tmodel_instance_info_descr_ibfk_1` FOREIGN KEY (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`) REFERENCES `tmodel_instance_info` (`BINDING_KEY`, `TMODEL_INSTANCE_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `juddi`.`tmodel_instance_info_descr`
--

/*!40000 ALTER TABLE `tmodel_instance_info_descr` DISABLE KEYS */;
/*!40000 ALTER TABLE `tmodel_instance_info_descr` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
