CREATE TABLE PLATFORMCONFIG (
	CONFIGID VARCHAR(200) NOT NULL, 
	emayor_platform_instance_id VARCHAR(20), 
	emayor_municipality_name VARCHAR(50), 
	emayor_operating_mode VARCHAR(20), 
	emayor_operating_mode_forward VARCHAR(20), 
	emayor_tmp_dir VARCHAR(50), 
	emayor_operating_mode_email VARCHAR(20), 
	emayor_email_test_user_address VARCHAR(50), 
	forward_manager_queue_host VARCHAR(50), 
	forward_manager_queue_name VARCHAR(50), 
	emayor_operation_mode_content_routing VARCHAR(20), 
	forward_manager_test_local_municipality_name VARCHAR(50), 
	forward_manager_test_remote_municipality_name VARCHAR(50), 
	forward_manager_test_local_municipality_address VARCHAR(200), 
	forward_manager_test_remote_municipality_address VARCHAR(200), 
	forward_manager_test_remote_profile_id VARCHAR(20), 
	emayor_service_invoker_endpoint VARCHAR(20), 
	emayor_admin_interface_is_enabled VARCHAR(4), 
	emayor_admin_interface_userid VARCHAR(20),
	emayor_admin_interface_password VARCHAR(16), 
	bpel_engine_ut_initial_context_factory VARCHAR(200), 
	bpel_engine_ut_security_principal VARCHAR(20), 
	bpel_engine_ut_security_credentials VARCHAR(16), 
	bpel_engine_ut_provider_url VARCHAR(200), 
	bpel_engine_ut_security_domain VARCHAR(20), 
	bpel_engine_ut_security_domain_password VARCHAR(16), 
	emayor_pe_info_dir VARCHAR(200), 
	emayor_notification_email_smtp_host VARCHAR(50), 
	emayor_notification_email_smtp_user VARCHAR(20), 
	emayor_notification_email_smtp_pass VARCHAR(16), 
	emayor_notification_email_smtp_auth VARCHAR(4), 
	CONSTRAINT pk_PLATFORMCONFIG PRIMARY KEY (CONFIGID)
);