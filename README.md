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
