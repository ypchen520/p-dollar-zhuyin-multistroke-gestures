JC := javac
SRC_DIR := dollar

.SUFFIXES: .java .class
default: classes

classes:
	$(JC) -source 1.8 -target 1.8 $(SRC_DIR)/*.java

clean:
	$(RM) *.jar $(SRC_DIR)/*.class *.csv