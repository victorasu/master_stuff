Deploys pe Tomcat (dezarhivat la $CATALINA_HOME):
-------------------------------------------------

1) cu arhiva war:
copy .../servlet1Clasic.war in $CATALINA_HOME/webapps/
Se apeleaza din browser http://localhost:8080/servlet1Clasic

2) cu copierea intregii structuri de directori:
copy continutul arhivei .../servlet1Clasic.war in in $CATALINA_HOME/webapps/servlet1Clasic/
Se apeleaza din browser http://localhost:8080/servlet1Clasic

3) cu definirea unui context nou, extern:
Continutul arhivei .../servlet1Clasic.war se desface in d:/tmp
Se creaza in $CATALINA_HOME/conf/Catalina/localhost/aliasTomcat.xml:
<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<Context docBase="d:/tmp/" path="" reloadable="true">
</Context>
Se apeleaza din browser http://localhost:8080/aliasTomcat

Deploys pe Jetty (dezarhivat la $JETTY_HOME):
---------------------------------------------

1) cu arhiva war:
copy .../servlet1Clasic.war in $JETTY_HOME/webapps/
Se apeleaza din browser http://localhost:8080/servlet1Clasic

2) cu copierea intregii structuri de directori:
copy continutul arhivei .../servlet1Clasic.war in in $JETTY_HOME/webapps/servlet1Clasic/
Se apeleaza din browser http://localhost:8080/servlet1Clasic

3) cu definirea unui context nou, extern:
Continutul arhivei .../servlet1Clasic.jar se desface in d:/tmp
Se creaza in $JETTY_HOME/webapps/unAliasJetty.xml:
<?xml version="1.0"  encoding="ISO-8859-1"?>
<!DOCTYPE Configure PUBLIC "-//Mort Bay Consulting//DTD Configure//EN"
                           "http://jetty.mortbay.org/configure.dtd">
<Configure class="org.eclipse.jetty.webapp.WebAppContext">
    <Set name="contextPath">/aliasJetty</Set>
    <Set name="resourceBase">d:/tmp/</Set>
</Configure>
Se apeleaza din browser http://localhost:8080/aliasJetty

Deploy pe WildFly (dezarhivat la $JBOSS_HOME):
----------------------------------------------

copy .../servlet1Clasic.war in $JBOSS_HOME/standalone/deployments/
Se apeleaza din browser http://localhost:8080/servlet1Clasic

Deploy pe Glassfish (dezarhivat la $GF_HOME):
---------------------------------------------

c:\glassfish5\glassfish\bin>asadmin start-domain
c:\glassfish5\glassfish\bin>asadmin deploy .../servlet1Clasic.war 
sau
copy .../servlet1Clasic.war in $GF_HOME/glassfish/domains/domain1/autodeploy
Se apeleaza din browser http://localhost:8080/servlet1Clasic
