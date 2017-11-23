# ubiquitous-tribble

A simple CSV folder reader that converts all CSV and TXT files containing
WiFi networks information inside a specific folder to a KML file, built in Java.


### A University Object Oriented Programming course Project.


 ## Instructions:
0. Import JAK KML API from here: [JAK Github Page](https://github.com/micromata/javaapiforkml) , 
or use included JAR files (same API) found in Javaapiforkml-master folder.
1. Create a new csvWriter and csvToKml objects with paramaters.
2. Invoke ObjectName.csvWriteFile(); to read from destination folder and write a new 
CSV file that includes top ten strongest networks.
3. Invoke ObjectName.writeFileKML(); to write the csv file and create a new formatted KML file from it.

