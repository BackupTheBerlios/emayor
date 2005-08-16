<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:edi="http://www.emayor.org/e-DocumentI.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:output method="xml" indent="yes"/>
  
<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="cdi:CertificatoDiResidenza">
	<cd:ResidenceCertificationDocument xsi:schemaLocation="http://www.emayor.org/BusinessDocument.xsd ../xsd/emayor/BusinessDocument-v0.2.xsd">
		<xsl:apply-templates select="edi:IdDocumento"/>
		<xsl:apply-templates select="cdi:ComuneDiEmissione"/>
		<xsl:apply-templates select="cdi:IntestatariCertificati"/>
		<xsl:apply-templates select="edi:DataEmissione"/>
		<xsl:apply-templates select="edi:Osservazioni"/>
		<xsl:apply-templates select="edi:Termini"/>
		<xsl:apply-templates select="cdi:Indirizzo"/>
	</cd:ResidenceCertificationDocument>
</xsl:template>

<xsl:template match="edi:IdDocumento">
	<ed:DocumentId>
		<xsl:apply-templates/>
	</ed:DocumentId>
</xsl:template>

<xsl:template match="cdi:ComuneDiEmissione">
	<cd:IssuingMunicipality>
		<xsl:apply-templates/>
	</cd:IssuingMunicipality>
</xsl:template>

<xsl:template match="cdi:IntestatariCertificati">
	<cd:CertifiedConcernedPersons>
		<xsl:for-each select="edi:DettagliCittadino">
			<ed:CitizenDetails>
				<xsl:apply-templates select="edi:NomeCittadino"/>
				<xsl:apply-templates select="edi:LinguePreferite"/>
				<xsl:apply-templates select="edi:DettagliContatto"/>
				<xsl:apply-templates select="edi:SessoCittadino"/>
				<xsl:apply-templates select="edi:StatoCivileCittadino"/>
				<xsl:apply-templates select="edi:DettagliNascitaCittadino"/>
				<xsl:apply-templates select="edi:Nazionalità"/>
				<xsl:apply-templates select="edi:DettagliIdentificazione"/>
			</ed:CitizenDetails>
		</xsl:for-each>
	</cd:CertifiedConcernedPersons>
</xsl:template>

<xsl:template match="edi:NomeCittadino">
	<ed:CitizenName>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</ed:CitizenName>
</xsl:template>

<xsl:template match="edi:LinguePreferite">
	<ed:PreferredLanguages>
		<xsl:apply-templates/>
	</ed:PreferredLanguages>
</xsl:template>

<xsl:template match="edi:DettagliContatto">
	<ed:ContactDetails>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</ed:ContactDetails>
</xsl:template>

<xsl:template match="edi:SessoCittadino">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenSex>
		  <xsl:choose>
                <xsl:when test="$sex = 'Maschio'">Male</xsl:when>
                <xsl:when test="$sex = 'Femmina'">Female</xsl:when>
            </xsl:choose>
	</ed:CitizenSex>
</xsl:template>

<xsl:template match="edi:StatoCivileCittadino">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenMaritalStatus>
		<xsl:choose>
                <xsl:when test="$status = 'Celibe/Nubile'">Single</xsl:when>
                <xsl:when test="$status = 'Coniugato/a'">Married</xsl:when>
                <xsl:when test="$status = 'Divorziato/a'">Divorced</xsl:when>
                <xsl:when test="$status = 'Vedovo/a'">Widowed</xsl:when>
                <xsl:when test="$status = 'Separato/a'">Separated</xsl:when>
        </xsl:choose>
	</ed:CitizenMaritalStatus>
</xsl:template>

<xsl:template match="edi:DettagliNascitaCittadino">
	<ed:CitizenBirthDetails>
		<xsl:apply-templates select="edi:DataDiNascita"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="edi:NumeroAttoNascita"/>
	</ed:CitizenBirthDetails>
</xsl:template>

<xsl:template match="edi:DataDiNascita">
	<ed:DateOfBirth>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</ed:DateOfBirth>
</xsl:template>

<xsl:template match="edi:NumeroAttoNascita">
	<ed:BirthActNumber>
		<xsl:apply-templates/>
	</ed:BirthActNumber>
</xsl:template>

<xsl:template match="edi:Nazionalità">
	<ed:Nationality>
		<xsl:apply-templates/>
	</ed:Nationality>
</xsl:template>

