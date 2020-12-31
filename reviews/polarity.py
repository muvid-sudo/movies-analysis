import glob
import matplotlib.pyplot as plt
import numpy as np
from nltk.corpus import stopwords
from nltk.stem.snowball import PorterStemmer
from nltk.tokenize import RegexpTokenizer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
from wordcloud import WordCloud

# Method for text pre-processing
def getCleanedReview(review):
    review = review.replace('<br /><br />', " ")

    # Tokenization of text
    tokenizer = RegexpTokenizer(r'\w+')
    wordsList = tokenizer.tokenize(review)
    wordsList = [word.lower() for word in wordsList]

    # Removing stopwords
    sw = stopwords.words('english')
    sw = set(sw)
    wordsList = [word for word in wordsList if word not in sw]

    # Text stemming
    ps = PorterStemmer()
    wordsList = [ps.stem(word) for word in wordsList]
    # print(wordsList)

    # Return clean review
    cleaned_review = " ".join(wordsList)

    return cleaned_review


# Array of positive reviews
posReviewsPath = []
posReviewsPath.extend(glob.glob('\\resources\\pos\\*.txt'))
pos = []
for f in posReviewsPath:
    g = open(f, encoding="utf8")
    a = g.read()
    g.close()
    pos.append(a)

# Array of negative reviews
negReviewsPath = []
negReviewsPath.extend(glob.glob('\\resources\\neg\\*.txt'))
neg = []
for f in negReviewsPath:
    g = open(f, encoding="utf8")
    a = g.read()
    g.close()
    neg.append(a)

allReviews = np.append(pos, neg)
m = 1500

# Array with 0 and 1 labels
y1 = np.array([1 for i in range(m)])
y2 = np.array([0 for i in range(m)])
y = np.append(y1, y2)

# Input from console
print('Enter: 1 for reading review from console | 2 for reading reviews from dataset ')
vote = int(input())

if vote == 1:
    testReviews = [" "]
    print('Enter your movie review: ')
    testReviews[0] = str(input())

elif vote == 2:
    print('Parsing reviews from data in process...')
    testReviews = []
    testReviewsPath = []
    testReviewsPath.extend(glob.glob('\\resources\\test\\*.txt'))

    for f in testReviewsPath:
        g = open(f, encoding="utf8")
        a = g.read()
        g.close()
        testReviews.append(a)

cleanTrain = [getCleanedReview(i) for i in allReviews]
cleanTest = [getCleanedReview(i) for i in testReviews]

# Count Vectorizer
cv = CountVectorizer(ngram_range=(1, 3))
x_vectorizedCorpus = cv.fit_transform(cleanTrain).toarray()
xtest_vectorizedCorpus = cv.transform(cleanTest).toarray()

# Bag of words model
cv_train_reviews = cv.fit_transform(cleanTrain)
cv_test_reviews = cv.transform(cleanTest)
print('Bag of words for train reviews: ', cv_train_reviews.shape)
print('Bag of words for test reviews: ', cv_test_reviews.shape)

# Prediction using Multinomial Naive Bayes method
mnb = MultinomialNB()
mnb.fit(x_vectorizedCorpus, y)
mnb.predict(xtest_vectorizedCorpus)
mnbPred = mnb.predict_proba(xtest_vectorizedCorpus)
print('Polarity by MNB: ', mnbPred)

# Train set word cloud
plt.figure(figsize=(10, 10))
positive_text = cleanTrain[1]
WC = WordCloud(width=1000, height=500, max_words=500, min_font_size=5)
positive_words = WC.generate(positive_text)
plt.imshow(positive_words, interpolation='bilinear')
plt.show

# Test set word cloud
plt.figure(figsize=(10, 10))
positive_text = cleanTest[1]
WC = WordCloud(width=1000, height=500, max_words=500, min_font_size=5)
positive_words = WC.generate(positive_text)
plt.imshow(positive_words, interpolation='bilinear')
plt.show