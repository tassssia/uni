cmake_minimum_required(VERSION 3.8)

project(lab1)

set(CMAKE_CXX_STANDART 17)
set(CMAKE_CXX_STANDART_REQUIRED True)

add_executable(${PROJECT_NAME} "Graph/Graph.h" "Graph/Graph.cpp" "Figures/Figures.h" "Figures/Figures.cpp" lab1.cpp)