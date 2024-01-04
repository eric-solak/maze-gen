# Makefile to build requirements standard plan
# author: Sébastien Mosser
# /!\ Run make check_env to check that your setup is correct

.DEFAULT_GOAL := svg

####
# Building PlantUML models
####

MODELS := $(shell find * -type f -name "*.puml")

SVGs = $(MODELS:.puml=.svg)

# transform a puml file into it's svg representation
%.svg: %.puml
	plantuml -tsvg ./$<

svg: $(SVGs)

####
# Cleaning (and clobbering)
####

clean:
	rm -rf *.html *.pdf

clobber: clean
	rm -rf $(SVGs)



