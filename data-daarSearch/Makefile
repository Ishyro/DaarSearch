# files and folders

DATA=src/main/resources
RESULT=test/results

JOPTIONS=-classpath src/main/java -d bin

JAVA=$(wildcard src/main/java/automata/*.java) \
	$(wildcard src/main/java/index/*.java) \
	$(wildcard src/main/java/kmp/*.java) \
	$(wildcard src/main/java/graph/*.java)

BOOKS=$(wildcard $(DATA)/books/*.txt)
INDEX=$(patsubst $(DATA)/books/%.txt,$(DATA)/indexes/%.index,$(BOOKS))
JACCARD=$(patsubst $(DATA)/books/%.txt,$(DATA)/jaccards/%.jaccard,$(BOOKS))

# utilities

MKDIR_P=mkdir -p

# values

JACCARD_TRESHHOLD=0.70
JACCARD_PRECISION=0.0025
NB_BOOKS=1000

# rules

all: dir_bin java index jaccard betweenness booklist jersey

dir_bin:
	$(MKDIR_P) bin

java: dir_bin
	javac $(JOPTIONS) $(JAVA)

index: java dir_index $(INDEX)

dir_index:
	$(MKDIR_P) $(DATA)/indexes

$(DATA)/indexes/%.index: data/books/%.txt
	java -classpath bin index.FileToIndex $< > $@

jaccard: java index $(DATA)/jaccard.txt jaccard_dir $(JACCARD)

$(DATA)/jaccard.txt:
	scripts/jaccard_distances.sh $(JACCARD_TRESHHOLD) > $@

jaccard_dir:
	$(MKDIR_P) $(DATA)/jaccards

$(DATA)/jaccards/%.jaccard: $(DATA)/books/%.txt $(DATA)/jaccard.txt
	scripts/jaccard_sort_1_book.sh $^ > $@

betweenness: java jaccard $(DATA)/betweenness.txt

$(DATA)/betweenness.txt: $(DATA)/jaccard.txt
	java -classpath bin graph.Graph $< $(NB_BOOKS) $(JACCARD_PRECISION) > $@

booklist: $(DATA)/betweenness.txt
	scripts/booklist.sh $< $(DATA)/books_list.txt

jersey: java index jaccard betweenness booklist
	mvn clean install

clean:
	rm -rf bin
	rm -rf target

clean_data:
	rm -rf $(DATA)

clean_all: clean clean_data
