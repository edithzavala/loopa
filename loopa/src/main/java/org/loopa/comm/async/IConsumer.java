package org.loopa.comm.async;

public interface IConsumer {
	public IMessage consume(IAsyncBuffer buffer);
}
