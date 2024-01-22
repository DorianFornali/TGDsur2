# TGD/2

Universitary project in the "Génie Logiciel" course of the first year of Computer science master in Université Cote d'Azur.

The goal was to develop a small video game of tower-defense genre by using well designed code and practices.

## Pre-requisites

You need a JVM installed with java 20 or higher. <br>
You can find JDKs here: https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html

## How to install

<br>
<b>First method:</b>

Download the .jar directly from here, to do so click on the file and then on "raw", your browser should download it.
<br><b>You can also download the jar file from my OneDrive: <br> https://unice-my.sharepoint.com/:u:/g/personal/dorian_fornali_etu_unice_fr/EVlXiS28ZrNPo2aVeoqOZ1IBUO5pfKiGo0PZa6QwJPSYVA </b>
<br>Then, from powershell if you are on windows or your linux terminal:

```bash
java -jar TGDsur2.jar
```

If it doesn't work, please verify that you have Java 20+ installed. 

```bash
java --version
```
<b>Second method:</b>

If you can't start the jar properly, clone the repository locally and execute Main.java from intelliJ IDEA directly. IntelliJ is required as the .iml file handles the dependency injection.

# Known bugs

Some testing revealed that somehow, a stage could never end (with no enemies spawning) when on windows 11 with jdk 20.0.2. If you are using this config and meet this bug, try to downgrade your jvm to 20.0.1 (Or you can just press esc and leave the stage when no enemies are spawning anymore ...)

## Developers

- FORNALI Dorian <br>
- PERALDI Laurent

## Credits
All songs are taken from https://www.epidemicsound.com/ and are free of use.<br>
Sprites:
<br>
  - https://fr.123rf.com/profile_saphatthachat <br>
  - https://opengameart.org/content/pixel-robot <br>
  - https://elthen.itch.io/
