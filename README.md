# Autism Bukkit Server Plugin

BYU CS IR Lab - Minecraft project for ScenicView Academy in Provo, UT.

## Installation

Download [spigot-1.12.2.jar](https://drive.google.com/file/d/1t82nWSSzr0O4EmujWyXA6LTFcvdTt-8v/view?usp=sharing).

Add it as a library to the project.

To do this in IntelliJ:

`File` -> `Project Structure` -> `Libraries` -> *`+`* -> `Java` -> Select the `.jar` file.

## Build JAR

We need to build this plugin's JAR file into order to get its functionality into Minecraft.

`Build` > `Build Artifacts...` > `Autism:jar` > `Build`

## Run

To test changes, you can download [the latest server](https://drive.google.com/file/d/1wjeAv2i-atwKnjt5EsTkbl2AHqK3hYle/view?usp=sharing).

Copy the build artifact (called `Autism.jar` by default) from the step above to `<server_folder>/plugins/`.

Start the server with `.\start.bat` from the command line (`cmd.exe`).

You can join your locally running server in Minecraft by:

Multiplayer -> Add Server -> Server Name: _AnythingYouWant_ -> Server IP: `localhost` -> Done -> Join Server

Your changes to `Autism` should be reflected in your local copy of the server.
