package org.loopa.examples;

import org.loopa.comm.message.IMessage;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.sensor.ISensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sensor implements ISensor {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private final IMonitor m;


  public Sensor(IMonitor m) {
    super();
    this.m = m;
  }

  @Override
  public void processSensorData(IMessage m) {
    LOGGER.info("Send data to LoopA");
    this.m.getReceiver().doOperation(m);
  }

}
