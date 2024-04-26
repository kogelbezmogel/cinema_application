import requests
import pickle

results_per_page = 10
num_pages = 25

headers = {
    'X-RapidAPI-Key': '02f1c794bemsh5671e405885bcc0p1602ffjsn4d3194eca920',
    'X-RapidAPI-Host': 'moviesdatabase.p.rapidapi.com'
  }

params = {
    'list': 'top_rated_250',
    'info': 'base_info',
    'page': '-1'
}


# fetching data from rapidapi.com top100 movies by imdb
pages_of_data = []
for i in range(15, num_pages+1):
    params['page'] = f'{i}'
    resp = requests.get("https://moviesdatabase.p.rapidapi.com/titles", headers=headers, params=params).json()
    pages_of_data.append( resp['results'] )
pickle.dump( pages_of_data, open("./data.pickle", "wb") )
pages_of_data = pickle.load( open("./data.pickle", "rb") )
#print( pages_of_data[0][0] )


# extracting neccessery data
genres = []
movie_access_data = []
for page in range(10-1, -1, -1):
    response = pages_of_data[page]

    for result in range(results_per_page-1, -1, -1):
        movie = {
            'id' : response[result]['id'],
            'imageUrl' : response[result]['primaryImage']['url'],
            'title' : response[result]['titleText']['text'],
            'plot' : response[result]['plot']['plotText']['plainText'],
            'runtime' : int(response[result]['runtime']['seconds']) // 60,
            'genres' : [ gen['text'] for gen in response[result]['genres']['genres'] ],
            'year' : response[result]['releaseDate']['year']   
        }

        for gen in response[result]['genres']['genres']:
            genres.append( gen['text'] )
        movie_access_data.append(movie)
genres = set(genres)

pickle.dump( genres, open("./genres.pickle", "wb") )
pickle.dump( movie_access_data, open("./top250_processed_data.pickle", "wb") )