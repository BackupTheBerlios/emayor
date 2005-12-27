<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cdi="http://www.emayor.org/BusinessDocumentG.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:edi="http://www.emayor.org/e-DocumentG.xsd" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:output method="xml" indent="yes"/>
  
<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="cdi:Meldebescheinigung">
	<cd:ResidenceCertificationDocument xsi:schemaLocation="http://www.emayor.org/BusinessDocument.xsd ../emayor/BusinessDocument-v0.3.xsd">
		<xsl:apply-templates select="edi:DokumentId"/>
		<xsl:apply-templates select="cdi:AusstellendeGemeinde"/>
		<xsl:apply-templates select="cdi:BescheinigteBeteiligten"/>
		<xsl:apply-templates select="edi:AusgestelltAm"/>
		<xsl:apply-templates select="edi:Bemerkungen"/>
		<xsl:apply-templates select="edi:Gültigkeitszeitraum"/>
		<xsl:apply-templates select="cdi:Timestamp"/>
		<xsl:apply-templates select="cdi:Bestätigungsstempel"/>		
		<xsl:apply-templates select="cdi:Adresse"/>
	</cd:ResidenceCertificationDocument>
</xsl:template>

<xsl:template match="edi:DokumentId">
	<ed:DocumentId>
		<xsl:apply-templates/>
	</ed:DocumentId>
</xsl:template>

<xsl:template match="cdi:AusstellendeGemeinde">
	<cd:IssuingMunicipality>
		<xsl:apply-templates/>
	</cd:IssuingMunicipality>
</xsl:template>

<xsl:template match="cdi:BescheinigteBeteiligten">
	<cd:CertifiedConcernedPersons>
		<xsl:for-each select="edi:EinwohnerAngaben">
			<ed:CitizenDetails>
				<xsl:apply-templates select="edi:EinwohnerName"/>
				<xsl:apply-templates select="edi:BevorzugteSprachen"/>
				<xsl:apply-templates select="edi:KontaktDetails"/>
				<xsl:apply-templates select="edi:EinwohnerGeschlecht"/>
				<xsl:apply-templates select="edi:EinwohnerZivilstand"/>
				<xsl:apply-templates select="edi:EinwohnerGeburtsdaten"/>
				<xsl:apply-templates select="edi:Nationalität"/>
				<xsl:apply-templates select="edi:Identifizierungsdaten"/>
			</ed:CitizenDetails>
		</xsl:for-each>
	</cd:CertifiedConcernedPersons>
</xsl:template>

<xsl:template match="edi:EinwohnerName">
	<ed:CitizenName>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</ed:CitizenName>
</xsl:template>

<xsl:template match="edi:BevorzugteSprachen">
	<ed:PreferredLanguages>
		<xsl:apply-templates/>
	</ed:PreferredLanguages>
</xsl:template>

<xsl:template match="edi:KontaktDetails">
	<ed:ContactDetails>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</ed:ContactDetails>
</xsl:template>

<xsl:template match="edi:EinwohnerGeschlecht">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenSex>
		  <xsl:choose>
                <xsl:when test="$sex = 'Männlich'">Male</xsl:when>
                <xsl:when test="$sex = 'Weiblich'">Female</xsl:when>
            </xsl:choose>
	</ed:CitizenSex>
</xsl:template>

<xsl:template match="edi:EinwohnerZivilstand">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenMaritalStatus>
		<xsl:choose>
                <xsl:when test="$status = 'Ledig'">Single</xsl:when>
                <xsl:when test="$status = 'Verheiratet'">Married</xsl:when>
                <xsl:when test="$status = 'Geschieden'">Divorced</xsl:when>
                <xsl:when test="$status = 'Verwitwet'">Widowed</xsl:when>
                <xsl:when test="$status = 'GetrenntLebend'">Separated</xsl:when>
        </xsl:choose>
	</ed:CitizenMaritalStatus>
</xsl:template>

<xsl:template match="edi:EinwohnerGeburtsdaten">
	<ed:CitizenBirthDetails>
		<xsl:apply-templates select="edi:Geburtsdatum"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="edi:GeburtsurkundeNummer"/>
	</ed:CitizenBirthDetails>
</xsl:template>

<xsl:template match="edi:Geburtsdatum">
	<ed:DateOfBirth>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</ed:DateOfBirth>
</xsl:template>

<xsl:template match="edi:GeburtsurkundeNummer">
	<ed:BirthActNumber>
		<xsl:apply-templates/>
	</ed:BirthActNumber>
</xsl:template>

<xsl:template match="edi:Nationalität">
	<ed:Nationality>
		<xsl:apply-templates/>
	</ed:Nationality>
</xsl:template>

<xsl:template match="edi:Identifizierungsdaten">
	<ed:IdentificationDetails>
		<xsl:apply-templates select="edi:PersonalausweisNummer"/>
		<xsl:apply-templates select="edi:ReisepassNummer"/>
	</ed:IdentificationDetails>
