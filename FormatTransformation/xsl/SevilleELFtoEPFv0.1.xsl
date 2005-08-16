<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocumentS.xsd" xmlns:ed="http://www.emayor.org/e-DocumentS.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0">
 
 <xsl:output method="xml" indent="yes"/>
  
<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>			
								<xsl:apply-templates select="//cd:CertificadoInscripcionPadronal"/>

					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cd:CertificadoInscripcionPadronal">
			<table style="width:100%">
				<tr>
					<td style="width:100%">
						<table>
							<tr>
								<td style="width:60%"><h2><b><xsl:apply-templates select="cd:AyuntamientoEmisor"/></b></h2></td>
								<td style="width:40%"><h3><i>Certificado de inscripcion padronal</i></h3></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><h3>Fecha de expedicion:</h3> </td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:FechaExpedicion"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><h3>Condiciones:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:Condiciones"/></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td><xsl:apply-templates select="cd:Direccion"/></td>
				</tr>
				<tr>
					<td><xsl:apply-templates select="cd:PersonasInscritasCertificado"/></td>
				</tr>
				<tr>
					<td>
						<table>
							<tr>
								<td><h3>Observaciones:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:Observaciones"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
</xsl:template>

<xsl:template match="cd:PersonasInscritasCertificado">
	
		<table border="1" style="width:100%" cellspacing="0">
			<tr><h3>Personas inscritas en el certificado:</h3></tr>
			<xsl:for-each select="ed:DatosCiudadano">			
					<tr><td bgcolor="#d0d0d0"><i><b>Datos de ciudadano: </b></i></td></tr>
					<tr>
						<td><xsl:apply-templates select="ed:NombreCompletoCiudadano">
							  <xsl:with-param name="no"><xsl:value-of select="position()"/></xsl:with-param>
							  <xsl:with-param name="sexType"><xsl:apply-templates select="ed:SexoCiudadano"/></xsl:with-param>
							  <xsl:with-param name="statusType"><xsl:apply-templates select="ed:EstadoCivilCiudadano"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DatosNacimientoCiudadano">
							<xsl:with-param name="nationalityType"><xsl:apply-templates select="ed:Nacionalidad"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DatosContacto"/></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DatosIdentificativos"/></td>
					</tr>
			</xsl:for-each>
		</table>
				<!-- <xsl:apply-templates select="ed:PreferredLanguages"/> -->
</xsl:template>

<xsl:template match="ed:NombreCompletoCiudadano">
	<xsl:param name="no"/>
	<xsl:param name="sexType"/>
	<xsl:param name="statusType"/>
	<table border="0" style="width:100%" cellspacing="0">
		<tr>
			<td style="width:15%"><b>No.</b></td>
			<td style="width:35%"><b>Nombre y apellidos</b></td>
			<td style="width:25%"><b>Sexo</b></td>
			<td style="width:25%"><b>Estado civil</b></td>
			<!-- <td><b>Suffix</b></td> -->
			<!-- <td><b>Requested Name</b></td> -->
		</tr>
		<tr>
			<td> <xsl:value-of select="$no"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameTitle"/>&#160;<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameForename"/>&#160;<xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSurname"/></td>
			<td><xsl:value-of select="$sexType"/></td>
			<td><xsl:value-of select="$statusType"/></td>
			<!-- <td><xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameSuffix"/></td> -->
			<!-- <td><xsl:apply-templates mode="copy-no-ns" select="aapd:CitizenNameRequestedName"/></td> -->
		</tr>
	</table>
</xsl:template>

<!--
<xsl:template match="ed:PreferredLanguages">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:DatosContacto">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Correo electronico</b></td>
			<td style="width:25%"><b>Telefono</b></td>
			<td style="width:25%"><b>Fax</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates mode="copy-no-ns" select="aapd:Email"/></td>
			<td><xsl:apply-templates select="aapd:Telephone"/></td>
			<td><xsl:apply-templates select="aapd:Fax"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="aapd:Telephone">
	+<xsl:apply-templates select="aapd:TelCountryCode"/>-<xsl:apply-templates select="aapd:TelExtensionNumber"/>-<xsl:apply-templates select="aapd:TelNationalNumber"/>
</xsl:template>

<xsl:template match="aapd:Fax">
	+<xsl:apply-templates select="aapd:FaxCountryCode"/>-<xsl:apply-templates select="aapd:FaxExtensionNumber"/>-<xsl:apply-templates select="aapd:FaxNationalNumber"/>
</xsl:template>

