#!/bin/bash

# Renders assets by calling blender many times.

DICEFILE="assetssrc/dice.blend"
OUTDIR="renders/"

function ensureBlender {
	if [[ ! -f "${BLENDER}" ]]; then
		if [[ ! -f "third_party/blender-2.79-linux-glibc219-x86_64.tar.bz2" ]]; then
			echo "Downloading Blender 2.79"
			mkdir -p third_party
			curl --progress-bar \
				https://download.blender.org/release/Blender2.79/blender-2.79-linux-glibc219-x86_64.tar.bz2 \
				--output third_party/blender-2.79-linux-glibc219-x86_64.tar.bz2.download \
				&& mv third_party/blender-2.79-linux-glibc219-x86_64.tar.bz2.download \
					third_party/blender-2.79-linux-glibc219-x86_64.tar.bz2
		fi
		echo "Extract blender archive"
		tar --directory=third_party/ \
			-xjf third_party/blender-2.79-linux-glibc219-x86_64.tar.bz2 
	fi
}

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

ensureBlender || exit 1

renderDie "d6" "6"
renderDie "d2" "2"
renderDie "d4" "4"
renderDie "d8" "8"
renderDie "d10" "10"
renderDie "d10x10" "10"
renderDie "d12" "12"
renderDie "d20" "20"
renderDie "d30" "30"

renderScene "Icon" "iTunesArtwork"
renderScene "IconAndroid" "ic_launcher"
renderScene "Logo" "logo"
renderScene "PromoGraphic" "featuredGraphic"
renderScene "WoodBG" "background"

