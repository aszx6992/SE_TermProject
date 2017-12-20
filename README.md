# mdparser #

This repository is for 2017-2 Software Engineering Term Project. 
This repository provides build script 'build.xml' to build mdparser program.
It consists of:
1. doc directory
 : It includes doc1.md ~ doc4.md which are md files sample for testing.
2. lib directory
 : It includes .jar files which are used for Ant, Jacoco and JUnit.
3. src directory
 : It includes .java files which are java programs for mdparser, and test programs.
5. build.xml
 : It is used to build mdparser, test JUnit, and analyze Jacoco branch coverage.
---------------------
## How to build ##
1. For just compiling mdparser programs, simply command :<br>
<code> $ ant </code>
2. If you want to run JUnit test for mdparser programs, command : <br>
<code> $ ant test </code>
3. For creating output of Jacoco analysis, command : <br>
<code> $ ant cov-test </code>
4. If you want to look at result graph and details of Jacoco with html file, command : <br>
<code> $ ant cov-report </code>

## How to use mdparser ##

* USER COMMANDS
  * NAME <br>
	mdparser : parses .md file into html file.

  * SYNOPSIS <br>
	<code>mdparser FILE.md [FILE.html]</code>

 * DESCRIPTION
	This manual page documents the mdparser command line functionality. It receives a .md file and
	converts it into a html file. Usage of mdparser are given below. 
	
		
	   -  mdparser source.md destination.html 
	      (converts source.md into a file named destination.html in stylish format)

	   -  mdparser source.md 		     
	      (assigns the same name with the source .md file if output file name is not given. In this case, returns source.html)


	*Note that at least one .md file must be specified*
