import praw
import nltk
import re
import sys
import warnings
import pandas as pd
from praw.models import MoreComments
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from nltk.corpus import stopwords
from nltk.stem.snowball import SnowballStemmer

if not sys.warnoptions:
    warnings.simplefilter("ignore")

stop_words = set(stopwords.words('english'))
stop_words.update(['zero','one','two','three','four','five','six','seven','eight','nine','ten','may','also','across','among','beside','however','yet','within'])
re_stop_words = re.compile(r"\b(" + "|".join(stop_words) + ")\\W", re.I)

stemmer = SnowballStemmer("english")

def removeStopWords(sentence):
    global re_stop_words
    return re_stop_words.sub(" ", sentence)

def stemming(sentence):
    stemSentence = ""
    for word in sentence.split():
        stem = stemmer.stem(word)
        stemSentence += stem
        stemSentence += " "
    stemSentence = stemSentence.strip()
    return stemSentence

def cleanHtml(sentence):
    cleanr = re.compile('<.*?>')
    cleantext = re.sub(cleanr, ' ', str(sentence))
    return cleantext
    
def cleanPunc(sentence): #function to clean the word of any punctuation or special characters
    cleaned = re.sub(r'[?|!|\'|"|#]',r'',sentence)
    cleaned = re.sub(r'[.|,|)|(|\|/]',r' ',cleaned)
    cleaned = cleaned.strip()
    cleaned = cleaned.replace("\n"," ")
    return cleaned
    
def keepAlpha(sentence):
    alpha_sent = ""
    for word in sentence.split():
        alpha_word = re.sub('[^a-z A-Z]+', ' ', word)
        alpha_sent += alpha_word
        alpha_sent += " "
    alpha_sent = alpha_sent.strip()
    return alpha_sent


def preprocess(text):
    text = text.lower()
    text = cleanHtml(text)
    text = cleanPunc(text)
    text = keepAlpha(text)
    text = removeStopWords(text)
    text = stemming(text)
    return text


reddit = praw.Reddit(client_id = "HcHVAaJGVQzH-Q", client_secret = "tl6k-jE3EzLnj2MOjej3agon4XMvCg", user_agent = "RedditWebScraper29183")

sub_reddits = [reddit.subreddit("Cooking"), reddit.subreddit("cookingforbeginners"), reddit.subreddit("videogames"), reddit.subreddit("Games"),
 reddit.subreddit("books"), reddit.subreddit("travel"), reddit.subreddit("GYM")]
 

for subs in sub_reddits:
    posts = []
    for post in subs.hot(limit=10):
    
        submission = reddit.submission(id=post.id)
        
        data = preprocess(post.selftext)
        
        posts.append([ post.id, post.subreddit, data])
        submission.comments.replace_more(limit = 0)
        
        for top_level_comment in submission.comments.list():
        
            if (len(top_level_comment.body) > 5):
                data = preprocess(top_level_comment.body)
                posts.append([ top_level_comment.id,  post.subreddit, data])    

    posts = pd.DataFrame(posts,columns=['id', 'subreddit', 'body',])
    posts.to_csv(str(subs) + ".csv", index = False)   

