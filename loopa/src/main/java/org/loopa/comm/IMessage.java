package org.loopa.comm;

public interface IMessage {
	public String getConcept();

	public String getContent();

	public ISender getSender();

	public IDestination getDestination();
}
