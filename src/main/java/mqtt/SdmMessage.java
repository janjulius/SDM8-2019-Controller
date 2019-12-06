package mqtt;

public class SdmMessage {
	public final SdmTopic topic;
	public byte[] message;
	
	public SdmMessage(SdmTopic topic, byte[] message) {
		this.topic = topic;
		this.message = message;
	}
	
	public void setMessage(byte[] message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "[SdmMessage] topic: " + topic.toString() + " message: " + message;
	}
}
