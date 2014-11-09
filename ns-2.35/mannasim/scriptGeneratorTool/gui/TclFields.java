/*
 * TclFields.java
 *
 * Created on 25 de Novembro de 2005, 11:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gui;

/**
 *
 * @author Helen Peters
 */
public class TclFields {
    
    /** Creates a new instance of TclFields */
    public TclFields() {
        setChannelType("Channel/WirelessChannel");
        setTransportProtocol("UDP");
        setRoutingProtocol("AODV");
        setMacLayer("MAC/802_11");
        setLinkLayer("LL");
        setPhyLayer("Phy/WirelessPhy - Mica2");
        setAntenna("Antenna/OmniAntenna");
        setPropagation("Propagation/TwoRayGround");
        setIfq("Queue/DropTail");
        setIfqLen(50);
        setBattery("EnergyModel/Battery");
        setScenarioX(100);
        setScenarioY(100);
        setTraceFile(true);
        setTraceFileName("trace.tr");
        setTraceRoute(true);
        setTraceMac(true);
        setTraceAgent(true);
        setSimulationStart(0);
        setSimulationStop(100);
        setAccessPointNumber(1);
        setAccessPointLocation("CENTER");
        setAccessPointEnergy(100);
        setAccessPointApplication("Application/AccessPointApp"); 
        setAccessPointTransmissionRange(100);
        setClusterHeadNumber(0);
        setClusterHeadLocation("RANDOM");
        setClusterHeadEnergy(10);
        setClusterHeadApplication("Application/SensorBaseApp/ClusterHeadApp"); 
        setClusterHeadProcessingType("Processing/AggregateProcessing"); 
        setClusterHeadTransmissionRange(70);
        setClusterHeadDissType("Programmed");
        setClusterHeadDissInterval(50);
        setCommonNodeNumber(10);
        setCommonNodeLocation("GRID");
        setCommonNodeEnergy(10);
        setCommonNodeApplication("Application/SensorBaseApp/CommonNodeApp");
        setCommonNodeProcessingType("Processing/AggregateProcessing");
        setCommonNodeTransmissionRange(50);
        setCommonNodeDissType("Programmed");
        setCommonNodeDissInterval(20);    
        setCommonNodeSensingType("Programmed");
        setCommonNodeSensingInterval(5);       
        setCommonNodeDataGeneratorType("TemperatureDataGenerator");
        setCommonNodeDataAverage(25);
        setCommonNodeDataDeviation(5);
        setCommonNodeMaximumData(30); 
    }
    
    private String channelType;
    private String transportProtocol;
    private String routingProtocol;
    private String macLayer;
    private String linkLayer;
    private String phyLayer;
    private String antenna;
    private String propagation;
    private String ifq;
    private int ifqLen;
    private String battery;
    private double scenaryX;
    private double scenaryY;
    private boolean traceFile;
    private String traceFileName;
    private boolean traceRoute;
    private boolean traceMac;
    private boolean traceAgent;
    private double simulationStart;
    private double simulationStop;
    private int accessPointNumber;
    private String accessPointLocation;
    private double accessPointEnergy;
    private String accessPointApplication; 
    private double accessPointTransmissionRange;
    private int clusterHeadNumber;
    private String clusterHeadLocation;
    private double  clusterHeadEnergy;
    private String  clusterHeadApplication; 
    private String  clusterHeadProcessingType; 
    private double  clusterHeadTransmissionRange;
    private String  clusterHeadDissType;
    private double clusterHeadDissInterval;
    private int commonNodeNumber;
    private String commonNodeLocation;
    private double commonNodeEnergy;
    private String commonNodeApplication; 
    private String commonNodeProcessingType; 
    private double commonNodeTransmissionRange;
    private String commonNodeDissType;
    private double commonNodeDissInterval;    
    private String commonNodeSensingType;
    private double commonNodeSensingInterval;       
    private String commonNodeDataGeneratorType;
    private double commonNodeDataAverage;
    private double commonNodeDataDeviation;
    private double commonNodeMaximumData;
    
    // Get/Set Methods

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getRoutingProtocol() {
        return routingProtocol;
    }

    public void setRoutingProtocol(String routingProtocol) {
        this.routingProtocol = routingProtocol;
    }

    public String getMacLayer() {
        return macLayer;
    }

