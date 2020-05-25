#D&D 5e Dungeon Master Tool

##Version 0.2 -- Home and Encounter Tools

In order to run the program, make sure Java is installed (this program was built primarily with JDK 10.0.2), redirect to the correct directory (\src), compile DMTool.java, \
`javac DMTool.java`, then run it, `java DMTool`

Some problems that might appear when compiling or running are as follows:

* This code was written and compiled on my machine using JDK 10.0.2, for use with JavaFX.  It may not compile if done with later versions

* The code was written in the JetBeans IntelliJ IDE.  Pathing issues came up a little bit during development (full fixes will be added in version 0.9). To alleviate this problem for now, there has been a constant defined in the DMTool.java and MonsterDirectory.java files, which can be modified manually to direct the file streams to the correct directories

* Some monsters (which have not been identified yet) throw some problems with startup.  These will be fixed in minor bug fixes in the future

Currently, only the Home Tab and the Encounter Tab are usable. The home tab can be used to find and display monsters quickly, and the encounter tab can be used to design encounters and create random encounters as well.

####Versions:

0.3: Homebrew Tab Content Added 

0.4: Settings Tab Content Added

0.5: Support for Templates added, additional monster attack capabilities added, additional encounter running tools added

0.6: All monsters added (For legal reasons, this may be reduced to only contain the monsters from the Basic Rules)

0.7: New random encounter algorithm designed

0.8: CSS file finalized, improved aesthetics

0.9: Bug fixes (file path names, etc.)

1.0: Final code rewrite & consolidation, .exe/.dmg file created

#####Additional versions may be made in the future, but are not planned yet
