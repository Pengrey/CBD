from neo4j import GraphDatabase


class GraphController(object):

    def __init__(self, uri, user, password):
        self._driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self._driver.close()

    def insert_data(self):
        # self.create_constraints()
        self.insert_nodes()
        self.insert_relations()

    def create_constraints(self):
        with self._driver.session() as session:
            print("Creating Constraints: ")
            session.run("CREATE CONSTRAINT ON (game:Game) ASSERT game.title IS UNIQUE")
            session.run("CREATE CONSTRAINT ON (publisher:Publisher) ASSERT publisher.name IS UNIQUE")
            session.run("CREATE CONSTRAINT ON (console:Console) ASSERT console.name IS UNIQUE")
            session.run("CREATE CONSTRAINT ON (year:Year) ASSERT year.yearName IS UNIQUE")

    def insert_nodes(self):
        with self._driver.session() as session:
            # Nodes
            print("Inserting Nodes: ", end="")
            session.run(
                        "LOAD CSV WITH HEADERS \
                        FROM \"file:///video_games.csv\" AS Row \
                        MERGE (game:Game {title: Row.Title}) \
                        MERGE (console:Console {name:Row.Release_Console}) \
                        MERGE (publisher:Publisher {companyName:Row.Metadata_Publishers}) \
                        MERGE (year:Year {yearName:Row.Release_Year}) \
                        SET game.Release_Year=Row.Release_Year, game.Release_Console=Row.Release_Console, game.Release_Rating=Row.Release_Rating, game.Metadata_Genres=Row.Metadata_Genres, game.Metadata_Publishers=Row.Metadata_Publishers"
                        )
            print("Done")

    def insert_relations(self):
        with self._driver.session() as session:
            # Relationships
            print("\nInserting Relationships consoles: ", end="")
            session.run(
                        "LOAD CSV WITH HEADERS \
                        FROM \"file:///video_games.csv\" AS Row \
                        MATCH (game:Game {title: Row.Title}),(console:Console {name:Row.Release_Console}) \
                        CREATE (game)-[:PublishedOn]->(console)"
                        )
            print("Done")

            print("\nInserting Relationships publishers: ", end="")
            session.run(
                        "LOAD CSV WITH HEADERS \
                        FROM \"file:///video_games.csv\" AS Row \
                        MATCH (game:Game {title: Row.Title}),(publisher:Publisher {companyName: Row.Metadata_Publishers}) \
                        CREATE (game)-[:PublishedBy]->(publisher)"
                        )
            print("Done")

            print("\nInserting Relationships years: ", end="")
            session.run(
                        "LOAD CSV WITH HEADERS \
                        FROM \"file:///video_games.csv\" AS Row \
                        MATCH (game:Game {title: Row.Title}),(year:Year {yearName: Row.Release_Year}) \
                        CREATE (game)-[:PublishedIn]->(year)"
                        )
            print("Done")

    def query(self, query):
        with self._driver.session() as session:
            for result in session.run(query):
                print(result.items())

if __name__ == "__main__":
    controller = GraphController("bolt://localhost:7687", "neo4j", "password")

    # Insert Data
    controller.insert_data() 

    # Queries
    # 1.Get all publishers
    print("\nGet all consoles:")
    controller.query("MATCH (console:Console) RETURN console.name")

    # 2. Get the PlayStation 3 games
    print("\nGet the PlayStation 3 games")
    controller.query("MATCH (game)-[:PublishedOn]->(console) WHERE console.name=\"PlayStation 3\" RETURN console.name, collect(DISTINCT game.title)")
    
    # 3. Get the Nintendo publised games
    print("\nGet the Nintendo publised games")
    controller.query("MATCH (game)-[:PublishedBy]->(pub) WHERE pub.companyName = \"Nintendo\" RETURN collect(DISTINCT game.title)")

    # 4. Get biggest publisher
    print("\nGet biggest publisher")
    controller.query("MATCH (game)-[r:PublishedBy]->(pub) WITH pub, count(r) AS countPub RETURN pub, countPub ORDER BY countPub DESC LIMIT 1")
    
    # 5. Get the years the games where published
    print("\nGet the years the games where published")
    controller.query("MATCH (game)-[r:PublishedIn]->(year) RETURN Distinct year.yearName")
    
    # 6. Get the year with the most games released
    print("\nGet the year with the most games released")
    controller.query("MATCH (game)-[r:PublishedIn]->(year) WITH year ,count(DISTINCT game) as countGames RETURN year.yearName, countGames ORDER BY countGames DESC LIMIT 1")

    # 7. Get all possible scores from games from Nintendo
    print("\nGet all possible scores from games from Nintendo")
    controller.query("MATCH (game)-[:PublishedBy]->(pub) WHERE pub.companyName = \"Nintendo\" RETURN collect(DISTINCT game.Release_Rating)")
    
    # 8. Get the genres for the games from Nintendo for each game
    print("\nGet the genres for the games from Nintendo for each game")
    controller.query("MATCH (game)-[:PublishedBy]->(pub) WHERE pub.companyName = \"Nintendo\" RETURN game.title, collect(DISTINCT game.Metadata_Genres)")
    
    # 9. Get most popular genre
    print("\nGet most popular genre")
    controller.query("MATCH (game)-[:PublishedBy]->(pub) WITH game.Metadata_Genres as genres, count(game.Metadata_Genres) as countG RETURN genres, countG ORDER BY countG DESC LIMIT 1")

    #10. Get all games from xbox with the action genre and published by EA on the year 2008
    print("\nGet all games from xbox with the action genre and published by EA on the year 2008")
    controller.query("MATCH (game)-[:PublishedOn]->(console) WHERE console.name=\"PlayStation 3\" and game.Metadata_Genres=\"Action\" and game.Metadata_Publishers=\"EA\" RETURN collect(DISTINCT game.title)")
    controller.close()