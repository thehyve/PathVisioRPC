package org.pathvisio.xmlrpc;

import junit.framework.TestCase;

import org.pathvisio.xmlrpc.MakePgexHandler;
import org.pathvisio.xmlrpc.StatExportHandler;
import org.pathvisio.xmlrpc.VisualizationXMLHandler;

public class TestApi extends TestCase {


    public void test_rpc_simple() throws Exception
    {
        //Set inputfile (tabdelimited);
        //String inputFile = "test/macs_glucose_challenge.txt";
        String inputFile = "testdata/data.txt";
        //Set bridgeDB file, see http://bridgedb.org/data/gene_database/
//        String dbFile = "test/metabolites_100227.bridge";
        String dbFile = "testdata/metabolites_081205.pgdb";
        //Set species, e.g. HomoSapiens
        String species = "HomoSapiens";
        visualizePathVisio(inputFile, dbFile, species, "testdata", "testdata/out");
    }

    /**
     *
     * @param inputFile
     * @param dbFile
     * @param species
     * @param pathWaysDir : the directory that contains the pathways
     * @param outputDir
     * @throws Exception
     */
    protected static void visualizePathVisio(String inputFile, String dbFile, String species, String pathWaysDir, String outputDir) throws Exception
    {
        System.out.println("started vis");
        String gexFile = inputFile+".pgex";
        MakePgexHandler pgex = new MakePgexHandler();

        String checkPgex = pgex.createPgex(inputFile, dbFile, species);

        VisualizationXMLHandler vis = new VisualizationXMLHandler();
        System.out.println("started vis");
        //Visualization options:
        String Gsam = "logFC;Fold Change";
        String colorNames = "blue,white,red;green,red";
        String values = "-1,0,1;-2,2";
        String Rsam = "P.Value";
        String colrNames = "yellow";
        String expressions = "[P.Value] < 0.5";
        String exprZ = "[P.Value]< 0.5";

        String checkVis = vis.createVisualization(gexFile, Gsam, colorNames, values, Rsam, colrNames, expressions);
        //println (checkVis)
        StatExportHandler stat = new StatExportHandler();
        Object checkStat = stat.xportInfo(gexFile, dbFile, pathWaysDir, exprZ, pathWaysDir, false, "");
        //(String gexFile, String dbFile, String pathDir, String exprZ, String output, boolean fromWeb, String webAddress) throws Exception {
        //println (checkStat)

        //see http://pathvisiorpc.wordpress.com/example-use-cases/
        //vis.

    }

}
