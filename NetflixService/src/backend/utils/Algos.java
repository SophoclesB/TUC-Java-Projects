package backend.utils;

import backend.content.Movie;

public class Algos {
    public static void quickSort(Movie[] movieList, int begin, int end) {
        if (begin < end) {
            int p = partition(movieList, begin, end);

            quickSort(movieList, begin, p - 1);
            quickSort(movieList, begin + 1, p);
        }
    }

    private static int partition(Movie[] movieList, int begin, int end) {
        int pivot = movieList[end].getContentId();
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (movieList[j].getContentId() < pivot) {
                i++;

                Movie temp = movieList[i];
                movieList[i] = movieList[j];
                movieList[j] = temp;
            }
        }

        Movie temp = movieList[i + 1];
        movieList[i + 1] = movieList[end];
        movieList[end] = temp;
        return i + 1;
    }
}
