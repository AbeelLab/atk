# atk

Abeel ToolKit -- a set of quite useful java and scala classes, objects and traits.


Some classes may have more specific license than the complete package, please refer to package level documentation to figure out which license your will need to follow if using any of the code from this collection.

Individual tools may have specific licenses to accommodate library and/or package licenses

## Overview 
The ATK library contains a large collection of small tools:

* compbio: phylogenetic tree for storing evolutional history between species
* io: file filters, GZIP tools, 
* net: email tool, client http tools
* util: MD5, ColorGradient, TimeInterval
* bioinformatics: FASTA format, some DNA util classes
* concurrency: DeamonThread
* graphics: Image handling tools
* gui: some panels and fields
* jfreechart: wrapper for JFreeChart
* util: alternatives for hashmap, alternative RNG



## Additional libraries
This library also contains hard copies of other libraries, which are not available on an external artifactory:

* edu.northwestern.at.utils which is a math toolbox

* org.arabidopsis.ahocorasick which is a string searching automaton.


# Maven Dependency
You can use this from maven with the following dependency.

```
	    <dependency>
		    <groupId>be.abeel</groupId>
		    <artifactId>atk</artifactId>
		    <version>1.2.1</version>
		</dependency>
```

and with the following repository

```
	<repositories>
		<repository>
			<id>artifactory.ewi.tudelft.nl</id>
			<url>https://artifactory.ewi.tudelft.nl/artifactory/libs-release</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
```

