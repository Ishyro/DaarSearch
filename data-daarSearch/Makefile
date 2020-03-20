# files and folders

DATA=src/main/resources
RESULT=test/results

JOPTIONS=-classpath src/main/java -d bin

JAVA=$(wildcard src/main/java/automata/*.java) \
	$(wildcard src/main/java/index/*.java) \
	$(wildcard src/main/java/kmp/*.java) \
	$(wildcard src/main/java/graph/*.java)

BOOKS=$(wildcard data/books/*.txt)
INDEX=$(patsubst data/books/%.txt,$(DATA)/indexes/%.index,$(BOOKS))

# utilities

MKDIR_P=mkdir -p

# values

JACCARD_TRESHHOLD=0.70
JACCARD_PRECISION=0.0025
NB_BOOKS=15

# rules

all: dir_bin java index jaccard betweenness jersey

dir_bin:
	$(MKDIR_P) bin

java: dir_bin
	javac $(JOPTIONS) $(JAVA)

index: java dir_index $(INDEX)

dir_index:
	$(MKDIR_P) $(DATA)/indexes

$(DATA)/indexes/%.index: data/books/%.txt
	java -classpath bin index.FileToIndex $< > $@

jaccard: java index $(DATA)/jaccard.txt

$(DATA)/jaccard.txt:
	scripts/jaccard_distances.sh $(JACCARD_TRESHHOLD) > $@

betweenness: java jaccard $(DATA)/betweenness.txt

$(DATA)/betweenness.txt: $(DATA)/jaccard.txt
	java -classpath bin graph.Graph $< $(NB_BOOKS) $(JACCARD_PRECISION) > $@

jersey: java index jaccard betweenness
	mvn clean install

clean:
	rm -f $(CLASS)
	rm -rf target

clean_data:
	rm -rf $(DATA)

clean_all: clean clean_data
