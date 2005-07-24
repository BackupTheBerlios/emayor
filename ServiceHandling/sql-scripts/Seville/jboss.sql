create database Seville;

GRANT ALL PRIVILEGES ON Seville.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Seville;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'Seville';
SET @MUNICIPALITY_ID = 'Seville';
SET @MUNICIPALITY_NAME = 'Municipality of Seville';

SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'Seville';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SET @FORWARD_MANAGER_QUEUE_HOST = 'localhost:1099';
SET @FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME = 'Seville';
SET @FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS = 'https://localhost:8443/eMayorEJB/ForwardManager';
SET @FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME = 'Bozen';
SET @FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS = 'https://localhost:18443/eMayorEJB/ForwardManager';

SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Seville/eMayorServiceStarter_v10/1.0';

SET @EMAYOR_CONTENTROUTING_LOCAL_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_LOCAL_PUBLISH = 'http://localhost:28080/juddi/publish';
SET @EMAYOR_CONTENTROUTING_REMOTE_INQUIRY = 'http://localhost:28080/juddi/inquiry';
SET @EMAYOR_CONTENTROUTING_USERID = 'juddi';
SET @EMAYOR_CONTENTROUTING_PASSWORD = 'juddi';

SET @CONFIG_IS_ACTIVE = '1';

SET @EMAYOR_OPERATING_MODE = 'production';
SET @EMAYOR_OPERATING_MODE_CONTENT_ROUTING = 'production';
SET @EMAYOR_OPERATING_MODE_EMAIL = 'test';
SET @EMAYOR_OPERATING_MODE_FORWARD = 'production';

SOURCE create_config_instance.sql;
