<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocumentG.xsd" xmlns:ed="http://www.emayor.org/e-DocumentG.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0">
 
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:1045px;">
				<tr>
					<td>
								<xsl:apply-templates select="//cd:Meldebescheinigung"/>
					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cd:Meldebescheinigung">
	<table cellspacing="0" style="width:100%;height:100%;">				
				<tr>
					<td style="width:100%;height=5%;">
						<table>
							<tr>
								<td style="width:70%"><h2><b><xsl:apply-templates select="cd:AusstellendeGemeinde"/></b></h2></td>
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
								<td><xsl:apply-templates select="ed:AusgestelltAm"/></td>
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
								<td><xsl:apply-templates select="ed:Gültigkeitszeitraum"/></td>
							</tr>
						</table>
					</td>
				</tr>		
				<tr>
					<td><xsl:apply-templates select="cd:Adresse"/></td>
				</tr>
				<tr>
					<td><xsl:apply-templates select="cd:BescheinigteBeteiligten"/></td>
				</tr>
				<tr>
					<td>
						<table style="width:100%;height:15%;">
							<tr>
								<td><h3>Bemerkungen:</h3></td>
							</tr>
							<tr>
								<td><xsl:apply-templates select="ed:Bemerkungen"/></td>
							</tr>
						</table>
					</td>
				</tr>
	</table>
</xsl:template>

<xsl:template match="cd:BescheinigteBeteiligten">
	
		<table border="1" style="width:100%;height:50%;" cellspacing="0">
			<tr><h3>Bescheinigte Beteiligten:</h3></tr>
			<xsl:for-each select="ed:EinwohnerAngaben">			
					<tr><td bgcolor="#d0d0d0"><i><b>Einwohner Angaben: </b></i></td></tr>
					<tr>
						<td><xsl:apply-templates select="ed:EinwohnerName">
							  <xsl:with-param name="no"><xsl:value-of select="position()"/></xsl:with-param>
							  <xsl:with-param name="sexType"><xsl:apply-templates select="ed:EinwohnerGeschlecht"/></xsl:with-param>
							  <xsl:with-param name="statusType"><xsl:apply-templates select="ed:EinwohnerZivilstand"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:EinwohnerGeburtsdaten">
							<xsl:with-param name="nationalityType"><xsl:apply-templates select="ed:Nationalität"/></xsl:with-param>
						</xsl:apply-templates></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:KontaktDetails"/></td>
					</tr>
					<tr>
						<td><xsl:apply-templates select="ed:Identifizierungsdaten"/></td>
					</tr>
			</xsl:for-each>
		</table>
				<!-- <xsl:apply-templates select="ed:PreferredLanguages"/> -->
</xsl:template>

<xsl:template match="ed:EinwohnerName">
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
<xsl:template match="ed:PreferredLanguages">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:KontaktDetails">
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

<xsl:template match="ed:EinwohnerGeschlecht">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:EinwohnerZivilstand">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:EinwohnerGeburtsdaten">
	<xsl:param name="nationalityType"/>
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:15%"><b>Geburtsdatum</b></td>
			<td style="width:35%"><b>Geburtsplatz</b></td>
			<td style="width:25%"><b>Geburtsurkunde Nummer</b></td>
			<td style="width:25"><b>Nationalität</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:Geburtsdatum"/></td>
			<td><xsl:apply-templates mode="copy-no-ns" select="cbc:CityName"/></td>
			<td><xsl:apply-templates select="ed:GeburtsurkundeNummer"/></td>
			<td><xsl:value-of select="$nationalityType"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:Geburtsdatum">
	<xsl:apply-templates mode="copy-no-ns" select="aapd:BirthDate"/>
		<!--<xsl:apply-templates mode="copy-no-ns" select="aapd:VerifiedBy"/>-->
</xsl:template>

<xsl:template match="ed:GeburtsurkundeNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Nationalität">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Identifizierungsdaten">
	<table border="0"  style="width:100%" cellspacing="0">
		<tr>
			<td style="width:50%"><b>Personalausweis Nummer</b></td>
			<td style="width:50%"><b>Reisepass Nummer</b></td>
		</tr>
		<tr>
			<td><xsl:apply-templates select="ed:PersonalausweisNummer"/></td>
			<td><xsl:apply-templates select="ed:ReisepassNummer"/></td>
		</tr>
	</table>
</xsl:template>

<xsl:template match="ed:PersonalausweisNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:ReisepassNummer">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:AusgestelltAm">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Bemerkungen">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Gültigkeitszeitraum">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cd:Adresse">
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
				<td><xsl:apply-templates select="ed:NombreCiudad"/></td>
				<td><xsl:apply-templates select="ed:Nukleus"/></td>
				<td><xsl:apply-templates select="ed:Kreis"/></td>
				<td><xsl:apply-templates select="ed:Sektion"/></td>
				<td><xsl:apply-templates select="ed:HojaInscripcion"/></td>
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
			<!-- <td><xsl:apply-templates select="ed:TipoVia"/></td> -->
			<td><xsl:apply-templates select="ed:Straße"/></td>
			<td><xsl:apply-templates select="ed:StraßennameZusatz"/></td>
			<td><xsl:apply-templates select="ed:GebäudeNummer"/></td>
			<td><xsl:apply-templates select="ed:Stadt"/></td>
			<td><xsl:apply-templates select="ed:Land"/></td>
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
			<td><xsl:apply-templates select="ed:Etage"/></td>
			<td><xsl:apply-templates select="ed:Raum"/></td>
			<td><xsl:apply-templates select="ed:Postbezirk"/></td>
			<td><xsl:apply-templates select="ed:Postfach"/></td>
			<td><xsl:apply-templates select="ed:GebäudeName"/></td>
			<!-- <td><xsl:apply-templates select="ed:Abteilung"/></td> -->
		</tr>
	</table>
		
		<!-- <xsl:apply-templates mode="copy-no-ns" select="ed:CountrySubentity"/> -->
</xsl:template>

<xsl:template match="ed:Postfach">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Etage">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Raum">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Straße">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:StraßennameZusatz">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:GebäudeName">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:GebäudeNummer">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="ed:Abteilung">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:Stadt">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="ed:Postbezirk">
		<xsl:apply-templates/>
</xsl:template>

<!--
<xsl:template match="ed:Kreis">
		<xsl:apply-templates/>
</xsl:template>
-->

<xsl:template match="ed:Land">
	<!-- <xsl:apply-templates mode="copy-no-ns" select="cac:IdentificationCode"/> -->
	<xsl:apply-templates mode="copy-no-ns" select="cbc:Name"/>
</xsl:template>

<!--
<xsl:template match="ed:Nukleus">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="ed:StraßeQualifizierer">
		<xsl:apply-templates/>
</xsl:template>
-->

<!--
<xsl:template match="ed:Sektion">
		<xsl:apply-templates/>
</xsl:template>
-->

</xsl:stylesheet>