
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
~ Mixed Multistroke Gestures (MMG) Dataset ~
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Lisa Anthony, Ph.D.
UMBC
Information Systems Department
1000 Hilltop Circle
Baltimore, MD 21250
lanthony@umbc.edu

Jacob O. Wobbrock, Ph.D.
The Information School
University of Washington
Mary Gates Hall, Box 352840
Seattle, WA 98195-2840
wobbrock@u.washington.edu


~~~~~~~~~~~~~~~~
~ Description ~
~~~~~~~~~~~~~~~~

This is the Mixed Multistroke Gestures (MMG) dataset. It contains samples from 20 people who entered each of 16 gesture types 10 times, using either their finger or a stylus on a Tablet PC, at three different speeds (slow, medium, fast), for a total of 9600 samples. The samples are stored in the $N Recognizer's data format, and each person's samples are separated into user-speed sub-folders. 

This is an anonymized, open-source dataset publicly available for use in gesture recognition or gesture interaction work.


~~~~~~~~~~~~
~ Citation ~
~~~~~~~~~~~~

If you use the MMG dataset in your own work, please cite us:

	Anthony, L. and Wobbrock, J.O. 2012. $N-Protractor: A Fast and Accurate Multistroke Recognizer. Proceedings of Graphics Interface (GI’2012), Toronto, Canada, p.117-120.


~~~~~~~~~~~~~~~~~~~
~ Data Collection ~
~~~~~~~~~~~~~~~~~~~

Data was collected on a Windows Tablet PC (Fujitsu Lifebook T900) from 20 unique users (13 males and 7 females, ages 18 to 33). The sixteen gesture types they entered are the following: {arrowhead, asterisk, D, exclamation point, five-pointed star, H, half-note, I, line, N, null symbol, P, pitchfork, six-pointed star, T, X}. To see a visual representation of what each symbol looked like, refer to the $N website: http://depts.washington.edu/aimgroup/proj/dollar/ndollar.html

Half of the users entered the samples using their finger ("finger"), and half used a digital stylus ("stylus").

Every user entered 10 samples of each gesture type at 3 speeds: slow, medium, and fast. For slow gestures, they were asked to "be as accurate as possible." For medium gestures, they were asked to "balance speed and accuracy." For fast gestures, they were asked to "go as fast as they can."


~~~~~~~~~~~~~~~
~ Data Format ~
~~~~~~~~~~~~~~~

All of the data is presented in 60 folders (20 users x 3 speeds). Each user's data is in 3 folders:
<username>-<entry_method>-SLOW
<username>-<entry_method>-MEDIUM
<username>-<entry_method>-FAST

Usernames were assigned randomly, so they are not consecutive.

Each folder contains the 10 samples by that user at that speed for all 16 gestures (160 gestures in each folder). Gesture files are in $N format, named by the following convention:
<username>-<entry_method>-<entry_speed>-<gesture_type>-<sample#>.xml

The XML file format is fairly self-explanatory. The header contains the total number of points in the gesture, the entry speed, the entry method ("InputType"), the username ("Subject"), and the gesture sample name ("Name"). Each stroke (pen-down to pen-up) has an index indicating in which temporal order it was drawn.

The strokes are made up of points, which have the X-coordinate, the Y-coordinate, timestamp (milliseconds since epoch), and pressure information (when available). The (x,y) coordinate system is in pixels with the upper-left corner of the gesture collection interface given as (0,0). X increases to the right and Y increases down.


~~~~~~~~~~
~ Tools ~
~~~~~~~~~~

You may find the parsing and recognizing tools available in the $N C# application useful. Refer to the $N website: http://depts.washington.edu/aimgroup/proj/dollar/ndollar.html


(c) 2012 Lisa Anthony and Jacob O. Wobbrock, last revised: 10/12/2012

--end