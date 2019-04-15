### Presentation Sprint 1 (April 10)

**Describe how well you did completing your planned features this Sprint and what helped or impeded your progress**
* We couldn’t complete our goal for Sprint 1, which was to have a working, playable game. A major reason for this was not being specific enough in the planning stage. We thought we talked through class interactions and which classes do what, but it was too general, and we spent a lot of time during Sprint 1 going over the specifics of what those should look like. For example, the roles weren’t defined enough for controller and backend, so there was initially confusion as to which would handle the majority of the logic. We eventually decided the controller should. 
* We did complete the following: a basic board layout (front end), created backend classes and basic methods for a functional game, define controller functionality, added tests for the StandardBoard, Bank, and Player classes

**Describe a specific significant event that occurred this Sprint and what your team learned about teamwork/process from this Sprint**
* In the last sprint (planning), we had initially drawn and planned out a design for our game that we thought would be extremely flexible and easy to integrate together. However, during implementation this week, we realized that our initial design was not as thorough as it should have been. For example, we did not explicitly discuss how the controller of our MVC model would work, such as which components it should tie together and how. This resulted in a 3 hour long team discussion on what tasks our controller should be responsible for and how our controller was going to pass things from the the front-end to the back-end and vice versa. We finally came to the conclusion that we would have a controller called Turn for now, which we may later split into several controllers if this one controller has too many responsibilities. This controller will call all the implementations needed for what a “turn” would consist of, such as rolling a dice and displaying player movement on front-end. From this experience, we learned about the importance of communication within the team, as well as the importance of fully planning out every aspect of our project, which includes running through some use cases to make sure that our design is robust. 

**Describe what worked, what did not, and something specific you plan to improve next Sprint**
*  What worked: Splitting up roles so people either worked on front end, back end, controller, or configurations. This allowed more efficient communication within the team so just people working on specific features can talk to the relevant subgroup instead of the whole team, which would get messy.
*  What didn’t work: Not defining/planning exactly the functionality of methods, classes, and interactions in advance. We had to backtrack and spent a lot of time working through these, which wasted time.
*  Specific plan to improve next sprint: Planning more specifically in advance, with an emphasis on logic (especially with the controller) and class interactions. 

**What features you expect to complete during the next Sprint**
* Overall goal: A fully functional, interactive, playable game
* Specific features to implement:
* Make program more flexible by getting information from the data files. This means no magic values, decrease member variables that are specific to only one game (ie. “houses” and “hotels”).
* Make controller functional
* Allow the View to take in user input, defining what it can and cannot do at certain points. For example, you can mortgage a property at anytime but you can only buy a property from the bank when you land on it. 
* Implement exceptions, generally resolve them through pop up messages.
* Create more tests as we code.

