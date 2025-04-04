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
		for (int i = 0; i < contentRepo.getAllContent().length; i++) {
			if (contentRepo.getAllContent()[i] instanceof Movie)
				movieCount++;
		}
		Movie[] allMovies = new Movie[movieCount];
		for (int i = 0; i < movieCount; i++) {
			if (contentRepo.getAllContent()[i] instanceof Movie)
				allMovies[i] = (Movie) contentRepo.getAllContent()[i];
		}

		Algos.quickSort(allMovies, 0, allMovies.length - 1);

		int recCount = 0;
		Movie[] recommendedMovies = new Movie[maxResults];
		for (int i = allMovies.length - 1; i >= 0; i--) {
			if (recCount <= maxResults) {
				for (int j = 1; j < allMovies.length; j++) {
				}
			} else
				break;
		}
		return recommendedMovies;
	}

	public void printRecommendations(Content[] recommendedMovies, Subscriber requestsRecs) {

	}
}
