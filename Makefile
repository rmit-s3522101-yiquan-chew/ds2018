main:
	javac *.java

runTest:
	java dbload -p 4096 ../BUSINESS_NAMES_201803.csv
	java dbquery hello 4096
