package backend.service;

import backend.content.Content;
import backend.content.Movie;
import backend.repository.ContentRepository;
import backend.subscription.Subscriber;
import backend.utils.Algos;

public class NetflixService {
	ContentRepository contentRepo;

	public NetflixService(ContentRepository contentRepo) {
		if (contentRepo == null)
			throw new IllegalArgumentException("ERROR: Content repository provided is null");
		this.contentRepo = contentRepo;
	}

	public Content[] getRecommendedMoviesByFavoriteGenres(Subscriber subscriber, int maxResults) {
		if (subscriber == null || maxResults <= 0)
			throw new IllegalArgumentException("ERROR: Illegal Arguments");
		if (subscriber.getFavoriteCount() == 0)
			return null;
		int movieCount = 0;

		Content[] allContent = contentRepo.getAllContent();

		for (int i = 0; i < allContent.length; i++) {
			if (allContent[i] instanceof Movie)
				movieCount++;
			else
				allContent[i] = null;
		}

		Movie[] allMovies = new Movie[movieCount];
		for (int i = 0; i < movieCount; i++) {
			for (int j = 0; j < allContent.length; j++) {
				if (allContent[j] != null) {
					allMovies[i] = (Movie) allContent[j];
					allContent[j] = null;
					break;
				}
			}
		}

		Algos.quickSort(allMovies, 0, allMovies.length - 1); // Sort by ID (Recency)

		int genreCount = 0;
		outer: for (int i = allMovies.length - 1; i >= 0; i--) {
			for (int j = i + 1; j < allMovies.length; j++) {
				if (allMovies[j] != null && allMovies[i].getPrimaryGenre().equals(allMovies[j].getPrimaryGenre())) {
					allMovies[i] = null;
					continue outer;
				}
			}
			genreCount++;
		}

		Movie[] uniqueGenreMovies = new Movie[genreCount];
		genreCount = 0;
		for (int i = allMovies.length - 1; i >= 0; i--) {
			if (allMovies[i] != null)
				uniqueGenreMovies[genreCount++] = allMovies[i];
		}

		int recCount = 0;
		Movie[] recommendedMovies = new Movie[(genreCount <= maxResults) ? genreCount : maxResults];
		for (int i = 0; i < maxResults; i++) {
			recommendedMovies[i] = uniqueGenreMovies[i];
			if (i >= uniqueGenreMovies.length - 1)
				break;
		}

		for (int i = 0; i < uniqueGenreMovies.length; i++) {
			for (int j = 0; j < subscriber.getFavorites().length; j++) {
				if (uniqueGenreMovies[i].getPrimaryGenre().equals(subscriber.getFavorites()[j])) {
					for (int k = recommendedMovies.length - 1; k > recCount; k--) {
						recommendedMovies[k] = recommendedMovies[k - 1];
					}
					recommendedMovies[recCount] = uniqueGenreMovies[i];
					recCount++;
				}
			}
		}

		return recommendedMovies;
	}

	public void printRecommendations(Content[] recommendedMovies, Subscriber requestsRecs) {
		System.out.println("Movies recommended for Subscriber " + requestsRecs.getSubscriberEmail());
		for (int i = 0; i < recommendedMovies.length; i++) {
			System.out.println(
					"- " + recommendedMovies[i].getTitle() + "(? " + recommendedMovies[i].getAverageRating() + ")");
		}
	}
}
