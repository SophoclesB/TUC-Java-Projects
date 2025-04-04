package backend.content;

import backend.content.Content;
import backend.content.Movie;
import backend.content.Series;
import backend.repository.ContentRepository;
import backend.repository.SubscriberRepository;
import backend.service.NetflixService;
import backend.subscription.Subscriber;
import backend.subscription.SubscriptionPlan;

public class Main {
    public static void main(String[] args) {
        ContentRepository contentRepo = new ContentRepository(100);
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

        Movie interstellar = (Movie) contentRepo.findContentByTitle("Interstellar");
        if (interstellar != null) {
            interstellar.addActor("Matthew McConaughey");
            interstellar.addActor("Anne Hathaway");
            interstellar.addActor("Jessica Chastain");
            interstellar.removeActor("Anne Hathaway");
        }

        System.out.println(interstellar.getNonNullActors());
    }
}
