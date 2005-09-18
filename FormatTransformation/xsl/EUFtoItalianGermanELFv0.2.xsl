<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cdg="http://www.emayor.org/BusinessDocumentG.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:edg="http://www.emayor.org/e-DocumentG.xsd"   xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:edi="http://www.emayor.org/e-DocumentI.xsd" xmlns:cdbi="http://www.emayor.org/BusinessDocumentBi.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >

<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="/">
	<xsl:apply-templates select="cd:ResidenceCertificationDocument" mode="italian"/>
</xsl:template>

<xsl:template match="cd:ResidenceCertificationDocument" mode="italian">
	<cdi:CertificatoDiResidenza xsi:schemaLocation="http://www.emayor.org/BusinessDocumentI.xsd
	../xsd/eMayor/BusinessDocument-v0.2i.xsd">
		<xsl:apply-templates select="ed:DocumentId" mode="italian"/>
		<xsl:apply-templates select="cd:IssuingMunicipality" mode="italian"/>
		<xsl:apply-templates select="cd:CertifiedConcernedPersons" mode="italian"/>
		<xsl:apply-templates select="ed:IssuanceDate" mode="italian"/>
		<xsl:apply-templates select="ed:Observations" mode="italian"/>
		<xsl:apply-templates select="ed:Terms" mode="italian"/>
		<xsl:apply-templates select="cd:Address" mode="italian"/>
	</cdi:CertificatoDiResidenza>
</xsl:template>

<xsl:template match="ed:DocumentId" mode="italian">
	<edi:IdDocumento>
		<xsl:apply-templates mode="italian"/>
	</edi:IdDocumento>
</xsl:template>

<xsl:template match="cd:IssuingMunicipality" mode="italian">
	<cdi:ComuneDiEmissione>
		<xsl:apply-templates mode="italian"/>
	</cdi:ComuneDiEmissione>
</xsl:template>

<xsl:template match="cd:CertifiedConcernedPersons" mode="italian">
	<cdi:IntestatariCertificati>
		<xsl:for-each select="ed:CitizenDetails">
			<edi:DettagliCittadino>
				<xsl:apply-templates select="ed:CitizenName" mode="italian"/>
				<xsl:apply-templates select="ed:PreferredLanguages" mode="italian"/>
				<xsl:apply-templates select="ed:ContactDetails" mode="italian"/>
				<xsl:apply-templates select="ed:CitizenSex" mode="italian"/>
				<xsl:apply-templates select="ed:CitizenMaritalStatus" mode="italian"/>
				<xsl:apply-templates select="ed:CitizenBirthDetails" mode="italian"/>
				<xsl:apply-templates select="ed:Nationality" mode="italian"/>
				<xsl:apply-templates select="ed:IdentificationDetails" mode="italian"/>
			</edi:DettagliCittadino>
		</xsl:for-each>
	</cdi:IntestatariCertificati>
</xsl:template>

<xsl:template match="ed:CitizenName" mode="italian">
	<edi:NomeCittadino>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</edi:NomeCittadino>
</xsl:template>

<!--
<xsl:template match="aapd:CitizenNameTitle">
	<edi:TitoloCittadino>
		<xsl:apply-Templates/>
	</edi:TitoloCittadino>
</xsl:template>

<xsl:template match="aapd:CitizenNameForename">
	<edi:PrefissoNomeCittadino>
		<xsl:apply-Templates/>
	</edi:PrefissoNomeCittadino>
</xsl:template>

<xsl:template match="aapd:CitizenNameSurname">
	<edi:CognomeCittadino>
		<xsl:apply-Templates/>
	</edi:CognomeCittadino>
</xsl:template>

<xsl:template match="aapd:CitizenNameSuffix">
	<edi:SuffissoNomeCittadino>
		<xsl:apply-Templates/>
	</edi:SuffissoNomeCittadino>
</xsl:template>

<xsl:template match="aapd:CitizenNameRequestedName">
<edi:CitizenNameRequestedName>
	<xsl:apply-Templates/>
</edi:CitizenNameRequestedName>
</xsl:template>
-->

<xsl:template match="ed:PreferredLanguages" mode="italian">
	<edi:LinguePreferite>
		<xsl:apply-templates mode="italian"/>
	</edi:LinguePreferite>
