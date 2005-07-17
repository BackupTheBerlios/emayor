create database Seville;

GRANT ALL PRIVILEGES ON Seville.* TO jboss@localhost IDENTIFIED BY 'emayor';

use Seville;

SOURCE create_config_table.sql

### default config ###
SET @CONFIG_ID = 'default';
SET @MUNICIPALITY_ID = 'Seville';
SET @MUNICIPALITY_NAME = 'Municipality of Seville';
SET @EMAYOR_SERVICE_INVOKER_ENDPOINT = 'http://localhost:9700/orabpel/Seville/eMayorServiceStarter_v10/1.0';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 'seville';
SET @BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 'bpel';

SOURCE create_config_instance.sql;


### add several municipality configurations ###
SOURCE create_config_defaults.sql;
