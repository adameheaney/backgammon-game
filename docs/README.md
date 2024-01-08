# READ ME

## How to run
- Download the files <br>
- Compile the code using """javac -d bin *.java""" once you are in the """src""" folder <br>
- After changing directory to the bin folder, run the code by using """java src.BackgammonRunner""" <br>

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
This is my latest project started in September 2023. I hope to make a fully functioning backgammon game that plays in the terminal. I will then create a GUI to make it more user-friendly. Afterwards, I hope to create an AI for backgammon!
