DATA=data
RESULT=test/results

JAVA=$(wildcard */*.java)
CLASS=$(patsubst %.java,%.class,$(JAVA)) automata/RegExTree.class

BOOKS=$(wildcard $(DATA)/books/*.txt)
INDEX=$(patsubst $(DATA)/books/%.txt,$(DATA)/index/%.index,$(BOOKS))
U_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.u_results,$(BOOKS))
M_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.m_results,$(BOOKS))
M_NA_TEST=$(patsubst $(DATA)/books/%.txt,$(RESULT)/%.m_na_results,$(BOOKS))

GRAPHS=test/graphs/u_graph.jpg test/graphs/m_graph.jpg

all: $(CLASS)

%.class: %.java
	javac $^

index: all dir_index $(INDEX)

dir_index:
	mkdir -p $(DATA)/index

$(DATA)/index/%.index: $(DATA)/books/%.txt $(CLASS)
	java index.FileToIndex $< > $@

test: all $(INDEX) dir_result $(U_TEST) $(M_TEST)

dir_result:
	mkdir -p $(RESULT)

$(RESULT)/%.u_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/unique_test_one_file.sh $<

$(RESULT)/%.m_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/multiple_test_one_file.sh $<

$(RESULT)/%.m_na_results: $(DATA)/books/%.txt $(CLASS) $(DATA)/index/%.index
	scripts/multiple_test_one_file_no_automata.sh $<

graph: dir_graph test/graphs/u_graph.jpg test/graphs/m_graph_all.jpg test/graphs/m_graph_no_automata.jpg

dir_graph:
	mkdir -p test/graphs

test/graphs/u_graph.jpg: $(U_TEST)
	scripts/unique_graph.sh

test/graphs/m_graph_all.jpg: $(M_TEST)
	scripts/multiple_graph.sh

test/graphs/m_graph_no_automata.jpg: $(M_NA_TEST)
	scripts/multiple_graph.sh

clean:
	rm -f $(CLASS)

clean_index:
	rm -rf $(DATA)/index

clean_test:
	rm -rf $(RESULT)

clean_graph:
	rm -rf test/graphs

clean_all: clean clean_index clean_test