</xsl:template>

<xsl:template match="ed:ContactDetails" mode="italian">
	<edi:DettagliContatto>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone" />
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</edi:DettagliContatto>
</xsl:template>

<!--
<xsl:template match="aapd:Email">
	<edi:Email>
		<xsl:apply-templates select="aapd:EmailAddress"/>
	</edi:Email>
</xsl:template>

<xsl:template match="aapd:EmailAddress">
	<edi:IndirizzoEmail>
		<xsl:apply-templates/>
	</edi:IndirizzoEmail>
</xsl:template>

<xsl:template match="aapd:Telephone">
	<edi:Telefono>
		<xsl:apply-templates select="aapd:TelNational Number"/>
		<xsl:apply-templates select="aapd:TelExtensionNumber"/>
		<xsl:apply-templates select="aapd:TelCountryCode"/>
	</edi:Telefono>
</xsl:template>

<xsl:template match="aapd:TelNationalNumber">
	<edi:NumTelefonoNazionale>
		<xsl:apply-templates/>
	</edi:NumTelefonoNazionale>
</xsl:template>

<xsl:template match="aapd:TelExtensionNumber">
	<edi:NumTelefonoInterno>
		<xsl:apply-templates/>
	</edi:NumTelefonoInterno>
</xsl:template>

<xsl:template match="aapd:TelCountryCode">
	<edi:PrefissoInternazionaleTel>
		<xsl:apply-templates/>
	</edi:PrefissoInternazionaleTel>
</xsl:template>

<xsl:template match="aapd:Fax">
	<edi:Fax>
		<xsl:apply-templates select="aapd:FaxNational Number"/>
		<xsl:apply-templates select="aapd:FaxExtensionNumber"/>
		<xsl:apply-templates select="aapd:FaxCountryCode"/>
	</edi:Fax>
</xsl:template>

<xsl:template match="aapd:FaxNationalNumber">
	<edi:NumFaxNazionale>
		<xsl:apply-templates/>
	</edi:NumFaxNazionale>
</xsl:template>

<xsl:template match="aapd:FaxExtensionNumber">
	<edi:NumFaxInterno>
		<xsl:apply-templates/>
	</edi:NumFaxInterno>
</xsl:template>

<xsl:template match="aapd:FaxCountryCode">
	<edi:PrefissoInternazionaleFax>
		<xsl:apply-templates/>
	</edi:PrefissoInternazionaleFax>
</xsl:template>
-->

<xsl:template match="ed:CitizenSex" mode="italian">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<edi:SessoCittadino>
		  <xsl:choose>
                <xsl:when test="$sex = 'Male'">Maschio</xsl:when>
                <xsl:when test="$sex = 'Female'">Femmina</xsl:when>
            </xsl:choose>
	</edi:SessoCittadino>
</xsl:template>

<xsl:template match="ed:CitizenMaritalStatus" mode="italian">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<edi:StatoCivileCittadino>
		<xsl:choose>
                <xsl:when test="$status = 'Single'">Celibe/Nubile</xsl:when>
                <xsl:when test="$status = 'Married'">Coniugato/a</xsl:when>
                <xsl:when test="$status = 'Divorced'">Divorziato/a</xsl:when>
                <xsl:when test="$status = 'Widowed'">Vedovo/a</xsl:when>
                <xsl:when test="$status = 'Separated'">Separato/a</xsl:when>
        </xsl:choose>
	</edi:StatoCivileCittadino>
</xsl:template>

<xsl:template match="ed:CitizenBirthDetails" mode="italian">
	<edi:DettagliNascitaCittadino>
		<xsl:apply-templates select="ed:DateOfBirth" mode="italian"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="ed:BirthActNumber" mode="italian"/>
	</edi:DettagliNascitaCittadino>
</xsl:template>

<xsl:template match="ed:DateOfBirth" mode="italian">
	<edi:DataDiNascita>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</edi:DataDiNascita>
</xsl:template>

<!--
<xsl:template match="aapd:BirthDate">
	<edi:DataDiNascita>
		<xsl:apply-templates/>
	</edi:DataDiNascita>
</xsl:template>

