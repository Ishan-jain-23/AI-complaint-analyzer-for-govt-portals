import joblib
import re
import nltk

from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

stop_words = set(stopwords.words("english"))
lemmatizer = WordNetLemmatizer()

tfidf_vectorizer = joblib.load("models/tfidf_vectorizer.pkl")
category_model = joblib.load("models/svm_model.pkl")
urgency_model = joblib.load("models/svm_urgency.pkl")


def preprocess(text):

    text = str(text)
    text = text.lower()

    text = re.sub(r"[^a-zA-Z0-9]", " ", text)

    tokens = word_tokenize(text)

    tokens = [
        word
        for word in tokens
        if word not in stop_words
    ]

    tokens = [
        lemmatizer.lemmatize(word)
        for word in tokens
    ]

    return " ".join(tokens)


def predict_complaint(complaint):

    processed = preprocess(complaint)

    vector = tfidf_vectorizer.transform([processed])

    category = category_model.predict(vector)[0]
    urgency = urgency_model.predict(vector)[0]

    return category, urgency