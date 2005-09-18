<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cdg="http://www.emayor.org/BusinessDocumentG.xsd" 
xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:edg="http://www.emayor.org/e-DocumentG.xsd" 
xmlns:edi="http://www.emayor.org/e-DocumentI.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0">
 
<xsl:output method="xml" indent="yes"/>

 
<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>			
								<xsl:apply-templates select="//cdi:CertificatoDiResidenza"/>

					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cdi:CertificatoDiResidenza">
	<table cellspacing="0" style="width:100%;height:100%;">		
				<tr>
					<td style="width:100%">
						<table style="width:100%;height=5%;">
							<tr>
								<td style="width:70%"><h2><b><xsl:apply-templates select="cdi:ComuneDiEmissione"/></b></h2></td>
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
									<td><xsl:apply-templates select="edi:DataEmissione"/></td>
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
								<td><xsl:apply-templates select="edi:Termini"/></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td><xsl:apply-templates select="cdi:Indirizzo"/></td>
				</tr>
				<tr>
					<td><xsl:apply-templates select="cdi:IntestatariCertificati"/></td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:15%;">
							<tr>
								<td><h3>Osservazioni:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="edi:Osservazioni"/></td>
							</tr>
						</table>
					</td>
				</tr>
		</table>
</xsl:template>

<xsl:template match="cdi:IntestatariCertificati">
	
		<table border="1" style="width:100%;height:50%" cellspacing="0">
			<tr><h3>Intestatari certificati:</h3></tr>
			<xsl:for-each select="edi:DettagliCittadino">			
					<tr><td bgcolor="#d0d0d0"><i><b>Dettagli del cittadino: </b></i></td></tr>
					<tr>
						<td><xsl:apply-templates select="edi:NomeCittadino">
							  <xsl:with-param name="no"><xsl:value-of select="position()"/></xsl:with-param>
							  <xsl:with-param name="sexType"><xsl:apply-templates select="edi:SessoCittadino"/></xsl:with-param>
							  <xsl:with-param name="statusType"><xsl:apply-templates select="edi:StatoCivileCittadino"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edi:DettagliNascitaCittadino">
							<xsl:with-param name="nationalityType"><xsl:apply-templates select="edi:Nazionalità"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edi:DettagliContatto"/></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edi:DettagliIdentificazione"/></td>
					</tr>
			</xsl:for-each>
		</table>
				<!-- <xsl:apply-templates select="edi:PreferredLanguages"/> -->
</xsl:template>

<xsl:template match="edi:NomeCittadino">
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
<xsl:template match="edi:PreferredLanguages">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edi:DettagliContatto">
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

<xsl:template match="edi:SessoCittadino">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:StatoCivileCittadino">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:DettagliNascitaCittadino">
	<xsl:param name="nationalityType"/>
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:20%"><b>Data di nascita</b></td>
			<td style="width:30%"><b>Luogo di nascita</b></td>
			<td style="width:25%"><b>Numero del atto di nascita</b></td>
			<td style="width:25"><b>Nazionalità</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="edi:DataDiNascita"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/></td>
			<td><xsl:apply-templates select="edi:NumeroAttoNascita"/></td>
			<td><xsl:value-of select="$nationalityType"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="edi:DataDiNascita">
	<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<!--<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>-->
</xsl:template>

