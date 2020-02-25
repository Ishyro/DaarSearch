TOPTARGETS := all index jaccard test graph clean clean_data clean_test clean_graph clean_all

SUBDIRS := data-daarSearch #$(wildcard */.)

$(TOPTARGETS): $(SUBDIRS)
$(SUBDIRS):
	$(MAKE) -C $@ $(MAKECMDGOALS)

.PHONY: $(TOPTARGETS) $(SUBDIRS)
