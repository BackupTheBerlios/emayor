<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cdg="http://www.emayor.org/BusinessDocumentG.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:edg="http://www.emayor.org/e-DocumentG.xsd" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >
  <xsl:output method="xml" indent="yes"/>
  
<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="/">
	<xsl:apply-templates select="cd:ResidenceCertificationDocument" mode="german"/>
</xsl:template>

<xsl:template match="cd:ResidenceCertificationDocument" mode="german">
	<cdg:Meldebescheinigung xsi:schemaLocation="http://www.emayor.org/BusinessDocumentG.xsd ../emayor/BusinessDocument-v0.2g.xsd">
		<xsl:apply-templates select="ed:DocumentId" mode="german"/>
		<xsl:apply-templates select="cd:IssuingMunicipality" mode="german"/>
		<xsl:apply-templates select="cd:CertifiedConcernedPersons" mode="german"/>
		<xsl:apply-templates select="ed:IssuanceDate" mode="german"/>
		<xsl:apply-templates select="ed:Observations" mode="german"/>
		<xsl:apply-templates select="ed:Terms" mode="german"/>
		<xsl:apply-templates select="cd:Timestamp" mode="german"/>
		<xsl:apply-templates select="cd:AcknowledgementStamp" mode="german"/>		
		<xsl:apply-templates select="cd:Address" mode="german"/>
	</cdg:Meldebescheinigung>
</xsl:template>

<xsl:template match="ed:DocumentId" mode="german">
	<edg:DokumentId>
		<xsl:apply-templates mode="german"/>
	</edg:DokumentId>
</xsl:template>

<xsl:template match="cd:IssuingMunicipality" mode="german">
	<cdg:AusstellendeGemeinde>
		<xsl:apply-templates mode="german"/>
	</cdg:AusstellendeGemeinde>
</xsl:template>

<xsl:template match="cd:CertifiedConcernedPersons" mode="german">
	<cdg:BescheinigteBeteiligten>
		<xsl:for-each select="ed:CitizenDetails">
			<edg:EinwohnerAngaben>
				<xsl:apply-templates select="ed:CitizenName" mode="german"/>
				<xsl:apply-templates select="ed:PreferredLanguages" mode="german"/>
				<xsl:apply-templates select="ed:ContactDetails" mode="german"/>
				<xsl:apply-templates select="ed:CitizenSex" mode="german"/>
				<xsl:apply-templates select="ed:CitizenMaritalStatus" mode="german"/>
				<xsl:apply-templates select="ed:CitizenBirthDetails" mode="german"/>
				<xsl:apply-templates select="ed:Nationality" mode="german"/>
				<xsl:apply-templates select="ed:IdentificationDetails" mode="german"/>
			</edg:EinwohnerAngaben>
		</xsl:for-each>
	</cdg:BescheinigteBeteiligten>
</xsl:template>

<xsl:template match="ed:CitizenName" mode="german">
	<edg:EinwohnerName>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</edg:EinwohnerName>
</xsl:template>

<xsl:template match="ed:PreferredLanguages" mode="german">
	<edg:BevorzugteSprachen>
		<xsl:apply-templates mode="german"/>
	</edg:BevorzugteSprachen>
</xsl:template>

<xsl:template match="ed:ContactDetails" mode="german">
	<edg:KontaktDetails>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</edg:KontaktDetails>
</xsl:template>

<xsl:template match="ed:CitizenSex" mode="german">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<edg:EinwohnerGeschlecht>
		  <xsl:choose>
                <xsl:when test="$sex = 'Male'">Männlich</xsl:when>
                <xsl:when test="$sex = 'Female'">Weiblich</xsl:when>
            </xsl:choose>
	</edg:EinwohnerGeschlecht>
</xsl:template>

<xsl:template match="ed:CitizenMaritalStatus" mode="german">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<edg:EinwohnerZivilstand>
		<xsl:choose>
                <xsl:when test="$status = 'Single'">Ledig</xsl:when>
                <xsl:when test="$status = 'Married'">Verheiratet</xsl:when>
                <xsl:when test="$status = 'Divorced'">Geschieden</xsl:when>
                <xsl:when test="$status = 'Widowed'">Verwitwet</xsl:when>
                <xsl:when test="$status = 'Separated'">GetrenntLebend</xsl:when>
        </xsl:choose>
	</edg:EinwohnerZivilstand>
</xsl:template>

<xsl:template match="ed:CitizenBirthDetails" mode="german">
	<edg:EinwohnerGeburtsdaten>
		<xsl:apply-templates select="ed:DateOfBirth" mode="german"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="ed:BirthActNumber" mode="german"/>
	</edg:EinwohnerGeburtsdaten>
</xsl:template>

<xsl:template match="ed:DateOfBirth" mode="german">
	<edg:Geburtsdatum>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</edg:Geburtsdatum>
</xsl:template>

<xsl:template match="ed:BirthActNumber" mode="german">
	<edg:GeburtsurkundeNummer>
		<xsl:apply-templates mode="german"/>
	</edg:GeburtsurkundeNummer>
</xsl:template>

<xsl:template match="ed:Nationality" mode="german">
	<edg:Nationalität>
		<xsl:apply-templates mode="german"/>
	</edg:Nationalität>
</xsl:template>

<xsl:template match="ed:IdentificationDetails" mode="german">
	<edg:Identifizierungsdaten>
		<xsl:apply-templates select="ed:IDCardNumber" mode="german"/>
		<xsl:apply-templates select="ed:PassportNumber" mode="german"/>
	</edg:Identifizierungsdaten>
