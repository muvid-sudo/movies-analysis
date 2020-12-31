public class Movie {

    private String imdbId;
    private String name;
    private int year;
    private int genre;
    private int directorId;
    private int imdbRating;
    private int numOfVotesImdb;
    private int twitterRating;
    private int numOfVotesTwitter;

    public Movie(){

    }

    public Movie(String imdbId){
        this.imdbId = imdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(int imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getNumOfVotesImdb() {
        return numOfVotesImdb;
    }

    public void setNumOfVotesImdb(int numOfVotesImdb) {
        this.numOfVotesImdb = numOfVotesImdb;
    }

    public int getTwitterRating() {
        return twitterRating;
    }

    public void setTwitterRating(int twitterRating) {
        this.twitterRating = twitterRating;
    }

    public int getNumOfVotesTwitter() {
        return numOfVotesTwitter;
    }

    public void setNumOfVotesTwitter(int numOfVotesTwitter) {
        this.numOfVotesTwitter = numOfVotesTwitter;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imdbId='" + imdbId + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", directorId=" + directorId +
                ", imdbRating=" + imdbRating +
                ", numOfVotesImdb=" + numOfVotesImdb +
                ", twitterRating=" + twitterRating +
                ", numOfVotesTwitter=" + numOfVotesTwitter +
                '}';
    }
}
