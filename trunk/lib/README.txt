Many .jar files were taken from the downloaded and cached .jar file when starting pathvisio from
the webstart link

 http://www.pathvisio.org/webstart/beta/pathvisio.jnlp

The web-start link will download a full .jar file to a "cache" location like:

 C:\Users\Pieter\AppData\LocalLow\Sun\Java\Deployment\cache\6.0\33

From there, new versions of the necessary jar files can be taken. These .jar files could not be found anywhere
else until now.
TODO: log a case for the PathVisio team asking for a "release" location for these .jar files.

org.bridgedb.*
and
derby* files are needed by pathvisio for handling .pgdb DB files

todo - TEST LAST additions...xmlrpc, ws, pdftrans