    public void setMacLayer(String macLayer) {
        this.macLayer = macLayer;
    }

    public String getLinkLayer() {
        return linkLayer;
    }

    public void setLinkLayer(String linkLayer) {
        this.linkLayer = linkLayer;
    }

    public String getPhyLayer() {
        return phyLayer;
    }

    public void setPhyLayer(String phyLayer) {
        this.phyLayer = phyLayer;
    }

    public String getAntenna() {
        return antenna;
    }

    public void setAntenna(String antenna) {
        this.antenna = antenna;
    }

    public String getPropagation() {
        return propagation;
    }

    public void setPropagation(String propagation) {
        this.propagation = propagation;
    }

    public String getIfq() {
        return ifq;
    }

    public void setIfq(String ifq) {
        this.ifq = ifq;
    }

    public int getIfqLen() {
        return ifqLen;
    }

    public void setIfqLen(int ifqLen) {
        this.ifqLen = ifqLen;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public double getScenarioX() {
        return scenaryX;
    }

    public void setScenarioX(double scenaryX) {
        this.scenaryX = scenaryX;
    }

    public double getScenarioY() {
        return scenaryY;
    }

    public void setScenarioY(double scenaryY) {
        this.scenaryY = scenaryY;
    }

    public boolean isTraceFile() {
        return traceFile;
    }

    public void setTraceFile(boolean traceFile) {
        this.traceFile = traceFile;
    }

    public String getTraceFileName() {
        return traceFileName;
    }

    public void setTraceFileName(String traceFileName) {
        this.traceFileName = traceFileName;
    }

    public boolean isTraceRoute() {
        return traceRoute;
    }

    public void setTraceRoute(boolean traceRoute) {
        this.traceRoute = traceRoute;
    }

    public boolean isTraceMac() {
        return traceMac;
    }

    public void setTraceMac(boolean traceMac) {
        this.traceMac = traceMac;
    }

    public boolean isTraceAgent() {
        return traceAgent;
    }

    public void setTraceAgent(boolean traceAgent) {
        this.traceAgent = traceAgent;
    }

    public double getSimulationStart() {
        return simulationStart;
    }

    public void setSimulationStart(double simulationStart) {
        this.simulationStart = simulationStart;
    }

    public double getSimulationStop() {
        return simulationStop;
    }

    public void setSimulationStop(double simulationStop) {
        this.simulationStop = simulationStop;
    }

    public int getAccessPointNumber() {
        return accessPointNumber;
    }

    public void setAccessPointNumber(int accessPointNumber) {
        this.accessPointNumber = accessPointNumber;
    }

    public String getAccessPointLocation() {
        return accessPointLocation;
    }

    public void setAccessPointLocation(String accessPointLocation) {
        this.accessPointLocation = accessPointLocation;
    }

    public double getAccessPointEnergy() {
        return accessPointEnergy;
    }

    public void setAccessPointEnergy(double accessPointEnergy) {
        this.accessPointEnergy = accessPointEnergy;
    }

    public String getAccessPointApplication() {
        return accessPointApplication;
    }

    public void setAccessPointApplication(String accessPointApplication) {
        this.accessPointApplication = accessPointApplication;
    }

    public double getAccessPointTransmissionRange() {
        return accessPointTransmissionRange;
    }

    public void setAccessPointTransmissionRange(double accessPointTransmissionRange) {
        this.accessPointTransmissionRange = accessPointTransmissionRange;
    }

    public int getClusterHeadNumber() {
        return clusterHeadNumber;
    }

    public void setClusterHeadNumber(int clusterHeadNumber) {
        this.clusterHeadNumber = clusterHeadNumber;
    }

    public String getClusterHeadLocation() {
        return clusterHeadLocation;
    }

    public void setClusterHeadLocation(String clusterHeadLocation) {
        this.clusterHeadLocation = clusterHeadLocation;
    }

    public double getClusterHeadEnergy() {
        return clusterHeadEnergy;
    }

    public void setClusterHeadEnergy(double clusterHeadEnergy) {
        this.clusterHeadEnergy = clusterHeadEnergy;
    }

    public String getClusterHeadApplication() {
        return clusterHeadApplication;
    }

    public void setClusterHeadApplication(String clusterHeadApplication) {
        this.clusterHeadApplication = clusterHeadApplication;
    }

    public String getClusterHeadProcessingType() {
        return clusterHeadProcessingType;
    }

    public void setClusterHeadProcessingType(String clusterHeadProcessingType) {
        this.clusterHeadProcessingType = clusterHeadProcessingType;
    }

    public double getClusterHeadTransmissionRange() {
        return clusterHeadTransmissionRange;
    }

    public void setClusterHeadTransmissionRange(double clusterHeadTransmissionRange) {
        this.clusterHeadTransmissionRange = clusterHeadTransmissionRange;
    }

    public String getClusterHeadDissType() {
        return clusterHeadDissType;
    }

    public void setClusterHeadDissType(String clusterHeadDissType) {
        this.clusterHeadDissType = clusterHeadDissType;
    }

    public double getClusterHeadDissInterval() {
        return clusterHeadDissInterval;
    }

    public void setClusterHeadDissInterval(double clusterHeadDissInterval) {
        this.clusterHeadDissInterval = clusterHeadDissInterval;
    }

    public int getCommonNodeNumber() {
        return commonNodeNumber;
    }

    public void setCommonNodeNumber(int commonNodeNumber) {
        this.commonNodeNumber = commonNodeNumber;
    }

    public String getCommonNodeLocation() {
        return commonNodeLocation;
    }

    public void setCommonNodeLocation(String commonNodeLocation) {
        this.commonNodeLocation = commonNodeLocation;
    }

    public double getCommonNodeEnergy() {
        return commonNodeEnergy;
    }

    public void setCommonNodeEnergy(double commonNodeEnergy) {
        this.commonNodeEnergy = commonNodeEnergy;
    }

    public String getCommonNodeApplication() {
        return commonNodeApplication;
    }

    public void setCommonNodeApplication(String commonNodeApplication) {
        this.commonNodeApplication = commonNodeApplication;
    }

    public String getCommonNodeProcessingType() {
        return commonNodeProcessingType;
    }

    public void setCommonNodeProcessingType(String commonNodeProcessingType) {
        this.commonNodeProcessingType = commonNodeProcessingType;
    }

    public double getCommonNodeTransmissionRange() {
        return commonNodeTransmissionRange;
    }

    public void setCommonNodeTransmissionRange(double commonNodeTransmissionRange) {
        this.commonNodeTransmissionRange = commonNodeTransmissionRange;
    }

    public String getCommonNodeDissType() {
        return commonNodeDissType;
    }

    public void setCommonNodeDissType(String commonNodeDissType) {
        this.commonNodeDissType = commonNodeDissType;
    }

    public double getCommonNodeDissInterval() {
        return commonNodeDissInterval;
    }

    public void setCommonNodeDissInterval(double commonNodeDissInterval) {
        this.commonNodeDissInterval = commonNodeDissInterval;
    }

    public String getCommonNodeSensingType() {
        return commonNodeSensingType;
    }

    public void setCommonNodeSensingType(String commonNodeSensingType) {
        this.commonNodeSensingType = commonNodeSensingType;
    }

    public double getCommonNodeSensingInterval() {
        return commonNodeSensingInterval;
    }

    public void setCommonNodeSensingInterval(double commonNodeSensingInterval) {
        this.commonNodeSensingInterval = commonNodeSensingInterval;
    }

    public String getCommonNodeDataGeneratorType() {
        return commonNodeDataGeneratorType;
    }

    public void setCommonNodeDataGeneratorType(String commonNodeDataGeneratorType) {
        this.commonNodeDataGeneratorType = commonNodeDataGeneratorType;
    }

    public double getCommonNodeDataAverage() {
        return commonNodeDataAverage;
    }

    public void setCommonNodeDataAverage(double commonNodeDataAverage) {
        this.commonNodeDataAverage = commonNodeDataAverage;
    }

    public double getCommonNodeDataDeviation() {
        return commonNodeDataDeviation;
    }

    public void setCommonNodeDataDeviation(double commonNodeDataDeviation) {
        this.commonNodeDataDeviation = commonNodeDataDeviation;
    }

    public double getCommonNodeMaximumData() {
        return commonNodeMaximumData;
    }

    public void setCommonNodeMaximumData(double commonNodeMaximumData) {
        this.commonNodeMaximumData = commonNodeMaximumData;
    }
    
    
}