</xsl:template>

<xsl:template match="edi:PersonalausweisNummer">
	<ed:IDCardNumber>
		<xsl:apply-templates/>
	</ed:IDCardNumber>
</xsl:template>

<xsl:template match="edi:ReisepassNummer">
	<ed:PassportNumber>
		<xsl:apply-templates/>
	</ed:PassportNumber>
</xsl:template>

<xsl:template match="edi:AusgestelltAm">
	<ed:IssuanceDate>
		<xsl:apply-templates/>
	</ed:IssuanceDate>
</xsl:template>

<xsl:template match="edi:Bemerkungen">
	<ed:Observations>
		<xsl:apply-templates/>
	</ed:Observations>
</xsl:template>

<xsl:template match="edi:Gültigkeitszeitraum">
	<ed:Terms>
		<xsl:apply-templates/>
	</ed:Terms>
</xsl:template>

<xsl:template match="cdi:Timestamp">
	<cd:Timestamp>
		<xsl:apply-templates/>
	</cd:Timestamp>
</xsl:template>

<xsl:template match="cdi:Bestätigungsstempel">
	<cd:AcknowledgementStamp>
		<xsl:apply-templates/>
	</cd:AcknowledgementStamp>
</xsl:template>

<xsl:template match="cdi:Adresse">
	<cd:Address>
		<xsl:apply-templates select="edi:Postfach"/>
		<xsl:apply-templates select="edi:Etage"/>
		<xsl:apply-templates select="edi:Raum"/>
		<xsl:apply-templates select="edi:Straße"/>
		<xsl:apply-templates select="edi:StraßennameZusatz"/>
		<xsl:apply-templates select="edi:GebäudeName"/>
		<xsl:apply-templates select="edi:GebäudeNummer"/>
		<xsl:apply-templates select="edi:Abteilung"/>
		<xsl:apply-templates select="edi:Stadt"/>
		<xsl:apply-templates select="edi:Postbezirk"/>
		<xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/>
		<xsl:apply-templates select="edi:Kreis"/>
		<xsl:apply-templates select="edi:Land"/>
		<xsl:apply-templates select="edi:Nukleus"/>
		<xsl:apply-templates select="edi:StraßeQualifizierer"/>
		<xsl:apply-templates select="edi:Sektion"/>
	</cd:Address>
</xsl:template>

<xsl:template match="edi:Postfach">
	<cbc:Postbox>
		<xsl:apply-templates/>
	</cbc:Postbox>
</xsl:template>

<xsl:template match="edi:Etage">
	<cbc:Floor>
		<xsl:apply-templates/>
	</cbc:Floor>
</xsl:template>

<xsl:template match="edi:Raum">
	<cbc:Room>
		<xsl:apply-templates/>
	</cbc:Room>
</xsl:template>

<xsl:template match="edi:Straße">
	<cbc:StreetName>
		<xsl:apply-templates/>
	</cbc:StreetName>
</xsl:template>

<xsl:template match="edi:StraßennameZusatz">
	<cbc:AdditionalStreetName>
		<xsl:apply-templates/>
	</cbc:AdditionalStreetName>
</xsl:template>

<xsl:template match="edi:GebäudeName">
	<cbc:BuildingName>
		<xsl:apply-templates/>
	</cbc:BuildingName>
</xsl:template>

<xsl:template match="edi:GebäudeNummer">
	<cbc:BuildingNumber>
		<xsl:apply-templates/>
	</cbc:BuildingNumber>
</xsl:template>

<xsl:template match="edi:Abteilung">
	<cbc:Department>
		<xsl:apply-templates/>
	</cbc:Department>
</xsl:template>

<xsl:template match="edi:Stadt">
	<cbc:CityName>
		<xsl:apply-templates/>
	</cbc:CityName>
</xsl:template>

<xsl:template match="edi:Postbezirk">
	<cbc:PostalZone>
		<xsl:apply-templates/>
	</cbc:PostalZone>
</xsl:template>

<xsl:template match="edi:Kreis">
	<cbc:District>
		<xsl:apply-templates/>
	</cbc:District>
</xsl:template>

<xsl:template match="edi:Land">
	<cac:Country>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</cac:Country>
</xsl:template>

<xsl:template match="edi:Nukleus">
	<ed:Nucleus>
		<xsl:apply-templates/>
	</ed:Nucleus>
</xsl:template>

<xsl:template match="edi:StraßeQualifizierer">
	<ed:StreetQualifier>
		<xsl:apply-templates/>
	</ed:StreetQualifier>
</xsl:template>

<xsl:template match="edi:Sektion">
	<ed:Section>
		<xsl:apply-templates/>
	</ed:Section>
</xsl:template>

</xsl:stylesheet>