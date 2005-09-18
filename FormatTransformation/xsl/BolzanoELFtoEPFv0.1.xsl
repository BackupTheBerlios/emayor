<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cdg="http://www.emayor.org/BusinessDocumentG.xsd" 
xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:edg="http://www.emayor.org/e-DocumentG.xsd" 
xmlns:edi="http://www.emayor.org/e-DocumentI.xsd">
 
<xsl:output method="xml" indent="yes"/>

<xsl:include href="SienaELFtoEPFv0.1.xsl"/>
<xsl:include href="AachenELFtoEPFv0.1.xsl"/>

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