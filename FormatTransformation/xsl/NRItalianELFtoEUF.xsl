<?xml version="1.0" encoding="UTF-8"?>
<!--
This file was generated by Altova MapForce 2005

YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.

Refer to the Altova MapForce 2005 Documentation for further details.
http://www.altova.com/mapforce
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bus="http://www.emayor.org/BusinessDocument.xsd" xmlns:n18="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:edc="http://www.emayor.org/e-Document.xsd" xmlns:ed2="http://www.emayor.org/e-DocumentI.xsd" xmlns:n19="http://www.govtalk.gov.uk/core" xmlns:aapd2="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails" xmlns:n22="http://www.govtalk.gov.uk/people/PersonDescriptives" xmlns:ds2="http://www.w3.org/2000/09/xmldsig#" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:n32="urn:oasis:names:specification:ubl:schema:xsd:AllowanceChargeReasonCode-1.0" xmlns:n42="urn:oasis:names:specification:ubl:schema:xsd:ChannelCode-1.0" xmlns:n52="urn:oasis:names:specification:ubl:schema:xsd:ChipCode-1.0" xmlns:cac2="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc2="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:n62="urn:oasis:names:specification:ubl:schema:xsd:CoreComponentTypes-1.0" xmlns:n72="urn:oasis:names:specification:ubl:schema:xsd:CountryIdentificationCode-1.0" xmlns:n82="urn:oasis:names:specification:ubl:schema:xsd:CurrencyCode-1.0" xmlns:n92="urn:oasis:names:specification:ubl:schema:xsd:DocumentStatusCode-1.0" xmlns:n102="urn:oasis:names:specification:ubl:schema:xsd:LatitudeDirectionCode-1.0" xmlns:n112="urn:oasis:names:specification:ubl:schema:xsd:LineStatusCode-1.0" xmlns:n122="urn:oasis:names:specification:ubl:schema:xsd:LongitudeDirectionCode-1.0" xmlns:n132="urn:oasis:names:specification:ubl:schema:xsd:OperatorCode-1.0" xmlns:n142="urn:oasis:names:specification:ubl:schema:xsd:PaymentMeansCode-1.0" xmlns:n152="urn:oasis:names:specification:ubl:schema:xsd:SpecializedDatatypes-1.0" xmlns:n162="urn:oasis:names:specification:ubl:schema:xsd:SubstitutionStatusCode-1.0" xmlns:n172="urn:oasis:names:specification:ubl:schema:xsd:UnspecializedDatatypes-1.0" xmlns="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:n1="http://www.govtalk.gov.uk/core" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails" xmlns:n2="http://www.govtalk.gov.uk/people/PersonDescriptives" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:n3="urn:oasis:names:specification:ubl:schema:xsd:AllowanceChargeReasonCode-1.0" xmlns:n4="urn:oasis:names:specification:ubl:schema:xsd:ChannelCode-1.0" xmlns:n5="urn:oasis:names:specification:ubl:schema:xsd:ChipCode-1.0" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:n6="urn:oasis:names:specification:ubl:schema:xsd:CoreComponentTypes-1.0" xmlns:n7="urn:oasis:names:specification:ubl:schema:xsd:CountryIdentificationCode-1.0" xmlns:n8="urn:oasis:names:specification:ubl:schema:xsd:CurrencyCode-1.0" xmlns:n9="urn:oasis:names:specification:ubl:schema:xsd:DocumentStatusCode-1.0" xmlns:n10="urn:oasis:names:specification:ubl:schema:xsd:LatitudeDirectionCode-1.0" xmlns:n11="urn:oasis:names:specification:ubl:schema:xsd:LineStatusCode-1.0" xmlns:n12="urn:oasis:names:specification:ubl:schema:xsd:LongitudeDirectionCode-1.0" xmlns:n13="urn:oasis:names:specification:ubl:schema:xsd:OperatorCode-1.0" xmlns:n14="urn:oasis:names:specification:ubl:schema:xsd:PaymentMeansCode-1.0" xmlns:n15="urn:oasis:names:specification:ubl:schema:xsd:SpecializedDatatypes-1.0" xmlns:n16="urn:oasis:names:specification:ubl:schema:xsd:SubstitutionStatusCode-1.0" xmlns:n17="urn:oasis:names:specification:ubl:schema:xsd:UnspecializedDatatypes-1.0" exclude-result-prefixes="xs bus n18 edc ed2 aapd2 ds2 cac2 cbc2 n19 n22 n32 n42 n52 n62 n72 n82 n92 n102 n112 n122 n132 n142 n152 n162 n172">
	<xsl:output method="xml" encoding="UTF-8"/>
		<xsl:template mode="copy-no-ns" match="*">
		<xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
			<xsl:copy-of select="@*"/>
			<xsl:apply-templates mode="copy-no-ns"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="/n18:RispostaNegativaCertificatoDiResidenza">
		<NegativeResponseResidenceCertificationDocument>
			<xsl:attribute name="xsi:schemaLocation">http://www.emayor.org/BusinessDocument.xsd ../emayor/BusinessDocument-v0.3.xsd</xsl:attribute>
			<xsl:for-each select="ed2:Osservazioni">
				<ed:Observations>
					<xsl:value-of select="."/>
				</ed:Observations>
			</xsl:for-each>
			<xsl:for-each select="n18:RiferimentoOrario">
				<TimeReference>
					<xsl:value-of select="."/>
				</TimeReference>
			</xsl:for-each>
			<xsl:for-each select="n18:Timestamp">
				<Timestamp>
					<xsl:value-of select="."/>
				</Timestamp>
			</xsl:for-each>
			<xsl:for-each select="n18:TimbroDiAccettazione">
				<AcknowledgementStamp>
					<xsl:value-of select="."/>
				</AcknowledgementStamp>
			</xsl:for-each>
			<xsl:for-each select="n18:RichiestaOriginaria">
				<OriginalRequest>
					<xsl:for-each select="bus:LoginServer">
						<LoginServer>
							<xsl:value-of select="."/>
						</LoginServer>
					</xsl:for-each>
					<xsl:for-each select="bus:TransactionId">
						<TransactionId>
							<xsl:value-of select="."/>
						</TransactionId>
					</xsl:for-each>
					<xsl:for-each select="bus:RequesterDetails">
						<RequesterDetails>
							<xsl:for-each select="edc:CitizenName">
								<ed:CitizenName>
									<xsl:for-each select="aapd2:CitizenNameTitle">
										<aapd:CitizenNameTitle>
											<xsl:value-of select="."/>
										</aapd:CitizenNameTitle>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameForename">
										<aapd:CitizenNameForename>
											<xsl:value-of select="."/>
										</aapd:CitizenNameForename>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameSurname">
										<aapd:CitizenNameSurname>
											<xsl:value-of select="."/>
										</aapd:CitizenNameSurname>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameSuffix">
										<aapd:CitizenNameSuffix>
											<xsl:value-of select="."/>
										</aapd:CitizenNameSuffix>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameRequestedName">
										<aapd:CitizenNameRequestedName>
											<xsl:value-of select="."/>
										</aapd:CitizenNameRequestedName>
									</xsl:for-each>
								</ed:CitizenName>
							</xsl:for-each>
							<xsl:for-each select="edc:PreferredLanguages">
								<ed:PreferredLanguages>
									<xsl:value-of select="."/>
								</ed:PreferredLanguages>
							</xsl:for-each>
							<xsl:for-each select="edc:ContactDetails">
								<ed:ContactDetails>
									<xsl:for-each select="aapd2:Email">
										<aapd:Email>
											<xsl:for-each select="@EmailUsage">
												<xsl:attribute name="EmailUsage">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@EmailPreferred">
												<xsl:attribute name="EmailPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:EmailAddress">
												<aapd:EmailAddress>
													<xsl:value-of select="."/>
												</aapd:EmailAddress>
											</xsl:for-each>
										</aapd:Email>
									</xsl:for-each>
									<xsl:for-each select="aapd2:Telephone">
										<aapd:Telephone>
											<xsl:for-each select="@TelUse">
												<xsl:attribute name="TelUse">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@TelMobile">
												<xsl:attribute name="TelMobile">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@TelPreferred">
												<xsl:attribute name="TelPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelNationalNumber">
												<aapd:TelNationalNumber>
													<xsl:value-of select="."/>
												</aapd:TelNationalNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelExtensionNumber">
												<aapd:TelExtensionNumber>
													<xsl:value-of select="."/>
												</aapd:TelExtensionNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelCountryCode">
												<aapd:TelCountryCode>
													<xsl:value-of select="."/>
												</aapd:TelCountryCode>
											</xsl:for-each>
										</aapd:Telephone>
									</xsl:for-each>
									<xsl:for-each select="aapd2:Fax">
										<aapd:Fax>
											<xsl:for-each select="@FaxUse">
												<xsl:attribute name="FaxUse">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@FaxMobile">
												<xsl:attribute name="FaxMobile">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@FaxPreferred">
												<xsl:attribute name="FaxPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxNationalNumber">
												<aapd:FaxNationalNumber>
													<xsl:value-of select="."/>
												</aapd:FaxNationalNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxExtensionNumber">
												<aapd:FaxExtensionNumber>
													<xsl:value-of select="."/>
												</aapd:FaxExtensionNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxCountryCode">
												<aapd:FaxCountryCode>
													<xsl:value-of select="."/>
												</aapd:FaxCountryCode>
											</xsl:for-each>
										</aapd:Fax>
									</xsl:for-each>
								</ed:ContactDetails>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenSex">
								<ed:CitizenSex>
									<xsl:value-of select="."/>
								</ed:CitizenSex>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenMaritalStatus">
								<ed:CitizenMaritalStatus>
									<xsl:value-of select="."/>
								</ed:CitizenMaritalStatus>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenBirthDetails">
								<ed:CitizenBirthDetails>
									<xsl:for-each select="edc:DateOfBirth">
										<ed:DateOfBirth>
											<xsl:for-each select="aapd2:BirthDate">
												<aapd:BirthDate>
													<xsl:value-of select="."/>
												</aapd:BirthDate>
											</xsl:for-each>
											<xsl:for-each select="aapd2:VerifiedBy">
												<aapd:VerifiedBy>
													<xsl:value-of select="."/>
												</aapd:VerifiedBy>
											</xsl:for-each>
										</ed:DateOfBirth>
									</xsl:for-each>
									<xsl:for-each select="cbc2:CityName">
										<cbc:CityName>
											<xsl:for-each select="@languageID">
												<xsl:attribute name="languageID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@languageLocaleID">
												<xsl:attribute name="languageLocaleID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:value-of select="."/>
										</cbc:CityName>
									</xsl:for-each>
									<xsl:for-each select="edc:BirthActNumber">
										<ed:BirthActNumber>
											<xsl:value-of select="."/>
										</ed:BirthActNumber>
									</xsl:for-each>
								</ed:CitizenBirthDetails>
							</xsl:for-each>
							<xsl:for-each select="edc:Nationality">
								<ed:Nationality>
									<xsl:value-of select="."/>
								</ed:Nationality>
							</xsl:for-each>
							<xsl:for-each select="edc:IdentificationDetails">
								<ed:IdentificationDetails>
									<xsl:for-each select="edc:IDCardNumber">
										<ed:IDCardNumber>
											<xsl:value-of select="."/>
										</ed:IDCardNumber>
									</xsl:for-each>
									<xsl:for-each select="edc:PassportNumber">
										<ed:PassportNumber>
											<xsl:value-of select="."/>
										</ed:PassportNumber>
									</xsl:for-each>
								</ed:IdentificationDetails>
							</xsl:for-each>
						</RequesterDetails>
					</xsl:for-each>
					<xsl:for-each select="bus:ConcernedPersonDetails">
						<ConcernedPersonDetails>
							<xsl:for-each select="edc:CitizenName">
								<ed:CitizenName>
									<xsl:for-each select="aapd2:CitizenNameTitle">
										<aapd:CitizenNameTitle>
											<xsl:value-of select="."/>
										</aapd:CitizenNameTitle>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameForename">
										<aapd:CitizenNameForename>
											<xsl:value-of select="."/>
										</aapd:CitizenNameForename>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameSurname">
										<aapd:CitizenNameSurname>
											<xsl:value-of select="."/>
										</aapd:CitizenNameSurname>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameSuffix">
										<aapd:CitizenNameSuffix>
											<xsl:value-of select="."/>
										</aapd:CitizenNameSuffix>
									</xsl:for-each>
									<xsl:for-each select="aapd2:CitizenNameRequestedName">
										<aapd:CitizenNameRequestedName>
											<xsl:value-of select="."/>
										</aapd:CitizenNameRequestedName>
									</xsl:for-each>
								</ed:CitizenName>
							</xsl:for-each>
							<xsl:for-each select="edc:PreferredLanguages">
								<ed:PreferredLanguages>
									<xsl:value-of select="."/>
								</ed:PreferredLanguages>
							</xsl:for-each>
							<xsl:for-each select="edc:ContactDetails">
								<ed:ContactDetails>
									<xsl:for-each select="aapd2:Email">
										<aapd:Email>
											<xsl:for-each select="@EmailUsage">
												<xsl:attribute name="EmailUsage">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@EmailPreferred">
												<xsl:attribute name="EmailPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:EmailAddress">
												<aapd:EmailAddress>
													<xsl:value-of select="."/>
												</aapd:EmailAddress>
											</xsl:for-each>
										</aapd:Email>
									</xsl:for-each>
									<xsl:for-each select="aapd2:Telephone">
										<aapd:Telephone>
											<xsl:for-each select="@TelUse">
												<xsl:attribute name="TelUse">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@TelMobile">
												<xsl:attribute name="TelMobile">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@TelPreferred">
												<xsl:attribute name="TelPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelNationalNumber">
												<aapd:TelNationalNumber>
													<xsl:value-of select="."/>
												</aapd:TelNationalNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelExtensionNumber">
												<aapd:TelExtensionNumber>
													<xsl:value-of select="."/>
												</aapd:TelExtensionNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:TelCountryCode">
												<aapd:TelCountryCode>
													<xsl:value-of select="."/>
												</aapd:TelCountryCode>
											</xsl:for-each>
										</aapd:Telephone>
									</xsl:for-each>
									<xsl:for-each select="aapd2:Fax">
										<aapd:Fax>
											<xsl:for-each select="@FaxUse">
												<xsl:attribute name="FaxUse">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@FaxMobile">
												<xsl:attribute name="FaxMobile">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@FaxPreferred">
												<xsl:attribute name="FaxPreferred">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxNationalNumber">
												<aapd:FaxNationalNumber>
													<xsl:value-of select="."/>
												</aapd:FaxNationalNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxExtensionNumber">
												<aapd:FaxExtensionNumber>
													<xsl:value-of select="."/>
												</aapd:FaxExtensionNumber>
											</xsl:for-each>
											<xsl:for-each select="aapd2:FaxCountryCode">
												<aapd:FaxCountryCode>
													<xsl:value-of select="."/>
												</aapd:FaxCountryCode>
											</xsl:for-each>
										</aapd:Fax>
									</xsl:for-each>
								</ed:ContactDetails>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenSex">
								<ed:CitizenSex>
									<xsl:value-of select="."/>
								</ed:CitizenSex>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenMaritalStatus">
								<ed:CitizenMaritalStatus>
									<xsl:value-of select="."/>
								</ed:CitizenMaritalStatus>
							</xsl:for-each>
							<xsl:for-each select="edc:CitizenBirthDetails">
								<ed:CitizenBirthDetails>
									<xsl:for-each select="edc:DateOfBirth">
										<ed:DateOfBirth>
											<xsl:for-each select="aapd2:BirthDate">
												<aapd:BirthDate>
													<xsl:value-of select="."/>
												</aapd:BirthDate>
											</xsl:for-each>
											<xsl:for-each select="aapd2:VerifiedBy">
												<aapd:VerifiedBy>
													<xsl:value-of select="."/>
												</aapd:VerifiedBy>
											</xsl:for-each>
										</ed:DateOfBirth>
									</xsl:for-each>
									<xsl:for-each select="cbc2:CityName">
										<cbc:CityName>
											<xsl:for-each select="@languageID">
												<xsl:attribute name="languageID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@languageLocaleID">
												<xsl:attribute name="languageLocaleID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:value-of select="."/>
										</cbc:CityName>
									</xsl:for-each>
									<xsl:for-each select="edc:BirthActNumber">
										<ed:BirthActNumber>
											<xsl:value-of select="."/>
										</ed:BirthActNumber>
									</xsl:for-each>
								</ed:CitizenBirthDetails>
							</xsl:for-each>
							<xsl:for-each select="edc:Nationality">
								<ed:Nationality>
									<xsl:value-of select="."/>
								</ed:Nationality>
							</xsl:for-each>
							<xsl:for-each select="edc:IdentificationDetails">
								<ed:IdentificationDetails>
									<xsl:for-each select="edc:IDCardNumber">
										<ed:IDCardNumber>
											<xsl:value-of select="."/>
										</ed:IDCardNumber>
									</xsl:for-each>
									<xsl:for-each select="edc:PassportNumber">
										<ed:PassportNumber>
											<xsl:value-of select="."/>
										</ed:PassportNumber>
									</xsl:for-each>
								</ed:IdentificationDetails>
							</xsl:for-each>
						</ConcernedPersonDetails>
					</xsl:for-each>
					<xsl:for-each select="bus:RequestDate">
						<RequestDate>
							<xsl:value-of select="."/>
						</RequestDate>
					</xsl:for-each>
					<xsl:for-each select="edc:Observations">
						<ed:Observations>
							<xsl:value-of select="."/>
						</ed:Observations>
					</xsl:for-each>
					<xsl:for-each select="bus:NotificationMedium">
						<NotificationMedium>
							<xsl:value-of select="."/>
						</NotificationMedium>
					</xsl:for-each>
					<xsl:for-each select="bus:AcknowledgementStamp">
						<AcknowledgementStamp>
							<xsl:value-of select="."/>
						</AcknowledgementStamp>
					</xsl:for-each>
					<xsl:for-each select="bus:ServingMunicipalityDetails">
						<ServingMunicipalityDetails>
							<xsl:value-of select="."/>
						</ServingMunicipalityDetails>
					</xsl:for-each>
					<xsl:for-each select="bus:ReceivingMunicipalityDetails">
						<ReceivingMunicipalityDetails>
							<xsl:value-of select="."/>
						</ReceivingMunicipalityDetails>
					</xsl:for-each>
					<xsl:for-each select="bus:RequestedDocumentFormat">
						<RequestedDocumentFormat>
							<xsl:value-of select="."/>
						</RequestedDocumentFormat>
					</xsl:for-each>
					<xsl:for-each select="bus:RequesterAddress">
						<RequesterAddress>
							<xsl:for-each select="cbc2:Postbox">
								<cbc:Postbox>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:Postbox>
							</xsl:for-each>
							<xsl:for-each select="cbc2:Floor">
								<cbc:Floor>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:Floor>
							</xsl:for-each>
							<xsl:for-each select="cbc2:Room">
								<cbc:Room>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:Room>
							</xsl:for-each>
							<xsl:for-each select="cbc2:StreetName">
								<cbc:StreetName>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:StreetName>
							</xsl:for-each>
							<xsl:for-each select="cbc2:AdditionalStreetName">
								<cbc:AdditionalStreetName>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:AdditionalStreetName>
							</xsl:for-each>
							<xsl:for-each select="cbc2:BuildingName">
								<cbc:BuildingName>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:BuildingName>
							</xsl:for-each>
							<xsl:for-each select="cbc2:BuildingNumber">
								<cbc:BuildingNumber>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:BuildingNumber>
							</xsl:for-each>
							<xsl:for-each select="cbc2:Department">
								<cbc:Department>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:Department>
							</xsl:for-each>
							<xsl:for-each select="cbc2:CityName">
								<cbc:CityName>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:CityName>
							</xsl:for-each>
							<xsl:for-each select="cbc2:PostalZone">
								<cbc:PostalZone>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:PostalZone>
							</xsl:for-each>
							<xsl:for-each select="cbc2:District">
								<cbc:District>
									<xsl:for-each select="@languageID">
										<xsl:attribute name="languageID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:for-each select="@languageLocaleID">
										<xsl:attribute name="languageLocaleID">
											<xsl:value-of select="."/>
										</xsl:attribute>
									</xsl:for-each>
									<xsl:value-of select="."/>
								</cbc:District>
							</xsl:for-each>
							<xsl:for-each select="cac2:Country">
								<cac:Country>
									<xsl:for-each select="cac2:IdentificationCode">
										<cac:IdentificationCode>
											<xsl:for-each select="@codeListID">
												<xsl:attribute name="codeListID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListAgencyID">
												<xsl:attribute name="codeListAgencyID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListAgencyName">
												<xsl:attribute name="codeListAgencyName">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListName">
												<xsl:attribute name="codeListName">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListVersionID">
												<xsl:attribute name="codeListVersionID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@name">
												<xsl:attribute name="name">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@languageID">
												<xsl:attribute name="languageID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListURI">
												<xsl:attribute name="codeListURI">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@codeListSchemeURI">
												<xsl:attribute name="codeListSchemeURI">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:value-of select="."/>
										</cac:IdentificationCode>
									</xsl:for-each>
									<xsl:for-each select="cbc2:Name">
										<cbc:Name>
											<xsl:for-each select="@languageID">
												<xsl:attribute name="languageID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:for-each select="@languageLocaleID">
												<xsl:attribute name="languageLocaleID">
													<xsl:value-of select="."/>
												</xsl:attribute>
											</xsl:for-each>
											<xsl:value-of select="."/>
										</cbc:Name>
									</xsl:for-each>
								</cac:Country>
							</xsl:for-each>
							<xsl:for-each select="edc:Nucleus">
								<ed:Nucleus>
									<xsl:value-of select="."/>
								</ed:Nucleus>
							</xsl:for-each>
							<xsl:for-each select="edc:StreetQualifier">
								<ed:StreetQualifier>
									<xsl:value-of select="."/>
								</ed:StreetQualifier>
							</xsl:for-each>
							<xsl:for-each select="edc:Section">
								<ed:Section>
									<xsl:value-of select="."/>
								</ed:Section>
							</xsl:for-each>
							<xsl:for-each select="edc:RegistrationSheet">
								<ed:RegistrationSheet>
									<xsl:value-of select="."/>
								</ed:RegistrationSheet>
							</xsl:for-each>
						</RequesterAddress>
					</xsl:for-each>
					<xsl:apply-templates mode="copy-no-ns" select="ds:Signature"/>
				</OriginalRequest>
			</xsl:for-each>
		</NegativeResponseResidenceCertificationDocument>
	</xsl:template>
</xsl:stylesheet>
