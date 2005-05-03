In order to use the eMayor servers certs some transformations
has been done. The jboss server is using a keystore of the 
PKCS12 type. 
The followin steps has been done to generate such keystores:

1. openssl pkcs12 -in Server2.p12 -out Server2.pem
2. openssl pkcs12 -export -in Server2.pem -out server2.pkcs12.keystore -name "Bozen"

<Connector port="8443" address="${jboss.bind.address}"
           maxThreads="100" minSpareThreads="5" maxSpareThreads="15"
           scheme="https" secure="true" clientAuth="yes" keystoreType="PKCS12"
           keystoreFile="${jboss.server.home.dir}/conf/server1.pkcs12.keystore"
           keystorePass="12345" sslProtocol = "TLS" />

