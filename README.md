## General info
Movies analysis

* Type: Course project
* Subject: Information Retrieval
* Institution: SCE - Shamoon College of Engineering
	
## Languages
Project is created with:
* Java 8
* Python 3.8

## Structure
Project consists of 2 parts:

1) Predicting of movie ratings on Twitter using k-Nearest Neighbour (KNN) classification (folder named ratings)
2) Determination the polarity of a movie review using Naive Bayes classifier (folder named reviews) 

## Brief procedure
Ratings part: 
1) Java: Calculate movie rating on Twitter and make JSON file using `Gson`
```json
{"imdbId":"tt0068646","name":"The Godfather","year":1972,"genre":3,"directorId":338,"imdbRating":3,"numOfVotesImdb":1599754,"twitterRating":3,"numOfVotesTwitter":693}
```
2) Convert from JSON to CSV [here](https://json-csv.com/)
3) Python: Apply the k-Nearest Neighbour (KNN) method using `scikit-learn`

Reviews part:
1) Python: Apply the Multinomial Naive Bayes classifier using `scikit-learn`

## Functionality
Ratings part:
1) Calculating accuracy of the prediction model
2) Predicting rating label for the each individual movie based on its year, genre, director, imdbRating and numOfVotesImdb
```python
# Testing on "Good Will Hunting" (https://www.imdb.com/title/tt0119217/)
movie = knn.predict([[1997, 4, 1814, 3, 855094]])
print("Prediction label for your movie: ", movie[0])
```

Reviews part:
1) Determination the polarity of a movie review (for set of reviews or for each individual review)
2) Bag of words model
3) Word clouds
4) Classification report

## Labeling
Polarity:
```txt
1 - Negative (4-5), 2 - Neutral (6-7), 3 - Positive (8-9)
```
Genres:
```txt
Adventure(1), Action(2), Crime(3), Drama(4), Biography(5), Comedy(6), Western(7), Mystery(8), Horror(9), Animation(10), Sci-Fi(11), Family(12), Romance(13), Fantasy(14), Thriller(15), Documentary(16)
```
## Datasets
For ratings part:
* [MovieTweetings](https://github.com/sidooms/MovieTweetings.git)
* [IMDb interfaces](https://www.imdb.com/interfaces/)

For reviews part:
* [Stanford Reviews Dataset](https://ai.stanford.edu/~amaas/data/sentiment/)

## Requirements

* 16 GB RAM for reviews polarity analysis

## Team members

Gleb Vasilyev (onetwogleb@yahoo.com)

Alexey Kuraev (alkur1994@gmail.com)
