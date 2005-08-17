-- MySQL dump 10.9
--
-- Host: localhost    Database: aachen
-- ------------------------------------------------------
-- Server version	4.1.12a-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*!40000 ALTER TABLE `service_info` DISABLE KEYS */;
LOCK TABLES `SERVICE_INFO` WRITE;
INSERT INTO `SERVICE_INFO` VALUES ('ResidenceCertificationService_v10','1.0','Residence Certification Service','org.emayor.services.rc.ResidenceCertificationService','org.emayor.services.rc.ResidenceCertificationServiceFactory','Residence Certification Service.','---',1,0,'Êþº¾\0\0\0.\0Z\04org/emayor/services/rc/ResidenceCertificationService\0\07org/emayor/servicehandling/kernel/AbstracteMayorService\0\0log\0Lorg/apache/log4j/Logger;\0DEF_XML_FILE\0Ljava/lang/String;\0\rConstantValue\0/SampleResidenceCertificationRequestDocument.xml\0\n\0class$0\0Ljava/lang/Class;\0	Synthetic\0<clinit>\0()V\0Code\0\0\r	\0\0\04org.emayor.services.rc.ResidenceCertificationService\0\0java/lang/Class\0\0forName\0%(Ljava/lang/String;)Ljava/lang/Class;\0\0\n\0\0\Z\0java/lang/NoClassDefFoundError\0\0java/lang/Throwable\0\0\ngetMessage\0()Ljava/lang/String;\0 \0!\n\0\0\"\0<init>\0(Ljava/lang/String;)V\0$\0%\n\0\0&\0org/apache/log4j/Logger\0(\0	getLogger\0,(Ljava/lang/Class;)Lorg/apache/log4j/Logger;\0*\0+\n\0)\0,\0\0	\0\0.\0 java/lang/ClassNotFoundException\00\0LineNumberTable\0LocalVariableTable\0$\0\n\0\04\0this\06Lorg/emayor/services/rc/ResidenceCertificationService;\0startService\0\'(Ljava/lang/String;Ljava/lang/String;)V\0\nExceptions\08org/emayor/servicehandling/kernel/eMayorServiceException\0;\0-> start processing ...\0=\0debug\0(Ljava/lang/Object;)V\0?\0@\n\0)\0A\0NO\0C\0\ZgetXMLDocumentFromResource\0&(Ljava/lang/String;)Ljava/lang/String;\0E\0F\n\0\0G\0<empty/>\0I\0startIt\0](Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V\0K\0L\n\0\0M\0-> ... processing DONE!\0O\0uid\0ssid\09(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V\0NOT SUPPORTED BY THIS SERVICE\0T\n\0<\0&\0requestDocument\0\nSourceFile\0\"ResidenceCertificationService.java\0!\0\0\0\0\0\0\n\0\0\0\0\0\0\0\0\0	\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0W\0\0\0\0\0\0\'²\0YÇ\0W¸\0Y³\0§\0»\0Z_¶\0#·\0\'¿¸\0-³\0/±\0\0\0\r\0\01\0\02\0\0\0\0\0\0\0\0#\0\0&\0\03\0\0\0\0\0\0\0$\0\0\0\0\0\0/\0\0\0\0\0*·\05±\0\0\0\02\0\0\0\0\0\0\0\03\0\0\0\0\0\0\0\06\07\0\0\0\08\09\0\0:\0\0\0\0\0<\0\0\0\0s\0\0\0\0\0!²\0/>¶\0B*D+,*¶\0HJ¶\0N²\0/P¶\0B±\0\0\0\02\0\0\0\Z\0\0\0\0\0\0\0\r\0\0\0\0\0\0 \0 \03\0\0\0 \0\0\0\0!\06\07\0\0\0\0\0!\0Q\0\0\0\0\0!\0R\0\0\0\08\0S\0\0:\0\0\0\0\0<\0\0\0\0^\0\0\0\0\0²\0/>¶\0B»\0<YU·\0V¿\0\0\0\02\0\0\0\n\0\0\0\0$\0\0%\03\0\0\0*\0\0\0\0\06\07\0\0\0\0\0\0Q\0\0\0\0\0\0R\0\0\0\0\0\0W\0\0\0\0X\0\0\0\0Y','Êþº¾\0\0\0.\0D\0;org/emayor/services/rc/ResidenceCertificationServiceFactory\0\06org/emayor/servicehandling/kernel/eMayorServiceFactory\0\0log\0Lorg/apache/log4j/Logger;\0class$0\0Ljava/lang/Class;\0	Synthetic\0<clinit>\0()V\0Code\0\0	\0\0\r\04org.emayor.services.rc.ResidenceCertificationService\0\0java/lang/Class\0\0forName\0%(Ljava/lang/String;)Ljava/lang/Class;\0\0\n\0\0\0java/lang/NoClassDefFoundError\0\0java/lang/Throwable\0\0\ngetMessage\0()Ljava/lang/String;\0\0\n\0\Z\0\0<init>\0(Ljava/lang/String;)V\0\0 \n\0\0!\0org/apache/log4j/Logger\0#\0	getLogger\0,(Ljava/lang/Class;)Lorg/apache/log4j/Logger;\0%\0&\n\0$\0\'\0\0	\0\0)\0 java/lang/ClassNotFoundException\0+\0LineNumberTable\0LocalVariableTable\0\nExceptions\08org/emayor/servicehandling/kernel/eMayorServiceException\00\0\0\n\0\02\0isDebugEnabled\0()Z\04\05\n\0$\06\0-> start processing ...\08\0debug\0(Ljava/lang/Object;)V\0:\0;\n\0$\0<\0-> ... processing DONE!\0>\0this\0=Lorg/emayor/services/rc/ResidenceCertificationServiceFactory;\0\nSourceFile\0)ResidenceCertificationServiceFactory.java\0!\0\0\0\0\0\0\Z\0\0\0\0\0\0\0\0	\0\0\0\0\0\0\0\n\0\0\0\0\0\0W\0\0\0\0\0\0\'²\0YÇ\0W¸\0Y³\0§\0»\0Z_¶\0·\0\"¿¸\0(³\0*±\0\0\0\r\0\0,\0\0-\0\0\0\0\0\0\0\0#\0\0&\0\0.\0\0\0\0\0\0\0\0\0\0/\0\0\0\0\01\0\0\0\0X\0\0\0\0\0*·\03²\0*¶\07™\0²\0*9¶\0=²\0*?¶\0=±\0\0\0\0-\0\0\0\0\0\0\0\0\0\0\r\0\Z\0\0\0\0\0.\0\0\0\0\0\0\0\0@\0A\0\0\0\0B\0\0\0\0C');
UNLOCK TABLES;
/*!40000 ALTER TABLE `SERVICE_INFO` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

