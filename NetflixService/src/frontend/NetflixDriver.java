package frontend;

import backend.content.Content;
import backend.content.Movie;
import backend.content.Series;
import backend.repository.ContentRepository;
import backend.repository.SubscriberRepository;
import backend.service.NetflixService;
import backend.subscription.Subscriber;
import backend.subscription.SubscriptionPlan;

/**
 * Main driver class for the project.
 * Demonstrates the use of object-oriented programming principles such as:
 * - Encapsulation (hiding data through repositories and controlled access to
 * attributes)
 * - Inheritance (Movie and Series inherit from Content)
 * - Polymorphism (Content reference holding Movie or Series objects)
 * - Abstraction (NetflixService handles recommendations without exposing logic
 * details)
 *
 * @author ngiatrakos
 */
public class NetflixDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Creating repositories and service class (Encapsulation - Data Management)
        ContentRepository contentRepo = new ContentRepository(100); // Stores movies and series
        SubscriberRepository subscriberRepo = new SubscriberRepository(); // Manages user subscriptions
        NetflixService netflixService = new NetflixService(contentRepo); // Handles movie recommendations

        // Creating Movie objects (Inheritance - Movie extends Content)
        Movie movie1 = new Movie("Interstellar", "Sci-Fi", "Christopher Nolan", 169);
        Movie movie2 = new Movie("Mad Max: Fury Road", "Action", "George Miller", 120);
        Movie movie3 = new Movie("The Dark Knight", "Action", "Christopher Nolan", 152);
        Movie movie4 = new Movie("Inception", "Sci-Fi", "Christopher Nolan", 148);
        Movie movie5 = new Movie("Gladiator", "Drama", "Ridley Scott", 155);
        Movie movie6 = new Movie("The Matrix", "Sci-Fi", "The Wachowskis", 136);
        Movie movie7 = new Movie("John Wick", "Action", "Chad Stahelski", 101);
        Movie movie8 = new Movie("Avengers: Endgame", "Superhero", "Anthony & Joe Russo", 181);
        Movie movie9 = new Movie("The Godfather", "Crime", "Francis Ford Coppola", 175);
        Movie movie10 = new Movie("Joker", "Crime", "Todd Phillips", 122);

        // Storing movies in the repository (Encapsulation - Controlled Data Access)
        contentRepo.addContent(movie1);
        contentRepo.addContent(movie2);
        contentRepo.addContent(movie3);
        contentRepo.addContent(movie4);
        contentRepo.addContent(movie5);
        contentRepo.addContent(movie6);
        contentRepo.addContent(movie7);
        contentRepo.addContent(movie8);
        contentRepo.addContent(movie9);
        contentRepo.addContent(movie10);

        // Adding ratings to movies (Encapsulation - Hiding direct data manipulation)
        movie1.addRating(5);
        movie1.addRating(4.5f);
        movie1.addRating(4.8f);

        movie2.addRating(4.7f);
        movie2.addRating(4.9f);

        movie3.addRating(5);
        movie3.addRating(4.8f);
        movie3.addRating(5);

        // Retrieving and printing movie details (Polymorphism - Using Content reference
        // for Movies)
        System.out.println("\n---------------------------------");
        // Retrieve the movie from contentRepo
        Movie storedMovie = (Movie) contentRepo.findContentByTitle("Interstellar"); // casting the generic Content
                                                                                    // object to its subclass Movie

        if (storedMovie != null) {
            double avgRating = storedMovie.getAverageRating(); // Get average rating
            System.out.println("\n🎬 Average Rating for 'Interstellar': " + avgRating);
        } else {
            System.out.println("❌ 'Interstellar' not found in contentRepo.");
        }
        System.out.println("\n---------------------------------");

        // Creating Series objects (Inheritance - Series extends Content)
        Series series1 = new Series("Breaking Bad", "Crime", "Vince Gilligan", 2, 5); // 2 seasons, max 5 episodes per
                                                                                      // season
        series1.addEpisode(1, 0, "Pilot");
        series1.addEpisode(1, 1, "Cat's in the Bag...");
        series1.addEpisode(2, 0, "Seven Thirty-Seven");
        series1.addEpisode(2, 1, "Grilled");

        Series series2 = new Series("Stranger Things", "Sci-Fi", "Duffer Brothers", 3, 5); // 3 seasons, max 5 episodes
                                                                                           // per season
        series2.addEpisode(1, 0, "The Vanishing of Will Byers");
        series2.addEpisode(1, 1, "The Weirdo on Maple Street");
        series2.addEpisode(2, 0, "Madmax");
        series2.addEpisode(2, 1, "The Spy");
        series2.addEpisode(3, 0, "Suzie, Do You Copy?");
        series2.addEpisode(3, 1, "The Mall Rats");

        Series series3 = new Series("The Witcher", "Fantasy", "Lauren Schmidt Hissrich", 1, 3); // 1 season, max 3
                                                                                                // episodes
        series3.addEpisode(1, 0, "The End's Beginning");
        series3.addEpisode(1, 1, "Four Marks");
        series3.addEpisode(1, 2, "Betrayer Moon");

        contentRepo.addContent(series1);
        contentRepo.addContent(series2);
        contentRepo.addContent(series3);

        // Casting the Content (super class) object returned by
        // contentRepo.findContentByTitle("Stranger Things");
        // to a Series object
        Series storedSeries2 = (Series) contentRepo.findContentByTitle("Stranger Things");
        Series storedSeries1 = (Series) contentRepo.findContentByTitle("Breaking Bad");

        if (storedSeries2 != null) {
            storedSeries2.removeEpisode(2, "The Spy");
        } else {
            System.out.println("❌ 'Stranger Things' not found in contentRepo.");
        }

        if (storedSeries1 != null) {
            storedSeries1.updateEpisode(1, 1, "Cat's in the Bag... (Updated)");
        } else {
            System.out.println("❌ 'Breaking Bad' not found in contentRepo.");
        }

        // Retrieve movies from contentRepo and add actors
        Movie interstellar = (Movie) contentRepo.findContentByTitle("Interstellar");
        if (interstellar != null) {
            interstellar.addActor("Matthew McConaughey");
            interstellar.addActor("Anne Hathaway");
            interstellar.addActor("Jessica Chastain");
        }

        Movie darkKnight = (Movie) contentRepo.findContentByTitle("The Dark Knight");
        if (darkKnight != null) {
            darkKnight.addActor("Christian Bale");
            darkKnight.addActor("Heath Ledger");
            darkKnight.addActor("Gary Oldman");
        }

        Movie inception = (Movie) contentRepo.findContentByTitle("Inception");
        if (inception != null) {
            inception.addActor("Leonardo DiCaprio");
            inception.addActor("Joseph Gordon-Levitt");
            inception.addActor("Elliot Page");
        }

        Movie godfather = (Movie) contentRepo.findContentByTitle("The Godfather");
        if (godfather != null) {
            godfather.addActor("Marlon Brando");
            godfather.addActor("Al Pacino");
            godfather.addActor("James Caan");
        }

        // Retrieve series from contentRepo and add actors
        Series breakingBad = (Series) contentRepo.findContentByTitle("Breaking Bad");
        if (breakingBad != null) {
            breakingBad.addActor("Bryan Cranston");
            breakingBad.addActor("Aaron Paul");
            breakingBad.addActor("Anna Gunn");
        }

        Series strangerThings = (Series) contentRepo.findContentByTitle("Stranger Things");
        if (strangerThings != null) {
            strangerThings.addActor("Winona Ryder");
            strangerThings.addActor("David Harbour");
            strangerThings.addActor("Millie Bobby Brown");
        }

        Series theWitcher = (Series) contentRepo.findContentByTitle("The Witcher");
        if (theWitcher != null) {
            theWitcher.addActor("Henry Cavill");
            theWitcher.addActor("Freya Allan");
            theWitcher.addActor("Anya Chalotra");
        }

        // Retrieve all content from contentRepo
        Content[] allContent = contentRepo.getAllContent();

        System.out.println("\n📜 All Content Details:");
        System.out.println("\n---------------------------------");
        for (Content content : allContent) {
            if (content != null) {
                System.out.println("\n---------------------------------");
                System.out.println(content.printDetails()); // ✅ Print details
            }
        }
        System.out.println("\n---------------------------------");

        SubscriptionPlan basicPlan = new SubscriptionPlan("Basic", 7.99, 1);
        SubscriptionPlan standardPlan = new SubscriptionPlan("Standard", 15.49, 2);
        SubscriptionPlan premiumPlan = new SubscriptionPlan("Premium", 22.99, 4);

        Subscriber subscriber1 = new Subscriber("alice@example.com", "SecurePass1!", basicPlan);
        Subscriber subscriber2 = new Subscriber("bob@example.com", "StrongPass2@", standardPlan);
        Subscriber subscriber3 = new Subscriber("charlie@example.com", "UltraPass3$", premiumPlan);

        subscriber1.watchContent(movie1);
        subscriber1.watchContent(series1);

        subscriber2.watchContent(movie2);
        subscriber2.watchContent(series2);

        subscriber3.watchContent(movie3);
        subscriber3.watchContent(series1);
        subscriber3.watchContent(series2);

        subscriberRepo.addSubscriber(subscriber1);
        subscriberRepo.addSubscriber(subscriber2);
        subscriberRepo.addSubscriber(subscriber3);

        // Deactivating a subscriber (Encapsulation - Prevents direct field
        // modification)
        subscriberRepo.deactivateSubscriber(subscriberRepo.findSubscriberByEmail("bob@example.com"));

        for (Subscriber subscriber : subscriberRepo.getSubscribers()) // Iterate through all subscribers
            if (subscriber != null)
                subscriber.printSubscriberDetails(); // Calls the `printDetails()` method

        // Setting favorite genres (Encapsulation - Controlled attribute modification)
        Subscriber alice = subscriberRepo.findSubscriberByEmail("alice@example.com");
        Subscriber bob = subscriberRepo.findSubscriberByEmail("bob@example.com");
        Subscriber charlie = subscriberRepo.findSubscriberByEmail("charlie@example.com");

        if (alice != null)
            alice.setFavoriteGenres(new String[] { "Sci-Fi", "Crime" });

        if (bob != null)
            bob.setFavoriteGenres(new String[] { "Action", "Sci-Fi" });

        if (charlie != null)
            charlie.setFavoriteGenres(new String[] { "Drama", "Superhero" });

        // ✅ Generating recommendations (Abstraction - Complex logic hidden inside
        // NetflixService)
        Subscriber requestsRecommendation = subscriberRepo.findSubscriberByEmail("bob@example.com");
        Content[] recommendations = netflixService.getRecommendedMoviesByFavoriteGenres(requestsRecommendation, 4);
        netflixService.printRecommendations(recommendations, requestsRecommendation);
    }
}