import com.google.gson.Gson;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.decimal4j.util.DoubleRounder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Dataset {

    // parsing file with imdb ids of top1700 popular movies in twitter
    public List<Movie> top1700() throws IOException {

        List<Movie> movies = new ArrayList<>();

        String line = new String();

        BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\top1700.txt"));

        while ((line = reader.readLine()) != null) {
            movies.add(new Movie(line));
        }

        reader.close();

        return movies;
    }

    // parsing imdb info(3 files)
    public List<Movie> parseImdb() throws IOException {

        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");

        TsvParser parser = new TsvParser(settings);

        List<Movie> movies = top1700();

        String[] row;

        parser.beginParsing(new File("src\\main\\resources\\imdb\\info.tsv"));
        while ((row = parser.parseNext()) != null) {
            for (Movie movie : movies) {
                if (row[0].equals(movie.getImdbId())) {
                    movie.setName(row[2]);
                    movie.setYear(Integer.parseInt(row[5]));
                    movie.setGenre(numOfGenre(row[8].substring(0, checkGenre(row[8]))));
                }
            }
        }

        parser.beginParsing(new File("src\\main\\resources\\imdb\\ratings.tsv"));
        while ((row = parser.parseNext()) != null) {
            for (Movie movie : movies) {
                if (row[0].equals(movie.getImdbId())) {
                    movie.setImdbRating((int) (DoubleRounder.round(Double.parseDouble(row[1]), 0) * 10));
                    movie.setNumOfVotesImdb(Integer.parseInt(row[2]));
                }
            }
        }

        parser.beginParsing(new File("src\\main\\resources\\imdb\\cast.tsv"));
        while ((row = parser.parseNext()) != null) {
            for (Movie movie : movies) {
                if (row[0].equals(movie.getImdbId()) && row[3].equals("director")) {
                    movie.setDirectorId(Integer.parseInt(row[2].substring(2)));
                }
            }
        }

        return movies;
    }

    public int checkGenre(String genres) {

        if (genres.contains(","))
            return genres.indexOf(",");

        else
            return genres.length();
    }

    // giving id for imdb movie genres
    public int numOfGenre(String genre) {

        int num;
        switch (genre) {
            case "Adventure":
                num = 1;
                break;
            case "Action":
                num = 2;
                break;
            case "Crime":
                num = 3;
                break;
            case "Drama":
                num = 4;
                break;
            case "Biography":
                num = 5;
                break;
            case "Comedy":
                num = 6;
                break;
            case "Western":
                num = 7;
                break;
            case "Mystery":
                num = 8;
                break;
            case "Horror":
                num = 9;
                break;
            case "Animation":
                num = 10;
                break;
            case "Sci-Fi":
                num = 11;
                break;
            case "Family":
                num = 12;
                break;
            case "Romance":
                num = 13;
                break;
            case "Fantasy":
                num = 14;
                break;
            case "Thriller":
                num = 15;
                break;
            case "Documentary":
                num = 16;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + genre);
        }

        return num;
    }

    // parsing twitter info(1 file)
    public List<String[]> parseTwitter() throws IOException {

        List<String> rows = new ArrayList<>(Files.readAllLines(Paths.get("src\\main\\resources\\twitter\\ratings.dat")));
        List<String[]> ratings = new ArrayList<>();

        for (String row : rows) {
            ratings.add(row.split("::"));
        }

        return ratings;
    }

    // calculating twitter rating using Bayes estimator formula
    public void calculateTwitterRating() throws IOException {

        float bayesEstimator;           // W - weighted rating
        double ratingWholePool = 7.0;   // C - the mean vote across the whole pool (currently 7.0)
        int weightForTop = 100;         // m - weight given to the prior estimate

        System.out.println("Parsing is started! In process...");
        List<String[]> twiRatings = parseTwitter();
        List<Movie> movies = parseImdb();

        for (Movie movie : movies) {
            int sumRating = 0;
            int numOfVotes = 0;
            for (String[] twi : twiRatings) {
                if (new String("tt").concat(twi[1]).equals(movie.getImdbId())) {
                    sumRating += Integer.parseInt(twi[2]);
                    numOfVotes++;
                }
            }

            movie.setNumOfVotesTwitter(numOfVotes);

            bayesEstimator = (float) (((sumRating / numOfVotes) * numOfVotes) + ratingWholePool * weightForTop) / (numOfVotes + weightForTop);
            movie.setTwitterRating((int) (DoubleRounder.round(bayesEstimator, 0)) * 10);
        }

        Collections.sort(movies, Comparator.comparingDouble(Movie::getTwitterRating).reversed());
        determinePolarity(movies);
    }

    // determining polarity of rating at imdb and twitter for future analysis
    public void determinePolarity(List<Movie> movies) throws IOException {

        // Polarity: 1 - Negative(4-5), 2 - Neutral(6-7), 3 - Positive(8-9)

        for (Movie movie : movies) {
            if (movie.getImdbRating() < 60)
                movie.setImdbRating(1);

            else if (movie.getImdbRating() < 80)
                movie.setImdbRating(2);

            else movie.setImdbRating(3);
        }

        for (Movie movie : movies) {
            if (movie.getTwitterRating() < 60)
                movie.setTwitterRating(1);

            else if (movie.getTwitterRating() < 80)
                movie.setTwitterRating(2);

            else movie.setTwitterRating(3);
        }

        writeToJson(movies);
    }

    public void writeToJson(List<Movie> movies) throws IOException {

        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter("src\\main\\resources\\result\\movies.json");
        gson.toJson(movies, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        System.out.println("Finished! Your JSON file is ready");
    }

/*
    public void findMostRated() throws IOException {

        Map<String, Integer> movies = new HashMap<>();
        List<String[]> twiRatings = parsingTwitter();

        for (String[] twi : twiRatings) {
            movies.put(new String("tt").concat(twi[1]), 0);
        }

        for (String[] twi : twiRatings) {
            if (movies.containsKey(new String("tt").concat(twi[1]))) {
                int count = movies.get(new String("tt").concat(twi[1]));
                movies.put(new String("tt").concat(twi[1]), count + 1);
            }
        }

        Map<String, Integer> sortMap = movies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        sortMap.forEach((k, v) -> System.out.println(k + " " + v));
    }
 */
}
