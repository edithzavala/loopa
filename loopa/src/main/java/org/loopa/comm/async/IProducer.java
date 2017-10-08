package org.loopa.comm.async;

public interface IProducer {
	public void produce(IMessage m, IAsyncBuffer buffer);
}
