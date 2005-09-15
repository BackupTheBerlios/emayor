<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocumentG.xsd" xmlns:ed="http://www.emayor.org/e-DocumentG.xsd" xmlns:aapd="http://www.govtalk.gov.uk/people/AddressAndPersonalDetails"  xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-1.0" xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-1.0">
 
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:200px;">
				<tr>
					<td>
								<xsl:apply-templates select="//cd:NegativeMeldebescheinigungMeldung"/>
					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cd:NegativeMeldebescheinigungMeldung">
	<table cellspacing="0" style="width:100%;height:100%;">				
				<tr>
					<td>
						<table>
							<tr>
								<td><h3><i>Negative Meldebescheinigung</i></h3></td>
							</tr>
							<tr>
								<td>Sehr geehrter Herr/Sehr geehrte Frau,</td>
							</tr>
							<tr>
								<td>Ihre Anfrage auf eine Meldebescheinigung wurde mit folgendem Grund abgelehnt:</td>
						    </tr>
						    <tr>
								<td><xsl:apply-templates select="ed:Bemerkungen"/></td>
							</tr>
							<tr>
								<td>Ihre Anfrage wurde am <b><xsl:apply-templates select="cd:Erstellungszeitpunkt"/></b> bearbeitet.</td>
							</tr>
						</table>
					</td>
				</tr>
	</table>
</xsl:template>

<xsl:template match="ed:Bemerkungen">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cd:Erstellungszeitpunkt">
	<xsl:variable name="dateTime">
		 <xsl:value-of select="."/>
	</xsl:variable> 
		<xsl:value-of select="substring($dateTime,9,2)"/>-<xsl:value-of select="substring($dateTime,6,2)"/>-<xsl:value-of select="substring($dateTime,1,4)"/>, <xsl:value-of select="substring($dateTime,12,8)"/>
</xsl:template>

</xsl:stylesheet>