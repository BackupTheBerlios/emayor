<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:ed="http://www.emayor.org/e-Document.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cdi="http://www.emayor.org/BusinessDocumentS.xsd" xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0" xmlns:edi="http://www.emayor.org/e-DocumentS.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <xsl:output method="xml" indent="yes"/>
  
<xsl:template mode="copy-no-ns" match="*">
  <xsl:element name="{name(.)}" namespace="{namespace-uri(.)}">
    <xsl:copy-of select="@*"/>
    <xsl:apply-templates mode="copy-no-ns"/>
  </xsl:element>
</xsl:template>

<xsl:template match="cdi:CertificadoInscripcionPadronal">
	<cd:ResidenceCertificationDocument xsi:schemaLocation="http://www.emayor.org/BusinessDocument.xsd ../emayor/BusinessDocument-v0.3.xsd">
		<xsl:apply-templates select="edi:IdDocumento"/>
		<xsl:apply-templates select="cdi:AyuntamientoEmisor"/>
		<xsl:apply-templates select="cdi:PersonasInscritasCertificado"/>
		<xsl:apply-templates select="edi:FechaExpedicion"/>
		<xsl:apply-templates select="edi:Observaciones"/>
		<xsl:apply-templates select="edi:Condiciones"/>
		<xsl:apply-templates select="cdi:Timestamp"/>
		<xsl:apply-templates select="cdi:AcuseDeRecibo"/>		
		<xsl:apply-templates select="cdi:Direccion"/>
	</cd:ResidenceCertificationDocument>
</xsl:template>

<xsl:template match="edi:IdDocumento">
	<ed:DocumentId>
		<xsl:apply-templates/>
	</ed:DocumentId>
</xsl:template>

<xsl:template match="cdi:AyuntamientoEmisor">
	<cd:IssuingMunicipality>
		<xsl:apply-templates/>
	</cd:IssuingMunicipality>
</xsl:template>

<xsl:template match="cdi:PersonasInscritasCertificado">
	<cd:CertifiedConcernedPersons>
		<xsl:for-each select="edi:DatosCiudadano">
			<ed:CitizenDetails>
				<xsl:apply-templates select="edi:NombreCompletoCiudadano"/>
				<xsl:apply-templates select="edi:IdiomasPreferidos"/>
				<xsl:apply-templates select="edi:DatosContacto"/>
				<xsl:apply-templates select="edi:SexoCiudadano"/>
				<xsl:apply-templates select="edi:EstadoCivilCiudadano"/>
				<xsl:apply-templates select="edi:DatosNacimientoCiudadano"/>
				<xsl:apply-templates select="edi:Nacionalidad"/>
				<xsl:apply-templates select="edi:DatosIdentificativos"/>
			</ed:CitizenDetails>
		</xsl:for-each>
	</cd:CertifiedConcernedPersons>
</xsl:template>

<xsl:template match="edi:NombreCompletoCiudadano">
	<ed:CitizenName>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/>
	</ed:CitizenName>
</xsl:template>

<xsl:template match="edi:IdiomasPreferidos">
	<ed:PreferredLanguages>
		<xsl:apply-templates/>
	</ed:PreferredLanguages>
</xsl:template>

<xsl:template match="edi:DatosContacto">
	<ed:ContactDetails>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Telephone"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:Fax"/>
	</ed:ContactDetails>
</xsl:template>

<xsl:template match="edi:SexoCiudadano">
	<xsl:variable name="sex">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenSex>
		  <xsl:choose>
                <xsl:when test="$sex = 'Hombre'">Male</xsl:when>
                <xsl:when test="$sex = 'Mujer'">Female</xsl:when>
            </xsl:choose>
	</ed:CitizenSex>
</xsl:template>

<xsl:template match="edi:EstadoCivilCiudadano">
	<xsl:variable name="status">
		 <xsl:value-of select="."/>
	</xsl:variable> 
	<ed:CitizenMaritalStatus>
		<xsl:choose>
                <xsl:when test="$status = 'Soltero/a'">Single</xsl:when>
                <xsl:when test="$status = 'Casado/a'">Married</xsl:when>
                <xsl:when test="$status = 'Divorciado/a'">Divorced</xsl:when>
                <xsl:when test="$status = 'Viudo/a'">Widowed</xsl:when>
                <xsl:when test="$status = 'Separado/a'">Separated</xsl:when>
        </xsl:choose>
	</ed:CitizenMaritalStatus>
</xsl:template>

<xsl:template match="edi:DatosNacimientoCiudadano">
	<ed:CitizenBirthDetails>
		<xsl:apply-templates select="edi:FechaNacimiento"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/>
		<xsl:apply-templates select="edi:NumeroCertificadoNacimiento"/>
	</ed:CitizenBirthDetails>
</xsl:template>

<xsl:template match="edi:FechaNacimiento">
	<ed:DateOfBirth>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>
	</ed:DateOfBirth>
</xsl:template>

<xsl:template match="edi:NumeroCertificadoNacimiento">
	<ed:BirthActNumber>
		<xsl:apply-templates/>
	</ed:BirthActNumber>
</xsl:template>

<xsl:template match="edi:Nacionalidad">
	<ed:Nationality>
		<xsl:apply-templates/>
	</ed:Nationality>
</xsl:template>

