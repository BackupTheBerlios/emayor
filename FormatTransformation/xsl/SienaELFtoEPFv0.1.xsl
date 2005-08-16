<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:ed="http://www.emayor.org/e-DocumentI.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0">
 
 <xsl:output method="xml" indent="yes"/>
  
<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>			
								<xsl:apply-templates select="//cd:CertificatoDiResidenza"/>

					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cd:CertificatoDiResidenza">
	<table cellspacing="0" style="width:100%;height:100%;">		
				<tr>
					<td style="width:100%">
						<table style="width:100%;height=5%;">
							<tr>
								<td style="width:70%"><h2><b><xsl:apply-templates select="cd:ComuneDiEmissione"/></b></h2></td>
								<td style="width:30%"><h3><i>Certificato di residenza</i></h3></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
							<table style="width:100%;height:5%;">
								<tr>
									<td><h3>Data di emissione:</h3> </td>
								</tr>
								<tr>
									<td><xsl:apply-templates select="ed:DataEmissione"/></td>
								</tr>
							</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:5%;">
							<tr>
								<td><h3>Termini:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:Termini"/></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td><xsl:apply-templates select="cd:Indirizzo"/></td>
				</tr>
				<tr>
					<td><xsl:apply-templates select="cd:IntestatariCertificati"/></td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:15%;">
							<tr>
								<td><h3>Osservazioni:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:Osservazioni"/></td>
							</tr>
						</table>
					</td>
				</tr>
		</table>
</xsl:template>

<xsl:template match="cd:IntestatariCertificati">
	
		<table border="1" style="width:100%;height:50%" cellspacing="0">
			<tr><h3>Intestatari certificati:</h3></tr>
			<xsl:for-each select="ed:DettagliCittadino">			
					<tr><td bgcolor="#d0d0d0"><i><b>Dettagli del cittadino: </b></i></td></tr>
					<tr>
						<td><xsl:apply-templates select="ed:NomeCittadino">
							  <xsl:with-param name="no"><xsl:value-of select="position()"/></xsl:with-param>
							  <xsl:with-param name="sexType"><xsl:apply-templates select="ed:SessoCittadino"/></xsl:with-param>
							  <xsl:with-param name="statusType"><xsl:apply-templates select="ed:StatoCivileCittadino"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DettagliNascitaCittadino">
							<xsl:with-param name="nationalityType"><xsl:apply-templates select="ed:Nazionalità"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DettagliContatto"/></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:DettagliIdentificazione"/></td>
					</tr>
			</xsl:for-each>
		</table>
				<!-- <xsl:apply-templates select="ed:PreferredLanguages"/> -->
</xsl:template>

<xsl:template match="ed:NomeCittadino">
	<xsl:param name="no"/>
	<xsl:param name="sexType"/>
	<xsl:param name="statusType"/>
	<table border="0" style="width:100%" cellspacing="0">
		<tr>
			<td style="width:20%"><b>No.</b></td>
			<td style="width:30%"><b>Nome e cognome</b></td>
			<td style="width:25%"><b>Sesso</b></td>
			<td style="width:25%"><b>Stato civile</b></td>
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

<xsl:template match="ed:DettagliContatto">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>e-mail</b></td>
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

<xsl:template match="ed:SessoCittadino">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:StatoCivileCittadino">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:DettagliNascitaCittadino">
	<xsl:param name="nationalityType"/>
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:20%"><b>Data di nascita</b></td>
			<td style="width:30%"><b>Luogo di nascita</b></td>
			<td style="width:25%"><b>Numero del atto di nascita</b></td>
			<td style="width:25"><b>Nazionalità</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:DataDiNascita"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/></td>
			<td><xsl:apply-templates select="ed:NumeroAttoNascita"/></td>
			<td><xsl:value-of select="$nationalityType"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:DataDiNascita">
	<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<!--<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>-->
</xsl:template>

