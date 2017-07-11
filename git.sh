#!/bin/bash
echo "----Begin----"
git pull origin master
git add .
read -p "enter commit info:" info
git commit -m "$info"
echo $info
git push origin master
echo "----End----"