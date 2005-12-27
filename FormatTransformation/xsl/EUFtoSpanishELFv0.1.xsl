<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cds="http://www.emayor.org/BusinessDocumentS.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:eds="http://www.emayor.org/e-DocumentS.xsd" xmlns:ds="http://www.w3.org/2000/09/xmldsig#" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:output method="xml" indent="yes"/>
  
<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="cd:ResidenceCertificationDocument">
	<cds:CertificadoInscripcionPadronal xsi:schemaLocation="http://www.emayor.org/BusinessDocumentS.xsd ../emayor/BusinessDocument-v0.2s.xsd">
		<xsl:apply-templates select="ed:DocumentId"/>
		<xsl:apply-templates select="cd:IssuingMunicipality"/>	
		<xsl:apply-templates select="cd:CertifiedConcernedPersons"/>
		<xsl:apply-templates select="ed:IssuanceDate"/>
		<xsl:apply-templates select="ed:Observations"/>
		<xsl:apply-templates select="ed:Terms"/>
		<xsl:apply-templates select="cd:Timestamp"/>
		<xsl:apply-templates select="cd:AcknowledgementStamp"/>
		<xsl:apply-templates select="cd:Address"/>
	</cds:CertificadoInscripcionPadronal>
</xsl:template>

<xsl:template match="ed:DocumentId">
	<eds:IdDocumento>
		<xsl:apply-templates/>
	</eds:IdDocumento>
</xsl:template>

<xsl:template match="cd:IssuingMunicipality">
	<cds:AyuntamientoEmisor>
		<xsl:apply-templates/>
	</cds:AyuntamientoEmisor>
</xsl:template>

<xsl:template match="cd:CertifiedConcernedPersons">
	<cds:PersonasInscritasCertificado>
		<xsl:for-each select="ed:CitizenDetails">
			<eds:DatosCiudadano>
				<xsl:apply-templates select="ed:CitizenName"/>
				<xsl:apply-templates select="ed:PreferredLanguages"/>
				<xsl:apply-templates select="ed:ContactDetails"/>
				<xsl:apply-templates select="ed:CitizenSex"/>
				<xsl:apply-templates select="ed:CitizenMaritalStatus"/>
				<xsl:apply-templates select="ed:CitizenBirthDetails"/>
				<xsl:apply-templates select="ed:Nationality"/>
				<xsl:apply-templates select="ed:IdentificationDetails"/>
			</eds:DatosCiudadano>
		</xsl:for-each>
	</cds:PersonasInscritasCertificado>
</xsl:template>

<xsl:template match="ed:CitizenName">
	<eds:NombreCompletoCiudadano>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</eds:NombreCompletoCiudadano>
</xsl:template>

<xsl:template match="ed:PreferredLanguages">
	<eds:IdiomasPreferidos>
		<xsl:apply-templates/>
	</eds:IdiomasPreferidos>
</xsl:template>

<xsl:template match="ed:ContactDetails">
	<eds:DatosContacto>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</eds:DatosContacto>
</xsl:template>

<xsl:template match="ed:CitizenSex">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<eds:SexoCiudadano>
		  <xsl:choose>
                <xsl:when test="$sex = 'Male'">Hombre</xsl:when>
                <xsl:when test="$sex = 'Female'">Mujer</xsl:when>
            </xsl:choose>
	</eds:SexoCiudadano>
</xsl:template>

<xsl:template match="ed:CitizenMaritalStatus">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<eds:EstadoCivilCiudadano>
		<xsl:choose>
                <xsl:when test="$status = 'Single'">Soltero/a</xsl:when>
                <xsl:when test="$status = 'Married'">Casado/a</xsl:when>
                <xsl:when test="$status = 'Divorced'">Divorciado/a</xsl:when>
                <xsl:when test="$status = 'Widowed'">Viudo/a</xsl:when>
                <xsl:when test="$status = 'Separated'">Separado/a</xsl:when>
        </xsl:choose>
	</eds:EstadoCivilCiudadano>
</xsl:template>

<xsl:template match="ed:CitizenBirthDetails">
	<eds:DatosNacimientoCiudadano>
		<xsl:apply-templates select="ed:DateOfBirth"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="ed:BirthActNumber"/>
	</eds:DatosNacimientoCiudadano>
</xsl:template>

<xsl:template match="ed:DateOfBirth">
	<eds:FechaNacimiento>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</eds:FechaNacimiento>
</xsl:template>

<xsl:template match="ed:BirthActNumber">
	<eds:NumeroCertificadoNacimiento>
		<xsl:apply-templates/>
	</eds:NumeroCertificadoNacimiento>
</xsl:template>

<xsl:template match="ed:Nationality">
	<eds:Nacionalidad>
		<xsl:apply-templates/>
	</eds:Nacionalidad>
</xsl:template>

<xsl:template match="ed:IdentificationDetails">
	<eds:DatosIdentificativos>
		<xsl:apply-templates select="ed:IDCardNumber"/>
		<xsl:apply-templates select="ed:PassportNumber"/>
	</eds:DatosIdentificativos>