<xsl:template match="aapd:VerifiedBy">
	<edi:VerificatoDa>
		<xsl:apply-templates/>
	</edi:VerificatoDa>
</xsl:template>

<xsl:template match="cbc:CityName">
	<edi:NomeCittà>
		<xsl:apply-templates/>
	</edi:NomeCittà>
</xsl:template>
-->

<xsl:template match="ed:BirthActNumber" mode="italian">
	<edi:NumeroAttoNascita>
		<xsl:apply-templates mode="italian"/>
	</edi:NumeroAttoNascita>
</xsl:template>

<xsl:template match="ed:Nationality" mode="italian">
	<edi:Nazionalità>
		<xsl:apply-templates mode="italian"/>
	</edi:Nazionalità>
</xsl:template>

<xsl:template match="ed:IdentificationDetails" mode="italian">
	<edi:DettagliIdentificazione>
		<xsl:apply-templates select="ed:IDCardNumber" mode="italian"/>
		<xsl:apply-templates select="ed:PassportNumber" mode="italian"/>
	</edi:DettagliIdentificazione>
</xsl:template>

<xsl:template match="ed:IDCardNumber" mode="italian">
	<edi:NumeroCartaIdentità>
		<xsl:apply-templates mode="italian"/>
	</edi:NumeroCartaIdentità>
</xsl:template>

<xsl:template match="ed:PassportNumber" mode="italian">
	<edi:NumeroPassaporto>
		<xsl:apply-templates mode="italian"/>
	</edi:NumeroPassaporto>
</xsl:template>

<xsl:template match="ed:IssuanceDate" mode="italian">
	<edi:DataEmissione>
		<xsl:apply-templates mode="italian"/>
	</edi:DataEmissione>
</xsl:template>

<xsl:template match="ed:Observations" mode="italian">
	<edi:Osservazioni>
		<xsl:apply-templates mode="italian"/>
	</edi:Osservazioni>
</xsl:template>

<xsl:template match="ed:Terms" mode="italian">
	<edi:Termini>
		<xsl:apply-templates mode="italian"/>
	</edi:Termini>
</xsl:template>

<xsl:template match="cd:Address" mode="italian">
	<cdi:Indirizzo>
		<xsl:apply-templates select="cbc:Postbox" mode="italian"/>
		<xsl:apply-templates select="cbc:Floor" mode="italian"/>
		<xsl:apply-templates select="cbc:Room" mode="italian"/>
		<xsl:apply-templates select="cbc:StreetName" mode="italian"/>
		<xsl:apply-templates select="cbc:AdditionalStreetName" mode="italian"/>
		<xsl:apply-templates select="cbc:BuildingName" mode="italian"/>
		<xsl:apply-templates select="cbc:BuildingNumber" mode="italian"/>
		<xsl:apply-templates select="cbc:Department" mode="italian"/>
		<xsl:apply-templates select="cbc:CityName" mode="italian"/>
		<xsl:apply-templates select="cbc:PostalZone" mode="italian"/>
		<xsl:apply-templates select="cbc:District" mode="italian"/>
		<xsl:apply-templates select="cac:Country" mode="italian"/>
		<xsl:apply-templates select="ed:Nucleus" mode="italian"/>
		<xsl:apply-templates select="ed:StreetQualifier" mode="italian"/>
		<xsl:apply-templates select="ed:Section" mode="italian"/>
	</cdi:Indirizzo>
</xsl:template>

<xsl:template match="cbc:Postbox" mode="italian">
	<edi:CasellaPostale>
		<xsl:apply-templates mode="italian"/>
	</edi:CasellaPostale>
</xsl:template>

<xsl:template match="cbc:Floor" mode="italian">
	<edi:Piano>
		<xsl:apply-templates mode="italian"/>
	</edi:Piano>
</xsl:template>

<xsl:template match="cbc:Room" mode="italian">
	<edi:Stanza>
		<xsl:apply-templates mode="italian"/>
	</edi:Stanza>
</xsl:template>

<xsl:template match="cbc:StreetName" mode="italian">
	<edi:NomeVia>
		<xsl:apply-templates mode="italian"/>
	</edi:NomeVia>
</xsl:template>

