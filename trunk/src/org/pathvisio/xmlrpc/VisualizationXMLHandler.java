package org.pathvisio.xmlrpc;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bridgedb.IDMapperException;
import org.pathvisio.core.Engine;
import org.pathvisio.core.model.ConverterException;
import org.pathvisio.core.preferences.PreferenceManager;
import org.pathvisio.data.DataException;
import org.pathvisio.data.ISample;
import org.pathvisio.desktop.gex.GexManager;
import org.pathvisio.desktop.gex.Sample;
import org.pathvisio.desktop.visualization.ColorGradient;
import org.pathvisio.desktop.visualization.ColorGradient.ColorValuePair;
import org.pathvisio.desktop.visualization.ColorRule;
import org.pathvisio.desktop.visualization.ColorSet;
import org.pathvisio.desktop.visualization.ColorSetManager;
import org.pathvisio.desktop.visualization.Visualization;
import org.pathvisio.desktop.visualization.VisualizationManager;
import org.pathvisio.visualization.plugins.ColorByExpression;
import org.pathvisio.visualization.plugins.DataNodeLabel;


public class VisualizationXMLHandler {
	
	public String createVisualization(String gexFile , String Gsam, String colorNames, String values, String Rsam, String colrNames, String expressions) throws IDMapperException, ConverterException, IOException, SecurityException, NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, DataException {
		PreferenceManager.init();
		Engine engine = new Engine ();//Create engine to load pathway
		GexManager gex = new GexManager();
		gex.setCurrentGex(gexFile,false);

		File xmlFile = new File(gex.getCurrentGex().getDbName() + ".xml");
		xmlFile.delete();
		
		VisualizationManager visman = new VisualizationManager(engine, gex);//Creating visualizationmanager and adding visualization,colorsetmanager and visualizationmethodregistry
		ColorSetManager colsetmgr = visman.getColorSetManager();
		ColorByExpression cbe = new ColorByExpression(gex, colsetmgr);//Creating Visualisation Method -- Colour by expression
		
		if(Gsam.contains(";")){
			String[] sample1 = Gsam.split(";");
			String[] colorNames1 = colorNames.split(";");
			String[] values1 = values.split(";");
			for(int gcount = 0; gcount < sample1.length; gcount = gcount+1 ){
				GradientVis grevis = new GradientVis();
				ColorSet cs1 = grevis.createGradient(gcount, colorNames1[gcount], values1[gcount]);
				colsetmgr.addColorSet(cs1);
				ISample s1 = gex.getCurrentGex().findSample(sample1[gcount]);
				cbe.addUseSample(s1);
				cbe.getConfiguredSample(s1).setColorSet(cs1);
				}
			}
		else{
			if(Gsam != null){
				ColorSet cs1 = new ColorSet("colorset");
				ColorGradient cg = new ColorGradient();//Creating and setting gradient
				String[] cols = colorNames.split(",");
				String[] valis = values.split(",");
				int vals[] = new int[valis.length];
				for (int k = 0; k < valis.length; k = k+1) {
					vals[k] = Integer.parseInt(valis[k]);
						}
				for (int n = 0; n < cols.length; n = n+1){
					java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(cols[n]);
					Color color = (Color)field.get(null);
					cg.addColorValuePair(new ColorValuePair(color,vals[n]));
						}
				cs1.setGradient(cg);
				colsetmgr.addColorSet(cs1);
				ISample s1 = gex.getCurrentGex().findSample(Gsam);
				cbe.addUseSample(s1);
				cbe.getConfiguredSample(s1).setColorSet(cs1);
				}
			}
				
		if(Rsam.contains(";")){
		String[] sample2 = Rsam.split(";");
		String[] colrNames1 = colrNames.split(";");
		String[] expr = expressions.split(";");
		for(int rcount = 0; rcount < sample2.length; rcount = rcount+1){
			RuleVis rulvis = new RuleVis();
			ColorSet cs2 = rulvis.createRule(rcount, gexFile, colrNames1[rcount], expr[rcount]);
			colsetmgr.addColorSet(cs2);
			ISample s2 = gex.getCurrentGex().findSample(sample2[rcount]);
			cbe.addUseSample(s2);
			cbe.getConfiguredSample(s2).setColorSet(cs2);
			}
		}
		else{
			if(Rsam != null){
				 ColorSet cs2 = new ColorSet("colorset-1");
				 ColorRule cr = new ColorRule(); //Creating and setting colorrule
				 java.lang.reflect.Field feld = Class.forName("java.awt.Color").getField(colrNames);
				 Color colour = (Color)feld.get(null);
				 cr.setColor(colour);
				 List<String> al;
				 al = gex.getCurrentGex().getSampleNames();
				 String msg = cr.setExpression(expressions,al);
				 if (msg != null) throw new IOException(msg);
				 cs2.addRule(cr);
				 colsetmgr.addColorSet(cs2);
				 ISample s2 = gex.getCurrentGex().findSample(Rsam);
				 cbe.addUseSample(s2);
				 cbe.getConfiguredSample(s2).setColorSet(cs2);
			 	}
			}
		DataNodeLabel dnl = new DataNodeLabel();
		
		Visualization vis = new Visualization("Visualization");//Creating visualization and adding visualizationmethod
		vis.addMethod(cbe);
		vis.addMethod(dnl);
		//vis.setShowLegend(true);
		visman.addVisualization(vis);
		visman.saveXML();    Pa - DELETING TEMP FILE IS FAILING
		
		return "Visualization created!";
	}
}
