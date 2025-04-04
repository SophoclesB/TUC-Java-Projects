package backend.content;

public class Series extends Content {
	private int seasons;
	private String[][] episodesPerSeason; // i: season, j: episode

	public Series(String title, String genre, String director, int seasons, int maxEpisodesPerSeason) {
		super(title, genre, director);
		if (seasons <= 0)
			throw new IllegalArgumentException("ERROR: Number of seasons cannot be negative or zero");
		if (maxEpisodesPerSeason <= 0)
			throw new IllegalArgumentException("ERROR: Max episodes per season cannot be negative or zero");
		this.seasons = seasons;
		this.episodesPerSeason = new String[seasons][maxEpisodesPerSeason];
	}

	public Series(String title, int seasons, int maxEpisodesPerSeason) {
		this(title, "Unknown", "Unknown", seasons, maxEpisodesPerSeason);
	}

	private int getSeasons() {
		return seasons;
	}

	public void addEpisode(int season, int episodeIndex, String episodeTitle) {
		if (season <= 0 || episodeIndex < 0 || episodeIndex > episodesPerSeason.length + 1 || episodeTitle == null
				|| episodeTitle.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Illegal arguments provided");
		this.episodesPerSeason[season - 1][episodeIndex] = episodeTitle;
	}

	public void removeEpisode(int season, String episodeTitle) {
		if (season > this.seasons || season < 0 || episodeTitle == null)
			throw new IllegalArgumentException("ERROR: invalid arguments");

		int seasonIndex = season - 1;
		for (int i = 0; i < episodesPerSeason[seasonIndex].length; i++) {
			if (episodesPerSeason[seasonIndex][i].equals(episodeTitle)) {
				episodesPerSeason[seasonIndex][i] = null;
				return;
			}
		}
		throw new NullPointerException("ERROR: Episode could not be found");
	}

	public void updateEpisode(int season, int episodeIndex, String newTitle) {
		if (season <= 0 || episodeIndex <= 0 || episodeIndex > episodesPerSeason.length || newTitle == null
				|| newTitle.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Illegal arguments provided");
		this.episodesPerSeason[season - 1][episodeIndex] = newTitle;
		System.out.println("Episode modified successfully: " + newTitle);
	}

	private String[] getEpisodes(int season) {
		return episodesPerSeason[season];
	}

	@Override
	public String printDetails() {
		StringBuffer detailBuf = new StringBuffer(super.printDetails());
		detailBuf.append("\nSeasons: " + getSeasons() + "\n");
		for (int i = 0; i < getSeasons(); i++) {
			detailBuf.append("Season " + (i + 1) + ": " + getEpisodes(i)[0]);
			for (int j = 1; j < getEpisodes(i).length; j++) {
				if (getEpisodes(i)[j] == null)
					break;
				detailBuf.append(", " + getEpisodes(i)[j]);
			}
			detailBuf.append('\n');
		}

		String details = detailBuf.toString();
		return details;
	}

}
