package mqtt;

public class SdmMessage {
	public final SdmTopic topic;
	public final byte[] message;
	
	public SdmMessage(SdmTopic topic, byte[] message) {
		this.topic = topic;
		this.message = message;
	}
}
