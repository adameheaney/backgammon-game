# READ ME

## How to run
- Download the files <br>
- Compile the code using ```terminal javac -d bin *.java``` once you are in the ```src``` folder <br>
- After changing directory to the bin folder, run the code by using ```java src.BackgammonRunner``` <br>

## How to play
- There are two teams on the board, B and W. Who starts is completely random. <br>
- When it is your turn to move, input the roll and coordinates as follows: "**roll** **x** **y**", with **roll** being a number you rolled, and **x** and **y** being the coordinate location of the piece you'd like to move. <br>
- "E" stands for the amount of eaten pieces you have, and "I" stands for the amount of pieces you have in home. <br> 
- It's that simple!

## Obscure rules of Backgammon included
There are a few rules to backgammon that aren't as well known that I have included (For all of these rules, the code accounts for if the move is allowed based on these rules.):
1. Cannot roll doubles for the first move, as who goes first is determined by who rolls the higher dice first (this is done automatically)
2. If there is only one viable move that a dice can play, it must be played. This means that if I can't use a roll without first using the other roll on a certain piece, then you must use the other roll on that certain piece.
3. If you can play only one spot with both dice, and then won't be able to use the other dice, you **must** use the higher roll

For an extensive list of all the rules, [here](https://www.bkgm.com/faq/BasicRules.html#what_if_i_can_only_play_one_number_) is a handbook from online

## About the project
#### ***Motivation***: 
- I started this project during my first semester as a sophomore at Brandeis University after my friend began reading into backgammon strategy. I hoped to create a backgammon bot that would be able to beat him despite his extensive effort to get better at the game. The first step of course is to recreate the entire game from scratch, so that's what I did. Why backgammon? Well, I come from an arabic background and grew up in a largely Syrian Jewish community, and therefore backgammon is an incredibly popular boardgame here.
#### ***Principles and what I learned***:
- Going into this project, I wanted to lean into what I learned about object oriented design, and what better way to do that than by making a game from scratch. I created different files for each "object" within the game. There is a **dice** class, a **board** class, a **piece** class, a **team** class, etc. Additionally, I wanted to maintain clean and well-documented code so that I'd be able to tweak and come back to it later on to learn from my past self and see where I went wrong. That is also why this README is very extensive and in-depth. Overall this project was a hands on experience with learning Object-Oriented programming that I just didn't get from the assignments in class and I am very glad I took this project on. 
#### ***Bug-testing***: 
- I made a few test scenarios to try to see if the edge cases work, but my testing isn't terribly meticulous, so there may be a few unknown bugs with how the game's logic works.

