# READ ME

## Getting Started
- Download the files
- Open your terminal
- Change the directory into the root folder
- Compile the code using ```javac -d bin src/*.java src/bot/*.java``` 
- After changing the directory to the bin folder, run the code using ```java src.BackgammonRunner```

Alternatively, you can use an IDE of your choice and run the BackgammonRunner class

- Once you run the code, it will prompt you, asking which type of game you want to play. Once you enter it, the game should begin!

## How to play a player vs. player game
- There are two teams on the board, B and W. Who starts is completely random. 
- When it is your turn to move, input the roll and coordinates as follows: "**roll** **x** **y**", with **roll** being a number you rolled, and **x** and **y** being the coordinate location of the piece you'd like to move. 
- "E" stands for the amount of eaten pieces you have, and "I" stands for the amount of pieces you have at home. 
- It's that simple!

## How to play a player vs. bot game
- The player is always team W (white) and the bot will be team B (black and also conveniently the b in Bot).
- When it is your turn, it is the same instructions as your turn in a player vs. player game.
- When it is the bot's turn, you just have to let the bot do its move, which happens instantly once it's its turn.
- The same rules as a player vs. player game applies

## How to play a bot vs. bot game
- When you type "BB" upon running the program, you will be prompted to choose your two bots. Enter the name of the bot of your choice's class. (if you made your own bot make sure to add it to the map; see [How to add your own bots!](#How-to-add-your-own-bots!) for more info)
- The game will happen instantly and be saved to the game.txt file. If you want it to be printed to the console, then comment out the line that calls printToFile()

## How to add your own bots!
- If you want to create and add your bots, there is a provided class BackgammonBot that you can extend with your bot class.
- The RandomBot is a very simple example of how to make your bot class.
- Once you create your bot, make sure to add it to the HashMap so that you can use it.

## Obscure rules of Backgammon included
There are a few backgammon rules that aren't as well known that I have included (For all of these rules, the code accounts for if the move is allowed based on these rules.):
1. Cannot roll doubles for the first move, as who goes first is determined by who rolls the higher dice first (this is done automatically)
2. If there is only one viable move that a dice can play, it must be played. This means that if I can't use a roll without first using the other roll on a certain piece, you must use the other one.
3. If you can play only one spot with both dice and then won't be able to use the other dice, you **must** use the higher roll

For an extensive list of all the rules, [here](https://www.bkgm.com/faq/BasicRules.html#what_if_i_can_only_play_one_number_) is a handbook from online

## About the project
#### ***Motivation***: 
- I started this project during my first semester as a sophomore at Brandeis University after my friend began reading into backgammon strategy. I hoped to create a backgammon bot that would be able to beat him despite his extensive effort to get better at the game. The first step of course is to recreate the entire game from scratch, so that's what I did. Why backgammon? Well, I come from an arabic background and grew up in a largely Syrian Jewish community, and therefore backgammon is an incredibly popular board game here.
#### ***Principles and what I learned***:
- Going into this project, I wanted to lean into what I learned about object-oriented design, and what better way to do that than by making a game from scratch? I created different files for each "object" within the game. There is a **dice** class, a **board** class, a **piece** class, a **team** class, etc. Additionally, I wanted to maintain clean and well-documented code so that I'd be able to tweak and come back to it later on to learn from my past self and see where I went wrong. That is also why this README is very extensive and in-depth. Overall this project was a hands-on experience with learning Object-Oriented programming that I just didn't get from the class assignments and I am very glad I took this project on. 
#### ***Bug-testing***: 
- I made a few test scenarios to try to see if the edge cases work, but my testing isn't meticulous, so there may be a few unknown bugs with how the game's logic works.