<xsl:template match="cbc:AdditionalStreetName" mode="italian">
	<edi:NomeViaAggiuntivo>
		<xsl:apply-templates mode="italian"/>
	</edi:NomeViaAggiuntivo>
</xsl:template>

<xsl:template match="cbc:BuildingName" mode="italian">
	<edi:NomeEdificio>
		<xsl:apply-templates mode="italian"/>
	</edi:NomeEdificio>
</xsl:template>

<xsl:template match="cbc:BuildingNumber" mode="italian">
	<edi:NumeroCivico>
		<xsl:apply-templates mode="italian"/>
	</edi:NumeroCivico>
</xsl:template>

<xsl:template match="cbc:Department" mode="italian">
	<edi:Dipartimento>
		<xsl:apply-templates mode="italian"/>
	</edi:Dipartimento>
</xsl:template>

<xsl:template match="cbc:CityName" mode="italian">
	<edi:NomeCittà>
		<xsl:apply-templates mode="italian"/>
	</edi:NomeCittà>
</xsl:template>

<xsl:template match="cbc:PostalZone" mode="italian">
	<edi:CAP>
		<xsl:apply-templates mode="italian"/>
	</edi:CAP>
</xsl:template>

<!--
<xsl:template match="ed:CountrySubentity" mode="italian">
	<edi:CountrySubentity>
		<xsl:apply-templates mode="italian"/>
	</edi:CounrtySubentity>
</xsl:template>
-->

<xsl:template match="cbc:District" mode="italian">
	<edi:Provincia>
		<xsl:apply-templates mode="italian"/>
	</edi:Provincia>
</xsl:template>

<xsl:template match="cac:Country" mode="italian">
	<edi:Nazione>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</edi:Nazione>
</xsl:template>

<!--
<xsl:template match="cac:IdentificationCode">
	<edi:CodicePaese>
		<xsl:apply-templates/>
	</edi:CodicePaese>
</xsl:template>

<xsl:template match="cbc:Name">
	<edi:Nome>
		<!xsl:apply-templates/>
	</edi:Nome>
</xsl:template>
-->

<xsl:template match="ed:Nucleus" mode="italian">
	<edi:Quartiere>
		<xsl:apply-templates mode="italian"/>
	</edi:Quartiere>
</xsl:template>

<xsl:template match="ed:StreetQualifier" mode="italian">
	<edi:IdentificativoVia>
		<xsl:apply-templates mode="italian"/>
	</edi:IdentificativoVia>
</xsl:template>

<xsl:template match="ed:Section" mode="italian">
	<edi:Sezione>
		<xsl:apply-templates mode="italian"/>
	</edi:Sezione>
</xsl:template>


<xsl:template match="/">
	<xsl:apply-templates select="cd:ResidenceCertificationDocument" mode="german"/>
</xsl:template>

<xsl:template match="cd:ResidenceCertificationDocument" mode="german">
	<cdg:Meldebescheinigung xsi:schemaLocation="http://www.emayor.org/BusinessDocumentG.xsd ../xsd/emayor/BusinessDocument-v0.2g.xsd">
		<xsl:apply-templates select="ed:DocumentId" mode="german"/>
		<xsl:apply-templates select="cd:IssuingMunicipality" mode="german"/>
		<xsl:apply-templates select="cd:CertifiedConcernedPersons" mode="german"/>
		<xsl:apply-templates select="ed:IssuanceDate" mode="german"/>
		<xsl:apply-templates select="ed:Observations" mode="german"/>
		<xsl:apply-templates select="ed:Terms" mode="german"/>
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


 <xsl:output method="xml" indent="yes"/>
 
<xsl:template match="/">
	<cdbi:CertificatoDiResidenza-Meldebescheinigung xsi:schemaLocation="http://www.emayor.org/BusinessDocumentBi.xsd
	../xsd/eMayor/BusinessDocument-v0.1bi.xsd">
			<xsl:apply-templates mode="italian" select="cd:ResidenceCertificationDocument"/>
			<xsl:apply-templates mode="german" select="cd:ResidenceCertificationDocument"/>
	</cdbi:CertificatoDiResidenza-Meldebescheinigung>
</xsl:template>

</xsl:stylesheet>