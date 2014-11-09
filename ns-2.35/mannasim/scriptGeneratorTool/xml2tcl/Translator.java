package xml2tcl;

import gui.TclFields;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class translates the TclFields from and to an XML document. It also
 * exports those fields to a TCL script for NS-2.
 * 
 * @author Júlio César e Melo
 * @since Nov. 2005
 * @version 0.1
 * 
 * This version doesn't work well with antenna settings.
 */
public class Translator
{
    private final String xsltFileName = "mannasim-tcl.xsl";
    private final String absolutePositionElement = "position";
    private final String accessPointElement = "accessPoint";
    private final String applicationElement = "application";
    private final String avgMeasureElement = "avgMeasure";
    private final String clusterHeadElement = "clusterHead";
    private final String commonNodeElement = "commonNode";
    private final String dataGeneratorElement = "dataGenerator";
    private final String defaultNetworkElement = "defaultNetwork";
    private final String disseminatingElement = "disseminatingType";
    private final String linkLayerElement = "linkLayer";
    private final String macLayerElement = "mac";
    private final String maximumDataElement = "max";
    private final String nodeEnergyElement = "energy";
    private final String nodeNetworkArchitectureElement = "network";
    private final String omniAntennaElement = "omniAntenna";
    private final String antennaElement = "antenna";
    private final String processingElement = "processing";
    private final String queueElement = "queue";
    private final String queueLengthAttribute = "queueLengthAttribute";
    private final String routingProtocolElement = "routingProtocol";
    private final String propagationElement = "propagation";
    private final String energyModelElement = "energyModel";
    private final String scenarioElement = "scenario";
    private final String sensingIntervalElement = "sensingInterval";
    private final String sensingTypeElement = "sensingType";
    private final String stdDeviationElement = "stdDeviation";
    private final String timeElement = "time";
    private final String traceElement = "trace";
    private final String wirelessPhyElement = "wirelessPhy";

    // Before chaning this, see createAbsolutePosition().
    private final String[] absolutePositions = new String[] { "CENTER",
            "UP LEFT CORNER", "DOWN LEFT CORNER", "UP RIGHT CORNER",
            "DOWN RIGHT CORNER", };

    /*
     * private final String [] distributedPositions = new String [] { "GRID",
     * "RANDOM" };
     */

    private TclFields fields;

    private Document document;

    public Translator(TclFields tclFields)
    {
        this.fields = tclFields;
        document = null;
    }

    public void open(File file) throws SAXException, IOException,
            ParserConfigurationException
    {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        document = documentBuilder.parse(file);

        importDocument();
    }

    public void save(String fileName) throws FileNotFoundException,
            TransformerConfigurationException, TransformerException,
            IOException, ParserConfigurationException
    {
        createDocument();

        FileOutputStream outputFile = new FileOutputStream(fileName);
        Source xmlSource = new DOMSource(document);
        Result result = new StreamResult(outputFile);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        transformer.transform(xmlSource, result);

        outputFile.close();
    }

    /**
     * Create Xml document.
     */
    private void createDocument() throws ParserConfigurationException
    {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Element simulation;

        document = documentBuilder.newDocument();

        simulation = document.createElement("simulation");

        appendChildIfNotNull(simulation, createTrace());
        simulation.appendChild(createTime());
        simulation.appendChild(createScenario());
        simulation.appendChild(createDefaultNetwork());
        simulation.appendChild(createAccessPoint());

        if (fields.getClusterHeadNumber() > 0)
            simulation.appendChild(createClusterHead());

        simulation.appendChild(createCommonNode());

        document.appendChild(simulation);
    }

    private void importDocument()
    {
        importTrace((Element) document.getElementsByTagName(traceElement).item(
                0));
        importTime((Element) document.getElementsByTagName(timeElement).item(0));
        importScenario((Element) document.getElementsByTagName(scenarioElement)
                .item(0));
        importDefaultNetwork((Element) document.getElementsByTagName(
                defaultNetworkElement).item(0));
        importAccessPoint((Element) document.getElementsByTagName(
                accessPointElement).item(0));
        importClusterHead((Element) document.getElementsByTagName(
                clusterHeadElement).item(0));
        importCommonNode((Element) document.getElementsByTagName(
                commonNodeElement).item(0));
    }

