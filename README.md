# StickHero
The game is a replica of a popular game Stick Hero

## Running the code
Run the main class from `App.java`.
- Change the directory to `/sample` before compiling the `pom.xml` file.

## Assumptions
- The cherry on top right while playing the game displays cherries collected in the current game.
- The cherries on top right of the **Game Over** Screen represents the cherries collected in all sessions of the game.
- Restarting the game or going to the Home Screen while the game is paused makes the player lose all the progress, that is the score and cherries of the players will not be saved.
- Restarting the game or going to the Home Screen after the game has ended updates the progress of the player.
- The **Save Button** only saves the statistical progress of the player, that is their score and cherries.
- On loading a saved game, only the statistical progress will be restored.
- Only the last saved game will be restored on loading saved games.
- The player needs a minimum of 5 cherries to revive. After the player has revived, 5 cherries are deducted from total cherries.
- After reviving, only the statistical progress is restored and the player starts the game again from the initial pillar, which is the checkpoint.

## Playing the game
- Length of the stick can be increased by long pressing the keypad/mouse.
- The stick drops on releasing the keypad/mouse.
- The hero flips by a single press.

## Code Implementation

### Design Patterns
- Singleton Design Pattern

   The PauseMenu is a singleton class


- Factory Design Pattern

  The factory design pattern is used to generate rectangles and lines ( Super class : Shape ) in the code.

### Junit 
1) NegativeCherryException 

   Error would be encountered when cherries of the hero are negative 


2) NegativeRotationException 

   Error would be encountered when rotation of the stick is positive, that is anti-clockwise direction.

3. NegativeScoreException 

   Error would be encountered when score of the hero is negative


4. NegativeSpeedException 

   Error would be encountered when speed of the hero is negative

### Programming Practices Used
* Use of **Serialization** to save the progress of the hero. The score and cherries of the hero are being serialized and deserialized respectively. Hero and Stick class implement Serializable interface.
   - gamestate.txt


* Proper use of encapsulation and error handling.


* Use of **Interface** (ScoreBoard) which is implemented by currentGameScore and gameOverScore classes.


* Use of **Inheritance** : Main class is being extended by MainMenu and SideMenu and SideMenu is extended by PauseMenu and gameOverMenu.


* Use of **abstractclass** : SideMenu


* Use of **file reading** : To save cherries and score in different points at game to store the game state.
   - cherry.txt
   - score.txt
   - high_score.txt

### Animations, Graphics and Sound used
- The movement of hero while playing the game
- The **Play** and **Load Saved Games** button change size when the mouse hovers over it.
- A Sound play button has been added to the home screen which plays the sound throughout the game. When it is again pressed, the sound icon changes to cancel sound icon. 
- The sound playing is not continuous so some gaps between the sound are expected.
