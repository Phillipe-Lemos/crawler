# Goals
This application  use Fork/Join framework retrieve a html page from an https://en.wikipedia.org/wiki/Europe, then stores each link found in this page and follow each link. This process is repeated until there are no more links to store and follow. 

A file is generated with all links found and the number of times each link appears.

# How to build the application
mvn clean package

# How to run the application 
mvn exec:java 

# Output
After run the application a text named urllist.txt file will be find in the application path. 
