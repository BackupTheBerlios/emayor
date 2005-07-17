create database Siena;

GRANT ALL PRIVILEGES ON Siena.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Siena;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'default';
SET @MUNICIPALITY_ID = 'Siena';
SET @MUNICIPALITY_NAME = 'Municipality of Siena';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Siena/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'siena';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SOURCE create_config_instance.sql;


### add several municipality configurations ###
SOURCE create_config_defaults.sql;

