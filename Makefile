DATA=data/
RESULT=test/results/

JAVA=$(wildcard */*.java)
CLASS=$(patsubst %.java,%.class,$(JAVA)) automata/RegExTree.class

TXT=$(wildcard $(DATA)*.txt)
INDEX=$(patsubst %.txt,%.index,$(TXT))
U_TEST=$(patsubst $(DATA)%.txt,$(RESULT)%.u_results,$(TXT))
M_TEST=$(patsubst $(DATA)%.txt,$(RESULT)%.m_results,$(TXT))
M_NA_TEST=$(patsubst $(DATA)%.txt,$(RESULT)%.m_na_results,$(TXT))

GRAPHS=test/graphs/u_graph.jpg test/graphs/m_graph.jpg

all: $(CLASS)

%.class: %.java
	javac $^

index: all $(INDEX)

%.index: %.txt $(CLASS)
	java index.FileToIndex $< > $@

test: all $(INDEX) $(U_TEST) $(M_TEST)

$(RESULT)%.u_results: $(DATA)%.txt $(CLASS) $(INDEX)
	scripts/unique_test_one_file.sh $<

$(RESULT)%.m_results: $(DATA)%.txt $(CLASS) $(INDEX)
	scripts/multiple_test_one_file.sh $<

$(RESULT)%.m_na_results: $(DATA)%.txt $(CLASS) $(INDEX)
	scripts/multiple_test_one_file_no_automata.sh $<

graph: test/graphs/u_graph.jpg test/graphs/m_graph_all.jpg test/graphs/m_graph_no_automata.jpg

test/graphs/u_graph.jpg: $(U_TEST)
	scripts/unique_graph.sh

test/graphs/m_graph_all.jpg: $(M_TEST)
	scripts/multiple_graph.sh

test/graphs/m_graph_no_automata.jpg: $(M_NA_TEST)
	scripts/multiple_graph.sh

clean:
	rm -f $(CLASS)

clean_index:
	rm -f $(INDEX)

clean_test:
	rm -f $(U_TEST) $(M_TEST) $(M_NA_TEST)

clean_graph:
	rm -f test/graphs/*

clean_all: clean clean_index clean_test
