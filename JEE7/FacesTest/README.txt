The following JNDI values must be configured in the application server prior
deploying this app. Additionally the itemis security domain must be
configured.

Base data type                    app JNDI name   JBoss JNDI name
===========================================================================
MailConnector                     "mail/Default"  "java:global/mail/Default"
Integer                           "build"         "java:global/build"
Boolean                           "productive"    "java:global/productive"
Text                              "ldap/url"      "java:global/ldap/url"
Text                              "ldap/baseDN"   "java:global/ldap/baseDN"
Text                              "ldap/baseDN"   "java:global/ldap/baseDN"
javax.naming.directory.DirContext "ldap/itemis"   "java:global/ldap/itemis" 
