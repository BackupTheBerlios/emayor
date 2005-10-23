create database Bozen;

GRANT ALL PRIVILEGES ON Bozen.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Bozen;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'Bozen';
SET @MUNICIPALITY_ID = 'Bolzano-Bozen';
SET @MUNICIPALITY_NAME = 'Municipality of Bolzano-Bozen';

SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'Bozen';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SET @FORWARD_MANAGER_QUEUE_HOST = 'localhost:11099';
SET @FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME = 'Bolzano/Bozen';
SET @FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS = 'https://localhost:18443/eMayorEJB/ForwardManager';
SET @FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME = 'Aachen';
SET @FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS = 'https://localhost:8443/eMayorEJB/ForwardManager';

SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Bozen/eMayorServiceStarter_v10/1.0';

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

SET @EMAYOR_E2M_CONTEXT = '//localhost:2001/Bozen/E2MServer';

SET @EMAYOR_NOTIFICATION_EMAIL_REPLYTO = 'emayor@emayor.org';

SOURCE create_config_instance.sql;
SOURCE admin_services.sql; 