    private void appendChildIfNotNull(Element root, Element child)
    {
        if (child != null)
            root.appendChild(child);
    }

    /**
     * Translate a xml document to tcl.
     * 
     * @param outputFileName
     *            Tcl script for ns.
     * 
     * @throws TransformerException
     */
    public void export(String outputFileName) throws TransformerException,
            FileNotFoundException, IOException, ParserConfigurationException
    {
        createDocument();

        FileOutputStream outputStream = new FileOutputStream(outputFileName);
        Source xmlSource = new DOMSource(document);
        Source xsltSource = new StreamSource(getClass().getResourceAsStream(
                xsltFileName));
        Result result = new StreamResult(outputStream);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer(xsltSource);

        transformer.transform(xmlSource, result);

        outputStream.close();
    }

    /**
     * Create trace element.
     * 
     * Xml path: /simulation/trace
     */
    private Element createTrace()
    {
        if (fields.isTraceFile())
        {
            Element trace = document.createElement(traceElement);

            trace.appendChild(document
                    .createTextNode(fields.getTraceFileName()));

            trace.setAttribute("mac", fields.isTraceMac() ? "true" : "false");
            trace.setAttribute("agent", fields.isTraceAgent() ? "true"
                    : "false");
            trace.setAttribute("router", fields.isTraceRoute() ? "true"
                    : "false");

            return trace;
        }
        else
            return null;
    }

    private void importTrace(Element trace)
    {
        if (trace != null)
        {
            fields.setTraceFileName(trace.getFirstChild().getNodeValue());
            fields.setTraceMac(trace.getAttribute("mac").compareToIgnoreCase(
                    "true") == 0);
            fields.setTraceAgent(trace.getAttribute("agent")
                    .compareToIgnoreCase("true") == 0);
            fields.setTraceRoute(trace.getAttribute("router")
                    .compareToIgnoreCase("true") == 0);
        }
    }

    /**
     * Create time element.
     * 
     * Xml path: /simulation/time
     */
    private Element createTime()
    {
        Element time = document.createElement(timeElement);

        time
                .setAttribute("start", Double.toString(fields
                        .getSimulationStart()));
        time.appendChild(document.createTextNode(Double.toString(fields
                .getSimulationStop())));

        return time;
    }

    private void importTime(Element trace)
    {
        fields.setSimulationStart(Double.parseDouble(trace
                .getAttribute("start")));
        fields.setSimulationStop(Double.parseDouble(trace.getFirstChild()
                .getNodeValue()));
    }

    /**
     * Create scenario element from ScenarioX and ScenarioY.
     * 
     * Xml path: /simulation/scenario
     */
    private Element createScenario()
    {
        Element scenario = document.createElement(scenarioElement);

        scenario.setAttribute("width", Double.toString(fields.getScenarioX()));
        scenario.setAttribute("height", Double.toString(fields.getScenarioY()));

        return scenario;
    }

    private void importScenario(Element scenario)
    {
        fields.setScenarioX(Double.parseDouble(scenario.getAttribute("width")));
        fields
                .setScenarioY(Double.parseDouble(scenario
                        .getAttribute("height")));
    }

    /**
     * Create a default network settings.
     * 
     * Xml path: /simulation/defaultNetwork
     */
    private Element createDefaultNetwork()
    {
        Element defaultNetwork = document.createElement(defaultNetworkElement);

//        if (fields.getAntenna().compareToIgnoreCase("Antenna/OmniAntenna") == 0)
//            defaultNetwork.appendChild(createOmniAntenna());
//        else
        	defaultNetwork.appendChild(createAntenna());

        defaultNetwork.appendChild(createWirelessPhy());
        defaultNetwork.appendChild(createMacLayer());
        defaultNetwork.appendChild(createQueue());
        defaultNetwork.appendChild(createLinkLayer());
        defaultNetwork.appendChild(createRoutingProtocol());
        defaultNetwork.appendChild(createPropagation());
        defaultNetwork.appendChild(createEnergyModel());

        return defaultNetwork;
    }

