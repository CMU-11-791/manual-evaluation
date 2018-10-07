#!/usr/bin/env bash

for f in `ls src/test/resources/data/4b/*.json` ; do
	name=`basename $f`
	target=src/main/resources/data/$name
	cat $f | jsonpp > $target
	echo "Wrote $target"
done