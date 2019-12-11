#!/bin/bash

babel src --minified -d dist --verbose $1;
cp package.json dist