<xsl:template match="edi:NumeroAttoNascita">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:Nazionalità">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:DettagliIdentificazione">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Numero della carta d' identità</b></td>
			<td style="width:50%"><b>Numero del passaporto</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="edi:NumeroCartaIdentità"/></td>
			<td><xsl:apply-templates select="edi:NumeroPassaporto"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="edi:NumeroCartaIdentità">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:NumeroPassaporto">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:DataDiEmissione">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:Osservazioni">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:Termini">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cdi:Indirizzo">
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
				<td><xsl:apply-templates select="edi:NombreCiudad"/></td>
				<td><xsl:apply-templates select="edi:Quartiere"/></td>
				<td><xsl:apply-templates select="edi:Provincia"/></td>
				<td><xsl:apply-templates select="edi:Sezione"/></td>
				<td><xsl:apply-templates select="edi:HojaInscripcion"/></td>
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
			<!-- <td><xsl:apply-templates select="edi:IdentificativoVia"/></td> -->
			<td><xsl:apply-templates select="edi:NomeVia"/></td>
			<td><xsl:apply-templates select="edi:NomeViaAggiuntivo"/></td>
			<td><xsl:apply-templates select="edi:NumeroCivico"/></td>
			<td><xsl:apply-templates select="edi:NomeCittà"/></td>
			<td><xsl:apply-templates select="edi:Nazione"/></td>
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
			<td><xsl:apply-templates select="edi:Piano"/></td>
			<td><xsl:apply-templates select="edi:Stanza"/></td>
			<td><xsl:apply-templates select="edi:CAP"/></td>
			<td><xsl:apply-templates select="edi:CasellaPostale"/></td>
			<td><xsl:apply-templates select="edi:NomeEdificio"/></td>
			<!-- <td><xsl:apply-templates select="edi:Dipartimento"/></td> -->
		</tr>
	</table>
		
		<!-- <xsl:apply-templates mode="copy-no-ns" select="edi:CountrySubentity"/> -->
</xsl:template>

<xsl:template match="edi:CasellaPostale">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:Piano">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:Stanza">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:NomeVia">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:NomeViaAggiuntivo">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:NomeEdificio">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:NumeroCivico">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="edi:Dipartimento">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edi:NomeCittà">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edi:CAP">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="edi:Provincia">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edi:Nazione">
	<!-- <xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/> -->
	<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
</xsl:template>

<!--
<xsl:template match="edi:Quartiere">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="edi:IdentificativoVia">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="edi:Sezione">
		<xsl:apply-templates/>
</xsl:template>
-->



<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>
								<xsl:apply-templates select="//cdg:Meldebescheinigung"/>
					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cdg:Meldebescheinigung">
	<table cellspacing="0" style="width:100%;height:100%;">				
				<tr>
					<td style="width:100%;height=5%;">
						<table>
							<tr>
								<td style="width:70%"><h2><b><xsl:apply-templates select="cdg:AusstellendeGemeinde"/></b></h2></td>
								<td style="width:30%"><h3><i>Meldebescheinigung</i></h3></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:5%;">
							<tr>
								<td><h3>Ausgestellt am:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="edg:AusgestelltAm"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:5%;">
							<tr>
								<td><h3>Gültigkeitszeitraum:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="edg:Gültigkeitszeitraum"/></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td><xsl:apply-templates select="cdg:Adresse"/></td>
				</tr>
				<tr>
					<td><xsl:apply-templates select="cdg:BescheinigteBeteiligten"/></td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:15%;">
							<tr>
								<td><h3>Bemerkungen:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="edg:Bemerkungen"/></td>
							</tr>
						</table>
					</td>
				</tr>
	</table>
</xsl:template>

<xsl:template match="cdg:BescheinigteBeteiligten">
	
		<table border="1" style="width:100%;height:50%;" cellspacing="0">
			<tr><h3>Bescheinigte Beteiligten:</h3></tr>
			<xsl:for-each select="edg:EinwohnerAngaben">			
					<tr><td bgcolor="#d0d0d0"><i><b>Einwohner Angaben: </b></i></td></tr>
					<tr>
						<td><xsl:apply-templates select="edg:EinwohnerName">
							  <xsl:with-param name="no"><xsl:value-of select="position()"/></xsl:with-param>
							  <xsl:with-param name="sexType"><xsl:apply-templates select="edg:EinwohnerGeschlecht"/></xsl:with-param>
							  <xsl:with-param name="statusType"><xsl:apply-templates select="edg:EinwohnerZivilstand"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edg:EinwohnerGeburtsdaten">
							<xsl:with-param name="nationalityType"><xsl:apply-templates select="edg:Nationalität"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edg:KontaktDetails"/></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="edg:Identifizierungsdaten"/></td>
					</tr>
			</xsl:for-each>
		</table>
				<!-- <xsl:apply-templates select="edg:PreferredLanguages"/> -->
