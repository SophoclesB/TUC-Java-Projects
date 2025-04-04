package backend.content;

import backend.repository.ContentRepository;

public abstract class Content {
	private int contentId;
	private String title;
	private String primaryGenre;
	private double avgRating;
	private float[] ratings;
	private String[] actorNames;
	private int actorCount;
	private String director;
	private int ratingCount;
	final static private int MAX_RATINGS = 100;
	final static private int MAX_ACTORS = 10;
	private static int idCount = 0;

	protected Content(String title, String genre, String director) {
		if (title == null || title.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Content title cannot be null/empty");
		if (title == null || title.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Content genre cannot be null/empty");
		if (title == null || title.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Content director cannot be null/empty");

		this.title = title;
		this.primaryGenre = genre;
		this.director = director;
		this.contentId = getNewId();
		actorCount = 0;
		ratingCount = 0;
		actorNames = new String[MAX_ACTORS];
		ratings = new float[MAX_RATINGS];
	}

	protected Content(String title) {
		this(title, "Unknown", "Unknown");
	}

	private static int getNewId() {
		return idCount++;
	}

	public int getContentId() {
		return contentId;
	}

	public String getTitle() {
		return title;
	}

	public String getPrimaryGenre() {
		return primaryGenre;
	}

	protected void setPrimaryGenre(String primaryGenre) {
		this.primaryGenre = primaryGenre;
	}

	public void addRating(float rating) {
		if (ratingCount < MAX_RATINGS && rating > 0.0 && rating <= 5.0) {
			this.ratings[ratingCount] = rating;
			ratingCount++;
			this.avgRating = setAverageRating();
		} else if (ratingCount >= MAX_RATINGS) {
			throw new IllegalArgumentException("Max amount of ratings reached.");
		} else {
			throw new IllegalArgumentException("Rating must inside the valid range (0.0->5.0)");
		}

	}

	private float[] getRatings() {
		return ratings;
	}

	private String[] getActorNames() {
		return actorNames;
	}

	protected String getDirector() {
		return director;
	}

	protected void setDirector(String director) {
		this.director = director;
	}

	private int getRatingCount() {
		return ratingCount;
	}

	private String getGenre() {
		return primaryGenre;
	}

	private void setGenre(String genre) {
		this.primaryGenre = genre;
	}

	private double setAverageRating() {
		if (ratingCount == 0)
			return 0;
		this.avgRating = 0;
		for (int i = 0; i < ratingCount; i++)
			avgRating += ratings[i];
		avgRating /= ratingCount;
		return avgRating;
	}

	public double getAverageRating() {
		return avgRating;
	}

	public String getNonNullActors() {
		if (actorNames != null) {
			StringBuffer s = new StringBuffer();
			s.append(actorNames[0]);
			for (int i = 1; i < actorCount; i++) {
				if (actorNames[i] != null)
					s.append(", " + actorNames[i]);
			}
			String concatenatedActorNames = s.toString();
			if (concatenatedActorNames == null || concatenatedActorNames.trim().isEmpty())
				throw new NullPointerException("ERROR: All actors are null or empty");
			return concatenatedActorNames;
		} else
			return null;
	}

	public void addActor(String actorName) {
		if (actorName == null || actorName.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Actor name cannot be null or empty");
		if (actorCount < MAX_ACTORS) {
			actorNames[actorCount] = actorName;
			actorCount++;
		}

		else
			throw new IllegalArgumentException("\nActor list is full. Please remove an actor in order to add another.");

	}

	public void removeActor(String actorName) {
		if (actorName == null || actorName.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Actor name provided is null or empty");
		int pos = 0;
		for (String i : actorNames) {
			if (i.equals(actorName)) {
				actorNames[pos] = null;
				for (int j = pos + 1; j < actorNames.length; j++) {
					if (actorNames[j] == null) {
						break;
					}
					actorNames[j - 1] = actorNames[j];
					actorNames[j] = null;
				}
				break;
			}
			pos++;
		}
		actorCount--;
	}

	public String printDetails() {
		StringBuffer detailBuf = new StringBuffer("");
		detailBuf.append("\nContent ID: " + getContentId());
		detailBuf.append("\nTitle: " + getTitle());
		detailBuf.append("\nPrimary Genre: " + getPrimaryGenre());
		detailBuf.append("\nDirector: " + getDirector());
		detailBuf.append("\nAverage Rating: " + getAverageRating());
		detailBuf.append("\nNumber of Ratings: " + getRatingCount());
		detailBuf.append("\nActors: " + getNonNullActors());
		String details = detailBuf.toString();

		return details;
	}

}