    private void importDefaultNetwork(Element defaultNetwork)
    {
    	importAntenna((Element) defaultNetwork.getElementsByTagName(
                antennaElement).item(0));
        importWirelessPhy((Element) defaultNetwork.getElementsByTagName(
                wirelessPhyElement).item(0));
        importMacLayer((Element) defaultNetwork.getElementsByTagName(
                macLayerElement).item(0));
        importQueue((Element) defaultNetwork.getElementsByTagName(queueElement)
                .item(0));
        importLinkLayer((Element) defaultNetwork.getElementsByTagName(
                linkLayerElement).item(0));
        importRoutingProtocol((Element) defaultNetwork.getElementsByTagName(
                routingProtocolElement).item(0));
        importPropagation((Element) defaultNetwork.getElementsByTagName(
        		propagationElement).item(0));
        importEnergyModel((Element) defaultNetwork.getElementsByTagName(
        		energyModelElement).item(0));
    }

//    /**
//     * Create an omni antenna.
//     * 
//     * Xml path: /simulation/defaultNetwork/omniAntenna
//     */
//    private Element createOmniAntenna()
//    {
//        Element omniAntenna = document.createElement(omniAntennaElement);
//
//        omniAntenna.setAttribute("x", "0");
//        omniAntenna.setAttribute("y", "0");
//        omniAntenna.setAttribute("z", "1.5");
//        omniAntenna.setAttribute("gt", "1.0");
//        omniAntenna.setAttribute("gr", "1.0");
//
//        return omniAntenna;
//    }
    
    /**
     * Create an antenna.
     * 
     * Xml path: /simulation/defaultNetwork/antenna
     */
    private Element createAntenna()
    {
    	Element antenna = document.createElement(antennaElement);
    	
    	antenna.setAttribute("name", fields.getAntenna());
    	antenna.setAttribute("x", "0");
        antenna.setAttribute("y", "0");
        antenna.setAttribute("z", "1.5");
        antenna.setAttribute("gt", "1.0");
        antenna.setAttribute("gr", "1.0");
        
        return antenna;
    }
    
    private void importAntenna(Element antenna)
    {
    	if (antenna != null)
    	{
    		fields.setAntenna(antenna.getAttribute("name"));
    	}
    }

    /**
     * Create a wireless phy based on profile.
     * 
     * Xml path: /simulation/defaultNetwork/wirelessPhy
     */
    private Element createWirelessPhy()
    {
        return createWirelessPhy(fields.getPhyLayer(), fields
                .getCommonNodeTransmissionRange());
    }

    private Element createWirelessPhy(String profile, double range)
    {
        Element wirelessPhy = document.createElement(wirelessPhyElement);

        if (profile == "Phy/WirelessPhy - Mica2")
            wirelessPhy.setAttribute("profile", "mica2");
        else
            wirelessPhy.setAttribute("profile", profile);

        wirelessPhy.setAttribute("range", Double.toString(range));

        return wirelessPhy;
    }

    private void importWirelessPhy(Element wirelessPhy)
    {
        fields.setPhyLayer(wirelessPhy.getAttribute("profile"));
        fields
                .setCommonNodeTransmissionRange(importWirelessPhyRange(wirelessPhy));
    }

    private double importWirelessPhyRange(Element wirelessPhy)
    {
        return Double.parseDouble(wirelessPhy.getAttribute("range"));
    }

    /**
     * Create a mac.
     * 
     * Xml path: /simulation/defaultNetwork/mac
     */
    private Element createMacLayer()
    {
        Element mac = document.createElement(macLayerElement);

        mac.appendChild(document.createTextNode(fields.getMacLayer()));

        return mac;
    }

