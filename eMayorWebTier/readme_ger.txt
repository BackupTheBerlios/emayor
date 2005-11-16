Pre 1.0 Version


CVS Setup
---------


ServiceHandling
---------------

In den Packaging Configurations:

Addiere /eMayorWebTier/eMayorWebTier.war  zum eMayorApp.ear


In ServiceHandling/src/META-INF/application.xml  fuege folgenden
Eintrag hinzu:

	<module>
		<web>
			<web-uri>eMayorWebTier.war</web-uri>
			<context-root>/eMayor</context-root>
		</web>
	</module>

-> Startseite ist:  http://localhost:8080/eMayor

[und nicht https - der WebTier schaltet spaeter automatisch um]	



eMayorWebTier
-------------

Kopiere den Ordner MunicipalityInformation
(aus /eMayorWebTier/WebRoot/ ) nach
JBoss/Server/Default/Conf

Eclipse macht jeweils noch "CVS" Unterordner - diese muss man loeschen.




Applet Installation [manuell noch]
----------------------------------

1) Das Applet braucht eine JRE 1.5 
-> Diese muss installiert und aktive sein, sonst scheitert
die Re-Authentikation im Startup.
[ JRE 1.4 wirft SSL Exceptions... deshalb 1.5 ]

 
2) Fuer XML Signer Unterstuetzung: (spaeter via Installer)

Kopiere das File  eMayorWebTier/.java.policy in das
User Directory (in Windows: Dokumente und Settings\UserName\)

Es enthält die Berechtigung, um die native Signer DLL zu starten:

grant {
    permission java.lang.RuntimePermission "loadLibrary";
};


Dann die Signer DLL Datei

eMayorWebTier/WebRoot/clientlibraries/pkcs11wrapper.dll

ins bin Verzeichnis der JRE 1.5 kopieren

(typisch: C:\Program Files\Java\jre1.5.0_04\bin )



Beim Auswahl der Services hats zwei:
RequestResidenceInformation und
RequestCertificationService

-> Nur der RequestCertificationService funktioniert.

Waehlt man RequestResidenceInformation an, sollte
das GUI sagen, dass der Service momentan ..nicht
verfuegbar ist ( == dient mir als Test eines
Fehlerzustandes ) - aber bei mir muss ich danach
den JBoss neustarten, damit das SH wieder funktioniert.
















	