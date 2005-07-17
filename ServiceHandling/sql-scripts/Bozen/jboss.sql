create database Bozen;

GRANT ALL PRIVILEGES ON Bozen.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Bozen;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'default';
SET @MUNICIPALITY_ID = 'Bozen';
SET @MUNICIPALITY_NAME = 'Municipality of Bozen';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Bozen/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'bozen';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SOURCE create_config_instance.sql;


### add several municipality configurations ###
SOURCE create_config_defaults.sql;