<xsl:template match="edi:DatosIdentificativos">
	<ed:IdentificationDetails>
		<xsl:apply-templates select="edi:DocumentoIdentidad"/>
		<xsl:apply-templates select="edi:NumeroPasaporte"/>
	</ed:IdentificationDetails>
</xsl:template>

<xsl:template match="edi:DocumentoIdentidad">
	<ed:IDCardNumber>
		<xsl:apply-templates/>
	</ed:IDCardNumber>
</xsl:template>

<xsl:template match="edi:NumeroPasaporte">
	<ed:PassportNumber>
		<xsl:apply-templates/>
	</ed:PassportNumber>
</xsl:template>

<xsl:template match="edi:FechaExpedicion">
	<ed:IssuanceDate>
		<xsl:apply-templates/>
	</ed:IssuanceDate>
</xsl:template>

<xsl:template match="edi:Observaciones">
	<ed:Observations>
		<xsl:apply-templates/>
	</ed:Observations>
</xsl:template>

<xsl:template match="edi:Condiciones">
	<ed:Terms>
		<xsl:apply-templates/>
	</ed:Terms>
</xsl:template>

<xsl:template match="cdi:Timestamp">
	<cd:Timestamp>
		<xsl:apply-templates/>
	</cd:Timestamp>
</xsl:template>

<xsl:template match="cdi:AcuseDeRecibo">
	<cd:AcknowledgementStamp>
		<xsl:apply-templates/>
	</cd:AcknowledgementStamp>
</xsl:template>

<xsl:template match="cdi:Direccion">
	<cd:Address>
		<xsl:apply-templates select="edi:Buzon"/>
		<xsl:apply-templates select="edi:Piso"/>
		<xsl:apply-templates select="edi:Puerta"/>
		<xsl:apply-templates select="edi:NombreVia"/>
		<xsl:apply-templates select="edi:NombreViaAdicional"/>
		<xsl:apply-templates select="edi:NombreEdificio"/>
		<xsl:apply-templates select="edi:NumeroEdificio"/>
		<xsl:apply-templates select="edi:Departamento"/>
		<xsl:apply-templates select="edi:NombreCiudad"/>
		<xsl:apply-templates select="edi:CodigoPostal"/>
		<xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/>
		<xsl:apply-templates select="edi:Distrito"/>
		<xsl:apply-templates select="edi:Pais"/>
		<xsl:apply-templates select="edi:Nucleo"/>
		<xsl:apply-templates select="edi:TipoVia"/>
		<xsl:apply-templates select="edi:Seccion"/>
		<xsl:apply-templates select="edi:HojaInscripcion"/>
	</cd:Address>
</xsl:template>

<xsl:template match="edi:Buzon">
	<cbc:Postbox>
		<xsl:apply-templates/>
	</cbc:Postbox>
</xsl:template>

<xsl:template match="edi:Piso">
	<cbc:Floor>
		<xsl:apply-templates/>
	</cbc:Floor>
</xsl:template>

<xsl:template match="edi:Puerta">
	<cbc:Room>
		<xsl:apply-templates/>
	</cbc:Room>
</xsl:template>

<xsl:template match="edi:NombreVia">
	<cbc:StreetName>
		<xsl:apply-templates/>
	</cbc:StreetName>
</xsl:template>

<xsl:template match="edi:NombreViaAdicional">
	<cbc:AdditionalStreetName>
		<xsl:apply-templates/>
	</cbc:AdditionalStreetName>
</xsl:template>

<xsl:template match="edi:NombreEdificio">
	<cbc:BuildingName>
		<xsl:apply-templates/>
	</cbc:BuildingName>
</xsl:template>

<xsl:template match="edi:NumeroEdificio">
	<cbc:BuildingNumber>
		<xsl:apply-templates/>
	</cbc:BuildingNumber>
</xsl:template>

<xsl:template match="edi:Departamento">
	<cbc:Department>
		<xsl:apply-templates/>
	</cbc:Department>
</xsl:template>

<xsl:template match="edi:NombreCiudad">
	<cbc:CityName>
		<xsl:apply-templates/>
	</cbc:CityName>
</xsl:template>

<xsl:template match="edi:CodigoPostal">
	<cbc:PostalZone>
		<xsl:apply-templates/>
	</cbc:PostalZone>
</xsl:template>

<xsl:template match="edi:Distrito">
	<cbc:District>
		<xsl:apply-templates/>
	</cbc:District>
</xsl:template>

<xsl:template match="edi:Pais">
	<cac:Country>
		<xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/>
		<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
	</cac:Country>
</xsl:template>

<xsl:template match="edi:Nucleo">
	<ed:Nucleus>
		<xsl:apply-templates/>
	</ed:Nucleus>
</xsl:template>

<xsl:template match="edi:TipoVia">
	<ed:StreetQualifier>
		<xsl:apply-templates/>
	</ed:StreetQualifier>
</xsl:template>

<xsl:template match="edi:Seccion">
	<ed:Section>
		<xsl:apply-templates/>
	</ed:Section>
</xsl:template>

<xsl:template match="edi:HojaInscripcion">
	<ed:RegistrationSheet>
		<xsl:apply-templates/>
	</ed:RegistrationSheet>
</xsl:template>

</xsl:stylesheet>