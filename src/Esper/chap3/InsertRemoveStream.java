package Esper.chap3;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import Esper.unit.EventType.SensorData;
import Esper.unit.Listener.GeneralListener;
import Esper.unit.Stream.StreamThread;

public class InsertRemoveStream {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// add EventType
		String Sensor = SensorData.class.getName();
		admin.getConfiguration().addEventType("Sensor", Sensor);

		// Sensor Stream divide into 3 different Stream
		String eplforTemp = "insert into tempSensor select * from  Sensor(id='Temp') ";
		String eplforHumidity = "insert into humiditySensor select * from   Sensor(id='Humidity') ";
		String eplforLight = "insert into lightSensor select * from  Sensor(id='Light') ";
		admin.createEPL(eplforTemp);
		admin.createEPL(eplforHumidity);
		admin.createEPL(eplforLight);

		
		// Epl: istream
		// String istreamEPL = "select istream * from
		// Sensor.win:length_batch(2)";
		// EPStatement stateistreamEPL = admin.createEPL(istreamEPL);
		// stateistreamEPL.addListener(new GeneralListener());

		// Epl: rstream
		// String rstreamEPL = "select rstream * from
		// Sensor.win:length_batch(2)";
		// EPStatement staterstreamEPL = admin.createEPL(rstreamEPL);
		// staterstreamEPL.addListener(new GeneralListener());

		// Epl: irstream
		String irstreamEPL = "select irstream * from Sensor.win:length_batch(2) ";
		EPStatement stateirstreamEPL = admin.createEPL(irstreamEPL);
		stateirstreamEPL.addListener(new GeneralListener());
		
		// run Sensor Thread
		StreamThread Temp = new StreamThread("Temp");
		StreamThread Humidity = new StreamThread("Humidity");
		StreamThread Light = new StreamThread("Light");
		Thread t = new Thread(Temp);
		Thread l = new Thread(Light);
		Thread h = new Thread(Humidity);
		t.start();
		// l.start();
		// h.start();

	
	}

}
