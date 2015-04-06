package net.samagames.api.channels;

public class PendingMessage {
	private final String channel;
	private final String message;
	private final Runnable callback;

	public PendingMessage(String channel, String message) {
		this.channel = channel;
		this.message = message;
		this.callback = null;
	}

	public PendingMessage(String channel, String message, Runnable callback) {
		this.channel = channel;
		this.message = message;
		this.callback = callback;
	}

	public String getChannel() {
		return channel;
	}

	public String getMessage() {
		return message;
	}

	public void runAfter() {
		try {
			if (callback != null)
				callback.run();
		} catch (Exception ignored) {}
	}
}