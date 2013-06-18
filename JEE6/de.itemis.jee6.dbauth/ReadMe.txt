
For compiling the source you have to:
1. configure your server runtime environment. You can do this under "Window -> Preferences ->
   Server -> Runtime Environment". You have to add this environment in your newly created project:
   "Project -> Properties -> Java Build Path -> Library -> Add Library".
2. You have to configure a data source inside your application server. This must be named like
   this: "jdbc/dbauthDS".
3. You should also configure your build.properties. You have to add the following properties:
   a) jboss.home
   b) glassfish.home
   Depending on your used application server you can remove those variables and you can adjust your
   build targets inside the build.xml file.
   
Finally: Do not forget to add all generated files inside src and res to source control!
