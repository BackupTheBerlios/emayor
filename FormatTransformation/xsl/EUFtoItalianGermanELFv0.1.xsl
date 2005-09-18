<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cd="http://www.emayor.org/BusinessDocument.xsd" xmlns:cdbi="http://www.emayor.org/BusinessDocumentBi.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" >

<xsl:include href="EUFtoItalianELFv0.1.xsl"/>
<xsl:include href="EUFtoGermanELFv0.1.xsl"/>

 <xsl:output method="xml" indent="yes"/>
 
<xsl:template match="/">
	<cdbi:CertificatoDiResidenza-Meldebescheinigung xsi:schemaLocation="http://www.emayor.org/BusinessDocumentBi.xsd
	../xsd/eMayor/BusinessDocument-v0.1bi.xsd">
			<xsl:apply-templates mode="italian" select="cd:ResidenceCertificationDocument"/>
			<xsl:apply-templates mode="german" select="cd:ResidenceCertificationDocument"/>
	</cdbi:CertificatoDiResidenza-Meldebescheinigung>
</xsl:template>

</xsl:stylesheet>