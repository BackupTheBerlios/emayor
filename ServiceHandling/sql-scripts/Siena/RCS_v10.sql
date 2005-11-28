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
-- Table structure for table `aachen`.`service_info`
--

DROP TABLE IF EXISTS `service_info`;
CREATE TABLE `service_info` (
  `SERVICEID` varchar(100) NOT NULL default '',
  `SERVICEVERSION` varchar(20) default NULL,
  `SERVICENAME` varchar(100) default NULL,
  `SERVICECLASSNAME` varchar(200) default NULL,
  `SERVICEFACTORYCLASSNAME` varchar(100) default NULL,
  `SERVICEDECRIPTION` text,
  `SERVICEENDPOINT` varchar(250) default NULL,
  `ACTIVE` tinyint(1) default NULL,
  `INSTANCES` int(11) default NULL,
  `SERVICECLASS` blob,
  `SERVICEFACTORYCLASS` blob,
  PRIMARY KEY  (`SERVICEID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `aachen`.`service_info`
--

/*!40000 ALTER TABLE `service_info` DISABLE KEYS */;
INSERT INTO `service_info` (`SERVICEID`,`SERVICEVERSION`,`SERVICENAME`,`SERVICECLASSNAME`,`SERVICEFACTORYCLASSNAME`,`SERVICEDECRIPTION`,`SERVICEENDPOINT`,`ACTIVE`,`INSTANCES`,`SERVICECLASS`,`SERVICEFACTORYCLASS`) VALUES 
 ('AdminService_v10','1.0','Platform Administration Service','org.emayor.services.admin.AdminService','org.emayor.services.admin.AdminServiceFactory','This service provides the functionality needed to maintance the eMayor platform. It could be only use by the user in the role admin.','---',1,6,0xCAFEBABE0000002E004B0100266F72672F656D61796F722F73657276696365732F61646D696E2F41646D696E536572766963650700010100376F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F4162737472616374654D61796F72536572766963650700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C00070008090002000D0100266F72672E656D61796F722E73657276696365732E61646D696E2E41646D696E5365727669636508000F01000F6A6176612F6C616E672F436C617373070011010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001300140A0012001501001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F720700170100136A6176612F6C616E672F5468726F7761626C6507001901000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C001B001C0A001A001D0100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C001F00200A001800210100176F72672F6170616368652F6C6F67346A2F4C6F676765720700230100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002500260A002400270C0005000609000200290100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07002B01000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C650C001F000B0A0004002F01000E69734465627567456E61626C656401000328295A0C003100320A002400330100172D3E2073746172742070726F63657373696E67202E2E2E0800350100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003700380A002400390100172D3E202E2E2E2070726F63657373696E6720444F4E452108003B010004746869730100284C6F72672F656D61796F722F73657276696365732F61646D696E2F41646D696E536572766963653B01000C737461727453657276696365010027284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E0700420100037569640100124C6A6176612F6C616E672F537472696E673B01000473736964010039284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601000F72657175657374446F63756D656E7401000A536F7572636546696C6501001141646D696E536572766963652E6A61766100210002000400000002001A000500060000100800070008000100090000000000040008000A000B0001000C000000530003000000000027B2000E59C7001C571210B8001659B3000EA7000FBB00185A5FB6001EB70022BFB80028B3002AB100010008000D0014002C0002002D0000000A0002000000100026000F002E0000000200000001001F000B0001000C00000058000200010000001E2AB70030B2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D0000001600050000001200040013000D001400150015001D0017002E0000000C00010000001E003D003E00000001003F0040000200410000000400010043000C00000064000200030000001AB2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D00000012000400000021000900220011002300190025002E0000002000030000001A003D003E00000000001A0044004500010000001A0046004500020001003F0047000200410000000400010043000C0000006E000200040000001AB2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D0000001200040000002F000900300011003100190033002E0000002A00040000001A003D003E00000000001A0044004500010000001A0046004500020000001A0048004500030001004900000002004A,0xCAFEBABE0000002E004401002D6F72672F656D61796F722F73657276696365732F61646D696E2F41646D696E53657276696365466163746F72790700010100366F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365466163746F72790700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C00070008090002000D01002D6F72672E656D61796F722E73657276696365732E61646D696E2E41646D696E53657276696365466163746F727908000F01000F6A6176612F6C616E672F436C617373070011010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001300140A0012001501001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F720700170100136A6176612F6C616E672F5468726F7761626C6507001901000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C001B001C0A001A001D0100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C001F00200A001800210100176F72672F6170616368652F6C6F67346A2F4C6F676765720700230100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002500260A002400270C0005000609000200290100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07002B01000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C6501000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E0700300C001F000B0A0004003201000E69734465627567456E61626C656401000328295A0C003400350A002400360100172D3E2073746172742070726F63657373696E67202E2E2E0800380100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003A003B0A0024003C0100172D3E202E2E2E2070726F63657373696E6720444F4E452108003E0100047468697301002F4C6F72672F656D61796F722F73657276696365732F61646D696E2F41646D696E53657276696365466163746F72793B01000A536F7572636546696C6501001841646D696E53657276696365466163746F72792E6A61766100210002000400000002001A000500060000100800070008000100090000000000020008000A000B0001000C000000570003000000000027B2000E59C7001C571210B8001659B3000EA7000FBB00185A5FB6001EB70022BFB80028B3002AB100010008000D0014002C0002002D0000000E000300000011002300100026000F002E0000000200000001001F000B0002002F0000000400010031000C00000058000200010000001E2AB70033B2002AB60037990013B2002A1239B6003DB2002A123FB6003DB100000002002D0000001600050000001700040018000D00190015001A001D001C002E0000000C00010000001E00400041000000010042000000020043),
 ('PEAdminService_v10','1.0','Policy Enforcer Administration Service','org.emayor.services.peadmin.PolicyEnforcerAdminService','org.emayor.services.peadmin.PolicyEnforcerAdminServiceFactory','Policy Enforcer Administration Service.','---',1,7,0xCAFEBABE0000002E004B0100366F72672F656D61796F722F73657276696365732F706561646D696E2F506F6C696379456E666F7263657241646D696E536572766963650700010100376F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F4162737472616374654D61796F72536572766963650700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C00070008090002000D0100366F72672E656D61796F722E73657276696365732E706561646D696E2E506F6C696379456E666F7263657241646D696E5365727669636508000F01000F6A6176612F6C616E672F436C617373070011010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001300140A0012001501001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F720700170100136A6176612F6C616E672F5468726F7761626C6507001901000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C001B001C0A001A001D0100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C001F00200A001800210100176F72672F6170616368652F6C6F67346A2F4C6F676765720700230100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002500260A002400270C0005000609000200290100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07002B01000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C650C001F000B0A0004002F01000E69734465627567456E61626C656401000328295A0C003100320A002400330100172D3E2073746172742070726F63657373696E67202E2E2E0800350100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003700380A002400390100172D3E202E2E2E2070726F63657373696E6720444F4E452108003B010004746869730100384C6F72672F656D61796F722F73657276696365732F706561646D696E2F506F6C696379456E666F7263657241646D696E536572766963653B01000C737461727453657276696365010027284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E0700420100037569640100124C6A6176612F6C616E672F537472696E673B01000473736964010039284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601000F72657175657374446F63756D656E7401000A536F7572636546696C6501001F506F6C696379456E666F7263657241646D696E536572766963652E6A61766100210002000400000002001A000500060000100800070008000100090000000000040008000A000B0001000C000000570003000000000027B2000E59C7001C571210B8001659B3000EA7000FBB00185A5FB6001EB70022BFB80028B3002AB100010008000D0014002C0002002D0000000E000300000011002300100026000F002E0000000200000001001F000B0001000C00000058000200010000001E2AB70030B2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D0000001600050000001300040014000D001500150016001D0018002E0000000C00010000001E003D003E00000001003F0040000200410000000400010043000C00000064000200030000001AB2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D00000012000400000022000900230011002400190026002E0000002000030000001A003D003E00000000001A0044004500010000001A0046004500020001003F0047000200410000000400010043000C0000006E000200040000001AB2002AB60034990013B2002A1236B6003AB2002A123CB6003AB100000002002D00000012000400000030000900310011003200190034002E0000002A00040000001A003D003E00000000001A0044004500010000001A0046004500020000001A0048004500030001004900000002004A,0xCAFEBABE0000002E004401003D6F72672F656D61796F722F73657276696365732F706561646D696E2F506F6C696379456E666F7263657241646D696E53657276696365466163746F72790700010100366F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365466163746F72790700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C00070008090002000D01003D6F72672E656D61796F722E73657276696365732E706561646D696E2E506F6C696379456E666F7263657241646D696E53657276696365466163746F727908000F01000F6A6176612F6C616E672F436C617373070011010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001300140A0012001501001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F720700170100136A6176612F6C616E672F5468726F7761626C6507001901000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C001B001C0A001A001D0100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C001F00200A001800210100176F72672F6170616368652F6C6F67346A2F4C6F676765720700230100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002500260A002400270C0005000609000200290100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07002B01000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C6501000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E0700300C001F000B0A0004003201000E69734465627567456E61626C656401000328295A0C003400350A002400360100172D3E2073746172742070726F63657373696E67202E2E2E0800380100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003A003B0A0024003C0100172D3E202E2E2E2070726F63657373696E6720444F4E452108003E0100047468697301003F4C6F72672F656D61796F722F73657276696365732F706561646D696E2F506F6C696379456E666F7263657241646D696E53657276696365466163746F72793B01000A536F7572636546696C65010026506F6C696379456E666F7263657241646D696E53657276696365466163746F72792E6A61766100210002000400000002001A000500060000100800070008000100090000000000020008000A000B0001000C000000570003000000000027B2000E59C7001C571210B8001659B3000EA7000FBB00185A5FB6001EB70022BFB80028B3002AB100010008000D0014002C0002002D0000000E000300000012002300110026000F002E0000000200000001001F000B0002002F0000000400010031000C00000058000200010000001E2AB70033B2002AB60037990013B2002A1239B6003DB2002A123FB6003DB100000002002D0000001600050000001800040019000D001A0015001B001D001D002E0000000C00010000001E00400041000000010042000000020043);
INSERT INTO `service_info` (`SERVICEID`,`SERVICEVERSION`,`SERVICENAME`,`SERVICECLASSNAME`,`SERVICEFACTORYCLASSNAME`,`SERVICEDECRIPTION`,`SERVICEENDPOINT`,`ACTIVE`,`INSTANCES`,`SERVICECLASS`,`SERVICEFACTORYCLASS`) VALUES 
 ('ResidenceCertificationService_v10','1.0','Residence Certification Service','org.emayor.services.rc.ResidenceCertificationService','org.emayor.services.rc.ResidenceCertificationServiceFactory','','---',1,1,0xCAFEBABE0000002E01010100346F72672F656D61796F722F73657276696365732F72632F5265736964656E636543657274696669636174696F6E536572766963650700010100376F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F4162737472616374654D61796F72536572766963650700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B01000C4445465F584D4C5F46494C450100124C6A6176612F6C616E672F537472696E673B01000D436F6E7374616E7456616C756501002F53616D706C655265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742E786D6C08000A010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C000C000D09000200120100346F72672E656D61796F722E73657276696365732E72632E5265736964656E636543657274696669636174696F6E5365727669636508001401000F6A6176612F6C616E672F436C617373070016010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001800190A0017001A01001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F7207001C0100136A6176612F6C616E672F5468726F7761626C6507001E01000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C002000210A001F00220100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C002400250A001D00260100176F72672F6170616368652F6C6F67346A2F4C6F676765720700280100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002A002B0A0029002C0C00050006090002002E0100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07003001000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C650C002400100A00040034010004746869730100364C6F72672F656D61796F722F73657276696365732F72632F5265736964656E636543657274696669636174696F6E536572766963653B01000C737461727453657276696365010027284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E07003B0100172D3E2073746172742070726F63657373696E67202E2E2E08003D0100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003F00400A0029004101001A676574584D4C446F63756D656E7446726F6D5265736F75726365010026284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F537472696E673B0C004300440A0002004501000B6564697452657175657374010038284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F537472696E673B0C004700480A000200490100024E4F08004B0100083C656D7074792F3E08004D0100077374617274497401005D284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B29560C004F00500A000200510100172D3E202E2E2E2070726F63657373696E6720444F4E45210800530100037569640100047373696401000B786D6C446F63756D656E7401000E6765745573657250726F66696C65010040284C6A6176612F6C616E672F537472696E673B294C6F72672F654D61796F722F506F6C696379456E666F7263656D656E742F435F5573657250726F66696C653B0C005800590A0002005A0100166A6176612F6C616E672F537472696E6742756666657207005C010012676F7420786D6C20646F63756D656E743A2008005E0A005D0026010006617070656E6401002C284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F537472696E674275666665723B0C006100620A005D0063010008746F537472696E670C006500210A005D0066010012676F7420757365722070726F66696C653A200800680100106A6176612F6C616E672F4F626A65637407006A0A006B00660100286A617661782F786D6C2F706172736572732F446F63756D656E744275696C646572466163746F727907006D01000B6E6577496E7374616E636501002C28294C6A617661782F786D6C2F706172736572732F446F63756D656E744275696C646572466163746F72793B0C006F00700A006E00710100126E6577446F63756D656E744275696C64657201002528294C6A617661782F786D6C2F706172736572732F446F63756D656E744275696C6465723B0C007300740A006E00750100146A6176612F696F2F537472696E675265616465720700770A007800260100176F72672F786D6C2F7361782F496E707574536F7572636507007A010013284C6A6176612F696F2F5265616465723B29560C0024007C0A007B007D0100216A617661782F786D6C2F706172736572732F446F63756D656E744275696C64657207007F0100057061727365010031284C6F72672F786D6C2F7361782F496E707574536F757263653B294C6F72672F7733632F646F6D2F446F63756D656E743B0C008100820A0080008301002A6F72672F654D61796F722F506F6C696379456E666F7263656D656E742F435F5573657250726F66696C6507008501000B676574557365724E616D650C008700210A008600880100012008008A0100106A6176612F6C616E672F537472696E6707008C01000573706C6974010027284C6A6176612F6C616E672F537472696E673B295B4C6A6176612F6C616E672F537472696E673B0C008E008F0A008D009001005E2F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F52657175657374657244657461696C732F436974697A656E4E616D652F436974697A656E4E616D65466F72656E616D652F7465787428290800920100196F72672F6170616368652F78706174682F585061746841504907009401001073656C65637453696E676C654E6F6465010038284C6F72672F7733632F646F6D2F4E6F64653B4C6A6176612F6C616E672F537472696E673B294C6F72672F7733632F646F6D2F4E6F64653B0C009600970A009500980100106F72672F7733632F646F6D2F4E6F646507009A01000C7365744E6F646556616C75650C009C00250B009B009D01005D2F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F52657175657374657244657461696C732F436974697A656E4E616D652F436974697A656E4E616D655375726E616D652F74657874282908009F01000C67657455736572456D61696C0C00A100210A008600A20100602F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F52657175657374657244657461696C732F436F6E7461637444657461696C732F456D61696C2F456D61696C416464726573732F7465787428290800A401000E67657455736572436F756E7472790C00A600210A008600A70100512F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F52657175657374657244657461696C732F5072656665727265644C616E6775616765732F7465787428290800A90100286F72672F656D61796F722F7365727669636568616E646C696E672F636F6E6669672F436F6E6669670700AB01000B676574496E7374616E636501002C28294C6F72672F656D61796F722F7365727669636568616E646C696E672F636F6E6669672F436F6E6669673B0C00AD00AE0A00AC00AF01000B67657450726F70657274790100152849294C6A6176612F6C616E672F537472696E673B0C00B100B20A00AC00B30100392F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F4C6F67696E5365727665722F7465787428290800B50100482F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F53657276696E674D756E69636970616C69747944657461696C732F7465787428290800B701004A2F5265736964656E636543657274696669636174696F6E52657175657374446F63756D656E742F526563656976696E674D756E69636970616C69747944657461696C732F7465787428290800B90100266A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D6572466163746F72790700BB01002A28294C6A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D6572466163746F72793B0C006F00BD0A00BC00BE01000E6E65775472616E73666F726D657201002328294C6A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D65723B0C00C000C10A00BC00C20100146A6176612F696F2F537472696E675772697465720700C40A00C500340100216A617661782F786D6C2F7472616E73666F726D2F646F6D2F444F4D536F757263650700C7010015284C6F72672F7733632F646F6D2F4E6F64653B29560C002400C90A00C800CA0100276A617661782F786D6C2F7472616E73666F726D2F73747265616D2F53747265616D526573756C740700CC010013284C6A6176612F696F2F5772697465723B29560C002400CE0A00CD00CF01001F6A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D65720700D10100097472616E73666F726D01003B284C6A617661782F786D6C2F7472616E73666F726D2F536F757263653B4C6A617661782F786D6C2F7472616E73666F726D2F526573756C743B29560C00D300D40A00D200D50A00C500660100136A6176612F6C616E672F457863657074696F6E0700D801000F7072696E74537461636B54726163650C00DA00100A00D900DB0A00D90022010006726573756C7401000770726F66696C6501002C4C6F72672F654D61796F722F506F6C696379456E666F7263656D656E742F435F5573657250726F66696C653B010007666163746F727901002A4C6A617661782F786D6C2F706172736572732F446F63756D656E744275696C646572466163746F72793B0100076275696C6465720100234C6A617661782F786D6C2F706172736572732F446F63756D656E744275696C6465723B01000C737472696E675265616465720100164C6A6176612F696F2F537472696E675265616465723B01000B696E707574536F757263650100194C6F72672F786D6C2F7361782F496E707574536F757263653B010008646F63756D656E740100164C6F72672F7733632F646F6D2F446F63756D656E743B01000576616C7565010006636F6E66696701002A4C6F72672F656D61796F722F7365727669636568616E646C696E672F636F6E6669672F436F6E6669673B01000C7472616E73466163746F72790100284C6A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D6572466163746F72793B0100057472616E730100214C6A617661782F786D6C2F7472616E73666F726D2F5472616E73666F726D65723B0100067772697465720100164C6A6176612F696F2F537472696E675772697465723B010006736F757263650100234C6A617661782F786D6C2F7472616E73666F726D2F646F6D2F444F4D536F757263653B01000673747265616D0100294C6A617661782F786D6C2F7472616E73666F726D2F73747265616D2F53747265616D526573756C743B010001650100154C6A6176612F6C616E672F457863657074696F6E3B010039284C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B4C6A6176612F6C616E672F537472696E673B295601001D4E4F5420535550504F52544544204259205448495320534552564943450800FB0A003C002601000F72657175657374446F63756D656E7401000A536F7572636546696C650100225265736964656E636543657274696669636174696F6E536572766963652E6A61766100210002000400000003000A0005000600000019000700080001000900000002000B0008000C000D0001000E0000000000050008000F001000010011000000570003000000000027B2001359C7001C571215B8001B59B30013A7000FBB001D5A5FB60023B70027BFB8002DB3002FB100010008000D00140031000200320000000E00030000001F0023001E0026001D0033000000020000000100240010000100110000002F00010001000000052AB70035B10000000200320000000600010000001D00330000000C0001000000050036003700000001003800390002003A000000040001003C001100000086000600040000002AB2002F123EB600422A120BB600464E2A2D2BB7004A4E2A124C2B2C2D124EB60052B2002F1254B60042B10000000200320000001A00060000002B0008002C000F002D0016002E0021002F0029003000330000002A00040000002A0036003700000000002A0055000800010000002A005600080002000F001B00570008000300020047004800010011000002E100040011000001692B4E2A2CB6005B3A04B2002FBB005D59125FB700602BB60064B60067B60042B2002FBB005D591269B700601904B6006CB60064B60067B600421904C6012CB800723A051905B600763A06BB0078592BB700793A07BB007B591907B7007E3A0819061908B600843A091904B60089128BB6009103323A0A190AC6001119091293B80099190AB9009E02001904B60089128BB6009104323A0A190AC60011190912A0B80099190AB9009E02001904B600A33A0A190AC60011190912A5B80099190AB9009E02001904B600A83A0A190AC60011190912AAB80099190AB9009E0200B800B03A0B190B03B600B43A0A190AC6002D190912B6B80099190AB9009E0200190912B8B80099190AB9009E0200190912BAB80099190AB9009E0200B800BF3A0C013A0D190CB600C33A0DBB00C559B700C63A0EBB00C8591909B700CB3A0FBB00CD59190EB700D03A10190D190F1910B600D6190EB600D74EA700103A051905B600DC1905B600DD4E2DB00001003E015A015A00D900020032000000A20028000000330002003400090036001F003700390039003E003B0043003C004A003D0054003E005F003F0068004200760043007B00440089004700970048009C004900AA004C00B1004D00B6004E00C4005100CB005200D0005300DE005600E3005700EB005800F0005900FE005A010C005B011A005E011F005F012200600129006101320062013D00630148006401510065015A0066015C0068016100690167006D0033000000B600120000016900360037000000000169005700080001000001690055000800020002016700DE000800030009016000DF00E000040043011700E100E20005004A011000E300E400060054010600E500E60007005F00FB00E700E80008006800F200E900EA0009007600E400EB0008000A00E3007700EC00ED000B011F003B00EE00EF000C0122003800F000F1000D0132002800F200F3000E013D001D00F400F5000F0148001200F600F70010015C000B00F800F900050001003800FA0002003A000000040001003C00110000005E0003000400000012B2002F123EB60042BB003C5912FCB700FDBF0000000200320000000A0002000000720008007300330000002A00040000001200360037000000000012005500080001000000120056000800020000001200FE00080003000100FF000000020100,0xCAFEBABE0000002E004401003B6F72672F656D61796F722F73657276696365732F72632F5265736964656E636543657274696669636174696F6E53657276696365466163746F72790700010100366F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365466163746F72790700030100036C6F670100194C6F72672F6170616368652F6C6F67346A2F4C6F676765723B010007636C61737324300100114C6A6176612F6C616E672F436C6173733B01000953796E7468657469630100083C636C696E69743E010003282956010004436F64650C00070008090002000D0100346F72672E656D61796F722E73657276696365732E72632E5265736964656E636543657274696669636174696F6E5365727669636508000F01000F6A6176612F6C616E672F436C617373070011010007666F724E616D65010025284C6A6176612F6C616E672F537472696E673B294C6A6176612F6C616E672F436C6173733B0C001300140A0012001501001E6A6176612F6C616E672F4E6F436C617373446566466F756E644572726F720700170100136A6176612F6C616E672F5468726F7761626C6507001901000A6765744D65737361676501001428294C6A6176612F6C616E672F537472696E673B0C001B001C0A001A001D0100063C696E69743E010015284C6A6176612F6C616E672F537472696E673B29560C001F00200A001800210100176F72672F6170616368652F6C6F67346A2F4C6F676765720700230100096765744C6F6767657201002C284C6A6176612F6C616E672F436C6173733B294C6F72672F6170616368652F6C6F67346A2F4C6F676765723B0C002500260A002400270C0005000609000200290100206A6176612F6C616E672F436C6173734E6F74466F756E64457863657074696F6E07002B01000F4C696E654E756D6265725461626C650100124C6F63616C5661726961626C655461626C6501000A457863657074696F6E730100386F72672F656D61796F722F7365727669636568616E646C696E672F6B65726E656C2F654D61796F7253657276696365457863657074696F6E0700300C001F000B0A0004003201000E69734465627567456E61626C656401000328295A0C003400350A002400360100172D3E2073746172742070726F63657373696E67202E2E2E0800380100056465627567010015284C6A6176612F6C616E672F4F626A6563743B29560C003A003B0A0024003C0100172D3E202E2E2E2070726F63657373696E6720444F4E452108003E0100047468697301003D4C6F72672F656D61796F722F73657276696365732F72632F5265736964656E636543657274696669636174696F6E53657276696365466163746F72793B01000A536F7572636546696C650100295265736964656E636543657274696669636174696F6E53657276696365466163746F72792E6A61766100210002000400000002001A000500060000000800070008000100090000000000020008000A000B0001000C000000570003000000000027B2000E59C7001C571210B8001659B3000EA7000FBB00185A5FB6001EB70022BFB80028B3002AB100010008000D0014002C0002002D0000000E000300000012002300110026000F002E0000000200000001001F000B0002002F0000000400010031000C00000058000200010000001E2AB70033B2002AB60037990013B2002A1239B6003DB2002A123FB6003DB100000002002D0000001600050000001800040019000D001A0015001B001D001D002E0000000C00010000001E00400041000000010042000000020043);
/*!40000 ALTER TABLE `service_info` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;