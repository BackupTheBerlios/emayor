create database Bozen;

GRANT ALL PRIVILEGES ON Bozen.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Bozen;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'Bozen';
SET @MUNICIPALITY_ID = 'Bozen';
SET @MUNICIPALITY_NAME = 'Municipality of Bozen';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Bozen/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'Bozen';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';
SET @EMAYOR_CONTENTROUTING_LOCAL_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_LOCAL_PUBLISH = 'http://localhost:28080/juddi/publish';
SET @EMAYOR_CONTENTROUTING_REMOTE_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_USERID = 'juddi';
SET @EMAYOR_CONTENTROUTING_PASSWORD = 'juddi';
SET @CONFIG_IS_ACTIVE = '1';

SOURCE create_config_instance.sql;
