<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cdg="http://www.emayor.org/BusinessDocumentG.xsd" 
xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:edg="http://www.emayor.org/e-DocumentG.xsd" 
xmlns:edi="http://www.emayor.org/e-DocumentI.xsd">
 
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:200px;">
				<tr>
					<td>			
								<xsl:apply-templates select="//cdi:RispostaNegativaCertificatoDiResidenza"/>

					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cdi:RispostaNegativaCertificatoDiResidenza">
	<table cellspacing="0" style="width:100%;height:100%;">		
				<tr>
					<td>
						<table>
							<tr>
								<td><h3><i>Certificato Di Residenza - Risposta Negativa</i></h3></td>
							</tr>
							<tr>
								<td>Egregio Signiore/a,</td>
							</tr>
							<tr>
								<td>La vostra richiesta di un Certificato di Residenza è stata rifiutata con le seguenti motivazioni:</td>
						    </tr>
						    <tr>
								<td><xsl:apply-templates select="edi:Osservazioni"/></td>
							</tr>
							<tr>
								<td>La vostra richiesta è stata elaborata il <b><xsl:apply-templates select="cdi:RiferimentoOrario"/></b>.</td>
							</tr>
						</table>
					</td>
				</tr>
		</table>
</xsl:template>

<xsl:template match="edi:Osservazioni">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cdi:RiferimentoOrario">
	<xsl:variable name="dateTime">
		 <xsl:value-of select="."/>
	</xsl:variable> 
		<xsl:value-of select="substring($dateTime,9,2)"/>-<xsl:value-of select="substring($dateTime,6,2)"/>-<xsl:value-of select="substring($dateTime,1,4)"/>, <xsl:value-of select="substring($dateTime,12,8)"/>
</xsl:template>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:200px;">
				<tr>
					<td>
								<xsl:apply-templates select="//cdg:NegativeMeldebescheinigungMeldung"/>
					</td>
				</tr>
			</table>
		</body>
	</html>
</xsl:template>

<xsl:template match="cdg:NegativeMeldebescheinigungMeldung">
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
								<td><xsl:apply-templates select="edg:Bemerkungen"/></td>
							</tr>
							<tr>
								<td>Ihre Anfrage wurde am <b><xsl:apply-templates select="cdg:Erstellungszeitpunkt"/></b> bearbeitet.</td>
							</tr>
						</table>
					</td>
				</tr>
	</table>
</xsl:template>

<xsl:template match="edg:Bemerkungen">
		<xsl:apply-templates/>
</xsl:template>

<xsl:template match="cdg:Erstellungszeitpunkt">
	<xsl:variable name="dateTime">
		 <xsl:value-of select="."/>
	</xsl:variable> 
		<xsl:value-of select="substring($dateTime,9,2)"/>-<xsl:value-of select="substring($dateTime,6,2)"/>-<xsl:value-of select="substring($dateTime,1,4)"/>, <xsl:value-of select="substring($dateTime,12,8)"/>
</xsl:template>

<xsl:template match="/">
	<html>
		<body>
			<table border="1" bordercolor="black" cellspacing="0" style="width:700px;height:400px;">
							<tr>
								<td>
									<table>
										<tr>
											<td><h3><i>Certificato Di Residenza - Risposta Negativa</i></h3></td>
										</tr>
										<tr>
											<td>Egregio Signore/a,</td>
										</tr>
										<tr>
											<td>La vostra richiesta di un Certificato di Residenza è stata rifiutata con le seguenti motivazioni:</td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edi:Osservazioni"/></td>
										</tr>
										<tr>
											<td>La vostra richiesta è stata elaborata il <b><xsl:apply-templates select="//cdi:RiferimentoOrario"/></b>.</td>
									    </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<td><h3><i>Negative Meldebescheinigung</i></h3></td>
										</tr>
										<tr>
											<td>Sehr geehrte Frau/Sehr geehrter Herr,</td>
										</tr>
										<tr>
											<td>Ihre Anfrage auf eine Meldebescheinigung wurde mit folgendem Grund abgelehnt:</td>
										</tr>
										<tr>
											<td><xsl:apply-templates select="//edg:Bemerkungen"/></td>
										</tr>
										<tr>
											<td>Ihre Anfrage wurde am <b><xsl:apply-templates select="//cdg:Erstellungszeitpunkt"/></b> bearbeitet.</td>
									    </tr>
									</table>
								</td>
							</tr>
			</table>
		</body>
	</html>
</xsl:template>
</xsl:stylesheet>