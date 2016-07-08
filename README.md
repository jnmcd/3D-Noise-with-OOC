# 3D Noise with OOC
3 dimensional fractal noise created using a new algorithm called Octohedrons, Octohedrons, and Cubes 

When I created this algorithm, I had terrain and cloud generation as its intended use, but the possibilities are endless. 

A window class is provided with a sample use of this algorithm.

Here's the algorithm:

1. Start with a cube with equal height, width, and length. All of these should be of the form 2^n + 1.

2. Randomly give the corners values.

3. Make the cube a subcube.

4. While the size of subcubes is greater than equal to 3:

    5. Average the corners of the subcube and offset by a random number proportional to the cube's size. This is the value of the middle of the cube.

    6. For each face, average the corners, the center of the cube, and the center of the subcube in the other direction (if available) and offset by a random number proportional to the cube's size. This is the value of the center of the face.

    7. For each edge, average the endpoints and the center of all sides adjacent to the edge. The sides don't have to be in the subcube. Then offset by a random number proportional to the cube's size. This is the value of the center of the edge.

    8. Divide the subcube in to 8 equal sized subcubes and repeat

This algorithm is based off the commonly known Diamonds and Squares Algorithm.



