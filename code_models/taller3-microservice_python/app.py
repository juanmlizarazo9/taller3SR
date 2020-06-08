import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
import os
from flask import Flask,render_template
from flask import request

Path = os.getcwd()

df_movies_diract_orig = pd.read_csv(Path + "/diract.csv")
print("loaded "+Path + "/diract.csv")

df_movies_diract_orig = df_movies_diract_orig.sort_values(by=['movieId'], ascending=True)
df_movies_diract_orig.reset_index(inplace=True)
df_movies_diract_orig.drop('index', axis = 1, inplace=True)

df_movies_diract = df_movies_diract_orig.copy()

df_movies_diract['directores'] = df_movies_diract['directores'].str.replace(' ','')
df_movies_diract['directores'] = df_movies_diract['directores'].str.replace('NotFound','')

#TF-idf for directors
vectorizer = TfidfVectorizer()
directores_tfidf = vectorizer.fit_transform(df_movies_diract["directores"])
print("tf-idf directors")

df_movies_diract['actores'] = df_movies_diract['actores'].str.replace(' ','')
df_movies_diract['actores'] = df_movies_diract['actores'].str.replace('NotFound','')

#TF-idf for actors
vectorizer = TfidfVectorizer()
actores_tfidf = vectorizer.fit_transform(df_movies_diract["actores"])
print("tf-idf actors")

df_movies = pd.read_csv(Path + "/movies.csv")
print("loaded "+Path + "/movies.csv")
#Drop special characters
df_movies['genres'] = df_movies['genres'].str.replace('-','')
df_movies['genres'] = df_movies['genres'].str.replace('(no genres listed)','')
#TF-idf for genres
vectorizer = TfidfVectorizer()
genres_tfidf = vectorizer.fit_transform(df_movies["genres"])
print("tf-idf genres")

#Load the relevance and name of the Tags
df_tag_relevance = pd.read_csv("genome-scores.csv")
print("loaded "+Path + "/genome-scores.csv")
df_tag_ids = pd.read_csv("genome-tags.csv")
print("loaded "+Path + "/genome-tags.csv")

#Create the inner join of tags relevance with tag names
df_tags_info= pd.merge(df_tag_relevance,df_tag_ids,how="inner",left_on="tagId", right_on="tagId")
#Load the tags selected for the user to each movie.
df_tag_user = pd.read_csv("tags.csv")
print("loaded "+Path + "/tags.csv")
#Get the most used tags for each movie
df_tag_user_count = df_tag_user.groupby(['movieId', 'tag']).size().reset_index(name='counts')
#Join between the most used tags with the tags relevance to generate a tag score for each movie
df_tags_total = pd.merge(df_tag_user_count,df_tags_info,how="inner",left_on=["movieId","tag"], right_on=["movieId","tag"])
df_tags_total['score'] = df_tags_total['counts'] * df_tags_total['relevance']
#The the first 20 scores for the tags of each movie and set as an array separated by '|' into the movie row
df_tags_total_firts20 = df_tags_total.sort_values(['movieId','score'],ascending=False).groupby('movieId').head(20)
df_tags_total_firts20_row = df_tags_total_firts20.groupby('movieId')['tag'].apply(list).reset_index(name='tags')
df_movies_tags = df_movies.merge(df_tags_total_firts20_row, how='left', on='movieId')
mask = df_movies_tags['tags'].notnull()
df_movies_tags.loc[mask, 'tags'] = ['|'.join(map(str, x)) for x in df_movies_tags.loc[mask, 'tags']]
#Clean tags
df_movies_tags['tags-or'] = df_movies_tags['tags']
df_movies_tags['tags'] = df_movies_tags['tags'].str.replace('-','')
df_movies_tags['tags'] = df_movies_tags['tags'].str.replace(' ','')
df_movies_tags["tags-or"] = df_movies_tags["tags-or"].fillna('')
df_movies_tags["tags"] = df_movies_tags["tags"].fillna('')
#TF-idf for tags
vectorizer = TfidfVectorizer()
tags_tfidf = vectorizer.fit_transform(df_movies_tags["tags"])
print("tf-idf tags")

