package backend.repository;

import backend.subscription.Subscriber;

public class SubscriberRepository {
	private static final int MAX_SUBSCRIBERS = 20;
	private Subscriber[] subscribers = new Subscriber[MAX_SUBSCRIBERS];
	private static int count = 0;

	public Subscriber[] getSubscribers() {
		return subscribers;
	}

	public boolean addSubscriber(Subscriber subscriber) {
		if (subscriber == null)
			throw new IllegalArgumentException("ERROR: Subscriber provided is null");
		if (count < MAX_SUBSCRIBERS && (findSubscriberByEmail(subscriber.getSubscriberEmail()) == null)) {
			subscribers[count] = subscriber;
			count++;
			return true;
		} else if (findSubscriberByEmail(subscriber.getSubscriberEmail()) != null) {
			throw new IllegalArgumentException("ERROR: Existing user with this email");
		} else
			return false;

	}

	public void deactivateSubscriber(Subscriber subscriber) {
		if (subscriber == null)
			throw new IllegalArgumentException("ERROR: Subscriber provided is null");
		subscriber.setActivity(false);
	}

	private void activateSubscriber(Subscriber subscriber) {
		if (subscriber == null)
			throw new IllegalArgumentException("ERROR: Subscriber provided is null");
		subscriber.setActivity(true);
	}

	public Subscriber findSubscriberByEmail(String subscriberEmail) {
		if (subscriberEmail == null || subscriberEmail.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Email provided is null");
		for (Subscriber i : subscribers) {
			if (i != null && i.getSubscriberEmail().equals(subscriberEmail)) {
				return i;
			}
			if (i == null)
				return null;
		}
		throw new IllegalArgumentException("ERROR: No users with this email found");
	}
}
