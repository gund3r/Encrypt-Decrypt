
run:
	java ./src/main/java/Main.java -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode

build:
	javac ./src/main/java/Main.java

.DEFAULT_GOAL := build-run
build-run: build run