<xsl:template match="ed:NumeroAttoNascita">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Nazionalità">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:DettagliIdentificazione">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Numero della carta d' identità</b></td>
			<td style="width:50%"><b>Numero del passaporto</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:NumeroCartaIdentità"/></td>
			<td><xsl:apply-templates select="ed:NumeroPassaporto"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:NumeroCartaIdentità">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NumeroPassaporto">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:DataDiEmissione">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Osservazioni">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Termini">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cd:Indirizzo">
	<!--
	<table border="0" style="width:100%" cellspacing="0">
			<tr>
				<td colspan="5" bgcolor="#d0d0d0"><h4>:</h4></td>
			</tr>
			<tr>
				<td style="width:10%"><b>Entidad singular</b></td>
				<td style="width:25%"><b>Quartiere</b></td>
				<td style="width:20%"><b>Provincia</b></td>
				<td style="width:20%"><b>Sezione</b></td>
				<td style="width:25%"><b>Hoja de inscription</b></td>
			</tr>
			<tr>
				<td><xsl:apply-templates select="ed:NombreCiudad"/></td>
				<td><xsl:apply-templates select="ed:Quartiere"/></td>
				<td><xsl:apply-templates select="ed:Provincia"/></td>
				<td><xsl:apply-templates select="ed:Sezione"/></td>
				<td><xsl:apply-templates select="ed:HojaInscripcion"/></td>
			</tr>
	</table>
	<p/> -->
	<table border="0" style="width:100%;height:20%;" cellspacing="0">
		<tr>
			<td colspan="5" bgcolor="#d0d0d0"><h4>INDIRIZZO:</h4></td>	
		</tr>
		<tr>
			<!-- <td style="width:10%"><b>Identificativo della via</b></td> -->
			<td style="width:25%"><b>Nome della via</b></td>
			<td style="width:25%"><b>Nome della via aggiuntivo</b></td>
			<td style="width:20%"><b>Numero</b></td>
			<td style="width:15%"><b>Città</b></td>
			<td style="width:15%"><b>Nazione</b></td>
		</tr>
		<tr>
			<!-- <td><xsl:apply-templates select="ed:IdentificativoVia"/></td> -->
			<td><xsl:apply-templates select="ed:NomeVia"/></td>
			<td><xsl:apply-templates select="ed:NomeViaAggiuntivo"/></td>
			<td><xsl:apply-templates select="ed:NumeroCivico"/></td>
			<td><xsl:apply-templates select="ed:NomeCittà"/></td>
			<td><xsl:apply-templates select="ed:Nazione"/></td>
		</tr>		
		<tr>
			<td style="width:10%"><b>Piano</b></td>
			<td style="width:10%"><b>Stanza</b></td>
			<td style="width:10%"><b>CAP</b></td>
			<td style="width:30%"><b>Casella postale</b></td>
			<td style="width:40%"><b>Nome del edificio</b></td>
			<!-- <td style="width:15%"><b>Dipartimento</b></td> -->
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:Piano"/></td>
			<td><xsl:apply-templates select="ed:Stanza"/></td>
			<td><xsl:apply-templates select="ed:CAP"/></td>
			<td><xsl:apply-templates select="ed:CasellaPostale"/></td>
			<td><xsl:apply-templates select="ed:NomeEdificio"/></td>
			<!-- <td><xsl:apply-templates select="ed:Dipartimento"/></td> -->
		</tr>
	</table>
		
		<!-- <xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/> -->
</xsl:template>

<xsl:template match="ed:CasellaPostale">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Piano">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Stanza">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NomeVia">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NomeViaAggiuntivo">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NomeEdificio">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:NumeroCivico">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="ed:Dipartimento">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:NomeCittà">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:CAP">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="ed:Provincia">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:Nazione">
	<!-- <xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/> -->
	<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
</xsl:template>

<!--
<xsl:template match="ed:Quartiere">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="ed:IdentificativoVia">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="ed:Sezione">
		<xsl:apply-templates/>
</xsl:template>
-->

</xsl:stylesheet>