# Campus Map

Authors: [Xiaxi Shen](https://github.com/xshen053)

## Project Description

- Designing and developing a task scheduler is interesting to us because most of us use some task scheduler software nowadays, such as Microsoft Todo. Therefore, we are curious about the mechanism of how to create a task scheduler. What’s more, the function of the present task scheduler is limited. So if we want to add some new function or features, we can only do them ourselves. Realizing such a task scheduler can not only help us solidify what we learned in CS100, but can also help us improve our ability of time management.

- The goal of this project is to build a full-stack web application, which is, a campus map! I learned a lot through the process.

- The languages/tools/technologies I used.

  - [Java](https://www.java.com/en/) - I primarily used java to finish the backend.

  - [Javascript](https://www.javascript.com/) - I used js/ts to build frontend.

  - [React](https://cmake.org/) - I used react to help build frontend.

  - [Spark Java](https://sparkjava.com/) - I used spark java as a micro framework to communicate with frontend.

  - [IntelliJ IDEA](https://www.jetbrains.com/idea/) - I used IntelliJ IDEA as Integrated development environment.

  - [Gitlab](https://about.gitlab.com/) - I used gitlab as version control system

  - [Gradle](https://gradle.org/) - I used gradle as the build automation tool.

- The input of my project would be two different buildings of Uw Campus.
- The output of my project would be the shortest path visualized in the map between these two buildings.

## Software Architecture

![avatar](https://github.com/xshen053/CampusMap/blob/main/image/overview-diagram.png?raw=true)

## Project Demo

![avatar](https://github.com/xshen053/CampusMap/blob/main/image/demo1.jpeg?raw=true)

# Installation/Usage

Instructions on installing and running our application:
In order to use our program, you would first clone our project repository (git clone https://github.com/cs100/final-project-xshen053-hyu146-jfern025-vvino005.git) into your local machine and change into its directory.
Next you would compile the application by running the command "g++ main.cpp -o main" in your terminal followed by "./main.exe" to execute it.

# Testing

We tested our project utilizing gtest for c++. We created a task folder that holds all of the seperate unit tests for each class and tested expected user inputs and invalid user inputs. The have a seperate branch to test the strategy pattern and we made mock test files to make sure that we implemented the functions correctly. We also tested mock main.cpp cases manually where we call the menu in a main.cpp, tests its actions and its functionality, and finally carry out those functions that implement both the composite pattern and strategy pattern.
