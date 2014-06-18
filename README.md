ecm
===
ECM Assets

Add FileNet related jars:
mvn install:install-file -Dfile=Jace.jar -DgroupId=com.ibm.filenet -DartifactId=Jace -Dversion=5.2 -Dpackaging=jar -DgeneratePom=true 
mvn install:install-file -Dfile=pe.jar -DgroupId=com.ibm.filenet -DartifactId=pe -Dversion=5.2 -Dpackaging=jar -DgeneratePom=true 
mvn install:install-file -Dfile=pe3pt.jar -DgroupId=com.ibm.filenet -DartifactId=pe3pt -Dversion=5.2 -Dpackaging=jar -DgeneratePom=true 
mvn install:install-file -Dfile=peResources.jar -DgroupId=com.ibm.filenet -DartifactId=peResources -Dversion=5.2 -Dpackaging=jar -DgeneratePom=true 
mvn install:install-file -Dfile=stax-api.jar -DgroupId=com.ibm.filenet -DartifactId=stax-api -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=xlxpScanner.jar -DgroupId=com.ibm.filenet -DartifactId=xlxpScanner -Dversion=1.1.14 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=xlxpScannerUtils.jar -DgroupId=com.ibm.filenet -DartifactId=xlxpScannerUtils -Dversion=1.1.14 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=P8Helper.jar -DgroupId=com.ibm.filenet -DartifactId=P8Helper -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
