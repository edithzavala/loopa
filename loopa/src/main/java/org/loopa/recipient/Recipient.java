package org.loopa.recipient;

import java.util.List;

public class Recipient implements IRecipient {

  private final String id;

  private List<String> typeOfData;

  private final Object recipient;


  public Recipient(String id, List<String> typeOfData, Object recipient) {
    super();
    this.id = id;
    this.typeOfData = typeOfData;
    this.recipient = recipient;
  }


  @Override
  public Object getRecipient() {
    return this.recipient;
  }

  @Override
  public String getrecipientId() {
    return this.id;
  }

  @Override
  public List<String> getTypeOfData() {
    return this.typeOfData;
  }

  public void setTypeOfData(List<String> typeOfData) {
    this.typeOfData = typeOfData;
  }
}