    private void importMacLayer(Element mac)
    {
        fields.setMacLayer(mac.getFirstChild().getNodeValue());
    }

    /**
     * Create a queue.
     * 
     * Xml path: /simulation/defaultNetwork/queue
     */
    private Element createQueue()
    {
        Element queue = document.createElement(queueElement);

        queue.appendChild(document.createTextNode(fields.getIfq()));
        queue.setAttribute(queueLengthAttribute, Integer.toString(fields
                .getIfqLen()));

        return queue;
    }

    private void importQueue(Element queue)
    {
        fields.setIfq(queue.getFirstChild().getNodeValue());
        fields.setIfqLen(Integer.parseInt(queue
                .getAttribute(queueLengthAttribute)));
    }

    /**
     * Create link layer.
     * 
     * Xml path: /simulation/defaultNetwork/link
     */
    private Element createLinkLayer()
    {
        Element link = document.createElement(linkLayerElement);

        link.appendChild(document.createTextNode(fields.getLinkLayer()));

        return link;
    }

    private void importLinkLayer(Element link)
    {
        fields.setLinkLayer(link.getFirstChild().getNodeValue());
    }

    /**
     * Create routing protocol.
     * 
     * Xml path: /simulation/defaultNetwork/routingProtocol
     */
    private Element createRoutingProtocol()
    {
        Element routing = document.createElement(routingProtocolElement);

        routing.appendChild(document
                .createTextNode(fields.getRoutingProtocol()));

        return routing;
    }

    private void importRoutingProtocol(Element routing)
    {
        fields.setRoutingProtocol(routing.getFirstChild().getNodeValue());
    }
    
    private Element createPropagation()
    {
    	Element propagation = document.createElement(propagationElement);
    	
    	propagation.appendChild(document
    			.createTextNode(fields.getPropagation()));
    	
    	return propagation;
    }
    
    private void importPropagation(Element propagation)
    {
    	fields.setPropagation(propagation.getFirstChild().getNodeValue());
    }
    
    private Element createEnergyModel()
    {
    	Element energyModel = document.createElement(energyModelElement);
    	
    	energyModel.appendChild(document
    			.createTextNode(fields.getBattery()));
    	
    	return energyModel;
    }
    
    private void importEnergyModel(Element energyModel)
    {
    	fields.setBattery(energyModel.getFirstChild().getNodeValue());
    }

    /**
     * Creates an access point.
     * 
     * Xml path: /simulation/accessPoint
     */
    private Element createAccessPoint()
    {
        Element accessPoint = document.createElement(accessPointElement);

        accessPoint.appendChild(createNodeNetwork(fields
                .getAccessPointTransmissionRange()));
        accessPoint
                .appendChild(createNodeEnergy(fields.getAccessPointEnergy()));

        if (isAbsolutePosition(fields.getAccessPointLocation()))
            accessPoint.appendChild(createAbsolutePositon(fields
                    .getAccessPointLocation()));
        else
            accessPoint.setAttribute("distribution", fields
                    .getAccessPointLocation().toLowerCase());

        accessPoint.appendChild(createApplication(fields
                .getAccessPointApplication()));

        accessPoint.setAttribute("times", Integer.toString(fields
                .getAccessPointNumber()));

        return accessPoint;
    }

    private void importAccessPoint(Element accessPoint)
    {
        fields
                .setAccessPointTransmissionRange(importNodeTransmissionRange(accessPoint));
        fields.setAccessPointEnergy(importNodeEnergy(accessPoint));
        fields.setAccessPointLocation(importNodePosition(accessPoint));
        fields.setAccessPointApplication(importNodeApplication(accessPoint));
        fields.setAccessPointNumber(importNodeTimes(accessPoint));
    }

