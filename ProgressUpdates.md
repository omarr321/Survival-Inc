# Progress Updates

## Week 1 (March 3 - March 10)
### What did your team do last week?
* We went over the general layout of the games features. Our goal is to figure out the requirements of the game mechanics. This would in turn, plan out the UML of our class structure for a Java Application.

* The GitHub repository was created along with the branches. To not complicate the merging process, it was best to separate the branches by front-end and back-end. We would also branch out our own branches to add features that may or may not work.

### What will you do this week?
* We want to make a UML that uses most of the concepts we've learned in CS2 but works with our game and can make the integration process smooth.

* Will need to touch up on JavaFX concepts for Omar's front-end. We decided to code all graphics instead of using a scene builder.

### Any blocking issues/challenges need to be addressed?
* JavaFX needs to be fixed on Wyatt's computer in order to debug and test the game.


## Week 2 (March 10 - March 17)
### What did your team do last week?
* Planned out the UML design of our project. It allowed us to understand how the game mechanics would work and what needed to be done to ensure our classes work with JavaFX (Front-end).

* The idea is to follow an inheritance model for physical entities in the game. This can include anything from the player to natural resources. For example, most physical objects in game inherit the Entity class. This will contain variables needed for rendering and game mechanics such as position and color. The player can also inherit this as it's also a physical thing that can be rendered.

### What will you do this week?

* We ended up making the map structure. But decided to set position based on indexes of a 2d map array and store only color objects that would define a tile. This may pose a challenge as it's not a good practice to use indexes as changeable values.

* The UI works well so far, we were able to generate maps and move the player in bounds to the grid pane. We even set up a procedural generating algorithm so tiles are not truly random and instead are grouped naturally into chunks.

### Any blocking issues/challenges need to be addressed?
* We need to utilize classes more. We have yet to make a Player class and all controls are defined as handlers in the UI class. We need to figure out how to separate the player as a class and implement a relation between player and JavaFX.

* Team communication can be difficult as we try to keep track of the code. We are constantly working to make sure merging our code is a smooth process.


## Week 3 (March 17 - March 24)
### What did your team do last week?
* We made a draft of how the game will look. While it was impressive and well polished, it failed to use Object Oriented programming.

* As a result, we scraped the code and decided to stick with the UML. This time, keeping in mind of how using such methods of programming will allow for the game to become scale-able.

### What will you do this week?
* Wyatt will work on the Map and Entity classes in the back-end as they are essential to creating the game environment for physical object handling. With the inheritance model for Entities, a limitless amount of in-game objects can be added.

* Omar will re-write the front-end to call methods from the Map and Entity classes using the UML.

### Any blocking issues/challenges need to be addressed?
* Mostly to get more done this week than last week. We need to stick to the main goals defined in our proposal and to use proper programming habits.


## Week 4 (March 24 - March 31)
### What did your team do last week?
* We completed the core methods of the Map and Entity classes. We created a binding system that relates entities to maps. Therefore, entities only belong to one map at a time. But maps can return lists of entities.

* We utilized lambda expressions to allow for the filtering of entities by the results of getter methods.

* Because terrain was static and was in a fixed position. We treated terrain as a separate class from entity. They are an enumerated type called Tiles and contain properties such as color.

* An API was documented in the classes as a way for the front-end developer to call appropriate methods to run a workable game.

### What will you do this week?
* We plan to add the in-game entities and define properties, as well as work on the player entity and how it might work with the the front-end.

* The idea is that adding entities is streamline because they have the code in the super-class to exist physically on a map. The use of OOP makes adding content easier later in the development cycle.

### Any blocking issues/challenges need to be addressed?
* We were using GitHub improperly to merge code. While we were using different branches, the back-end branch was derived from the front-end and re-basing was required. We did not know how to re-base which mean we spent a few hours just to fix the way we merge our classes.

* The integration was later then we thought. While both ends had good code, we had yet to combine it into a functional game. We hope to integrate everything by this Friday.


## Week 5 (March 31 - April 7)
### What did your team do last week?
* We added different entities such as stick, tree, lava, and the player. It was really easy since the abstract class takes care of the map relationships.

* The front-end was also re-written and properly renders the maps. We created a world class to implement sub-map viewing.

* More methods were added to Map as requested by front-end developer, such as a method to get the most frequent tile in map.

### What will you do this week?
* Polish up the game to include the rest of the proposed features.

* Include an inventory system for the player. A great approach is to make the inventory a 1x1 map where entities are placed into. Therefore, we can use methods such as getAllEntites() for inventory management.

* A crafting system will also be implemented by Omar. One example of crafting is to turn sticks into wooden planks.

* Prepare for the final presentation. I (Wyatt) want to put everything we've used in CS2 and how we applied it to our project, in the PowerPoint.

### Any blocking issues/challenges need to be addressed?
* Implementation required a lot of explanation and teamwork. It was hard not to write messy code. However, we remembered to document anything that was organized and to scrap out messy code for clean code.