df_movies_semantic = df_movies_tags.merge(df_movies_diract_orig, how='inner', on='movieId')
df_movies_semantic = df_movies_semantic.drop(columns=['tags'])

#Load movie Ratings
df_ratings = pd.read_csv("ratings.csv")
print("loaded "+Path + "/ratings.csv")

app = Flask(__name__)
@app.route('/predict', methods=['GET'])
def predict():
    global df_ratings
    userId = request.args.get('userId')
    print("userId: "+userId)
    user_ratings = df_ratings[df_ratings.userId == int(userId)][["movieId","rating"]]
    print(user_ratings)
    # Get the index of the movies who have user ratings
    index_movies_user_ratings = []
    index_movies_user = []
    for index, row in user_ratings.iterrows():
        i = df_movies_semantic.movieId[df_movies_semantic.movieId == row.movieId].index[0]
        index_movies_user_ratings.append([i,row.rating])
        index_movies_user.append(i)

    #Calculate the cosine similarity between all the movies and the user rating movies
    cosine_sim_genres = cosine_similarity(genres_tfidf,genres_tfidf[index_movies_user])
    cosine_sim_tags = cosine_similarity(tags_tfidf,tags_tfidf[index_movies_user])
    cosine_sim_actores = cosine_similarity(actores_tfidf,actores_tfidf[index_movies_user])
    cosine_sim_directores = cosine_similarity(directores_tfidf,directores_tfidf[index_movies_user])

    #Iterate over item precomputed similarity (tags and genres similarity)
    predictions = []
    movies_len = len(df_movies_semantic)
    for i in range(movies_len):
        ratings_sim = []
        sim = []
        cosine_item_sim_genres = cosine_sim_genres[i]
        cosine_item_sim_tags = cosine_sim_tags[i]
        cosine_item_sim_actores = cosine_sim_actores[i]
        cosine_item_sim_directores = cosine_sim_directores[i]
        #cosine_sim_tags
        #Get the similarity of the actual item with the items with ratings for the user.
        item_index = 0
        for index in index_movies_user_ratings:
            genres_sim = cosine_item_sim_genres[item_index]*0.15
            tags_sim = cosine_item_sim_tags[item_index]*0.2
            actores_sim = cosine_item_sim_actores[item_index]*0.3
            directores_sim = cosine_item_sim_directores[item_index]*0.35
            total_sim = genres_sim + tags_sim + actores_sim + directores_sim
            ratings_sim.append([index[1],total_sim])
            if index[0] != i:
                sim.append(total_sim)
            else:
                #The similarity between the same item does´nt has to be used.
                sim.append(-1)
            item_index += 1
        #Get the 20 more similar items with the actual item
        arr = np.array(sim)
        max_values = arr.argsort()[-20:][::-1]
        sum_sim = 0
        sum_mult = 0
        #Cosine similarity item-item
        for x in max_values:
            rat = ratings_sim[x]
            sum_sim += rat[1]
            sum_mult += rat[0]*rat[1]

        predictions.append(sum_mult/sum_sim)

    #Top 10 Predictions
    pred_arr = np.array(predictions)
    pred_arr = np.nan_to_num(pred_arr)
    print(pred_arr)
    #has to be 58098
    print(len(pred_arr))

    #Remove the user rating movies from the predictions
    for i in index_movies_user:
        pred_arr[i] = -1

    recommended_movies_indexes = pred_arr.argsort()[-10:][::-1]
    print(recommended_movies_indexes)

    for index in recommended_movies_indexes:
        print(index)
        print(predictions[index])

    print(df_movies_semantic.iloc[recommended_movies_indexes])

    return df_movies_semantic.iloc[recommended_movies_indexes].to_json(orient='values')

@app.route('/add_rating', methods=['GET'])
def add_rating():
    global df_ratings

    userId = request.args.get('userId')
    print("userId: "+userId)
    movieId = request.args.get('movieId')
    print("movieId: "+movieId)
    rating = request.args.get('rating')
    print("rating: "+rating)

    new_row = {'userId':int(userId), 'movieId':int(movieId), 'rating' : float(rating)}
    df_ratings = df_ratings.append(new_row, ignore_index=True)

    return df_ratings[df_ratings.userId == int(userId)].to_json(orient='values')