    private Element createCommonNode()
    {
        Element node = document.createElement(commonNodeElement);

        node.appendChild(createNodeNetwork(fields
                .getCommonNodeTransmissionRange(), fields
                .getCommonNodeDissType(), fields.getCommonNodeDissInterval()));
        node.appendChild(createNodeEnergy(fields.getCommonNodeEnergy()));

        if (isAbsolutePosition(fields.getCommonNodeLocation()))
            node.appendChild(createAbsolutePositon(fields
                    .getCommonNodeLocation()));
        else
            node.setAttribute("distribution", fields.getCommonNodeLocation()
                    .toLowerCase());

        node.appendChild(createApplication(fields.getCommonNodeApplication()));
        node
                .appendChild(createProcessing(fields
                        .getCommonNodeProcessingType()));
        node.appendChild(createDataGenerator(fields
                .getCommonNodeDataGeneratorType(), fields
                .getCommonNodeSensingInterval(), fields
                .getCommonNodeSensingType(), fields.getCommonNodeDataAverage(),
                fields.getCommonNodeDataDeviation(), fields
                        .getCommonNodeMaximumData()));

        node.setAttribute("times", Integer.toString(fields
                .getCommonNodeNumber()));

        return node;
    }

    private void importCommonNode(Element commonNode)
    {
        Element dataGenerator;

        dataGenerator = (Element) commonNode.getElementsByTagName(
                dataGeneratorElement).item(0);

        fields
                .setCommonNodeTransmissionRange(importNodeTransmissionRange(commonNode));
        fields.setCommonNodeDissType(importNodeDisseminatingType(commonNode));
        fields
                .setCommonNodeDissInterval(importNodeDisseminatingInterval(commonNode));
        fields.setCommonNodeEnergy(importNodeEnergy(commonNode));
        fields.setCommonNodeLocation(importNodePosition(commonNode));
        fields.setCommonNodeApplication(importNodeApplication(commonNode));
        fields.setCommonNodeProcessingType(importNodeProcessing(commonNode));
        fields
                .setCommonNodeDataGeneratorType(importDataGeneratorType(dataGenerator));
        fields
                .setCommonNodeSensingInterval(importDataGeneratorSensingInterval(dataGenerator));
        fields
                .setCommonNodeSensingType(importDataGeneratorSensingType(dataGenerator));
        fields
                .setCommonNodeDataAverage(importDataGeneratorAvgMeasure(dataGenerator));
        fields
                .setCommonNodeDataDeviation(importDataGeneratorStdDeviation(dataGenerator));
        fields.setCommonNodeMaximumData(importDataGeneratorMax(dataGenerator));
    }

    private Element createClusterHead()
    {
        Element clusterHead = document.createElement(clusterHeadElement);

        clusterHead
                .appendChild(createNodeNetwork(fields
                        .getClusterHeadTransmissionRange(), fields
                        .getClusterHeadDissType(), fields
                        .getClusterHeadDissInterval()));
        clusterHead
                .appendChild(createNodeEnergy(fields.getClusterHeadEnergy()));

        if (isAbsolutePosition(fields.getClusterHeadLocation()))
            clusterHead.appendChild(createAbsolutePositon(fields
                    .getClusterHeadLocation()));
        else
            clusterHead.setAttribute("distribution", fields
                    .getClusterHeadLocation().toLowerCase());

        clusterHead.appendChild(createApplication(fields
                .getClusterHeadApplication()));
        clusterHead.appendChild(createProcessing(fields
                .getClusterHeadProcessingType()));

        clusterHead.setAttribute("times", Integer.toString(fields
                .getClusterHeadNumber()));

        return clusterHead;
    }

    private void importClusterHead(Element clusterHead)
    {
        if (clusterHead != null)
        {
            fields
                    .setClusterHeadTransmissionRange(importNodeTransmissionRange(clusterHead));
            fields
                    .setClusterHeadDissType(importNodeDisseminatingType(clusterHead));
            fields
                    .setClusterHeadDissInterval(importNodeDisseminatingInterval(clusterHead));
            fields.setClusterHeadEnergy(importNodeEnergy(clusterHead));
            fields.setClusterHeadLocation(importNodePosition(clusterHead));
            fields
                    .setClusterHeadApplication(importNodeApplication(clusterHead));
            fields
                    .setClusterHeadProcessingType(importNodeProcessing(clusterHead));
            fields.setClusterHeadNumber(importNodeTimes(clusterHead));
        }
    }

