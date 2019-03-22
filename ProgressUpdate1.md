### What did your team do last week?
* Planned out the UML design of our project. It allowed us to understand how the game mechanics would work and what needed to be done to ensure our classes work with JavaFX (Front-end).
* The idea is to follow an inheritance model for physical entities in the game. This can include anything from the player to natural resources. For example, most physical objects in game inherit the **Entity** class. This will contain variables needed for rendering and game mechanics such as position and color. The player can also inherit this as it's also a physical thing that can be rendered.

### What will you do this week?
* We ended up making the map structure. But decided to set position based on indexes of a 2d map array and store only color objects that would define a tile. This may pose a challange as it's not a good practice to use indexes as changable values.
* The UI works well so far, we were able to generate maps and move the player in bounds to the grid pane. We even set up a procedural generating algorithm so tiles are not truely random and instead are grouped naturaly into chunks.

### Any blocking issues/challenges need to be addressed?
* We need to utilise classes more. We have yet to make a Player class and all controls are defined as handlers in the UI class. We need to figure out how to seperate the player as a class and implement a relation between player and JavaFX
* Team communication can be difficult as we try to keep track of the code. We are constantly working to make sure merging our code is a smooth process.
