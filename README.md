# Slimes vs Chickens

# @author Bean Baumstark, GitHub: bean-b
# @author Miriam Kleit, GitHub: Mkleit
# @author Aahanaa Tibrewal, GitHub: Atibrewa

This is a tower-defence style game made with the kilt-graphics package. The goal is to kill the enemy chickens before they can damage our lives by strategically placing slime towers to shoot them down.
There is a monetary system where you start with a certain amount of money which you can then use to buy and sell towers. You can get additional money for every chicken killed based of the strength and difficulty of the enemy. There are 10 rounds of gameplay and you lose if enough chickens make it to the end, bringing your lives down to zero. You win if you make it to the end of round ten without running out of lives. There are 3 difficulty levels - easy, normal, hard - with different amount of lives and money. There are 5 types of slimes with different shooting abilities, for example, single shot, multiple shots in all directions, etc. and 6 types of enemy chickens including the MEGA chicken and an armoured chicken, all with different attributes.

To run the game, you just need to run gamehandler, select a difficulty level, and then play!

This version is somewhat simplified from our original plan, though not by very much. We originally planned to have multiple maps with different trackways, which we ended up not doing. We also originally planned to have tower upgrades where you could spend money to increase the attack or speed of towers you had already placed. However, this was too much to complete in the given timeframe. However, we added some things such as the slime/tower descriptions showing up when you click on the slime images in the user interface, armoured chickens, a tower that attacks the enemy furthest from it, and projectiles.

There were multiple parts that were difficult. Programming the projectiles was especially hard, as was figuring out the tower attack methods. We also ran into some trouble with making the tower descriptions appear/change properly in the user interface without causing lag, though we eventually figured it out.


