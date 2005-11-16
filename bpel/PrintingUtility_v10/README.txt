--- changes ---

the ejb binding is searching the PE service using the naming service
of the jboss (eMayor-jboss) and therefor it needs the right location
and port information. this information is specified in the BPELPolicyEnforcerService.wsdl
file and has to be adjusted according to the jboss sesstings

the default value is localhost:1099

<ejb:address 
	className="org.emayor.servicehandling.interfaces.BPELPolicyEnforcerHome" 
	jndiName="ejb/emayor/sh/BPELPolicyEnforcer" 
	initialContextFactory="org.jnp.interfaces.NamingContextFactory" 
	jndiProviderURL="localhost:1099"/>