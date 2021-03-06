package archived.artifactsreloaded.common.util.artifact.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.netty.buffer.ByteBuf;


public class CToSMessage implements IMessage {

private byte[] data = new byte[0];

public CToSMessage()
{
	this(new byte[0]);
}

public CToSMessage(ByteBuf dataToSet)
{
	this(dataToSet.array());
}

public CToSMessage(byte[] dataToSet)
{

	if (dataToSet.length > 0x1ffff0)
	{
		throw new IllegalArgumentException("Payload may not be larger than 2097136 (0x1ffff0) bytes");
	}

	this.data = dataToSet;

}

/**
	* Deconstruct your message into the supplied byte buffer
	* @param buffer is what you cal the buffer from
	*/
@Override
public void toBytes(ByteBuf buffer) {
//		System.out.println("Encoding");
	if (data.length > 0x1ffff0)
	{
		throw new IllegalArgumentException("Payload may not be larger than 2097136 (0x1ffff0) bytes");
	}

	buffer.writeShort(this.data.length);
	buffer.writeBytes(this.data);
}

/**
	* Convert from the supplied buffer into your specific message type
	* @param buffer
	*/
@Override
public void fromBytes(ByteBuf buffer) {
//		System.out.println("Decoding");

	this.data = new byte[buffer.readShort()];
	buffer.readBytes(this.data);
}

public byte[] getData() {
	return this.data;
}

}