    /**
     * Creates a node network architecture.
     */
    private Element createNodeNetwork(double range)
    {
        Element network = document
                .createElement(nodeNetworkArchitectureElement);

        network.appendChild(createWirelessPhy(fields.getPhyLayer(), range));

        return network;
    }

    private int importNodeTimes(Element node)
    {
        return Integer.parseInt(node.getAttribute("times"));
    }

    /**
     * Imports the radio range from a node.
     * 
     * @param node
     *            AccessPoint, ClusterHead or CommonNode XML element.
     * 
     * @return Radio range.
     */
    private double importNodeTransmissionRange(Element node)
    {
        return importWirelessPhyRange((Element) getNodeNetwork(node)
                .getElementsByTagName(wirelessPhyElement).item(0));
    }

    private Element getNodeNetwork(Element node)
    {
        return (Element) node.getElementsByTagName(
                nodeNetworkArchitectureElement).item(0);
    }

    /**
     * Creates a node network architecture.
     */
    private Element createNodeNetwork(double range, String disseminatingType,
            double disseminatingInterval)
    {
        Element network = createNodeNetwork(range);

        network.appendChild(createDisseminating(disseminatingType,
                disseminatingInterval));

        return network;
    }

    private Element createDisseminating(String type, double interval)
    {
        Element disseminating = document.createElement(disseminatingElement);

        disseminating.appendChild(document.createTextNode(type.toLowerCase()));
        disseminating.setAttribute("interval", Double.toString(interval));

        return disseminating;
    }

    /**
     * Imports node disseminating type.
     * 
     * @param node
     *            AccessPoint, ClusterHead or CommoNode XML element.
     * 
     * @return Disseminating type.
     */
    private String importNodeDisseminatingType(Element node)
    {
        return ((Element) node.getElementsByTagName(disseminatingElement).item(
                0)).getFirstChild().getNodeValue();
    }

    private double importNodeDisseminatingInterval(Element node)
    {
        return Double.parseDouble(((Element) node.getElementsByTagName(
                disseminatingElement).item(0)).getAttribute("interval"));
    }

    /**
     * Creates a node initial energy.
     * 
     * @param energy
     *            Initial energy.
     */
    private Element createNodeEnergy(double energy)
    {
        Element energyElement = document.createElement(nodeEnergyElement);

        energyElement.appendChild(document.createTextNode(Double
                .toString(energy)));

        return energyElement;
    }

    private double importNodeEnergy(Element node)
    {
        return Double.parseDouble(node.getElementsByTagName(nodeEnergyElement)
                .item(0).getFirstChild().getNodeValue());
    }

    /**
     * Check if the string position is an absolute position.
     * 
     * @return Returns if "position" is an absolute position.
     */
    private boolean isAbsolutePosition(String position)
    {
        boolean itIs = false;

        for (int i = 0; i < absolutePositions.length; i++)
            itIs |= position == absolutePositions[i];

        return itIs;
    }

    /**
     * Creates an absolute position based on a string.
     * 
     * @return Xml absolute position element.
     */
    private Element createAbsolutePositon(String strPosition)
    {
        Element position = document.createElement(absolutePositionElement);
        double x = 0, y = 0;

        // "CENTER", "UP LEFT CORNER", "DOWN LEFT CORNER", "UP RIGHT CORNER",
        // "DOWN RIGHT CORNER",

        if (strPosition == absolutePositions[0]) // Centered
        {
            x = fields.getScenarioX() / 2;
            y = fields.getScenarioY() / 2;
        }
        else if (strPosition == absolutePositions[1]) // Top left
        {
            x = y = 0;
        }
        else if (strPosition == absolutePositions[2]) // Bottom left
        {
            x = 0;
            y = fields.getScenarioY();
        }
        else if (strPosition == absolutePositions[3]) // Top right
        {
            x = fields.getScenarioX();
            y = 0;
        }
        else if (strPosition == absolutePositions[4]) // Bottom right
        {
            x = fields.getScenarioX();
            y = fields.getScenarioY();
        }

        position.setAttribute("x", Double.toString(x));
        position.setAttribute("y", Double.toString(y));
        position.setAttribute("z", "0.0");
        position.setAttribute("description", strPosition);

        return position;
    }

