create database Aachen;

GRANT ALL PRIVILEGES ON Aachen.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Aachen;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'default';
SET @MUNICIPALITY_ID = 'Aachen';
SET @MUNICIPALITY_NAME = 'Municipality of Aachen';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Aachen/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'aachen';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SOURCE create_config_instance.sql;


### add several municipality configurations ###
SOURCE create_config_defaults.sql;