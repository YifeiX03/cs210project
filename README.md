[//]: # (# My Personal Project)

[//]: # ()
[//]: # (## A subtitle)

[//]: # ()
[//]: # (A *bulleted* list:)

[//]: # (- item 1)

[//]: # (- item 2)

[//]: # (- item 3)

[//]: # ()
[//]: # (An example of text with **bold** and *italic* fonts.  )

# Project Plan
## Inventory manager for a video game

I'm planning to create an inventory system that allows the storage and retrieval of hypothetical video game items.
I plan to use it as a way to see how inventory systems are designed.
This project is of interest to me because I want to create games, and in most games an inventory plays a vital role.

## User Stories

*What the inventory should allow:*
- As a user, I want to be able to add and take items from it
- As a user, I want to be able to inspect items: look at names, descriptions, and values
- As a user, I want the inventory to display the names of all items and amount of individual items
- As a user, I want to be able to select multiple items and see their collective value
- As a user, I want to be able to save my items made and inventory to file
- As a user, I want to be able to load my items made and inventory from file
- As a user, I want the program to ask me if I want to save before exiting

# Instructions for Grader

- You can generate the first required event by clicking add or remove,
  this will bring you to a menu allowing you to choose an item and the amount you want to add or remove
- You can generate the second required event by clicking inspect,
  this will bring you to a menu allowing you to choose an item in your inventory to inspect
- You can locate my visual component by starting the application, there is a capybara
- You can save the state of my application by pressing the save button
- You can reload the state of my application by pressing the load button

# Phase 4: Task 2
Tue Aug 09 11:27:21 PDT 2022 \
5 x Awesome Possum added to inventory \
Tue Aug 09 11:27:21 PDT 2022 \
5 x Awesome Possum added to inventory \
Tue Aug 09 11:27:26 PDT 2022 \
10 x wow added to inventory \
Tue Aug 09 11:27:30 PDT 2022 \
20 x MR KRABS added to inventory \
Tue Aug 09 11:27:35 PDT 2022 \
3 x wow removed from inventory 

Note: Inspecting items do not add anything to event log as inspecting is implemented entirely in the GUI,
and in accordance to the instructions no logEvent calls can be made from the ui package

# Phase 4: Task 3
I think that there is low cohesion in my code, and I believe that if I split the ui and gui parts of the code into
multiple classes - for example: one class handles adding, another one handles removing,
and another one handles inspecting - that would have made the code easier to understand. \
Additionally, the code for adding, removing, and inspecting something from the inventory are similar, with all of them
involving looping through the slots to find an item with a name equal to the input string. If I had more time, I
would've made a helper function that reduced the amount of repeated code in those 3 functions. \
There is also no exceptions in the code, so users can input "illegal" values like negative amounts, so I would also add
exception handling for those cases.