<xsl:template match="edi:DettagliIdentificazione">
	<ed:IdentificationDetails>
		<xsl:apply-templates select="edi:NumeroCartaIdentità"/>
		<xsl:apply-templates select="edi:NumeroPassaporto"/>
	</ed:IdentificationDetails>
</xsl:template>

<xsl:template match="edi:NumeroCartaIdentità">
	<ed:IDCardNumber>
		<xsl:apply-templates/>
	</ed:IDCardNumber>
</xsl:template>

<xsl:template match="edi:NumeroPassaporto">
	<ed:PassportNumber>
		<xsl:apply-templates/>
	</ed:PassportNumber>
</xsl:template>

<xsl:template match="edi:DataEmissione">
	<ed:IssuanceDate>
		<xsl:apply-templates/>
	</ed:IssuanceDate>
</xsl:template>

<xsl:template match="edi:Osservazioni">
	<ed:Observations>
		<xsl:apply-templates/>
	</ed:Observations>
</xsl:template>

<xsl:template match="edi:Termini">
	<ed:Terms>
		<xsl:apply-templates/>
	</ed:Terms>
</xsl:template>

<xsl:template match="cdi:Indirizzo">
	<cd:Address>
		<xsl:apply-templates select="edi:CasellaPostale"/>
		<xsl:apply-templates select="edi:Piano"/>
		<xsl:apply-templates select="edi:Stanza"/>
		<xsl:apply-templates select="edi:NomeVia"/>
		<xsl:apply-templates select="edi:NomeViaAggiuntivo"/>
		<xsl:apply-templates select="edi:NomeEdificio"/>
		<xsl:apply-templates select="edi:NumeroCivico"/>
		<xsl:apply-templates select="edi:Dipartimento"/>
		<xsl:apply-templates select="edi:NomeCittà"/>
		<xsl:apply-templates select="edi:CAP"/>
		<xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/>
		<xsl:apply-templates select="edi:Provincia"/>
		<xsl:apply-templates select="edi:Nazione"/>
		<xsl:apply-templates select="edi:Quartiere"/>
		<xsl:apply-templates select="edi:IdentificativoVia"/>
		<xsl:apply-templates select="edi:Sezione"/>
	</cd:Address>
</xsl:template>

<xsl:template match="edi:CasellaPostale">
	<cbc:Postbox>
		<xsl:apply-templates/>
	</cbc:Postbox>
</xsl:template>

<xsl:template match="edi:Piano">
	<cbc:Floor>
		<xsl:apply-templates/>
	</cbc:Floor>
</xsl:template>

<xsl:template match="edi:Stanza">
	<cbc:Room>
		<xsl:apply-templates/>
	</cbc:Room>
</xsl:template>

<xsl:template match="edi:NomeVia">
	<cbc:StreetName>
		<xsl:apply-templates/>
	</cbc:StreetName>
</xsl:template>

<xsl:template match="edi:NomeViaAggiuntivo">
	<cbc:AdditionalStreetName>
		<xsl:apply-templates/>
	</cbc:AdditionalStreetName>
</xsl:template>

<xsl:template match="edi:NomeEdificio">
	<cbc:BuildingName>
		<xsl:apply-templates/>
	</cbc:BuildingName>
</xsl:template>

<xsl:template match="edi:NumeroCivico">
	<cbc:BuildingNumber>
		<xsl:apply-templates/>
	</cbc:BuildingNumber>
</xsl:template>

<xsl:template match="edi:Dipartimento">
	<cbc:Department>
		<xsl:apply-templates/>
	</cbc:Department>
</xsl:template>

<xsl:template match="edi:NomeCittà">
	<cbc:CityName>
		<xsl:apply-templates/>
	</cbc:CityName>
</xsl:template>

<xsl:template match="edi:CAP">
	<cbc:PostalZone>
		<xsl:apply-templates/>
	</cbc:PostalZone>
</xsl:template>

<xsl:template match="edi:Provincia">
	<cbc:District>
		<xsl:apply-templates/>
	</cbc:District>
</xsl:template>

<xsl:template match="edi:Nazione">
	<cac:Country>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</cac:Country>
</xsl:template>

<xsl:template match="edi:Quartiere">
	<ed:Nucleus>
		<xsl:apply-templates/>
	</ed:Nucleus>
</xsl:template>

<xsl:template match="edi:IdentificativoVia">
	<ed:StreetQualifier>
		<xsl:apply-templates/>
	</ed:StreetQualifier>
</xsl:template>

<xsl:template match="edi:Sezione">
	<ed:Section>
		<xsl:apply-templates/>
	</ed:Section>
</xsl:template>

</xsl:stylesheet>