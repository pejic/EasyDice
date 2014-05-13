
     _/_/_/_/                                    _/_/_/    _/
    _/          _/_/_/    _/_/_/  _/    _/      _/    _/        _/_/_/    _/_/
   _/_/_/    _/    _/  _/_/      _/    _/      _/    _/  _/  _/        _/_/_/_/
  _/        _/    _/      _/_/  _/    _/      _/    _/  _/  _/        _/
 _/_/_/_/    _/_/_/  _/_/_/      _/_/_/      _/_/_/    _/    _/_/_/    _/_/_/
                                    _/
                               _/_/_/

# Easy Dice

## Introduction

Use Easy Dice to roll dice of your choosing.

* Variety of die types: D4, D6, D8, D10, D10x10, D12, D20
* Choose which dice to roll together. For example a D4 with two D8.
* Re-rolling of dice. Tap dice to select which ones will roll and which ones
  will stay.
* Adding up. Sums all the die face values.
* Die sets: keep die sets for later. E.g. different attacks in an RPG game.

## Building

The build process has a number of steps due to assets being rendered.  However
assets need only be rendered once, and rescaled per app.

To render assets using blender run the render script.
  
     $ ./render.sh

This will undoubtedly take some time.  You will find many new files in the
render/ directory.  These are the high resolution renders that will be rescaled
to get the assets that apps will actually incorporate.  To build the android
assets run mkassets.sh from the androidsrc directory.

     $ cd androidsrc
     $ ./mkassets.sh

At this point you can build the Android project in the usual ways.

## Copyright & Licence

Copyright (c) Slobodan Pejic 2011, 2012, 2013, 2014

This software is licenced under the GNU General Public Licence.  See
[LICENCE.txt](LICENCE.txt) for more details.  The artwork falls under the same
licence.

Please note that there some assets (since removed) in past versions that are
not licensed under the GPL and may not be reproduced.  In particular the
following files Default.png (PureEgo logo), Default@2x.png (PureEgo logo), and
Default-568h@2x.png (PureEgo logo) are not under the GPLv3.  These offending
files have been removed since commit ff706e4dad45b2210fb3b2969fd94cd47d24fcdc.