</xsl:template>

<xsl:template match="ed:IDCardNumber" mode="german">
	<edg:PersonalausweisNummer>
		<xsl:apply-templates mode="german"/>
	</edg:PersonalausweisNummer>
</xsl:template>

<xsl:template match="ed:PassportNumber" mode="german">
	<edg:ReisepassNummer>
		<xsl:apply-templates mode="german"/>
	</edg:ReisepassNummer>
</xsl:template>

<xsl:template match="ed:IssuanceDate" mode="german">
	<edg:AusgestelltAm>
		<xsl:apply-templates mode="german"/>
	</edg:AusgestelltAm>
</xsl:template>

<xsl:template match="ed:Observations" mode="german">
	<edg:Bemerkungen>
		<xsl:apply-templates mode="german"/>
	</edg:Bemerkungen>
</xsl:template>

<xsl:template match="ed:Terms" mode="german">
	<edg:Gültigkeitszeitraum>
		<xsl:apply-templates mode="german"/>
	</edg:Gültigkeitszeitraum>
</xsl:template>

<xsl:template match="cd:Timestamp" mode="german">
	<cdg:Timestamp>
		<xsl:apply-templates mode="german"/>
	</cdg:Timestamp>
</xsl:template>

<xsl:template match="cd:AcknowledgementStamp" mode="german">
	<cdg:Bestätigungsstempel>
		<xsl:apply-templates mode="german"/>
	</cdg:Bestätigungsstempel>
</xsl:template>

<xsl:template match="cd:Address" mode="german">
	<cdg:Adresse>
		<xsl:apply-templates select="cbc:Postbox" mode="german"/>
		<xsl:apply-templates select="cbc:Floor" mode="german"/>
		<xsl:apply-templates select="cbc:Room" mode="german"/>
		<xsl:apply-templates select="cbc:StreetName" mode="german"/>
		<xsl:apply-templates select="cbc:AdditionalStreetName" mode="german"/>
		<xsl:apply-templates select="cbc:BuildingName" mode="german"/>
		<xsl:apply-templates select="cbc:BuildingNumber" mode="german"/>
		<xsl:apply-templates select="cbc:Department" mode="german"/>
		<xsl:apply-templates select="cbc:CityName" mode="german"/>
		<xsl:apply-templates select="cbc:PostalZone" mode="german"/>
		<xsl:apply-templates select="cbc:District" mode="german"/>
		<xsl:apply-templates select="cac:Country" mode="german"/>
		<xsl:apply-templates select="ed:Nucleus" mode="german"/>
		<xsl:apply-templates select="ed:StreetQualifier" mode="german"/>
		<xsl:apply-templates select="ed:Section" mode="german"/>
	</cdg:Adresse>
</xsl:template>

<xsl:template match="cbc:Postbox" mode="german">
	<edg:Postfach>
		<xsl:apply-templates mode="german"/>
	</edg:Postfach>
</xsl:template>

<xsl:template match="cbc:Floor" mode="german">
	<edg:Etage>
		<xsl:apply-templates mode="german"/>
	</edg:Etage>
</xsl:template>

<xsl:template match="cbc:Room" mode="german">
	<edg:Raum>
		<xsl:apply-templates mode="german"/>
	</edg:Raum>
</xsl:template>

<xsl:template match="cbc:StreetName" mode="german">
	<edg:Straße>
		<xsl:apply-templates mode="german"/>
	</edg:Straße>
</xsl:template>

<xsl:template match="cbc:AdditionalStreetName" mode="german">
	<edg:StraßennameZusatz>
		<xsl:apply-templates mode="german"/>
	</edg:StraßennameZusatz>
</xsl:template>

<xsl:template match="cbc:BuildingName" mode="german">
	<edg:GebäudeName>
		<xsl:apply-templates mode="german"/>
	</edg:GebäudeName>
</xsl:template>

<xsl:template match="cbc:BuildingNumber" mode="german">
	<edg:GebäudeNummer>
		<xsl:apply-templates mode="german"/>
	</edg:GebäudeNummer>
</xsl:template>

<xsl:template match="cbc:Department" mode="german">
	<edg:Abteilung>
		<xsl:apply-templates mode="german"/>
	</edg:Abteilung>
</xsl:template>

<xsl:template match="cbc:CityName" mode="german">
	<edg:Stadt>
		<xsl:apply-templates mode="german"/>
	</edg:Stadt>
</xsl:template>

<xsl:template match="cbc:PostalZone" mode="german">
	<edg:Postbezirk>
		<xsl:apply-templates mode="german"/>
	</edg:Postbezirk>
</xsl:template>

<xsl:template match="cbc:District" mode="german">
	<edg:Kreis>
		<xsl:apply-templates mode="german"/>
	</edg:Kreis>
</xsl:template>

<xsl:template match="cac:Country" mode="german">
	<edg:Land>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</edg:Land>
</xsl:template>

<xsl:template match="ed:Nucleus" mode="german">
	<edg:Nukleus>
		<xsl:apply-templates mode="german"/>
	</edg:Nukleus>
</xsl:template>

<xsl:template match="ed:StreetQualifier" mode="german">
	<edg:StraßeQualifizierer>
		<xsl:apply-templates mode="german"/>
	</edg:StraßeQualifizierer>
</xsl:template>

<xsl:template match="ed:Section" mode="german">
	<edg:Sektion>
		<xsl:apply-templates mode="german"/>
	</edg:Sektion>
</xsl:template>

</xsl:stylesheet>