</xsl:template>

<xsl:template match="ed:IDCardNumber">
	<eds:DocumentoIdentidad>
		<xsl:apply-templates/>
	</eds:DocumentoIdentidad>
</xsl:template>

<xsl:template match="ed:PassportNumber">
	<eds:NumeroPasaporte>
		<xsl:apply-templates/>
	</eds:NumeroPasaporte>
</xsl:template>

<xsl:template match="ed:IssuanceDate">
	<eds:FechaExpedicion>
		<xsl:apply-templates/>
	</eds:FechaExpedicion>
</xsl:template>

<xsl:template match="ed:Observations">
	<eds:Observaciones>
		<xsl:apply-templates/>
	</eds:Observaciones>
</xsl:template>

<xsl:template match="ed:Terms">
	<eds:Condiciones>
		<xsl:apply-templates/>
	</eds:Condiciones>
</xsl:template>

<xsl:template match="cd:Timestamp">
	<cds:Timestamp>
		<xsl:apply-templates/>
	</cds:Timestamp>
</xsl:template>

<xsl:template match="cd:AcknowledgementStamp">
	<cds:AcuseDeRecibo>
		<xsl:apply-templates/>
	</cds:AcuseDeRecibo>
</xsl:template>

<xsl:template match="cd:Address">
	<cds:Direccion>
		<xsl:apply-templates select="cbc:Postbox"/>
		<xsl:apply-templates select="cbc:Floor"/>
		<xsl:apply-templates select="cbc:Room"/>
		<xsl:apply-templates select="cbc:StreetName"/>
		<xsl:apply-templates select="cbc:AdditionalStreetName"/>
		<xsl:apply-templates select="cbc:BuildingName"/>
		<xsl:apply-templates select="cbc:BuildingNumber"/>
		<xsl:apply-templates select="cbc:Department"/>
		<xsl:apply-templates select="cbc:CityName"/>
		<xsl:apply-templates select="cbc:PostalZone"/>
		<xsl:apply-templates select="cbc:District"/>
		<xsl:apply-templates select="cac:Country"/>
		<xsl:apply-templates select="ed:Nucleus"/>
		<xsl:apply-templates select="ed:StreetQualifier"/>
		<xsl:apply-templates select="ed:Section"/>
		<xsl:apply-templates select="ed:RegistrationSheet"/>
	</cds:Direccion>
</xsl:template>

<xsl:template match="cbc:Postbox">
	<eds:Buzon>
		<xsl:apply-templates/>
	</eds:Buzon>
</xsl:template>

<xsl:template match="cbc:Floor">
	<eds:Piso>
		<xsl:apply-templates/>
	</eds:Piso>
</xsl:template>

<xsl:template match="cbc:Room">
	<eds:Puerta>
		<xsl:apply-templates/>
	</eds:Puerta>
</xsl:template>

<xsl:template match="cbc:StreetName">
	<eds:NombreVia>
		<xsl:apply-templates/>
	</eds:NombreVia>
</xsl:template>

<xsl:template match="cbc:AdditionalStreetName">
	<eds:NombreViaAdicional>
		<xsl:apply-templates/>
	</eds:NombreViaAdicional>
</xsl:template>

<xsl:template match="cbc:BuildingName">
	<eds:NombreEdificio>
		<xsl:apply-templates/>
	</eds:NombreEdificio>
</xsl:template>

<xsl:template match="cbc:BuildingNumber">
	<eds:NumeroEdificio>
		<xsl:apply-templates/>
	</eds:NumeroEdificio>
</xsl:template>

<xsl:template match="cbc:Department">
	<eds:Departamento>
		<xsl:apply-templates/>
	</eds:Departamento>
</xsl:template>

<xsl:template match="cbc:CityName">
	<eds:NombreCiudad>
		<xsl:apply-templates/>
	</eds:NombreCiudad>
</xsl:template>

<xsl:template match="cbc:PostalZone">
	<eds:CodigoPostal>
		<xsl:apply-templates/>
	</eds:CodigoPostal>
</xsl:template>

<xsl:template match="cbc:District">
	<eds:Distrito>
		<xsl:apply-templates/>
	</eds:Distrito>
</xsl:template>

<xsl:template match="cac:Country">
	<eds:Pais>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</eds:Pais>
</xsl:template>

<xsl:template match="ed:Nucleus">
	<eds:Nucleo>
		<xsl:apply-templates/>
	</eds:Nucleo>
</xsl:template>

<xsl:template match="ed:StreetQualifier">
	<eds:TipoVia>
		<xsl:apply-templates/>
	</eds:TipoVia>
</xsl:template>

<xsl:template match="ed:Section">
	<eds:Seccion>
		<xsl:apply-templates/>
	</eds:Seccion>
</xsl:template>

<xsl:template match="ed:RegistrationSheet">
	<eds:HojaInscripcion>
		<xsl:apply-templates/>
	</eds:HojaInscripcion>
</xsl:template>

</xsl:stylesheet>