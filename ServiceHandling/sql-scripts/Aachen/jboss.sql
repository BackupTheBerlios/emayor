create database Aachen;

GRANT ALL PRIVILEGES ON Aachen.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Aachen;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'Aachen';
SET @MUNICIPALITY_ID = 'Aachen';
SET @MUNICIPALITY_NAME = 'Municipality of Aachen';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Aachen/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'Aachen';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';
SET @EMAYOR_CONTENTROUTING_LOCAL_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_LOCAL_PUBLISH = 'http://localhost:28080/juddi/publish';
SET @EMAYOR_CONTENTROUTING_REMOTE_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_USERID = 'juddi';
SET @EMAYOR_CONTENTROUTING_PASSWORD = 'juddi';
SET @CONFIG_IS_ACTIVE = '1';

SOURCE create_config_instance.sql;