<xsl:template match="ed:SexoCiudadano">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:EstadoCivilCiudadano">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:DatosNacimientoCiudadano">
	<xsl:param name="nationalityType"/>
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:15%"><b>Fecha nacimiento</b></td>
			<td style="width:35%"><b>Lugar de nacimiento</b></td>
			<td style="width:25%"><b>Numero certificado nacimiento</b></td>
			<td style="width:25"><b>Pais de nacionalidad</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:FechaNacimiento"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/></td>
			<td><xsl:apply-templates select="ed:NumeroCertificadoNacimiento"/></td>
			<td><xsl:value-of select="$nationalityType"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:FechaNacimiento">
	<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<!--<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>-->
</xsl:template>

<xsl:template match="ed:NumeroCertificadoNacimiento">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Nacionalidad">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:DatosIdentificativos">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Documento identidad</b></td>
			<td style="width:50%"><b>Numero pasaporte</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:DocumentoIdentidad"/></td>
			<td><xsl:apply-templates select="ed:NumeroPasaporte"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:DocumentoIdentidad">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NumeroPasaporte">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:FechaExpedicion">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Observaciones">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Condiciones">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cd:Direccion">
	<table border="0" style="width:100%" cellspacing="0">
			<tr>
				<td colspan="5" bgcolor="#d0d0d0"><h4>DATOS DEL PADRON MUNICIPAL DE HABITANTES:</h4></td>
			</tr>
			<tr>
				<td style="width:10%"><b>Entidad singular</b></td>
				<td style="width:25%"><b>Nucleo / diseminado</b></td>
				<td style="width:20%"><b>Distrito</b></td>
				<td style="width:20%"><b>Seccion</b></td>
				<td style="width:25%"><b>Hoja de inscription</b></td>
			</tr>
			<tr>
				<td><xsl:apply-templates select="ed:NombreCiudad"/></td>
				<td><xsl:apply-templates select="ed:Nucleo"/></td>
				<td><xsl:apply-templates select="ed:Distrito"/></td>
				<td><xsl:apply-templates select="ed:Seccion"/></td>
				<td><xsl:apply-templates select="ed:HojaInscripcion"/></td>
			</tr>
	</table>
	<p/>
	<table border="0" style="width:100%" cellspacing="0">
		<tr>
			<td colspan="6" bgcolor="#d0d0d0"><h4>DATOS DE LA VIVIENDA:</h4></td>	
		</tr>
		<tr>
			<td style="width:10%"><b>Tipo di via</b></td>
			<td style="width:25%"><b>Nombre de via</b></td>
			<td style="width:25%"><b>Nombre de via adicional</b></td>
			<td style="width:10%"><b>Numero</b></td>
			<td style="width:15%"><b>Ciudad</b></td>
			<td style="width:15%"><b>Pais</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:TipoVia"/></td>
			<td><xsl:apply-templates select="ed:NombreVia"/></td>
			<td><xsl:apply-templates select="ed:NombreViaAdicional"/></td>
			<td><xsl:apply-templates select="ed:NumeroEdificio"/></td>
			<td><xsl:apply-templates select="ed:NombreCiudad"/></td>
			<td><xsl:apply-templates select="ed:Pais"/></td>
		</tr>		
		<tr>
			<td style="width:10%"><b>Piso</b></td>
			<td style="width:25%"><b>Puerta</b></td>
			<td style="width:25%"><b>Codigo postal</b></td>
			<td style="width:10%"><b>Buzon</b></td>
			<td style="width:15%"><b>Nombre edificio</b></td>
			<td style="width:15%"><b>Departamento</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:Piso"/></td>
			<td><xsl:apply-templates select="ed:Puerta"/></td>
			<td><xsl:apply-templates select="ed:CodigoPostal"/></td>
			<td><xsl:apply-templates select="ed:Buzon"/></td>
			<td><xsl:apply-templates select="ed:NombreEdificio"/></td>
			<td><xsl:apply-templates select="ed:Departamento"/></td>
		</tr>
	</table>
		
		<!-- <xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/> -->
</xsl:template>

<xsl:template match="ed:Buzon">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Piso">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Puerta">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NombreVia">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NombreViaAdicional">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NombreEdificio">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NumeroEdificio">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Departamento">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NombreCiudad">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:CodigoPostal">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Distrito">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Pais">
	<!-- <xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/> -->
	<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
</xsl:template>

<xsl:template match="ed:Nucleo">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:TipoVia">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Seccion">
		<xsl:apply-templates/>
</xsl:template>

</xsl:stylesheet>