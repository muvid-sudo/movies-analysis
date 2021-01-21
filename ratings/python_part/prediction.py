import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn import linear_model, metrics
import numpy as np
import matplotlib.pyplot as plt

movies = pd.read_csv("movies.csv")

rating = movies.twitterRating.unique()
print('Polarity labels are: ', rating)
print('Polarity: 1 - Negative(4-5), 2 - Neutral(6-7), 3 - Positive(8-9)')

movies["twitterRating"] = movies["twitterRating"].astype("category")
movies["class_label"] = movies["twitterRating"].cat.codes

print(movies)

# Split the data into training and test sets

X = movies[['year', 'genre', 'directorId', 'imdbRating', 'numOfVotesImdb']]
y = movies['twitterRating']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# Starting knn method for prediction

knn = KNeighborsClassifier(n_neighbors=7, metric='minkowski')

# Fit the classifier to the training data
knn.fit(X_train, y_train)

y_pred = knn.predict(X_test)

print("Accuracy of prediction model:", metrics.accuracy_score(y_test, y_pred))

# Testing on "Good Will Hunting" (https://www.imdb.com/title/tt0119217/)

movie = knn.predict([[1997, 4, 1814, 3, 855094]])
print("Prediction label for your movie: ", movie[0])


# Genres: Adventure(1), Action(2), Crime(3), Drama(4), Biography(5), Comedy(6), Western(7), Mystery(8)
#        Horror(9), Animation(10), Sci-Fi(11), Family(12), Romance(13), Fantasy(14), Thriller(15), Documentary(16)

# Polarity: 1 - Negative(4-5), 2 - Neutral(6-7), 3 - Positive(8-9)


# Visualization of cases with different number of neighbours
neighbors = np.arange(1, 15)
accuracyTrain = np.empty(len(neighbors))
accuracyTest = np.empty(len(neighbors))

for i, k in enumerate(neighbors):
    knn = KNeighborsClassifier(n_neighbors=k)

    knn.fit(X_train, y_train)

    accuracyTrain[i] = knn.score(X_train, y_train)

    accuracyTest[i] = knn.score(X_test, y_test)

plt.title('k-NN: Varying Number of Neighbors')
plt.plot(neighbors, accuracyTest, label='Testing Accuracy')
plt.plot(neighbors, accuracyTrain, label='Training Accuracy')
plt.legend()
plt.xlabel('Number of Neighbors')
plt.ylabel('Accuracy')
plt.show()