</xsl:template>

<xsl:template match="edg:EinwohnerName">
	<xsl:param name="no"/>
	<xsl:param name="sexType"/>
	<xsl:param name="statusType"/>
	<table border="0" style="width:100%" cellspacing="0">
		<tr>
			<td style="width:15%"><b>No.</b></td>
			<td style="width:35%"><b>Vorname und Familienname</b></td>
			<td style="width:25%"><b>Geschlecht</b></td>
			<td style="width:25%"><b>Zivilstand</b></td>
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
<xsl:template match="edg:PreferredLanguages">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edg:KontaktDetails">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>e-mail</b></td>
			<td style="width:25%"><b>Telefon</b></td>
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

<xsl:template match="edg:EinwohnerGeschlecht">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:EinwohnerZivilstand">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:EinwohnerGeburtsdaten">
	<xsl:param name="nationalityType"/>
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:15%"><b>Geburtsdatum</b></td>
			<td style="width:35%"><b>Geburtsplatz</b></td>
			<td style="width:25%"><b>Geburtsurkunde Nummer</b></td>
			<td style="width:25"><b>Nationalität</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="edg:Geburtsdatum"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/></td>
			<td><xsl:apply-templates select="edg:GeburtsurkundeNummer"/></td>
			<td><xsl:value-of select="$nationalityType"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="edg:Geburtsdatum">
	<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<!--<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>-->
</xsl:template>

<xsl:template match="edg:GeburtsurkundeNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Nationalität">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Identifizierungsdaten">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Personalausweis Nummer</b></td>
			<td style="width:50%"><b>Reisepass Nummer</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="edg:PersonalausweisNummer"/></td>
			<td><xsl:apply-templates select="edg:ReisepassNummer"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="edg:PersonalausweisNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:ReisepassNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:AusgestelltAm">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Bemerkungen">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Gültigkeitszeitraum">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cdg:Adresse">
	<!--
	<table border="0" style="width:100%" cellspacing="0">
			<tr>
				<td colspan="5" bgcolor="#d0d0d0"><h4>:</h4></td>
			</tr>
			<tr>
				<td style="width:10%"><b>Entidad singular</b></td>
				<td style="width:25%"><b>Nukleus</b></td>
				<td style="width:20%"><b>Kreis</b></td>
				<td style="width:20%"><b>Sektion</b></td>
				<td style="width:25%"><b>Hoja de inscription</b></td>
			</tr>
			<tr>
				<td><xsl:apply-templates select="edg:NombreCiudad"/></td>
				<td><xsl:apply-templates select="edg:Nukleus"/></td>
				<td><xsl:apply-templates select="edg:Kreis"/></td>
				<td><xsl:apply-templates select="edg:Sektion"/></td>
				<td><xsl:apply-templates select="edg:HojaInscripcion"/></td>
			</tr>
	</table>
	<p/> -->
	<table border="0" style="width:100%;height=20%;" cellspacing="0">
		<tr>
			<td colspan="5" bgcolor="#d0d0d0"><h4>ADRESSE:</h4></td>	
		</tr>
		<tr>
			<!-- <td style="width:10%"><b>StraßeQualifizierer</b></td> -->
			<td style="width:25%"><b>Straße</b></td>
			<td style="width:25%"><b>Straßenname Zusatz</b></td>
			<td style="width:20%"><b>Nummer</b></td>
			<td style="width:15%"><b>Stadt</b></td>
			<td style="width:15%"><b>Land</b></td>
		</tr>
		<tr>
			<!-- <td><xsl:apply-templates select="edg:TipoVia"/></td> -->
			<td><xsl:apply-templates select="edg:Straße"/></td>
			<td><xsl:apply-templates select="edg:StraßennameZusatz"/></td>
			<td><xsl:apply-templates select="edg:GebäudeNummer"/></td>
			<td><xsl:apply-templates select="edg:Stadt"/></td>
			<td><xsl:apply-templates select="edg:Land"/></td>
		</tr>		
		<tr>
			<td style="width:10%"><b>Etage</b></td>
			<td style="width:10%"><b>Raum</b></td>
			<td style="width:10%"><b>Postbezirk</b></td>
			<td style="width:30%"><b>Postfach</b></td>
			<td style="width:40%"><b>Gebäude Name</b></td>
			<!-- <td style="width:15%"><b>Abteilung</b></td> -->
		</tr>
		<tr>
			<td><xsl:apply-templates select="edg:Etage"/></td>
			<td><xsl:apply-templates select="edg:Raum"/></td>
			<td><xsl:apply-templates select="edg:Postbezirk"/></td>
			<td><xsl:apply-templates select="edg:Postfach"/></td>
			<td><xsl:apply-templates select="edg:GebäudeName"/></td>
			<!-- <td><xsl:apply-templates select="edg:Abteilung"/></td> -->
		</tr>
	</table>
		
		<!-- <xsl:apply-templates mode="copy-no-ns" select="edg:CountrySubentity"/> -->
