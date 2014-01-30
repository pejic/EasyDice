#!/bin/bash

# Renders assets by calling blender many times.

DICEFILE="assetssrc/dice.blend"
OUTDIR="renders/"

function renderDie {
	echo "Render Die $1 $2"
	blender -b "$DICEFILE" \
		-S Dice."$1" \
		-x 1 -o "$OUTDIR""$1"- -F PNG -s 1 -e "$2" -a
}

function renderScene {
	echo "Render Scene $1 $2"
	blender -b "$DICEFILE" \
		-S "$1" \
		-x 0 -o "$OUTDIR""$2".png -f 1 -F PNG
	mv "$OUTDIR""$2".png0001 "$OUTDIR""$2".png
}

renderDie "d6" "6"
renderDie "d4" "4"
renderDie "d8" "8"
renderDie "d10" "10"
renderDie "d10x10" "10"
renderDie "d12" "12"
renderDie "d20" "20"

renderScene "Icon" "iTunesArtwork"
renderScene "IconAndroid" "ic_launcher"
renderScene "Logo" "logo"
renderScene "PromoGraphic" "featuredGraphic"
renderScene "WoodBG" "background"

