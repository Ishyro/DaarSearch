TOPTARGETS := all java index jaccard betweenness clean clean_data clean_all

SUBDIRS := data-daarSearch #$(wildcard */.)

$(TOPTARGETS): $(SUBDIRS)
$(SUBDIRS):
	$(MAKE) -C $@ $(MAKECMDGOALS)

.PHONY: $(TOPTARGETS) $(SUBDIRS)