</xsl:template>

<xsl:template match="edg:Postfach">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Etage">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Raum">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Straße">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:StraßennameZusatz">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:GebäudeName">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:GebäudeNummer">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="edg:Abteilung">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edg:Stadt">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="edg:Postbezirk">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="edg:Kreis">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="edg:Land">
	<!-- <xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/> -->
	<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
</xsl:template>

<!--
<xsl:template match="edg:Nukleus">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="edg:StraßeQualifizierer">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="edg:Sektion">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>		
						
						
						<table cellspacing="0" style="width:100%;height:100%;">				
							<tr style="height:5%">
								<td style="width:50%" valign="top">
									<table>
										<tr>
											<td style="width:70%"><h2><b><xsl:apply-templates select="//cdi:ComuneDiEmissione"/></b></h2></td>
											<td style="width:30%"><h3><i>Certificato di residenza</i></h3></td>
										</tr>
									</table>
								</td>
								<td style="width:50%" valign="top">
									<table>
										<tr>
											<td style="width:70%"><h2><b><xsl:apply-templates select="//cdg:AusstellendeGemeinde"/></b></h2></td>
											<td style="width:30%"><h3><i>Meldebescheinigung</i></h3></td>
										</tr>
									</table>
								</td>
							</tr>
							
							<tr style="height:5%">
								<td valign="top">
									<table>
										<tr>
											<td><h3>Data di emissione:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edi:DataEmissione"/></td>
										</tr>
									</table>
								</td>
								<td valign="top">
									<table>
										<tr>
											<td><h3>Ausgestellt am:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edg:AusgestelltAm"/></td>
										</tr>
									</table>
								</td>
							</tr>
				
				
							<tr style="height:5%">
								<td valign="top">
									<table>
										<tr>
											<td><h3>Termini:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edi:Termini"/></td>
										</tr>
									</table>
								</td>
								<td valign="top">
									<table>
										<tr>
											<td><h3>Gültigkeitszeitraum:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edg:Gültigkeitszeitraum"/></td>
										</tr>
									</table>
								</td>
							</tr>		
							
							
							<tr style="height:20%">
								<td valign="top"><xsl:apply-templates select="//cdi:Indirizzo"/></td>
								<td valign="top"><xsl:apply-templates select="//cdg:Adresse"/></td>
							</tr>
							
							
							<tr style="height:50%">
								<td valign="top"><xsl:apply-templates select="//cdi:IntestatariCertificati"/></td>
								<td valign="top"><xsl:apply-templates select="//cdg:BescheinigteBeteiligten"/></td>
							</tr>
				
				
							<tr style="height:15%">
								<td valign="top">
									<table>
										<tr>
											<td><h3>Osservazioni:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edi:Osservazioni"/></td>
										</tr>
									</table>
								</td>
								<td valign="top">
									<table>
										<tr>
											<td><h3>Bemerkungen:</h3></td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edg:Bemerkungen"/></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					
					
					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>



</xsl:stylesheet>