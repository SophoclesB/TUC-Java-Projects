package backend.subscription;

import backend.content.Content;

public class Subscriber {
	private String subscriberEmail;
	private String subscriberPassword;
	private SubscriptionPlan plan;
	private Content[] watchHistory;
	private final static int MAX_FAVORITES = 3;
	private final static int MAX_HISTORY = 10;
	private boolean active;
	private int watchCount;
	private String[] favoriteGenres;
	private int favoriteCount;

	public Subscriber(String email, String password, SubscriptionPlan plan) {
		if (plan == null)
			throw new IllegalArgumentException("ERROR: Plan cannot be null");
		setSubscriberEmail(email);
		setSubscriberPassword(password);
		this.plan = plan;
		this.active = true;
		this.watchCount = 0;
		this.watchHistory = new Content[MAX_HISTORY];
	}

	public String getSubscriberEmail() {
		return subscriberEmail;
	}

	private SubscriptionPlan getPlan() {
		return plan;
	}

	private boolean isActive() {
		return active;
	}

	public void setActivity(boolean set) {
		this.active = set;
	}

	private int getWatchCount() {
		return watchCount;
	}

	public String[] getFavorites() {
		return favoriteGenres;
	}

	private String getConcatenatedFavoriteGenres() {
		if (favoriteGenres != null) {
			StringBuffer s = new StringBuffer();
			s.append(favoriteGenres[0]);
			for (int i = 1; i < this.favoriteCount; i++) {
				s.append(", " + this.favoriteGenres[i]);
			}
			String concacentatedGenres = s.toString();
			return concacentatedGenres;
		} else
			return null;
	}

	public void setFavoriteGenres(String[] favoriteGenres) {
		if (favoriteGenres == null)
			throw new IllegalArgumentException("ERROR: Genre list cannot be null");
		for (int i = 0; i < favoriteGenres.length; i++) {
			if (favoriteGenres[i].trim().isEmpty())
				throw new IllegalArgumentException("ERROR: Genre list cannot contain empty elements");
			else if (favoriteGenres[i] == null)
				break;
		}
		if (this.isActive() && favoriteGenres.length <= MAX_FAVORITES) {
			this.favoriteGenres = new String[favoriteGenres.length];
			this.favoriteGenres = favoriteGenres;
			favoriteCount = favoriteGenres.length;
		} else if (!isActive())
			throw new IllegalArgumentException(
					"ERROR: User " + this.getSubscriberEmail() + " inactive, cannot add to favorite list");
		else
			throw new NullPointerException(
					"ERROR: Max number of favorite genres reached, system unable to add more");
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	private void setSubscriberEmail(String email) {
		if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
			subscriberEmail = email;
		else
			throw new IllegalArgumentException("ERROR: Invalid email");
	}

	private void setSubscriberPassword(String password) {
		if (password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"))
			subscriberPassword = password;
		else
			throw new IllegalArgumentException("ERROR: Invalid password, please give another.");
	}

	public void watchContent(Content content) {
		if (content == null)
			throw new IllegalArgumentException("ERROR: Content is null");
		if (isActive() && watchCount < MAX_HISTORY) {
			watchHistory[watchCount] = content;
			watchCount++;
			System.out.println(this.getSubscriberEmail() + " watched " + content.getTitle());
		} else if (isActive() && watchCount >= MAX_HISTORY) {
			for (int i = 0; i < MAX_HISTORY - 1; i++) {
				watchHistory[i] = watchHistory[i + 1];
			}
			watchHistory[MAX_HISTORY - 1] = content;
		} else {
			throw new IllegalArgumentException("ERROR: User inactive, cannot log content in watch history");
		}
	}

	private void printHistory() {
		if (watchHistory != null) {
			for (Content i : watchHistory) {
				if (i != null)
					System.out.println("- " + i.getTitle());
			}
		} else
			System.out.println("Empty");
	}

	public void printSubscriberDetails() {
		System.out.println("\n---------------------------------");
		System.out.println("? Email: " + getSubscriberEmail());
		System.out.println("? Subscription Plan: " + plan.getPlanName());
		System.out.println("? Price: " + plan.getPlanPrice());
		System.out.println("? Max Screens: " + plan.getPlanMaxScreens());
		System.out.println("? Active Status: " + (isActive() ? "Active" : "Inactive"));
		System.out.println("? Favorite Genres: "
				+ (getConcatenatedFavoriteGenres() != null ? getConcatenatedFavoriteGenres() : "None"));
		System.out.println("? Watch History:");
		printHistory();
	}

}
