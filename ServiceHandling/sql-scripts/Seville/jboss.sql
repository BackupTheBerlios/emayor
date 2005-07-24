create database Seville;

GRANT ALL PRIVILEGES ON Seville.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Seville;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'Seville';
SET @MUNICIPALITY_ID = 'Seville';
SET @MUNICIPALITY_NAME = 'Municipality of Seville';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Seville/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'Seville';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';
SET @EMAYOR_CONTENTROUTING_LOCAL_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_LOCAL_PUBLISH = 'http://localhost:28080/juddi/publish';
SET @EMAYOR_CONTENTROUTING_REMOTE_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_USERID = 'juddi';
SET @EMAYOR_CONTENTROUTING_PASSWORD = 'juddi';
SET @CONFIG_IS_ACTIVE = '1';

SOURCE create_config_instance.sql;
