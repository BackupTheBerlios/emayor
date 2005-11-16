The input is of the usual type (eMayorServiceRequest), the document delivered(<reqestDocument>)
should be a string of an xml-representation following the schema located under
xsd/eMayorNotificationBuilderRequest.xsd.
A correct value for an "reqestDocument" should look somehow like this:

-------------------------8<----------------------------------------------------------
<reqestDocument>
	<subject>
		Get your Certificate NOW!
	</subject>
	<message>
		Dear ${TITLE} ${FORENAME} ${SURNAME},
		
		we are pleased to announce your certification request was performed
		and you can get your certificate .....
		
		Best regards,
		
		${COMPANY}
	</message>
	<!--
		!! only one mapping per line is allowed !!
		so at least the given String should contain "\n" between two mappings
	-->
	<mapping>
		TITLE=/Request/Citizen/Title
		FORENAME=/Request/Citizen/Forename
		SURNAME=/Request/Citizen/Surname
		COMPANY=/Request/Company/Name
	</mapping>
	<profile>
		<Request>
			<Citizen>
				<Title>Dr. phil.</Title>
				<Forename>Hans</Forename>
				<Surname>Mustermann</Surname>
			</Citizen>
			<Company>
				<Name>eMayor Inc.</Name>
			</Company>
		</Request>
	</profile>
	<qualifier>
		NOTE
	</qualifier>
</reqestDocument>
-------------------------8<----------------------------------------------------------


The subject specifies the subject of the email, the "message" will be composed using the
keys specified in the "mapping", that itself uses references to access nodes of the "profile".
The "qualifier" is currently only a placeholder.