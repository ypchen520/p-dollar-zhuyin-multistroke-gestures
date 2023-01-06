# Human-Centered Input Recognition Algorithms: Project 2

* This is the final project for the Human-Centered Input Recognition Algorithms class in Spring 2022 at UF
* The objectives of this graduate course project are two-fold: 
  1. to examine the performance of the $P recognizer on a new dataset called the Zhuyin Multistroke Gestures (ZMG) dataset and 
  2. to investigate whether familiarity with the Zhuyin symbols may affect the recognition accuracy.
* More details cab be found in the [final report](./HCIRA-Proj2-report-yupengchen.pdf)
* The [hcira](./hcira/) folder includes the following relevant materials
  * applications: executive files
  * dataset: Zhuyin Multistroke Gesture
  * source: the source code
  * testing logs: offline user-dependent testing logs
  * analysis: GHoST heatmaps and feature data
  * demo slides

## Applications

* In the ```applications``` folder, there are 4 executive files.
  * For all the executive files to run successfully, the ```jre8``` folder is required. It should be placed in the same folder as the executable file.
  * ```canvas-data-collection.exe```
    * This executive file runs the canvas application for data collection.
    * The ```zhuyin.gif``` is required to display the example figure.
      * If the example image does not show up, make sure the file extension is ```.gif``` instead of ```.png```
    * The example figure used in project 1 is replaced with the ```zhuyin.gif``` figure.
    * a folder called ```xml_logs``` will be created after the user submit the getures they draw.
  * ```canvas-live-demo.exe```
    * This executive file runs the canvas application for live demo.
    * The ```live_demo``` folder is required to run the live demo.
      * This folder stores the loaded training templates.
    * The ```zhuyin.gif``` is required to display the example figure.
      * If the example image does not show up, make sure the file extension is ```.gif``` instead of ```.png```
    * The "Submit" button is replaced with the "Recognize" button.
    * The instruction is also modify to display the recognition results in the format: "LABEL (SCORE)"
  * ```user-dependent-testing-mmg.exe```
    * This executive file runs the offline user-dependent testing on the MMG dataset.
    * The ```mmg``` folder is required to run the testing
    * A log file named "user-dependent-testing-log.csv" will be created after the testing is started.
  * ```user-dependent-testing-zhuyin.exe```
    * This executive file runs the offline user-dependent testing on the Zhuyin Multistroke dataset.
    * The ```zhuyin``` folder is required to run the testing.
    * A log file named "user-dependent-testing-log.csv" will be created after the testing is started.

## Dataset

* A new dataset called ```Zhuyin Multistroke Gesture``` is collected.
  * Katarina and I collected the data from a total of 12 people 
    * 6 of them, with the PID from 001 to 006, are people who are familiar with the Zhuyin symbols.
    * 6 of them, with the PID other than 001 to 006, are people who are unfamiliar with the Zhuyin symbols.
* The ```informed-consent``` folder contains the signed informed consent forms.

## Source

* The ```Source``` folder contains the source code for building the 4 executive files mentioned above.
  * The 4 executive files share the same code structure, but certain parts of the source code have to be commented out for building different executive files.
  * To build executives files that can run on the Windows operating system, the path name has to be changed accordingly.
    * from '\' to '//'
* Parts that are different from the source code for project 1 include:
  * $P recognizer
    * The ```PDollarRecognizer``` class is added to replace the ```OneDollarRecognizer``` class.
  * Point cloud
    * The ```PointCloud``` class is added to replace the ```Gesture``` class, with the ```StrokeID``` attribute added to the class.
  * Live demo implementation
    * Previously, the live demo part for project 1 was implemented in JavaScript. For project 2, the live demo part is integrated into the ```GesturePanel``` class.
  * Slight different data format
    * The MMG dataset has a data format that is slightly different from the dataset we used for project 1, so I have to make changes in the ```Utils``` class accordingly.

## User-dependent testing logs

* The same user-dependent test was run on two different datasets
  * The MMG dataset
    * This testing is to confirm that the $P recognizer I implemented can produce similar results to the ones published in the $P paper.
    * The results include 288,000 individual recognitions (20 users).
    * The overall accuracy is 99.27 (similar to the one in the $P paper).
  * The Zhuyin Multistroke Gesture dataset
    * This testing is to see the performance of the $P recognizer on a new dataset.
    * The results include 172,800 individual recognitions (12 users).
    * The overall accuracy is 87.50.

## Analysis - GHoST

* This folder includes the heatmap images and the feature data for the following data:
  * The entire Zhuyin Multistroke Gesture dataset
  * The data from participants who are familiar with the Zhuyin symbols
  * The data from participants who are unfamiliar with the Zhuyin symbols

## Deployment Instructions

### For Windows

* the file path should be using the backslash "\\"

### For MacOS/Linux

* the file path should be using the forward slash "/"

### Compile the code and create the executable

* The following commands are support on macOS/Linux operating system
* Compile the code using javac
  * Change directory to "project1-part3/"
    * run: ```cd part4/```
  * Compile using the makefile
    * run: ```make```
  * Clean the compiled files
    * run: ```make clean```
* Creating a JAR File
  * Need to specify the name of the output JAR file
    * run: ```jar cvfm FILE_NAME.jar manifest dollar/*.class```
* Run the JAR File
  * run: ```java -jar FILE_NAME.jar```
* Wrapping the Java applications
  * [Lauch4j](http://launch4j.sourceforge.net/index.html)
    * a cross-platform tool for wrapping Java applications distributed as JARs in lightweight Windows native executables
  * Minimum JRE (Java Runtime Environment) version required
    * JRE 8 (version string: "1.8.0")
