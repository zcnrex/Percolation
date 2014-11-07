Percolation
===========

====Abstract====

The program simulates the process of percolation, the movement and filtering of fluids through porous materials. The program utilizes Monte Carlo method to increase the number of pores overtime, and finally creates a path that connects top and bottom rows. After the path appears, the fluid will percolate. 

After doing the simulation numerous times, we would find an average percentage of pores needed for percolation. This program does not try to show the result of that percentage, but to visually show the whole process of one simulation.



====Algorithms====

The program implements Union-Find data type, the material covered in the first week of Algorithm PartI by Princeton University in Cousera.org.

The program adapts Quick-Union algorithm with improvement of Path Compression to implement Union-Find. However, it is difficult to apply this algorithm to two-dimensional field, as it is hard to determine the root of each points when there is no connection to the top row. As a compromization, the program calls the union method for every spot everytime it opens any new spots. This iteration dramatically increases the runtime from O(nlogn) to O(n^2). When the number of spots is very large(>10000), the simulation would take a long time. Upon estimation, simulation of 62500 spots would take half an hour. 



====Summary====

The Quick-Union algorithm is easy and clear in theory, but when put into application, it requires many modifications.
