# Amtrak Travel Planner
## Introduction
Welcome to the Amtrak Travel Planner! I created this software for Purdue University's CS 18000 Honors Section's Self 
Study Project. This program allows a user to view currently running and scheduled Amtrak lines through their desired
stations. It also provides valuable timetables (database source updates every 20 minutes) for the different
routes presented to the user. A full feature list is available [below](#features).

## Motivation
1. I love traveling, transit, and just about everything to do with those subjects (maps, timetables, etc.).
2. Over the years, I've realized that there's not a great way to view multiple different Amtrak routes and their current
statuses (Amtrak's proprietary service *exists*, but it's not my favorite).
So, this assignment gave me a great opportunity to try and change that!
3. After thinking of a rough project idea, I stumbled upon 
[Greg Walker's Amtrak API](https://mgwalker.github.io/amtrak-api/). Without this API, my project would
not have been possible.

## Features
- View which Amtrak trains are available, if any, between two cities in the itinerary
  - If there's no route available, the GUI will make that clear
  - An itinerary supports as few as 2 stations or as many as all 558 stations in the database!
- Explore train statuses relative to stations
  - Departed, Arrived, Enroute, or Scheduled
- Study the duration of an Amtrak ride between two cities
- Check for any delays on routes
- View the total travel time of all routes in your trip (all routes determined from your station selections)
- Print searched stations or search results to a text file ("Search File")
- Read a search from an existing Search File
<img src="/Amtrak%20Travel%20Planner%20Welcome%20Screen.png" alt="The Amtrak Travel Planner's Welcome Screen" width="50%" title="Amtrak Travel Planner Welcome Screen">
<img src="/Amtrak%20Travel%20Planner%20Create%20a%20New%20Amtrak%20Trip.png" alt="The Amtrak Travel Planner's station selection functionality" width="50%" title="Amtrak Travel Planner Create a New Amtrak Trip">
<img src="/Amtrak%20Travel%20Planner%20Sample%20Trip.png" alt="A demonstration of the Amtrak Travel Planner's trip generation functionality with 5 stations as input" width="50%" title="Amtrak Travel Planner Sample Trip">

## How to Run
2 Options:
1. Download and run the [.jar executable](AmtrakTravelPlanner-v.1.0.0.jar)
2. Clone this project and run [PlannerDriver.java](src/PlannerDriver.java)

## Methods & Technologies
- Complex GUIs with Action Listeners in Java Swing
  - Composed with IntelliJ Swing UI Designer
- Custom objects
- Accessors and mutators
- HttpURLConnection
- JSON Parsing
- PrintWriter
- BufferedReader
- Time Package
## Credits
- Amtrak Travel Planner v.1.0.0
- Created by Ayush Shukla Arora
- Purdue University
- April 11, 2024-April 22, 2024
- A special thank you to Daren Fadolalkarim (Lab Graduate Teaching Assistant, L-19)
for her support throughout this project!
- [Data from Greg Walker's Amtrak API](https://mgwalker.github.io/amtrak-api/)
- [JSON Data Collection Tutorial](https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751/)
