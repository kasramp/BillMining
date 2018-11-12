# BillMining
Mine your bill with this program to optain optimal price for items your purchase

## Description
BillMining is a Swing program with Sqlite as backend with the purpose of adding company, outlet, item and item categories of your purchased bill to get optimal price for the items you purchase based on the various outlets you perform your purchases. It is able to suggest list of outlets for the particular items which are sold cheaper in comparison with other places (shops). 

The use of this application is not difficult, it just requires some basic configuration for companies, outlets (branches) and items. After that you can start adding your bills and get an analysis report for your bills and get suggestion which items you should purchase from which places. As a result, you can save your cost and lower your grocery or any other expenditure.

This project has not finalized yet, still is under active development and requires more features which will be adding gradually.

## Dependencies
The program works out the box so smoothly and all necessary dependencies have been already added to the project. 
You just need to compile the code and run the executable jar file.

## How to use  
BillMining is cross platform and can be executed in Linux, Windows, Mac OS X flowlessly. You either need to download Eclipse or maven and run the program.

	$ maven clean install
	$ cd target  
	$ java -jar BillMining-0.0.1.jar

## Technical description
The project sturucture is like following,

* /src --> source code
 * Run.java --> for debugging purposes
 * org --> Eclipse related (Ignore it)
 * com/outlet/common --> comman utilies and functions that are used system wides
 * com/outlet/db --> database initialization and connection files
 * com/outlet/gui --> gui related files (Add for add functions [pages], Report for reporting functions [pages], MainPage --> First page).
 * com/outlet/objects --> mapping Sql table to java objects
* /sql --> all sql files (tables) for database (Deprecated, using ORMLite instead)
* /target --> project executable jar file
* /doc --> project documentation
* /bin --> compiled .class files

## Contact
* kasra@madadipouya.com  
	
## License
BillMining is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License version 3
as published by the Free Software Foundation.

BillMining is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.  <http://www.gnu.org/licenses/>

Author(s):

© 2015-2018 Kasra Madadipouya <kasra@madadipouya.com>

