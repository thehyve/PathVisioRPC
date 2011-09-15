package org.pathvisio.xmlrpc;

import org.pathvisio.desktop.PvDesktop;
import org.pathvisio.desktop.plugin.Plugin;

public class XmlRpcPlugin implements Plugin
{
	private RpcServer server;
	
	@Override
	public void init(PvDesktop desktop)
	{
		try {
			server = new RpcServer();
			server.startServer(server.getDefaultPort());
		} 
		catch (Exception exception) 
		{
			// pass on, will be caught by plugin initialization code.
			throw new Error (exception);
		}
	}

	@Override
	public void done()
	{
		if (server != null)
			server.shutdown();	
	}
}
