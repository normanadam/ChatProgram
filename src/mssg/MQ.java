package mssg;

import java.util.ArrayList;

public class MQ {
	ArrayList<MQMessage>	queue;

	public MQ() {
		this.queue = new ArrayList<MQMessage>();
	}

	public synchronized void addMessage(MQMessage message) {
		queue.add(message);
	}

	public synchronized MQMessage getMessage() {
		if (queue.isEmpty()) {
			return null;
		} else {
			MQMessage message = queue.get(0);
			queue.remove(0);
			return message;
		}
	}
}
