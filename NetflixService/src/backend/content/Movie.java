package backend.content;

public class Movie extends Content {
	private int duration;

	public Movie(String title, String genre, String director, int duration) {
		super(title, genre, director);
		if (duration <= 0)
			throw new IllegalArgumentException("ERROR: Duration cannot be 0 or negative");
		this.duration = duration;
	}

	public Movie(String title, int duration) {
		this(title, "Unknown", "Unknown", duration);
	}

	private int getDuration() {
		return duration;
	}

	@Override
	public String printDetails() {
		StringBuffer detailBuf = new StringBuffer(super.printDetails());
		detailBuf.append("\nDuration: " + getDuration());

		String details = detailBuf.toString();
		return details;
	}

}
