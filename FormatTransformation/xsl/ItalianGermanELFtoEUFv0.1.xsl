<?xml version="1.0"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:cdi="http://www.emayor.org/BusinessDocumentI.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
 <xsl:output method="xml" indent="yes"/>
  
<xsl:include href="C:/eMayor/xsl/ItalianELFtoEUFv0.1.xsl"/>

<xsl:template match="/">
	<xsl:apply-templates select="//cdi:CertificatoDiResidenza"/>
</xsl:template>

</xsl:stylesheet>