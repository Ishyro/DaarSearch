# files and folders

DATA=src/main/resources
RESULT=test/results

JOPTIONS=-classpath src/main/java -d bin

JAVA=$(wildcard src/main/java/automata/*.java) \
	$(wildcard src/main/java/index/*.java) \
	$(wildcard src/main/java/kmp/*.java) \
	$(wildcard src/main/java/graph/*.java)
CLASS=$(patsubst src/main/java/%.java,bin/%.class,$(JAVA)) bin/automata/RegExTree.class

BOOKS=$(wildcard $(DATA)/books/*.txt)
INDEX=$(patsubst $(DATA)/books/%.txt,$(DATA)/indexes/%.index,$(BOOKS))
JACCARD=$(patsubst $(DATA)/books/%.txt,$(DATA)/jaccards/%.jaccard,$(BOOKS))
U_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.u_results,$(BOOKS))
M_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.m_results,$(BOOKS))
M_NA_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.m_na_results,$(BOOKS))

GRAPHS=test/graphs/u_graph.jpg test/graphs/m_graph_all.jpg test/graphs/m_graph_no_automata.jpg

# utilities

MKDIR_P=mkdir -p

# values

JACCARD_TRESHHOLD=0.7
JACCARD_PRECISION=0
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

$(DATA)/indexes/%.index: $(DATA)/books/%.txt
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

jersey: java index jaccard betweenness
	mvn clean install

test: java index dir_result $(U_TEST) $(M_TEST)

dir_result:
	$(MKDIR_P) $(RESULT)

$(RESULT)/%.u_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/unique_test_one_file.sh $<

$(RESULT)/%.m_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/multiple_test_one_file.sh $<

$(RESULT)/%.m_na_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/multiple_test_one_file_no_automata.sh $<

graph: java index test dir_graph $(GRAPHS)

dir_graph:
	$(MKDIR_P) test/graphs

test/graphs/u_graph.jpg: $(U_TEST)
	scripts/unique_graph.sh

test/graphs/m_graph_all.jpg: $(M_TEST)
	scripts/multiple_graph.sh

test/graphs/m_graph_no_automata.jpg: $(M_NA_TEST)
	scripts/multiple_graph.sh

clean:
	rm -f $(CLASS)
	rm -rf target

clean_data:
	rm -f $(DATA)/jaccard.txt
	rm -rf $(DATA)/indexes
	rm -rf $(DATA)/jaccards

clean_test:
	rm -rf $(RESULT)

clean_graph:
	rm -rf test/graphs

clean_all: clean clean_data clean_test clean_graph
