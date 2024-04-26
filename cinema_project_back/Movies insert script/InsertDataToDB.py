import psycopg2
import pickle


genres = pickle.load( open("./genres.pickle", "rb") )
data = pickle.load( open('./top250_processed_data.pickle', 'rb') )

statement_m = f"INSERT INTO movies(title, year, length_min, description, image_url) VALUES "
statement_g = f"INSERT INTO genres(genre) VALUES "
statement_mg= f"INSERT INTO movies_genres(movie_id, genre_id) VALUES "

statement_fetch_movie_id = "SELECT m.id FROM movies AS m WHERE m.title = "
statement_fetch_genre_id = "SELECT g.id FROM genres AS g WHERE g.genre = "

#print( data[0] )
for movie in data:
    movie['title'] = movie['title'].replace("'", "''")
    movie['plot'] = movie['plot'].replace("'", "''")

for gen in genres:
    statement_g += f"('{gen}'), "
statement_g = statement_g[:-2] + ';'

for movie in data:
    statement_m += f"('{movie['title']}', {movie['year']},  {movie['runtime']}, '{movie['plot']}', '{movie['imageUrl']}'), "
statement_m = statement_m[:-2] + ';'

conn = psycopg2.connect( "postgres://aluzesfv:EZVwscQXajHKJ7S5Phw5P7ohF7plSYyL@dumbo.db.elephantsql.com/aluzesfv" )
cursor = conn.cursor()

cursor.execute(statement_m)
conn.commit()

cursor.execute(statement_g)
conn.commit()


movie_genre_insert = []
for movie in data:
    cursor.execute( statement_fetch_movie_id + "'" + movie['title'] + "' ;" )
    movie_id = cursor.fetchone()[0]
    
    for genre in movie['genres']:
        cursor.execute( statement_fetch_genre_id + "'" + genre + "' ;")
        genre_id = cursor.fetchone()[0]
        movie_genre_insert.append( [movie_id , genre_id] )

for m_g in movie_genre_insert:
    statement_mg += f"({m_g[0]}, {m_g[1]}), "
statement_mg = statement_mg[:-2] + ';'

cursor.execute(statement_mg)
conn.commit()


cursor.close()
conn.close()