    private String importNodePosition(Element node)
    {
        Element absolutePosition;

        absolutePosition = (Element) node.getElementsByTagName(
                absolutePositionElement).item(0);

        if (absolutePosition != null)
        {
            String description;

            description = ((Element) node.getElementsByTagName(
                    absolutePositionElement).item(0))
                    .getAttribute("description");

            if (description != null && description != "")
                return description;
        }

        return node.getAttribute("distribution");
    }

    private Element createApplication(String application)
    {
        Element appElement = document.createElement(applicationElement);

        appElement.appendChild(document.createTextNode(application));

        return appElement;
    }

    private String importNodeApplication(Element node)
    {
        return node.getElementsByTagName(applicationElement).item(0)
                .getFirstChild().getNodeValue();
    }

    private Element createProcessing(String type)
    {
        Element processing = document.createElement(processingElement);

        processing.appendChild(document.createTextNode(type));

        return processing;
    }

    private String importNodeProcessing(Element node)
    {
        return node.getElementsByTagName(processingElement).item(0)
                .getFirstChild().getNodeValue();
    }

    private Element createDataGenerator(String type, double sensingInterval,
            String sensingType, double avgMeasure, double stdDeviation,
            double max)
    {
        Element dataGenerator = document.createElement(dataGeneratorElement);
        Element eSensingInterval = document
                .createElement(sensingIntervalElement);
        Element eSensingType = document.createElement(sensingTypeElement);
        Element eAvgMeasure = document.createElement(avgMeasureElement);
        Element eStdDeviation = document.createElement(stdDeviationElement);
        Element eMax = document.createElement(maximumDataElement);

        eSensingInterval.appendChild(document.createTextNode(Double
                .toString(sensingInterval)));
        eSensingType.appendChild(document.createTextNode(sensingType.toLowerCase()));
        eAvgMeasure.appendChild(document.createTextNode(Double
                .toString(avgMeasure)));
        eStdDeviation.appendChild(document.createTextNode(Double
                .toString(stdDeviation)));
        eMax.appendChild(document.createTextNode(Double.toString(max)));

        dataGenerator.appendChild(eSensingInterval);
        dataGenerator.appendChild(eSensingType);
        dataGenerator.appendChild(eAvgMeasure);
        dataGenerator.appendChild(eStdDeviation);
        dataGenerator.appendChild(eMax);

        dataGenerator.setAttribute("type", type);

        return dataGenerator;
    }

    private String importDataGeneratorType(Element dataGenerator)
    {
        return dataGenerator.getAttribute("type");
    }

    private double importDataGeneratorSensingInterval(Element dataGenerator)
    {
        return Double.parseDouble(dataGenerator.getElementsByTagName(
                sensingIntervalElement).item(0).getFirstChild().getNodeValue());
    }

    private String importDataGeneratorSensingType(Element dataGenerator)
    {
        return dataGenerator.getElementsByTagName(sensingTypeElement).item(0)
                .getFirstChild().getNodeValue();
    }

    private double importDataGeneratorAvgMeasure(Element dataGenerator)
    {
        return Double.parseDouble(dataGenerator.getElementsByTagName(
                avgMeasureElement).item(0).getFirstChild().getNodeValue());
    }

    private double importDataGeneratorStdDeviation(Element dataGenerator)
    {
        return Double.parseDouble(dataGenerator.getElementsByTagName(
                stdDeviationElement).item(0).getFirstChild().getNodeValue());
    }

    private double importDataGeneratorMax(Element dataGenerator)
    {
        return Double.parseDouble(dataGenerator.getElementsByTagName(
                maximumDataElement).item(0).getFirstChild().getNodeValue());
    }
}